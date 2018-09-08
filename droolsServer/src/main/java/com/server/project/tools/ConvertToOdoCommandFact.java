package com.server.project.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.server.project.wms4.OdoCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 5:42 PM
 */
@Component(value = "odoCommandFact")
public class ConvertToOdoCommandFact implements JsonToProjectFact<OdoCommand> {

    @Override
    public List<OdoCommand> convertJsonToObj(Object[] projectCommand) {
        //List list = Stream.of(projectCommand).parallel().forEach(p -> JSONObject.parseObject(p.toString(), OdoCommand.class)).collect(Collectors.toList());
        //List<OdoCommand> data = (List<OdoCommand>) list;
        return null;
    }


}
