package com.server.project.tools;

import com.server.project.wms4.OdoCommand;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 5:42 PM
 */
@Component(value = "odoCommandFact")
public class ConvertToOdoCommandFact implements JsonToProjectFact<OdoCommand> {

    @Override
    public List<OdoCommand> convertJsonToObj(Object projectCommand) {
        Object[] datas = (Object[]) projectCommand;
        return null;
    }


}
