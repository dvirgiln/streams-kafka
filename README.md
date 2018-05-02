# Goal
* Create kafka infrastructure using docker
* Create one simple producer and consumer.

# Instructions usage using docker-compose
    1. sbt docker:publishLocal
    2. docker-compose  -f docker-compose-spotify.yml up -d
    3. docker ps
    4. docker logs -f $consumer_container

# Running consumer and producer outside of docker
    1. Modify the docker-compose-spotify.yml and the producer main and consumer main to point to localhost:9092
    2. Run the kafka-spotify image alone: using docker run, or commenting the code in the yml file related the consumer and producer
    
# Cluster monitoring
Use kafka-manager to monitor kafka. Very useful.

        https://github.com/yahoo/kafka-manager

Start localhost:9000 in the browser.


# Useful links:
Usage of spotify docker image:

        https://github.com/spotify/docker-kafka
