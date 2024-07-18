# Bloom Filter

This project implements a Bloom Filter with two types of implementations: in-memory and using Redis. The infrastructure is managed using Docker and Docker Compose, and it includes Redis, Spring Boot backend services, and an NGINX load balancer.

## Table of Contents

1. [Installation](#installation)
2. [Configuration](#configuration)
3. [Usage](#usage)
4. [Running the Tests](#running-the-tests)
5. [Deployment](#deployment)
6. [Contributing](#contributing)

### Installation

Follow step-by-step guide to get a development environment running.

1. **Clone the repository**:
    ```sh
    git clone https://github.com/trushilpatel/bloom-filter.git
    cd bloom-filter
    ```

2. **Build and Run with Docker Compose**:
    ```sh
    cd project-root/infra/docker/
    docker-compose up --build
    ```

### Configuration

Configure the project, including environment variables and configuration files.
  
- **Spring Boot Configuration**: 
    Edit the `src/main/resources/application.properties` to set up the required configurations.

    ```sh
    export SPRING_PORT=8080
    export REDIS_HOST=redis
    export REDIS_PORT=6379
    # Add more environment variables as needed
    ```

- **Redis Configuration**: 
    Edit the `infra/docker/env/redis.env` to set up the required configurations.

    ```sh
    REDIS_HOST: "bloom-filter-redis"
    REDIS_PORT: 6379
    REDIS_TIMEOUT: 2000
    REDIS_PASSWORD: "mysecretpassword"
    REDIS_MAXTOTAL: 128
    REDIS_MAXIDLE: 128
    REDIS_MINIDLE: 16
    REDIS_TESTONBORROW: "true"
    REDIS_TESTONRETURN: "true"
    REDIS_TESTWHILEIDLE: "true"
    REDIS_TIMEEVICTIONRUNS: 30000
    REDIS_MINEVICTIONIDLE: 60000
    REDIS_NUMTESTSEVICTION: -1
    # Add more environment variables as needed
    ```

### Usage

    Once the application is running, access it at `http://localhost:80`.

### Running the Tests

The project includes test cases to demonstrate the functionality of both types of Bloom Filter implementations.

1. **Run Unit Tests**:
    ```sh
    mvn test
    ```

### Deployment

Additional notes about how to deploy this on a live system.

- **Deploy with Docker**:
    ```sh
    docker-compose -f docker-compose.prod.yml up --build
    ```

### Contributing

Guidelines for contributing to the project.

1. Fork the repository.
2. Create your feature branch:
    ```sh
    git checkout -b feature/fooBar
    ```
3. Commit your changes:
    ```sh
    git commit -m 'Add some fooBar'
    ```
4. Push to the branch:
    ```sh
    git push origin feature/fooBar
    ```
5. Open a Pull Request.
