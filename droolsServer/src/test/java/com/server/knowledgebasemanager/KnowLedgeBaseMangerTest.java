package com.server.knowledgebasemanager;

import com.server.SpringBootDroolsApplication;
import com.server.utility.KnowledgeBaseLib;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/26/18
 * Time: 7:29 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = SpringBootDroolsApplication.class)
public class KnowLedgeBaseMangerTest {

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Autowired
    private KnowledgeBaseLib knowledgeBaseLib;

    @Test
    public void testKnowLedgeBaseManager() throws InterruptedException {
        System.out.println("======================="+knowledgeBaseLib.getAllInternalKnowledgeBase().size());
        knowLedgeBaseManger.createKnowledgeBase(null,"aaaaa");
        Thread.sleep(1000);
        System.out.println("======================="+knowledgeBaseLib.getAllInternalKnowledgeBase().size());

        //knowLedgeBaseManger.destoryKieSession(null);
    }

}
