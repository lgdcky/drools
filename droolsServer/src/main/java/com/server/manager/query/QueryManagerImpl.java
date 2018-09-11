package com.server.manager.query;

import com.server.project.wms4.OdoCommand;
import com.server.utility.KnowledgeBaseLib;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.api.command.Command;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/27/18
 * Time: 10:54 AM
 */

@Service
public class QueryManagerImpl<T, V> implements QueryManager<T, V> {

    @Autowired
    private KnowledgeBaseLib knowledgeBaseLib;

    /**
     * 使用有状态SESSION进行请求
     *
     * @param knowLedgeBaseName
     * @param data
     * @return T
     */
    @Override
    public T queryCommandWithKieSessionAsList(String knowLedgeBaseName, V data) {
        Function<String, InternalKnowledgeBase> internalKnowledgeBaseFunction = knowledgeBaseLib::getInternalKnowledgeBase;
        Supplier<KieSession> kieSessionSupplier = internalKnowledgeBaseFunction.apply(knowLedgeBaseName)::newKieSession;
        KieSession kieSession = kieSessionSupplier.get();
        List resultList = new ArrayList();
        if (null != data && data instanceof List) {
            List dataList = (List) data;
            kieSession.setGlobal("list", resultList);
            dataList.forEach(dataInfo->{
                kieSession.insert(dataInfo);
                kieSession.getAgenda().getAgendaGroup(knowLedgeBaseName).setFocus();
                kieSession.fireAllRules();
            });
        }
        return (T) resultList;
    }

    /**
     * 使用无状态SESSION进行请求
     *
     * @param knowLedgeBaseName
     * @param data
     * @return T
     */
    @Override
    public T queryCommandWithStatelessKieSessionAsList(String knowLedgeBaseName, V data) {
        Function<String, InternalKnowledgeBase> internalKnowledgeBaseFunction = knowledgeBaseLib::getInternalKnowledgeBase;
        Supplier<StatelessKieSession> statelessKieSessionSupplier = internalKnowledgeBaseFunction.apply(knowLedgeBaseName)::newStatelessKieSession;
        StatelessKieSession statelessKieSession = statelessKieSessionSupplier.get();
        if (null != data && data instanceof List) {
            ((List) data).parallelStream().forEach(datainfo->{
                statelessKieSession.execute(datainfo);
            });
        }
        return (T) data;
    }

    /**
     * 使用有状态SESSION进行请求,返回具体请求规则的结果
     *
     * @param knowLedgeBaseName
     * @param ruleMap
     * @param data
     * @return T
     */
    @Override
    public T queryCommandWithKieSession(String knowLedgeBaseName, Map<String, Map<String, List<String>>> ruleMap, V data) {
        Function<String, InternalKnowledgeBase> internalKnowledgeBaseFunction = knowledgeBaseLib::getInternalKnowledgeBase;
        Supplier<KieSession> kieSessionSupplier = internalKnowledgeBaseFunction.apply(knowLedgeBaseName)::newKieSession;
        KieSession kieSession = kieSessionSupplier.get();
        List<Command> list = new ArrayList<>();
        if (null != data && data instanceof List) {
            List dataList = (List) data;
            int dataSize = dataList.size();
            for (int i = 0; i < dataSize; i++) {
                list.add(CommandFactory.newInsert(dataList.get(i)));
            }
            list.add(CommandFactory.newFireAllRules());
            list.add(CommandFactory.newGetObjects());
            ruleMap.forEach((ruleName, queryNames) -> {
                queryNames.forEach((queryName, args) -> list.add(CommandFactory.newQuery(ruleName, queryName, args.toArray())));
            });
        }
        return (T) kieSession.execute(CommandFactory.newBatchExecution(list));
    }

    /**
     * 使用无状态SESSION进行请求,返回具体请求规则的结果
     *
     * @param knowLedgeBaseName
     * @param ruleMap
     * @param data
     * @return T
     */
    @Override
    public T queryCommandWithStatelessKieSession(String knowLedgeBaseName, Map<String, Map<String, List<String>>> ruleMap, V data) {
        Function<String, InternalKnowledgeBase> internalKnowledgeBaseFunction = knowledgeBaseLib::getInternalKnowledgeBase;
        Supplier<StatelessKieSession> statelessKieSessionSupplier = internalKnowledgeBaseFunction.apply(knowLedgeBaseName)::newStatelessKieSession;
        StatelessKieSession statelessKieSession = statelessKieSessionSupplier.get();
        List<Command> list = new ArrayList<>();
        if (null != data && data instanceof List) {
            List dataList = (List) data;
            int dataSize = dataList.size();
            for (int i = 0; i < dataSize; i++) {
                list.add(CommandFactory.newInsert(dataList.get(i)));
            }
            list.add(CommandFactory.newFireAllRules());
            list.add(CommandFactory.newGetObjects());
            ruleMap.forEach((ruleName, queryNames) -> {
                queryNames.forEach((queryName, args) -> list.add(CommandFactory.newQuery(ruleName, queryName, args.toArray())));
            });
        }
        return (T) statelessKieSession.execute(CommandFactory.newBatchExecution(list));
    }
}
