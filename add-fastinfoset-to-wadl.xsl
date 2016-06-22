<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:wadl="http://wadl.dev.java.net/2009/02">
    <!-- <xsl:output method="xml" encoding="UTF-8" indent="yes" saxon:indent-spaces="4" /> xmlns:saxon="http://saxon.sf.net/" -->
    <xsl:output method="xml" encoding="UTF-8" indent="yes" />
    <xsl:template match="wadl:representation[@mediaType='application/xml']">
        <xsl:variable name="xmlElement" select="@element" />
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
        <wadl:representation mediaType="application/fastinfoset">
            <xsl:attribute name="element"><xsl:value-of select="$xmlElement" /></xsl:attribute>
        </wadl:representation>
    </xsl:template>
    <!-- standard copy template -->
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>