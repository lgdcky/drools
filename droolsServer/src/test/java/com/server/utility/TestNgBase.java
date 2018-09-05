package com.server.utility;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 11:42 AM
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNgBase  extends AbstractTestNGSpringContextTests {
}
