/*
 * Copyright 2012-2021 THALES.
 *
 * This file is part of AuthzForce CE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ow2.authzforce.rest.api.test;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Example of extra JAXB-annotated class for extra XML content sent in XACML Request/Content by the JAX-RS test client
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ContentTest")
public class ContentTest implements Serializable
{

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "realContent")
	public String realContent = "Hello World";
}
