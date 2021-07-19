dmn-remote-client: remotely call DM's REST API
======================================================
Author: Nevin Zhu  
Technologies: JBoss Fuse, Springboot
Summary: The `dmn-remote-client` is an app that invokes DM's REST API for traffic violation and demonstrates how to remotely call DM engine
Target Product: JBoss Fuse 7.x

What is it?
-----------

The `dmn-remote-client` demonstrates how to invoke REST API via camel route, invoke DM's REST API, pass in JSON payload via BASIC auth

It contains the following parts:

1. Camel REST Component - Camel route is opened via a REST API so the route can be triggered on a browser to initiate the route
2. Freemarker Templates - To build the JSON payload
3. Camel HTTP Component - To invoke DM REST API
4. JUnit Test Case - Demonstrate that the route is working assuming an existing DM instance is running

Run the JUnit Test Case to run and execute the camel route. This is a starter project and for demonstration purpose only. Not for production use. 
Additional enhancements are needed for future iterations such as cleaning up pom.xml. There are unnecessary dependencies that need to be removed.



System requirements
-------------------

The application this project produces is designed to be run on maven 3.x

All you need to build this project is Java 8.0 (Java SDK 1.8) or later, Maven 3.0 or later.

 

