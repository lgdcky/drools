package com.server.factory;

import com.server.SpringBootDroolsApplication;
import com.server.knowledgebasemanager.KnowLedgeBaseManger;
import org.drools.compiler.lang.DrlDumper;
import org.drools.compiler.lang.api.DescrFactory;
import org.drools.compiler.lang.api.PackageDescrBuilder;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.io.impl.BaseResource;
import org.drools.core.io.impl.ByteArrayResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 7/5/18
 * Time: 2:56 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(classes = SpringBootDroolsApplication.class)
public class FacotryTest {

    @Autowired
    private DroolsServicesBuilderFactory droolsServicesBuilderFactory;

    @Autowired
    private KnowLedgeBaseManger knowLedgeBaseManger;

    @Test
    public void createSession(){
        List<BaseResource> resources = new ArrayList<>();
        resources.add(createRule());
        KnowledgeBuilder knowledgeBuilder = droolsServicesBuilderFactory.createKnowledgeBuilder();
        droolsServicesBuilderFactory.addRules(knowledgeBuilder,resources,ResourceType.DRL);
        InternalKnowledgeBase internalKnowledgeBase = droolsServicesBuilderFactory.createInternalKnowledgeBase();
        droolsServicesBuilderFactory.buliderBase(internalKnowledgeBase,knowledgeBuilder);
        KieSession kieSession = internalKnowledgeBase.newKieSession();
        KieBase kieBase = kieSession.getKieBase();
    }

    @Test
    public void loadPackage(){
        KieServices kss = KieServices.Factory.get();

        ResourceFactory.newByteArrayResource(createRule().getBytes()).setResourceType(ResourceType.DRL);

    }

    /**
     * 有状态SESSION
     */
    @Test
    public void createKnowledgeBaseTest(){
        InternalKnowledgeBase internalKnowledgeBase = null;
        List<BaseResource> resources = new ArrayList<>();
        resources.add(new ByteArrayResource((createRule().toString().getBytes())));
        long start = System.currentTimeMillis();
        internalKnowledgeBase = knowLedgeBaseManger.createKnowledgeBase(resources,"test");
        long split = System.currentTimeMillis();
        KieSession kieSession = droolsServicesBuilderFactory.createKieSession(internalKnowledgeBase);
        System.out.println(internalKnowledgeBase.getContainerId());//有id
        kieSession.fireAllRules();
        System.out.println("=================================11111111          "+(split-start));
        kieSession.destroy();
        start = System.currentTimeMillis();
        kieSession = droolsServicesBuilderFactory.createKieSession(internalKnowledgeBase);
        kieSession.fireAllRules();
        System.out.println("=================================22222222            "+(System.currentTimeMillis()-start));
        System.out.println(internalKnowledgeBase.getContainerId());//无id
    }

    @Test
    public void createKnowledgeBaseStatelessTest(){
        InternalKnowledgeBase internalKnowledgeBase = null;
        List<BaseResource> resources = new ArrayList<>();
        resources.add(createRule());
        long start = System.currentTimeMillis();
        internalKnowledgeBase = knowLedgeBaseManger.createKnowledgeBase(resources,"test");
        long split = System.currentTimeMillis();
    }

    public ByteArrayResource createRule() {
        /*PackageDescrBuilder packBuilder =
                DescrFactory.newPackage().newGlobal().identifier("list").type(List.class.getName()).end()
                        .name("com.server.command")
                        .newRule().name("test")
                        .lhs()
                        .pattern().id("$stockTick", true).type(StockTickT.class.getName()).end()
                        .pattern().id("$stockTick", true).type(StockTickT.class.getName()).constraint("price>100").end()
                        .end()
                        .rhs("    System.out.println(\"foo===================================\");\n")
                        .end();
        packBuilder.newQuery().name("query-1").lhs()
                .pattern().id("$stockTick", true).type(StockTickT.class.getName()).constraint("price>100").end();
        String drl = new DrlDumper().dump(packBuilder.getDescr());*/
        return new ByteArrayResource("".getBytes(), "UTF-8");
    }

}
