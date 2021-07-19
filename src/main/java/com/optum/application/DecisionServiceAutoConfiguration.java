package com.optum.application;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Camel Route Builder for exposing REST API endpoints and handling data transformation
 * h_ naming convention used to indicate exchange header parameter
 * 
 * @author Nevin Zhu
 * @version 1.0
 * @since 2020-5-7
 *
 */
@Configuration
public class DecisionServiceAutoConfiguration {
	public static final String TARGET_WITH_AUTH = "http:localhost:8080/kie-server/services/rest/server/containers/traffic-violation_1.0.0-SNAPSHOT/dmn" +
            "?authMethod=Basic&authUsername=rhpamAdmin&authPassword=P@ssword123";
	
    @Bean
    public RouteBuilder routeBuilder() {
    	
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	/*
            	 *  REST endpoints definition
            	 */
                rest("/data")
                    .post("/srecord/{h_sid}").to("direct:build-submission-record");

               
                /*
                 * Inbound Data Description: Invokes the DM Rest API
                 * Inbound Data Type: JSON
                 * Accept-Content: text/plain
                 * Method: HTTP-POST
                 * Outbound Data Description: rest://method:path[:uriTemplate]?[options]
                 */
                from("direct:build-submission-record").id("build-submission-record")
                    .log("${body}")
                    .to("freemarker:templates/submission_record_req.ftl")
                    .setHeader(Exchange.CONTENT_TYPE).simple("application/json")
                    .to(TARGET_WITH_AUTH)
                    .log("...response built for submission record request: ${body}");
 
            }
        };
    }
}