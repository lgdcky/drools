package com.server.manager.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.server.manager.query.QueryManager;
import com.server.project.tools.JsonToProjectFact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 2:29 PM
 */

@Component
public class MessageHandleManagerImpl implements MessageHandleManager {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private QueryManager queryManager;

    @Override
    public Object[] messageHandle(String message) {
        Map<String, Object[]> map = convertJsonStrToMap(message);

        Object[] param = map.get("param");

        String group = param[0].toString();
        String type = param[1].toString();

        Object[] data = map.get("data");

        JsonToProjectFact jsonToProjectFact = (JsonToProjectFact) applicationContext.getBean(type);

        queryManager.queryCommandWithStatelessKieSessionAsList(group, jsonToProjectFact.convertJsonToObj(data));

        return null;
    }


    public static final Map<String, Object[]> convertJsonStrToMap(String jsonStr) {
        Map<String, Object[]> map = JSON.parseObject(
                jsonStr, new TypeReference<Map<String, Object[]>>() {
                });
        return map;
    }
}