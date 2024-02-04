# Change log
All notable changes to this project are documented in this file following the [Keep a CHANGELOG](http://keepachangelog.com) conventions. This project adheres to [FIWARE Versioning](http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/Releases_and_Sprints_numbering).

## 7.0.0
### Changed
- Parent project `authzforce-ce-parent` version: 9.1.0:
  - **Migrated to Java 17** (as the minimum required JRE version from now on) and **JAXB 4.0** (javax.xml.bind packages replaced with jakarta.xml.bind.*)
- Upgraded dependencies:
  - authzforce-ce-atom-model: 9.1.0
  - authzforce-ce-xacml-model: 9.1.0
  - authzforce-ce-pdp-ext-model: 9.1.0
  - **jakarta.ws.rs-api (Jakarta REST API): 3.0.0, replacing javax.ws.rs-api**
  - org.json:json: 20231013

### Added
- Added support of GeoXACML media types to API (WADL)


## 6.0.0
### Changed
- Parent project `authzforce-ce-parent` version: 8.0.0 
  - Upgraded generated code to Java 11. Java 8 no longer supported.
  - Upgraded managed dependencies:	
    - Jakarta RESTful Web Services: v2.1.6
    - org.json:json: v20190722	

	
## 5.7.0
### Changed
- Parent project `authzforce-ce-parent` version: 7.0.0 -> 7.1.0
	- Dependency version: json: 20170516 -> 20171018


## 5.6.0
### Changed
- Parent project `authzforce-ce-parent` version: 6.0.1 -> 7.0.0
- API schema `authz-rest-api.xsd` (5.2.0 -> 5.3.0): new ProductMetadata type used for new "/version" resource providing product metadata.

### Added
- Resource "/version" allowing GET method to get product metadata, as described in https://jira.ow2.org/browse/AUTHZFORCE-30 (product name,
version, release_date, uptime, REST API doc URL)
- Supported accept/content-type = `application/xacml+xml` - defined by [RFC 7061](https://tools.ietf.org/html/rfc7061) - on `/domains/{id}/pap/policies` for XACML PolicySet payload, and on `/domains/{id}/pdp` for XACML Request/Response payload
- Support for [JSON Profile of XACML](http://docs.oasis-open.org/xacml/xacml-json-http/v1.0/xacml-json-http-v1.0.html)'s media type on `/domains/{id}/pdp`: accept/content-type = `application/xacml+json`


## 5.5.0
### Changed
- Maven project parent (authzforce-ce-parent) version: 6.0.1
- License: GPL v3.0 replaced by Apache License v2.0


## 5.4.0
### Changed
* Maven parent project version: 3.3.7 -> 4.1.1
	* **Java version: 1.7 -> 1.8**
	* authzforce-ce-atom-model: 3.3.7 -> 4.1.1
	* authzforce-ce-xacml-model: 3.3.7 -> 4.1.1
	* authzforce-ce-pdp-ext-model: 3.3.7 -> 4.1.1


## 5.3.1
### Fixed
- Some API operations missing JSON mediatype support


## 5.3.0
### Added
- Json support to the WADL


## 5.2.0
### Added
- Github #1: Enhanced management of PDP features: all supported features may be listed, and each feature may have a 'type' (e.g. XACML function, datatype...) and an 'enabled' (true or false) state that can be updated via the API


## 5.1.2
### Fixed
- Version number in the main XML schema of the API (authz-rest-api.xsd) 


## 5.1.1
### Fixed
REST API implementations no longer forced to support FastInfoset, with WADL split in two:
- Master WADL without 'application/fastinfoset' mediatype (authz-api.wadl)
- New WADL with 'application/fastinfoset' mediatype (authz-api.fastinfoset.wadl), generated from master wadl


## 5.1.0
WADL defines "application/xml" representation type always before "application/fastinfoset" to make "application/xml" the default Content-Type produced by REST implementation when no particular Accept header is specified by clients.


## 5.0.0
### Added
- URL path specific to PDP properties:
	- GET /domains/{domainId}/pap/pdp.properties gives properties of the PDP, including date/time of last modification and active/applicable policies (root policy and policies referenced directly/indirectly from root)
	- PUT /domains/{domainId}/pap/pdp.properties also allows to set PDP's root policy and PDP implementation-specific features in new <feature>* element (e.g. support for a specific XACML profile)
- URL path specific to PRP (Policy Repository Point) properties
	- GET or PUT /domains/{domainId}/pap/prp.properties: set/get PRP properties, i.e. maxPolicyCount (maximum number of policies), maxVersionCount (maximum number of versions per policy), versionRollingEnabled (enable policy version rolling, i.e. oldest versions auto-removed when the number of versions of a policy is about to exceed 'maxVersionCount') 
- API allows the special keyword "latest" as version ID to get the latest version of a given policy (in addition to XACML version IDs like before), e.g. GET /domains/{domainId}/pap/policies/P1/latest -> returns the latest version of policy "P1"
- FastInfoset support: new data representation type 'application/fastinfoset' (in addition to 'application/xml') for all API payloads

### Changed
- PDP's root policy reference set via PUT /domains/{domainId}/pap/pdp.properties (no longer set via PUT /domains/{domainId}/properties)
- URL path /domains/{domainId}/pap/attributeProviders changed to /domains/{domainId}/pap/attribute.providers for applying better practices of REST API design (case-insensitive URLs) and consistent with other API paths 'pdp.properties' and 'prp.properties'


## 4.3.0
### Added
- Parameter 'externalId' to be set by client when provisioning/updating a domain (like in SCIM REST API). Used in query parameter to retrieve a domain resource.
- REST resource representing a specific policy with path /domains/{domainId}/pap/policies/{policyId}
- REST resource representing a specific policy version, with path /domains/{domainId}/pap/policies/{policyId}/{version} 

### Changed
- XML namespaces using public github.io URLs and versioning (namespace with major version and 'version' attribute in root schema element)


## 4.2.0
### Added
- Source distribution for packaging WADL, XML schemas and test client in a ZIP for end-users or architects


## 4.1.0
### Changed
- Initial release in open source
