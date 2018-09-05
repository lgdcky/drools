package com.server.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.server.command.OdoCommand;
import com.server.command.WhOdoLineCommand;
import com.server.utility.TestNgBase;
import org.testng.annotations.Test;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/28/18
 * Time: 10:53 AM
 */
public class JsonTest extends TestNgBase {

    @Test
    public void gsonTest() {
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

        long start = System.currentTimeMillis();
        String fJson = JSON.toJSONString(odoCommands, SerializerFeature.WriteMapNullValue);
        //System.out.println("fjson bean to json--------------" + (System.currentTimeMillis() - start));

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
        return  Snappy.compress(srcBytes);
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
