# Iniciative
The initial idea is to create a repository where I can place different experiments related streaming libraries. The idea is to start with Akka Streams and then compare with Spark Streaming and other libraries.

The main goal is to reproduce a production environment in your local machine. For doing that, what it is better than Docker?

In this example it is define:
1. Kafka Broker using the spotify kafka image. Has been evaluated as well the [wurstmeister/kafka](https://hub.docker.com/r/wurstmeister/kafka/), but it works better this one.
2. A producer and consumer using the [Akka Streams Kafka](https://doc.akka.io/docs/akka-stream-kafka/current/home.html) library.

# Initial Requisites
* Have sbt installed
* Have docker and docker-compose installed.

# Goal
* Create kafka infrastructure using docker
* Create one simple producer and consumer that write and consume random numbers.

# Instructions usage using docker-compose
    1. sbt docker
    2. docker-compose  -f docker-compose.yml up -d
    3. docker ps
    4. docker logs -f $consumer_container
    5. docker-compose  -f docker-compose.yml down

# Running consumer and producer outside of docker
    1. Modify the docker-compose.yml and the producer main and consumer main to point to localhost:9092
    2. Run the kafka-spotify image alone: using docker run, or commenting the code in the yml file related the consumer and producer

# Cluster monitoring
Use kafka-manager to monitor kafka. Very useful.

        https://github.com/yahoo/kafka-manager

Start localhost:9000 in the browser.


# Useful links:
Usage of spotify docker image:

        https://github.com/spotify/docker-kafka
