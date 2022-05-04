# InteropEHRate Pseudo-Identity Generation Service

## Description

The aim of the Pseudo-Identity Generation Service is to generate a pseudo-identity in order to be utilized in the pseudonymization operation of the InteropEHRate project. This service consists of two components, a) a Spring Boot service listening on port 8080 and b) an SQL database listening on port 3306. The request takes as a parameter the ID of the research study, generates the pseudo-identity and stores the ID of the study along with an incremental number to the database.

## Endpoints

The Pseudo-Identity Generation Service is deployed at the Reference Research Center and has one endpoint. The S-EHR Application invokes this endpoint in order to retrieve a pseudo-identity for a specific study. 

**Endpoint:** [GET] URL:8080/pseudo_identity 
* Parameters: prefix , _which represents the ID of the research study_
* Response:

```json
{
    "prefix":"985324",
    "message":"The pseudo-identity was generated successfully.",
    "pseudo-identity":"9853241e0044c7f04fed8c9fa222dbf984a1d875a376843428fbc205b836ff685358859",
    "status":200
}
```

## Installation Guide

1.	The service requires both [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/) to be installed.
2.	Download the source code of the service from GitLab repository.
3.	Navigate to the root directory of the project **/pseudo-id-generator** and open a terminal.
4.	Run the command `docker-compose up -d`.
5.	The Pseudo-Identity Generation Service is up and running and can generate pseudo-identities by invoking the URL `http://[URL]:8080/pseudo_identity?prefix=[studyID]`. 
