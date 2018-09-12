package com.server.manager.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.server.manager.query.QueryManager;
import com.server.tools.TypeConvertTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.server.tools.CompressTool.compresss;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 2:29 PM
 */

@Component
public class MessageHandleManagerImpl implements MessageHandleManager {

    private static Logger logger = LoggerFactory.getLogger(MessageHandleManagerImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private QueryManager queryManager;

    @Override
    public byte[] messageHandle(String message) throws IOException {
        Map<String, Object[]> map = convertJsonStrToMap(message);
        Object[] param = map.get("param");

        String group = param[0].toString();
        String type = param[1].toString();

        Object[] data = map.get("data");

        byte[] bytes = doFilter(data,type,group,queryManager);
        return bytes;
    }

    @Override
    public byte[] messageHandle(Map message) throws IOException {
        long star = 0l;
        Object[] param = (Object[]) message.get("param");
        Object[] data = (Object[]) message.get("data");
        String group = param[0].toString();
        String type = param[1].toString();
        byte[] bytes = doFilter(data,type,group,queryManager);
        return bytes;
    }

    public static byte[] doFilter(Object[] data,String type,String group,QueryManager queryManager) throws IOException {
        List<Object> dataList = Stream.of(data).collect(Collectors.toList());
        return compresss(TypeConvertTools.objToBytesByStream(queryManager.queryCommandWithStatelessKieSessionAsList(group, dataList)));
    }


    public static final Map<String, Object[]> convertJsonStrToMap(String jsonStr) {
        Map<String, Object[]> map = JSON.parseObject(
                jsonStr, new TypeReference<Map<String, Object[]>>() {
                });
        return map;
    }
}