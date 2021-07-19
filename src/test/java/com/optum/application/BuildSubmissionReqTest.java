package com.optum.application;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.optum.application.DecisionServiceApplication;
/**
 * JUnit test cases to validate each route defined in DecisionServiceAutoConfiguration
 * 
 * @author Nevin Zhu
 * @version 1.0
 * @since 2021-7-18
 *
 */
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes={DecisionServiceApplication.class})
@EnableRouteCoverage
public class BuildSubmissionReqTest {
	
	@EndpointInject(uri = MOCK_RESULT)
    private MockEndpoint resultEndpoint;
 
    @EndpointInject(uri = ROUTE_START)
    private ProducerTemplate producer;
 
    private static final String MOCK_RESULT = "mock:buildSubmissionResult";
    private static final String ROUTE_START = "direct:build-submission-record";
 
    
    /*
     * This is the unit test case for invoking decision manager rest API for traffic violation quickstart project
     * DM server must be running with traffic violation sample project deployed on a local machine where unit test is running
     */
    @Test
    public void buildSubmissionRecordRequestTest() throws Exception {
    	
    	String inputData = "MOCK DATA FOR TESTING PURPOSE";

        resultEndpoint.expectedMessageCount(1);
        
        producer.sendBody(inputData);

        resultEndpoint.assertIsSatisfied();

    }
    
}
