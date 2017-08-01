<?xml version="1.0" encoding="UTF-8"?>
<!--

    Copyright 2012-2017 Thales Services SAS.

    This file is part of AuthzForce CE.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wadl="http://wadl.dev.java.net/2009/02" xmlns:xacml="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17">
   <!-- <xsl:output method="xml" encoding="UTF-8" indent="yes" saxon:indent-spaces="4" /> xmlns:saxon="http://saxon.sf.net/" -->
   <xsl:output method="xml" encoding="UTF-8" indent="yes" />
   <xsl:param name="enable_xacml_json_profile" select="false"/>
   <xsl:template match="wadl:representation[@mediaType='application/xml']">
      <!-- <xsl:variable name="xmlElement" select="@element" /> -->
      <xsl:copy>
         <xsl:apply-templates select="@*|node()" />
      </xsl:copy>
      <wadl:representation mediaType="application/json">
         <!-- If we specify an element again for 'application/json', CXF wadl2java plugin ignores the fact that it is the same and maps to generic java type 'javax.xml.transform.Source' -->
         <!-- <xsl:attribute name="element"><xsl:value-of select="$xmlElement" /></xsl:attribute> -->
      </wadl:representation>
      <xsl:if test="$enable_xacml_json_profile = 'true' and (@element = 'xacml:Request' or @element = 'xacml:Response')">
         <wadl:representation mediaType="application/xacml+json" />
      </xsl:if>
   </xsl:template>
   <!-- standard copy template -->
   <xsl:template match="@*|node()">
      <xsl:copy>
         <xsl:apply-templates select="@*|node()" />
      </xsl:copy>
   </xsl:template>
</xsl:stylesheet>