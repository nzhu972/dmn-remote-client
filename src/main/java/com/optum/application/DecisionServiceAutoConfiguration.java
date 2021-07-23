package com.optum.application;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Camel Route Builder for exposing REST API endpoints and handling data transformation
 * h_ naming convention used to indicate exchange header parameter
 * 
 * @author Nevin Zhu 
 * @version 1.0
 * @since 2021-07-23
 *
 */
@Configuration
public class DecisionServiceAutoConfiguration {
	// Credential used in this URL is for demo purpose only. For production, credentials should be externalized and loaded during run time
	public static final String TARGET_WITH_AUTH = "http:localhost:8080/kie-server/services/rest/server/containers/traffic-violation_1.0.0-SNAPSHOT/dmn" +
            "?authMethod=Basic&authUsername=rhpamAdmin&authPassword=P@ssword123";
	
    @Bean
    public RouteBuilder routeBuilder() {
    	
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	/*
            	 *  This rest component exposes a REST endpoint in the route. Allowing 
            	 *  user to trigger the route through a browser
            	 */
                rest("/data")
                    .post("/srecord").to("direct:build-submission-record");

               
                /*
                 * Inbound Data Description: Invokes the DM Rest API             
                 * Accept-Content: application/json
                 * Method: HTTP-POST
                 * Outbound Data Description: See sample JSON payload in the payload directory in the root project. 
                 * JSON Path expression is implemented based on the sample response body from kie server. 
                 * JSON request used in the kie server HTTP request is in src/main/resources/templates
                 */
                from("direct:build-submission-record").id("build-submission-record")
                     // Using freemarker template to create a JSON request
                    .to("freemarker:templates/submission_record_req.ftl")
                    .setHeader(Exchange.CONTENT_TYPE).simple("application/json")
                    // Invoking the DM REST API using BASIC auth method
                    .to(TARGET_WITH_AUTH)
                    // Two decisions are sent in the response body. Using splitter to parse each decision
                    .split().jsonpath("result.dmn-evaluation-result.decision-results[*]")
                    .choice()
                      .when().jsonpath("$.[?(@.decision-name == 'Fine')]")
                         .setHeader("h_points").jsonpath("$..Points")
                         .log("...points is parsed from response body: ${headers.h_points}")
                      .otherwise()
	                      .setHeader("h_isSuspended").jsonpath("$.result")
	                      .log("...suspensin result is parsed from response body: ${headers.h_isSuspended}");                        

            }
        };
    }
}