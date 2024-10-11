# Kafka Template

## Kafka Starter With Docker & Zookeeper
1. docker-compose.yml
    ```yaml
    version: '3'
    services:
      zookeeper:
        image: zookeeper:3.7
        hostname: zookeeper
        ports:
          - "2181:2181"
        environment:
          ZOO_MY_ID: 1
          ZOO_PORT: 2181
        volumes:
          - ./data/zookeeper/data:/data
          - ./data/zookeeper/datalog:/datalog
      kafka1:
        image: confluentinc/cp-kafka:7.0.0
        hostname: kafka1
        ports:
          - "9091:9091"
        environment:
          KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka1:19091,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9091
          KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
          KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
          KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
          KAFKA_BROKER_ID: 1
          KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
        volumes:
          - ./data/kafka1/data:/tmp/kafka-logs
        depends_on:
          - zookeeper
      kafka2:
        image: confluentinc/cp-kafka:7.0.0
        hostname: kafka2
        ports:
          - "9092:9092"
        environment:
          KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka2:19092,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092
          KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
          KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
          KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
          KAFKA_BROKER_ID: 2
          KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
        volumes:
          - ./data/kafka2/data:/tmp/kafka-logs
        depends_on:
          - zookeeper
      kafka3:
        image: confluentinc/cp-kafka:7.0.0
        hostname: kafka3
        ports:
          - "9093:9093"
        environment:
          KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka3:19093,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9093
          KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
          KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
          KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
          KAFKA_BROKER_ID: 3
          KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
        volumes:
          - ./data/kafka3/data:/tmp/kafka-logs
        depends_on:
          - zookeeper
      kafdrop:
        image: obsidiandynamics/kafdrop
        restart: "no"
        ports: 
          - "9000:9000"
        environment:
          KAFKA_BROKER_CONNECT: "kafka1:19091"
        depends_on:
          - kafka1
          - kafka2
          - kafka3
    ```
2. start docker-compose
   ```shell
   docker-compose up -d
   ```
3. create topic
   ```
   /bin/kafka-topics --create --topic test-events-1 \ 
   --bootstrap-server localhost:9091 \
   --partitions 1 --replication-factor 1
   ```


## Test
```text
GET: localhost:8080/test/HelloKafka
OUTPUT: TestConsumer.listenerConsumerRecord(topic = test-events-1, partition = 0, leaderEpoch = 0, offset = 6, CreateTime = 1728612851378, serialized key size = -1, serialized value size = 11, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = HelloKafka5)
```