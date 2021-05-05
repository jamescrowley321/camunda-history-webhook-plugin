# camunda-history-webhook-plugin
**Note** - This project is in a WIP status. The readme will be updated as it matures. The code is based on the examples provided by https://github.com/camunda-consulting/code

## Purpose
To allow history events to be piped to an external service via HTTP. 

### Design Considerations

Rather than using a Sprint Boot Application like this example - https://github.com/camunda-consulting/code/tree/master/snippets/engine-plugin-history-to-elasticsearch, this plugin is intended to be as simple as possible from a deployment standpoint and removes the need to have intimate knowledge of Spring Boot. As such, it makes use of its own configuration based on environment variables, rather than using the Spring Boot `@Configuration` decorator.

### Current Features

* Post to an endpoint
* Symmetric key encryption for JWTs

### Draft Roadmap

* Allow for configuration of map of events to different endpoints. While from a design perspective, making the recipient filter the events is ideal, in some cases in may not be practical.
* Support for the OAuth2 `client_credentials` grant for integration with OpenID Connect identity providers.
* Additional configuration options for JWT generation using symmetric signing keys
* Plugin versioning and support in the build system
* Automation of DB provisioning
* Performance optimizations
* Configuration of properties such as the history level ID and name
* Complete CI/CD pipeline
* Example Deployments
  * k8s
  * ECS

## Configuration

| Property         | Default | Description                              |
| ---------------- | ------- | ---------------------------------------- |
| JWT_ISSUER       |         | Issuer of the JWT for symmetric signing. |
| JWT_SECRET       |         | Secret to use to generate the JWT.       |
| WEBHOOK_BASE_URL |         | URL to POST history events to.           |

Refer to [https://github.com/camunda/docker-camunda-bpm-platform](https://github.com/camunda/docker-camunda-bpm-platform) for Camunda specific configurations.

