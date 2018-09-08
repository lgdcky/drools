package com.server.manager.query;

import org.kie.internal.executor.api.Command;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/27/18
 * Time: 10:25 AM
 */
public interface QueryManager<T,V> {


    /**
     * 使用有状态SESSION进行请求返回结果类型为list
     * @param knowLedgeBaseName
     * @param data
     * @return T
     */
    public T queryCommandWithKieSessionAsList(String knowLedgeBaseName,V data);

    /**
     * 使用无状态SESSION进行请求返回结果类型为list
     * @param knowLedgeBaseName
     * @param data
     * @return T
     */
    public T queryCommandWithStatelessKieSessionAsList(String knowLedgeBaseName,V data);

    /**
     * 使用有状态SESSION进行请求,返回具体请求规则的结果
     * @param knowLedgeBaseName
     * @param ruleMap
     * @param data
     * @return T
     */
    public T queryCommandWithKieSession(String knowLedgeBaseName, Map<String,Map<String, List<String>>> ruleMap, V data);

    /**
     * 使用无状态SESSION进行请求,返回具体请求规则的结果
     * @param knowLedgeBaseName
     * @param ruleMap
     * @param data
     * @return T
     */
    public T queryCommandWithStatelessKieSession(String knowLedgeBaseName, Map<String,Map<String,List<String>>> ruleMap, V data);

}
