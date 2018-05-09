## Iniciative
The initial idea is to create a repository where I can place different experiments related streaming libraries. The idea is to start with Akka Streams and then compare with Spark Streaming and other libraries.

The main goal is to reproduce a production environment in your local machine. For doing that, what it is better than Docker?

In this example it is define:
1. Kafka Broker using the spotify kafka image. Has been evaluated as well the [wurstmeister/kafka](https://hub.docker.com/r/wurstmeister/kafka/), but it works better this one.
2. A producer and consumer using the [Akka Streams Kafka](https://doc.akka.io/docs/akka-stream-kafka/current/home.html) library.
3. An example how to run Spark locally using docker
    - Create a spark cluster: spark master and spark workers using docker compose.
    - Create a consumer using spark streaming.
      - To create the consumer I tried to use the scala template available here
            lhttps://github.com/big-data-europe/docker-spark
      - The template was not up to date with the latest version of spark, so I created a new template available as part of this build. See the [spark-docker-template](https://github.com/dvirgiln/streams-kafka/tree/master/spark-docker-template) subproject.
## Initial Requisites
* Have sbt installed
* Have docker and docker-compose installed.

## Goal
* Create kafka infrastructure using docker
* Create spark cluster infrastructure using docker.
* Create one simple producer and consumer that write and consume random numbers.
* Explore Akka Streams and Spark Streaming.

## Instructions usage using docker-compose
    1. sbt docker
    2. docker-compose  -f docker-compose.yml up -d
    3. docker ps
    4. docker logs -f $consumer_container
    5. docker-compose  -f docker-compose.yml down

## Running consumer and producer outside of docker
    1. Modify the docker-compose.yml and the producer main and consumer main to point to localhost:9092
    2. Run the kafka-spotify image alone: using docker run, or commenting the code in the yml file related the consumer and producer

## Cluster monitoring
Use kafka-manager to monitor kafka. Very useful.

        https://github.com/yahoo/kafka-manager

Start localhost:9000 in the browser.

## Technical considerations
  * Usage of docker in one click:
    * Kafka broker
    * Spark cluster.
    * Akka producer.
    * Akka consumer.
    * Spark streaming consumer.
  * Creation of a scala template that allows to submit spark jobs to the spark master node.
  * All the projects are dockerized using sbt. Has been used the [marcuslonnberg plugin](https://github.com/marcuslonnberg/sbt-docker)
  * This is a good example how to run different subprojects using  different scala versions:
             [sbt-cross plugin](https://github.com/marcuslonnberg/sbt-docker)


## Useful links:
Usage of spotify docker image:

        https://github.com/spotify/docker-kafka

How to build multiprojects with different scala versions:
        
        https://github.com/lucidsoftware/sbt-cross

Spark Docker images from:
        https://github.com/big-data-europe/docker-spark
