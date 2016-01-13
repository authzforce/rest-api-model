# Change log
All notable changes to this project are documented in this file following the [Keep a CHANGELOG](http://keepachangelog.com) conventions. This project adheres to [FIWARE Versioning](http://forge.fiware.org/plugins/mediawiki/wiki/fiware/index.php/Releases_and_Sprints_numbering).

## Unreleased

## 4.3.0
### Added
- Parameter 'externalId' to be set by client when provisioning/updating a domain (like in SCIM REST API). Used in query parameter to retrieve a domain resource.
- REST resource representing a specific policy with path /domains/{id}/pap/policies/{id}
- REST resource representing a specific policy version, with path /domains/{id}/pap/policies/{id}/{version} 

### Changed
- XML namespaces using public github.io URLs and versioning (namespace with major version and 'version' attribute in root schema element)


## 4.2.0
### Added
- Source distribution for packaging WADL, XML schemas and test client in a ZIP for end-users or architects

## 4.1.0
### Changed
- Initial release in open source
