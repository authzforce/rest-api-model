# Change log
All notable changes to this project are documented in this file following the [Keep a CHANGELOG](http://keepachangelog.com) conventions. This project adheres to [FIWARE Versioning](http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/Releases_and_Sprints_numbering).

## Unreleased
### Changed
- Root policy reference no longer set via PUT /domains/{domainId}/properties but via PUT /domains/{domainId}/pap/properties
- GET /domains/{domainId}/pap/properties gives the properties of the PDP, including date/time of last modification and active policies

### Added
- API allows the special keyword "latest" as version ID to get the latest version of a given policy (in addition to XACML version IDs like before), e.g. URL path /domains/{domainId}/pap/policies/P1/latest represents the latest version of policy "P1"

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
