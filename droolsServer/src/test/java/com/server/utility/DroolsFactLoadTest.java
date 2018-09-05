package com.server.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.model.FactClassDescriptionInfo;
import com.server.tools.FactLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/23/18
 * Time: 4:06 PM
 */
public class DroolsFactLoadTest extends TestNgBase {

    @Autowired
    private FactLoader factLoader;

    @Test
    public void loadTest(){
        factLoader.setClassPath("/command/*.class");
        List<FactClassDescriptionInfo> factClassDescriptionInfoList = factLoader.entityInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(factClassDescriptionInfoList);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
