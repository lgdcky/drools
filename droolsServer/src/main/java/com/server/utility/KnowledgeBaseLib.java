package com.server.utility;

import com.server.exception.KnowLedgeBuilderException;
import com.server.manager.knowledge.KnowLedgeBaseManger;
import com.server.manager.rule.RuleLoadManager;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.io.impl.BaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 2:11 PM
 */

@Component
@Scope("singleton")
public class KnowledgeBaseLib {

    private static Logger logger = LoggerFactory.getLogger(KnowledgeBaseLib.class);

    private Map<String,InternalKnowledgeBase> internalKnowledgeBaseMap = new ConcurrentHashMap<>();

    private static final String NORULEEXCEPTION = "not found any rule exception";

    @Autowired
    private RuleLoadManager ruleLoadManager;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    /**
     * 添加知识库到容器
     * @param knowLedgeBaseName
     * @param internalKnowledgeBase
     */
    public void addInternalKnowledgeBase(String knowLedgeBaseName,InternalKnowledgeBase internalKnowledgeBase){
        this.internalKnowledgeBaseMap.put(knowLedgeBaseName,internalKnowledgeBase);
    }

    /**
     * 获得知识库
     * @param knowLedgeBaseName
     * @return
     */
    public InternalKnowledgeBase getInternalKnowledgeBase(String knowLedgeBaseName){
        if(hasInternalKnowledgeBase(knowLedgeBaseName)){
            return this.internalKnowledgeBaseMap.get(knowLedgeBaseName);
        }else{
            List<BaseResource> resourceList = ruleLoadManager.loadRule(knowLedgeBaseName);
            if(null==resourceList && resourceList.isEmpty()){
                logger.warn(NORULEEXCEPTION);
                throw new KnowLedgeBuilderException(NORULEEXCEPTION);
            }
            knowLedgeBaseManger.createKnowledgeBase(resourceList,knowLedgeBaseName);
            return this.internalKnowledgeBaseMap.get(knowLedgeBaseName);
        }
    }

    /**
     * 检测是否存在知识库
     * @param knowLedgeBaseName
     * @return
     */
    public boolean hasInternalKnowledgeBase(String knowLedgeBaseName){
        return this.internalKnowledgeBaseMap.containsKey(knowLedgeBaseName);
    }

    public Map<String,InternalKnowledgeBase> getAllInternalKnowledgeBase(){
        return this.internalKnowledgeBaseMap;
    }

    public void clearAllInternalKnowledgeBase(){
        this.internalKnowledgeBaseMap.clear();
    }

    public void removeInternalKnowledgeBase(String knowLedgeBaseName){
        this.internalKnowledgeBaseMap.remove(knowLedgeBaseName);
    }

}
