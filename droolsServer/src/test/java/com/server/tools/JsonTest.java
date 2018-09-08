package com.server.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.server.command.OdoCommand;
import com.server.command.WhOdoLineCommand;
import com.server.utility.TestNgBase;
import org.testng.annotations.Test;
import org.xerial.snappy.Snappy;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/28/18
 * Time: 10:53 AM
 */
public class JsonTest extends TestNgBase {

    @Test
    public void objToList() {
        List<OdoCommand> odoCommands = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            odoCommands.add(dataBuilder(i));
        }
        Object[] objects = odoCommands.toArray();

        Map<String, Object[]> data = new HashMap<>();
        data.put("data", objects);

        String json = JSONObject.toJSONString(data);


        Map<String, Object[]> map = JSON.parseObject(
                json, new TypeReference<Map<String, Object[]>>() {
                });

        Object[] dataGroup = map.get("data");

        int size = dataGroup.length;
        JSONObject jsonObject = new JSONObject();
        List<OdoCommand> list = new LinkedList<>();

        Long star = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(jsonObject.parseObject(dataGroup[i].toString(), OdoCommand.class));
        }
        System.out.println(System.currentTimeMillis() - star);

        star = System.currentTimeMillis();
        List list1 = Stream.of(dataGroup).parallel().collect(Collectors.toList());
        list1.parallelStream().forEach(d -> {
            jsonObject.parseObject(d.toString(), OdoCommand.class);
        });
        System.out.println(System.currentTimeMillis() - star);


        star = System.currentTimeMillis();
        List list3 = Stream.of(dataGroup).collect(Collectors.toList());
        list3.forEach(d -> {
            jsonObject.parseObject(d.toString(), OdoCommand.class);
        });
        System.out.println(System.currentTimeMillis() - star);


    }

    @Test
    public void gsonTest() throws IOException, ClassNotFoundException {
        List<OdoCommand> odoCommands = new ArrayList<>();
/*        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();*/
        for (int i = 0; i < 50000; i++) {
            odoCommands.add(dataBuilder(i));
        }
/*        long start = System.currentTimeMillis();
        String gJson = gson.toJson(odoCommands);
        System.out.println("gjson bean to json--------------" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        gson.fromJson(gJson,List.class);
        System.out.println("gjson json to bean--------------" + (System.currentTimeMillis() - start));*/

        JSONObject jsonObject = new JSONObject();


        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("a", "cc");
        map.put("c", "dd");
        map.put("c", jsonObject.parseArray(jsonObject.toJSONString(odoCommands)));
        byte[] fJson2 = compresss(JSONObject.toJSONString(odoCommands).getBytes());
        System.out.println("fjson bean to json  a--------------" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        Map<String, Object[]> maps = new HashMap<>();
        maps.put("a", new Object[]{"cc", "dd"});
        maps.put("c", odoCommands.toArray());
        byte[] fJson3 = compresss(jsonObject.toJSONString(odoCommands).getBytes());
        System.out.println("fjson bean to json  b--------------" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        Map<String, Object> map1s = new HashMap<>();
        map1s.put("a", "cc");
        map1s.put("c", "dd");
        map1s.put("c", odoCommands);

        byte[] yourBytes = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos);) {
            out.writeObject(map1s);
            out.flush();
            yourBytes = compresss(bos.toByteArray());
        }
        System.out.println("fjson bean to json  c--------------" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        Map<String, Object> map1 = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(uncompresss(yourBytes)); ObjectInputStream is = new ObjectInputStream(in);) {
            map1 = (Map<String, Object>) is.readObject();
        }
        System.out.println("fjson bean to json  d--------------" + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        JSONArray.parseArray(JSON.toJSONString(odoCommands));
        System.out.println("fjson bean to json--------------" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        String fJson1 = JSONObject.toJSONString(odoCommands, SerializerFeature.WriteMapNullValue);
        System.out.println("fjson bean to jsonList--------------" + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();

        String fJson = JSON.toJSONString(odoCommands, SerializerFeature.WriteMapNullValue);
        System.out.println("fjson bean to json--------------" + (System.currentTimeMillis() - start));

        System.out.println(fJson.getBytes().length);


        try {
            //sn压缩测试  压缩比例与压缩效率性价比高
            start = System.currentTimeMillis();
            byte[] bytes = compresss(fJson.getBytes());
            System.out.println("sn压缩测试--------------" + (System.currentTimeMillis() - start));
            System.out.println(bytes.length);
            start = System.currentTimeMillis();
            bytes = uncompresss(bytes);
            System.out.println("sn压缩测试--------------" + (System.currentTimeMillis() - start));
            System.out.println(bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*start = System.currentTimeMillis();
        List<OdoCommand> odoCommandList = JSON.parseArray(fJson, OdoCommand.class);
        System.out.println("fjson json to bean--------------" + (System.currentTimeMillis() - start));*/

    }


    //snappy
    public static byte[] compresss(byte srcBytes[]) throws IOException {
        return Snappy.compress(srcBytes);
    }

    public static byte[] uncompresss(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }


    public static OdoCommand dataBuilder(int i) {
        String t = "" + i;
        OdoCommand odoCommand = new OdoCommand();
        odoCommand.setAmt(new Double(1));
        odoCommand.setOuId(Long.valueOf(t));
        odoCommand.setOdoType(String.valueOf(new Random().nextInt(5)));
        odoCommand.setCustomerId(Long.valueOf(new Random().nextInt(2)));
        odoCommand.setOdoIndex(String.valueOf(new Random().nextInt(2)));
        odoCommand.setActualQty(Double.parseDouble(String.valueOf(new Random().nextInt(10))));
        List<WhOdoLineCommand> whOdoLineCommands = new ArrayList<>();
        WhOdoLineCommand whOdoLineCommand1 = new WhOdoLineCommand();
        whOdoLineCommand1.setQty(Double.parseDouble(t));
        whOdoLineCommand1.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
        whOdoLineCommand1.setActualQty(new Random(i).nextDouble());
        whOdoLineCommands.add(whOdoLineCommand1);
        if (i % 2 > 0) {
            WhOdoLineCommand whOdoLineCommand2 = new WhOdoLineCommand();
            whOdoLineCommand2.setSkuBarCode(i % 2 > 0 ? "192283108136" : "192283108137");
            whOdoLineCommand2.setQty(Double.parseDouble(t));
            whOdoLineCommand2.setActualQty(new Random(i).nextDouble());
            whOdoLineCommands.add(whOdoLineCommand2);
        }
        odoCommand.setWhOdoLineCommands(whOdoLineCommands);
        return odoCommand;
    }

}
