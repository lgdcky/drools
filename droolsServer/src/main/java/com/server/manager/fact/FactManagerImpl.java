package com.server.manager.fact;

import com.server.MessageCommand.FactMessage;
import com.server.tools.FactLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    public FactMessage getFactClassDescriptionInfo(String path) {
        factLoader.setClassPath(path);
        try {
            return new FactMessage(FactMessage.SUCCESS, null, "Internal Server Error", false, factLoader.entityInfo());
        } catch (IOException e) {
            return new FactMessage(FactMessage.FAILED, null, null, false, null);
        }
    }
}
