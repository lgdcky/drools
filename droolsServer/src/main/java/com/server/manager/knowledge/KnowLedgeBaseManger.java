package com.server.manager.knowledge;

import com.server.MessageCommand.KnowledgeMessage;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.io.impl.BaseResource;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 11:05 AM
 */
public interface KnowLedgeBaseManger {

    public InternalKnowledgeBase createKnowledgeBase(List<BaseResource> resources, String knowLedgeBaseName);

    /**
     * 重载全部知识库
     */
    public void reloadRules();

    /**
     * 重载特定知识库规则
     * @param knowLedgeBaseName
     */
    public InternalKnowledgeBase reloadRule(String knowLedgeBaseName);

    /**
     * 释放session
     * @param kieSession
     */
    public void disposeKieSession(KieSession kieSession);

    /**
     * 销毁session
     * @param kieSession
     */
    public void destoryKieSession(KieSession kieSession);

    /**
     * 测试规则
     * @param resources
     */
    public KnowledgeMessage testRule(List<BaseResource> resources);

}
