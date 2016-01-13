/**
 * Copyright (C) 2012-2016 Thales Services SAS.
 *
 * This file is part of AuthZForce CE.
 *
 * AuthZForce CE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthZForce CE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuthZForce CE.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ow2.authzforce.rest.api.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.Attribute;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.AttributeValueType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Attributes;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Request;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Response;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.Result;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.ow2.authzforce.rest.api.jaxrs.DomainResource;
import org.ow2.authzforce.rest.api.jaxrs.DomainsResource;
import org.xml.sax.SAXException;

/**
 * Sample client code to request the Authorization PDP
 * 
 */
public class AzClient
{
	private final static JAXBContext XACML_JAXB_CONTEXT;
	static
	{
		try
		{
			XACML_JAXB_CONTEXT = JAXBContext.newInstance(Request.class, Response.class);
		} catch (JAXBException e)
		{
			throw new RuntimeException("Failed to initialize XACML schema's JAXB context for (un)marshalling Request/Response elements", e);
		}
	}

	private final static Schema XACML_SCHEMA;
	static
	{
		try (final InputStream xacmlPolicyXsdIn = AzClient.class.getResourceAsStream("/xml.xsd");
				final InputStream xacmlCtxXsdIn = AzClient.class.getResourceAsStream("/xacml-core-v3-schema-wd-17.xsd");)
		{
			final Source xacmlPolicyXsd = new StreamSource(xacmlPolicyXsdIn);
			final Source xacmlCtxXsd = new StreamSource(xacmlCtxXsdIn);
			XACML_SCHEMA = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new Source[] { xacmlPolicyXsd, xacmlCtxXsd });
		} catch (IOException | SAXException e)
		{
			throw new RuntimeException("Failed to load XACML schema for validating Request/Response elements", e);
		}
	}

	private static final Validator XACML_SCHEMA_VALIDATOR = XACML_SCHEMA.newValidator();

	/**
	 * @param args
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws JAXBException, SAXException, IOException
	{
		// For SSL debugging
		System.setProperty("javax.net.debug", "all");

		// final String serviceBaseURL = "https://az.testbed.fi-ware.eu/authzforce";
		final String serviceBaseURL = "http://localhost:6080/authzforce-webapp";
		final String domainId = "f1b60aaa-41cc-11e3-845a-978549de16ec";

		/**
		 * Set the SSL/TLS client parameters, change the configuration in cxfClient.xml, check your keystore/truststore paths in particular.
		 */
		final SpringBusFactory bf = new SpringBusFactory();
		// cxfClient.xml must be on the classpath
		final Bus bus = bf.createBus("/cxfClient.xml");
		BusFactory.setDefaultBus(bus);

		/**
		 * Create the REST (JAX-RS) client
		 */
		final DomainsResource domainsResourceProxy = JAXRSClientFactory.create(serviceBaseURL, DomainsResource.class);

		/**
		 * Request/response logging (for debugging).
		 */
		final ClientConfiguration clientConf = WebClient.getConfig(domainsResourceProxy);
		clientConf.getInInterceptors().add(new LoggingInInterceptor());
		clientConf.getOutInterceptors().add(new LoggingOutInterceptor());

		// Get your domain's resource
		final DomainResource myDomain = domainsResourceProxy.getDomainResource(domainId);

		/**
		 * Request some authorization decision from my domain's PDP First build the XACML request. First build the XACML request
		 */
		final List<Attributes> attributesList = new ArrayList<>();

		// Subject/Subject ID
		final AttributeValueType subjIdAttrVal = new AttributeValueType(Collections.<Serializable> singletonList("bs@simpsons.com"),
				"http://www.w3.org/2001/XMLSchema#string", null);
		final Attribute subjIdAttr = new Attribute(Collections.singletonList(subjIdAttrVal), "urn:oasis:names:tc:xacml:1.0:subject:subject-id",
				"http://issuer.example.com", false);
		final Attributes subjectAttributes = new Attributes(null, Collections.singletonList(subjIdAttr),
				"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject", null);
		attributesList.add(subjectAttributes);

		// Resource/Resource ID
		final AttributeValueType resIdAttrVal = new AttributeValueType(
				Collections.<Serializable> singletonList("file://example/med/record/patient/BartSimpson"), "http://www.w3.org/2001/XMLSchema#string", null);
		final Attribute resIdAttr = new Attribute(Collections.singletonList(resIdAttrVal), "urn:oasis:names:tc:xacml:1.0:resource:resource-id",
				"http://issuer.example.com", false);
		final Attributes resourceAttributes = new Attributes(null, Collections.singletonList(resIdAttr),
				"urn:oasis:names:tc:xacml:3.0:attribute-category:resource", null);
		attributesList.add(resourceAttributes);

		// Action/Action ID
		final AttributeValueType actIdAttrVal = new AttributeValueType(Collections.<Serializable> singletonList("read"),
				"http://www.w3.org/2001/XMLSchema#string", null);
		final Attribute actIdAttr = new Attribute(Collections.singletonList(actIdAttrVal), "urn:oasis:names:tc:xacml:1.0:action:action-id",
				"http://issuer.example.com", false);
		final Attributes actionAttributes = new Attributes(null, Collections.singletonList(actIdAttr),
				"urn:oasis:names:tc:xacml:3.0:attribute-category:action", null);
		attributesList.add(actionAttributes);

		// Environment/current-date
		final AttributeValueType envAttrVal = new AttributeValueType(Collections.<Serializable> singletonList("2010-01-11"),
				"http://www.w3.org/2001/XMLSchema#date", null);
		final Attribute envAttr = new Attribute(Collections.singletonList(envAttrVal), "urn:oasis:names:tc:xacml:1.0:environment:current-date",
				"http://issuer.example.com", false);
		final Attributes envAttributes = new Attributes(null, Collections.singletonList(envAttr),
				"urn:oasis:names:tc:xacml:3.0:attribute-category:environment", null);
		attributesList.add(envAttributes);

		Request req = new Request(null, attributesList, null, false, false);
		// Request validation against schema (a malformed XACML request will be rejected by the
		// service)
		XACML_SCHEMA_VALIDATOR.validate(new JAXBSource(XACML_JAXB_CONTEXT, req));

		// Send the XACML request to PDP
		final Response response = myDomain.getPdpResource().requestPolicyDecision(req);
		XACML_SCHEMA_VALIDATOR.validate(new JAXBSource(XACML_JAXB_CONTEXT, response));

		for (final Result result : response.getResults())
		{
			System.out.println("Authorization decision: " + result.getDecision() + "; status code: " + result.getStatus().getStatusCode().getValue()
					+ "; status message: " + result.getStatus().getStatusMessage() + "; "
					+ (result.getObligations() == null ? 0 : result.getObligations().getObligations().size()) + " obligation(s)");
		}
	}

}
