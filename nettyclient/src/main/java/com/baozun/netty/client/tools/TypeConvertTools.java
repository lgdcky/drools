package com.baozun.netty.client.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/10/18
 * Time: 11:16 AM
 */
public final class TypeConvertTools {

    private static ParserConfig parserConfig = new ParserConfig();

    private static SerializeConfig serializeConfig = new SerializeConfig();

    private static JSONObject jsonObject = new JSONObject(50000);

    private static JSONArray jsonArray = new JSONArray(50000);

    private static final Integer BUFFERSIZE = 3 * 1024 * 1024;

    public static byte[] ObjToBytesByFastJson(Object object) throws UnsupportedEncodingException {
        return jsonObject.toJSONString(object, serializeConfig).getBytes("UTF-8");
    }

    public static byte[] ObjMapToBytesByFastJson(Map<String, Object[]> object) throws UnsupportedEncodingException {
        return jsonObject.toJSONString(object).getBytes("UTF-8");
    }

    public static byte[] objToBytesByStream(Object object) throws IOException {
        byte[] bytes = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
             ObjectOutput out = new ObjectOutputStream(bos);) {
            out.writeObject(object);
            bytes = bos.toByteArray();
        }
        return bytes;
    }

    public static Object bytesToObjectByStream(byte[] resource) throws IOException, ClassNotFoundException {
        Object object = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(resource);
             ObjectInput in = new ObjectInputStream(bis);) {
            object = in.readObject();
        }
        return object;
    }

}
