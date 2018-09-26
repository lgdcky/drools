package com.server.manager.fact;

import com.server.MessageCommand.FactMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 2:53 PM
 */
public interface FactManager {

    public FactMessage getFactClassDescriptionInfo(String path) throws Exception;

}
