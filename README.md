[![Javadocs](http://javadoc.io/badge/org.ow2.authzforce/authzforce-ce-rest-api-model.svg)](http://javadoc.io/doc/org.ow2.authzforce/authzforce-ce-rest-api-model)

This package includes a sample client code that shows how to to send a XACML request to the AuthzForce PDP API. This code is in the test class `org.ow2.authzforce.rest.api.test` in `src/test/java`. 

If you are accessing an Authorization PDP implementation such as AuthzForce Server over SSL (HTTPS), configure the keystore (resp. truststore) location and password in configuration element `keyManagers` (resp. `trustManagers`) in file `src/test/resources/cxfClient.xml`. The keystore is the one that contains your own private key and the certificate that you use to authenticate to the server. The truststore is in `src/test/resources`, you may have to change to an absolute path for the truststore location, depending on where you execute the code from.

Now, you should be able to compile and run the `main()` of the AzClient class mentioned previously. If you are using an IDE such as Eclipse, you can import the project as Maven project (requires Eclipse maven plugin installed), right-click on the new project > Run as > maven generate-sources, configure the keystore as above, right-click on the AzClient.java and "Run as" > "Java Application".
