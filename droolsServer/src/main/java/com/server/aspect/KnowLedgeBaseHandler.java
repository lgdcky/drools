package com.server.aspect;

import com.server.utility.KnowledgeBaseLib;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.drools.core.impl.InternalKnowledgeBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 7:15 PM
 */

/**
 * 用于拦截添加知识库,将知识库进行存储
 */
@Aspect
@Component
public class KnowLedgeBaseHandler {

    @Autowired
    private KnowledgeBaseLib knowledgeBaseLib;

    @Pointcut("execution(public * com.server.manager.knowledge.KnowLedgeBaseMangerImpl.*(..))")
    public void pointCut() {
    };

    @AfterReturning(value = "pointCut()", returning = "result",pointcut="createKnowledgeBase")
    public void storeInternalKnowledgeBase(JoinPoint joinPoint, InternalKnowledgeBase result) {
        String name = joinPoint.getArgs()[1].toString();
        if(!knowledgeBaseLib.hasInternalKnowledgeBase(name)){
            knowledgeBaseLib.addInternalKnowledgeBase(name,result);
        }
    }

}
