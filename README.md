# health-apis-mock-vler

A [Spring Boot](https://spring.io/projects/spring-boot) microservice
that emulates the VLER service.
This can be used to provide synthetic VLER data outside the VA Intranet.

To invoke, make a get request to the `/direct/addresses` path. Example request:

```
http://localhost:9090/direct/addresses
```

Mock data is stored in JSON files within the repository that are loaded.

No application properties are required to run this application.
