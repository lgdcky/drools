package com.server.manager.fact;

import com.server.MessageCommand.FactMessage;
import com.server.tools.FactLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.server.tools.TypeConvertTools.objectListToJson;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 2:55 PM
 */
@Component
public class FactManagerImpl implements FactManager {

    private static Logger logger = LoggerFactory.getLogger(FactManagerImpl.class);

    @Autowired
    private FactLoader factLoader;

    @Override
    public String getFactClassDescriptionInfo(String path) {
        factLoader.setClassPath(path);
        try {
            return objectListToJson(factLoader.entityInfo());
        } catch (ClassNotFoundException | IOException e) {
            try {
                logger.error(e.toString());
                return objectListToJson(new FactMessage(null, e.toString(), "Internal Server Error", false));
            } catch (ClassNotFoundException | IOException ex) {
                logger.error(ex.toString());
            }
        }
        return null;
    }
}
