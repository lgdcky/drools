package com.server.knowledgebasemanager;

import com.server.MessageCommand.KnowledgeMessage;
import com.server.exception.KnowLedgeBuilderException;
import com.server.factory.DroolsServicesBuilderFactory;
import com.server.ruleManager.RuleLoadManager;
import com.server.utility.KnowledgeBaseLib;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.io.impl.BaseResource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 1:35 PM
 */
@Component
public class KnowLedgeBaseMangerImpl implements KnowLedgeBaseManger {

    @Autowired
    private DroolsServicesBuilderFactory droolsServicesBuilderFactory;

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private KnowledgeBaseLib knowledgeBaseLib;

    /**
     * 根据传入规则创建知识库
     *
     * @param resources         规则
     * @param knowLedgeBaseName 知识库名称
     * @return
     */
    @Override
    public InternalKnowledgeBase createKnowledgeBase(List<BaseResource> resources, String knowLedgeBaseName) {
        KnowledgeBuilder knowledgeBuilder = droolsServicesBuilderFactory.createKnowledgeBuilder();
        InternalKnowledgeBase internalKnowledgeBase = droolsServicesBuilderFactory.createInternalKnowledgeBase();
        droolsServicesBuilderFactory.addRules(knowledgeBuilder, resources, ResourceType.DRL);
        BiConsumer<InternalKnowledgeBase, KnowledgeBuilder> baseBuilder = droolsServicesBuilderFactory::buliderBase;
        baseBuilder.accept(internalKnowledgeBase, knowledgeBuilder);
        return internalKnowledgeBase;
    }

    /**
     * 测试规则
     *
     * @param resources
     */
    @Override
    public KnowledgeMessage testRule(List<BaseResource> resources) {
        KnowledgeBuilder knowledgeBuilder = droolsServicesBuilderFactory.createKnowledgeBuilder();
        InternalKnowledgeBase internalKnowledgeBase = droolsServicesBuilderFactory.createInternalKnowledgeBase();
        KnowledgeMessage knowledgeMessage = new KnowledgeMessage();
        try {
            droolsServicesBuilderFactory.addRules(knowledgeBuilder, resources, ResourceType.DRL);
            knowledgeBuilder.undo();
        }catch (KnowLedgeBuilderException ruleException){
            knowledgeMessage.setMessage(ruleException.toString());
        }finally {
            internalKnowledgeBase = null;
        }
        return knowledgeMessage;
    }

    /**
     * 重载全部知识库
     */
    @Override
    public void reloadRules() {
        knowledgeBaseLib.getAllInternalKnowledgeBase().forEach((name, internalKnowledgeBase) -> {
            internalKnowledgeBase.getKieSessions().forEach(kieSession -> {
                kieSession.destroy();
            });
        });
        knowledgeBaseLib.clearAllInternalKnowledgeBase();
        ruleLoadManager.loadAllRules().forEach((knowledgeBaseName, resources) -> createKnowledgeBase(resources, knowledgeBaseName));
    }

    /**
     * 重载特定知识库规则
     *
     * @param knowLedgeBaseName
     */
    @Override
    public InternalKnowledgeBase reloadRule(String knowLedgeBaseName) {
        knowledgeBaseLib.getInternalKnowledgeBase(knowLedgeBaseName).getKieSessions().forEach(kiesession -> kiesession.destroy());
        knowledgeBaseLib.removeInternalKnowledgeBase(knowLedgeBaseName);
        createKnowledgeBase(ruleLoadManager.loadRule(knowLedgeBaseName), knowLedgeBaseName);
        return knowledgeBaseLib.getInternalKnowledgeBase(knowLedgeBaseName);
    }

    /**
     * 释放session资源
     *
     * @param kieSession
     */
    @Override
    public void disposeKieSession(KieSession kieSession) {
        kieSession.dispose();
    }

    /**
     * 销毁session
     *
     * @param kieSession
     */
    @Override
    public void destoryKieSession(KieSession kieSession) {
        kieSession.destroy();
    }

}
