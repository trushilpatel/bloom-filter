## Table of Contents

1. [Installation](#installation)
2. [Motivation](#motivation)
3. [Bloom Filter Application](#bloom-filter-application)
4. [Installation](#installation)
5. [Configurations](#configurations)
6. [Future Ideas](#future-ideas)

# Introduction
Welcome to the Bloom Filter Project! This repository contains a proof of concept (POC) implementation of a Bloom Filter. The goal of this project is to explore and answer several questions I had about implementing Bloom Filters in a distributed system and understanding the speed differences among various approaches.

# Motivation
Bloom Filters are an efficient probabilistic data structure used to test whether an element is a member of a set. They are particularly useful for applications where a small memory footprint and fast membership checks are crucial. The primary questions driving this project include:

**In-Memory Implementation**: What would be the performance of a Bloom Filter when implemented entirely in memory?
**Redis Integration**: How does integrating a Redis database affect the performance and scalability of a Bloom Filter?
**Multiple Backend Systems with Redis**: Can using multiple backend systems with Redis improve the overall performance and scalability of the Bloom Filter?
**Load Balancer Usage**: How can a load balancer be leveraged to distribute the workload and enhance the performance of a Bloom Filter in a distributed system?

# Bloom Filter Application

This project implements a Bloom Filter with two types of implementations: in-memory and using Redis. The infrastructure is managed using Docker and Docker Compose, and it includes Redis, Spring Boot backend services, and an NGINX load balancer.

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
3. **Stop Docker Compose**:
    ```sh
    cd project-root/infra/docker/
    docker-compose down
    ```

### Configurations

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

### Acsees Application

    Once the application is running, access it at `http://localhost:80`.

### Running the Tests

The project includes test cases to demonstrate the functionality of both types of Bloom Filter implementations.

1. **Run Unit Tests**:
    ```sh
    mvn test
    ```

## Future Ideas
- Scalable Bloom Filter: Develop a more scalable version of the Bloom Filter that can handle larger data sets and more complex queries efficiently.

## Contributing
Contributions are welcome! If you have any ideas or improvements, please feel free to open an issue or submit a pull request.
