package com.server.manager.fact;

import com.server.utility.TestNgBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/26/18
 * Time: 2:32 PM
 */
public class FactManagerImplTest extends TestNgBase {

    @Autowired
    private FactManager factManager;

    @Test
    public void testGetFactClassDescriptionInfo() {
        try {
            factManager.getFactClassDescriptionInfo("aa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}