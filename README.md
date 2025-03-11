# User and Group Service

PositionPal service designed to manage users and groups efficiently and effectively. This service provides a robust API to handle user authentication, authorization, and group management functionalities.

## Development

> [!WARNING]
> This repository depends on the [`shared-kernel`](https://github.com/orgs/position-pal/packages?repo_name=shared-kernel) package published on GitHub Packages, which requires authentication to be successfully resolved.
> In CI environments, credentials are automatically inherited from the context of the GitHub Actions workflow.
> However, to correctly build and run the project locally, you need to make sure to have configured your GitHub username and a valid personal access token either in the `gradle.properties` file or as environment variables:
>
> **Credential setup**:
>
>- **`gradle.properties`**:
>  Add your credentials to the `gradle.properties` file located in `GRADLE_USER_HOME` (`~/.gradle` on Unix and `C:\Users\YourUser\.gradle` on Windows) as follows:
>    ```properties
>    gpr.user=<USERNAME>
>    gpr.key=<TOKEN>
>    ```
>   For more information about `gradle.properties` file refer to the [Gradle documentation](https://docs.gradle.org/current/userguide/build_environment.html).
>
> - **Environment variables**:
>   Setup the following environment variables:
>     ```bash
>     export GPR_USER=<USERNAME>
>     export GPR_KEY=<TOKEN>
>     ```
> For more information about how to create a personal access token, refer to the [GitHub documentation](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens).
>
> In case you encounter any problem, please open a new issue in the repository.

## Pre-requisites

In order for the service to function properly, the following environment variables must be set and available at startup.
**However, they are not required for testing.**

| Variable Name       | Description                                                                                                                                                                                                                                    | Default     |
|---------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| `JWT_SECRET`        | The secret key used to sign the JWT tokens. Ensure the secret is stored and used securely, for example, by using Docker or Kubernetes secrets.                                                                                                 |             |
| `RABBITMQ_HOST`     | The host address of the RabbitMQ server where the message broker is running.                                                                                                                                                                   | `localhost` |
| `RABBITMQ_PORT`     | The port on which the RabbitMQ server is listening for incoming connections.                                                                                                                                                                   | `5672`      |
| `RABBITMQ_USERNAME` | The username used to authenticate with the RabbitMQ server. This should be an account with sufficient permissions to interact with the necessary queues and exchanges in the virtual host.                                                     | `guest`     |
| `RABBITMQ_PASSWORD` | The password associated with the `RABBITMQ_USERNAME`. This password is used in conjunction with the username for authentication purposes. Ensure the password is stored and used securely, for example, by using Docker or Kubernetes secrets. | `guest`     |
| `USER_SERVICE_PORT` | The port on which the gRPC server listens for incoming requests.                                                                                                                                                                               | `5052`      |
| `POSTGRES_DB`       | The name of the Postgres database to connect to.                                                                                                                                                                                               |             |
| `POSTGRES_USER`     | The username used to authenticate to the Postgres database instance.                                                                                                                                                                           |             |
| `POSTGRES_PASSWORD` | The password associated with the `POSTGRES_USER`. This password is used in conjunction with the username for authentication purposes. Ensure the password is stored and used securely, for example, by using Docker or Kubernetes secrets.     |             |
| `POSTGRES_HOST`     | The host address of the Postgres database instance.                                                                                                                                                                                            | `localhost` |
| `POSTGRES_PORT`     | The port on which the Postgres database instance is listening for incoming connections.                                                                                                                                                        | `5432`      |

An example of valid environment setup is shown below:

```env
JWT_SECRET: mUOoxn4zZQU6Vbpe1WFO7wou5YZe5zbsoSLVSlMGn4I= # don't worry, no secret exposed. This was randomly generated and is not used in production!
RABBITMQ_HOST: rabbitmq-broker
RABBITMQ_PORT: 5672
RABBITMQ_USERNAME: guest
RABBITMQ_PASSWORD: admin
POSTGRES_DB: "users_and_groups"
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
POSTGRES_HOST: postgres-db
USER_SERVICE_PORT: 5052
```

Moreover, the service requires a Postgres database. Before starting the service, make sure the database is properly configured with all the necessary tables.
The scripts to create the tables can be found in the following SQL scripts:
- [`./init.sql`](./init.sql)

## Documentation

The dokka documentation can be found [here](https://position-pal.github.io/user-service/).

Refer to the [project documentation](https://position-pal.github.io/docs/) for more details on the service implementation and the overall system architecture.
