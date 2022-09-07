# InteropEHRate Pseudo-Identity Generation Service

## Description

The aim of the Pseudo-Identity Generation Service is to generate a pseudo-identity in order to be utilized in the pseudonymization operation of the InteropEHRate project. This service consists of two components, a) a Spring Boot service listening on port 8080 and b) an SQL database listening on port 3306.

## Endpoints

The Pseudo-Identity Generation Service is deployed at the Reference Research Center and has one endpoint. The S-EHR Application invokes this endpoint in order to retrieve a pseudo-identity for a specific study.

1. **/pseudo_identity**: This endpoint is invoked so as to generate and distribute pseudo-identities.

<ins>Parameters:</ins>
  * the _**prefix**_, which represents the ID of the current research study.

## Installation Guide

1.	The service requires both [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/) to be installed.
2.	Download or clone the folder with the source code of the service from GitHub repository.
3.	Navigate to the root directory of the project **/pseudo-identity-generation-service** and open a terminal.
4.	Run the command `docker-compose up -d`.
5.	The Pseudo-Identity Generation Service is up and running and can generate pseudo-identities by invoking the URL `http://[URL]:8080/pseudo_identity?prefix=[studyID]`.

<ins>Response:</ins> The response will be a unique pseudo-identity for each citizen and for each research study.

```
{
    "prefix":"985324",
    "message":"The pseudo-identity was generated successfully.",
    "pseudo-identity":"9853241e0044c7f04fed8c9fa222dbf984a1d875a376843428fbc205b836ff685358859",
    "status":200
}
```

<ins>Error response:</ins> If the **prefix** parameter is either not valid or not provided at all, the response will be as follows.

```
{
    "message":"Prefix should contain only letters, numbers, underscores and dashes [a-z, A-Z, 0-9, _, -].",
    "status":400
}
```

```
{
    "message":"Prefix parameter should be provided.",
    "status":400
}
```
