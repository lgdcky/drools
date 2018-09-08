package com.server.project.tools;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 4:25 PM
 */
public interface JsonToProjectFact<T> {

    public List<T> convertJsonToObj(Object[] projectCommand);

}
