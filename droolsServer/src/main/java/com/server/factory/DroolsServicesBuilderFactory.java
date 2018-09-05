package com.server.factory;

import com.server.config.CustomKieBaseConfiguration;
import com.server.exception.KnowLedgeBuilderException;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.io.impl.BaseResource;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/4/18
 * Time: 5:58 PM
 */
@Service
public class DroolsServicesBuilderFactory {

    private static final Logger log = LoggerFactory.getLogger(DroolsServicesBuilderFactory.class);

    @Autowired
    private CustomKieBaseConfiguration customKieBaseConfiguration;

    /**
     * 知识规则的构建器
     *
     * @return
     */
    public KnowledgeBuilder createKnowledgeBuilder() {
        return KnowledgeBuilderFactory.newKnowledgeBuilder();
    }

    /**
     * 添加规则
     * @param knowledgeBuilder
     * @param resources
     * @param type
     */
    public void addRules(KnowledgeBuilder knowledgeBuilder, List<BaseResource> resources,
                                    ResourceType type) {
        resources.forEach(resource -> knowledgeBuilder.add(resource,type));
        if(knowledgeBuilder.hasErrors()){
            KnowledgeBuilderErrors kbuidlerErrors = knowledgeBuilder.getErrors();
            for (Iterator iter = kbuidlerErrors.iterator(); iter.hasNext();) {
                throw new KnowLedgeBuilderException(iter.next().toString());
            }
        }
    }

    /**
     * 创建内部支持库
     * @return
     */
    public InternalKnowledgeBase createInternalKnowledgeBase(){
        KieBaseConfiguration kbc = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        customKieBaseConfiguration.getKieBaseconfiguration().forEach((key,value)->kbc.setProperty(key,value));
        return KnowledgeBaseFactory.newKnowledgeBase(kbc);
    }

    /**
     * 构建基础库
     * @param internalKnowledgeBase
     * @param knowledgeBuilder
     */
    public void buliderBase(InternalKnowledgeBase internalKnowledgeBase,KnowledgeBuilder knowledgeBuilder){
        internalKnowledgeBase.addPackages(knowledgeBuilder.getKnowledgePackages());
    }

    /**
     * 创建有状态session
     * @param internalKnowledgeBase
     * @return
     */
    public KieSession createKieSession(InternalKnowledgeBase internalKnowledgeBase){
        Supplier<KieSession> kieSessionSupplier = internalKnowledgeBase::newKieSession;
        return kieSessionSupplier.get();
    }

    /**
     * 创建无状态session
     * @param internalKnowledgeBase
     * @return
     */
    public StatelessKieSession createStatelessKieSession(InternalKnowledgeBase internalKnowledgeBase){
        Supplier<StatelessKieSession> kieSessionSupplier = internalKnowledgeBase::newStatelessKieSession;
        return kieSessionSupplier.get();
    }

    /**
     * 释放session
     * @param kieSession
     */
    public void disposeKieSession(KieSession kieSession){
        kieSession.dispose();
    }


    /**
     * 销毁Kiession
     * @param kieSession
     */
    public void destroyKiession(KieSession kieSession){
        kieSession.destroy();
    }
}
