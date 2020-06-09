# stream-processing-workshop

## References
1. Based on: https://github.com/Stream-Processing-with-Kafka-Streams/workshop

1. Introduction to kafka streams: https://www.youtube.com/watch?v=Z3JKCLG3VP4

1. Spring Tips: Spring Cloud Stream Kafka Streams : https://www.youtube.com/watch?v=YPDzcmqwCNo

## Get Started
1. Use java 8
2. Get the following [command](http://bit.ly/docker-kafka) so you can get a kafka / zookeeper running locally via docker.
3. Use maven build to generate some java classes:
```
  > mvn clean compile
```

### If you don't have Docker
You can get kafka and run it locally

Go to the kafka [quickstart](https://kafka.apache.org/quickstart):
1. Download the [latest](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.2.0/kafka_2.12-2.2.0.tgz)
2. Start Zookeeper (Kafka provides you with a single node zookeeper instance)
```
  > bin/zookeeper-server-start.sh config/zookeeper.properties
```
3. start Kafka
```
  > bin/kafka-server-start.sh config/server.properties
```

That will get your kafka running on the default port: localhost:9092 and zookeeper on: localhost:2181
These are also the ports used by default by Spring Cloud Stream
