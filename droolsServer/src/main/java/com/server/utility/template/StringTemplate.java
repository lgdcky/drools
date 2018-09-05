package com.server.utility.template;

import org.drools.core.io.impl.ByteArrayResource;


/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/16/18
 * Time: 11:32 PM
 */
public class StringTemplate {

    public ByteArrayResource setStringTemplate(String rule) {
        return new ByteArrayResource(rule.getBytes(), "UTF-8");
    }


}
