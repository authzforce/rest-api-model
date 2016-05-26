# Change log
All notable changes to this project are documented in this file following the [Keep a CHANGELOG](http://keepachangelog.com) conventions. This project adheres to [FIWARE Versioning](http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/Releases_and_Sprints_numbering).

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
