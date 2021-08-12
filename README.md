Source: The full source code of this project can be found here: https://github.com/dilipsundarraj1/kafka-for-developers-using-spring-boot

# Enable Lombok on Intellij
Goto > File > Settings > and search for > Annotation Processors. (See the screenshot Annotation Processors) > and Enable annotation processing

# Yaml code completion Intellij
Install the following plugin: Spring Assistant

# Setting Up Kafka
## Start Zookeeper and Kafka Broker
Start up the Zookeeper.
```
zookeeper-server-start.bat ..\..\config\zookeeper.properties
```
Start up the Kafka Broker.
```
kafka-server-start.bat ..\..\config\server.properties
```
## How to create a topic ?
```
kafka-topics.bat --create --topic test-topic -zookeeper localhost:2181 --replication-factor 1 --partitions 4

kafka-topics.bat --create --topic test-topic --partitions 1
```
## [](https://github.com/dilipsundarraj1/kafka-for-developers-using-spring-boot/blob/master/SetUpKafka.md#how-to-instantiate-a-console-producer-1)How to instantiate a Console Producer?
### Without Key
```
kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic
```
### With Key
```
kafka-console-producer.bat --broker-list localhost:9092 --topic test-topic --property "key.separator=-" --property "parse.key=true"
```
## How to instantiate a Console Consumer?
### Without Key
```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```
### With Key
```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning -property "key.separator= - " --property "print.key=true"
```
### With Consumer Group
```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --group <group-name>
```
## Setting Up Multiple Kafka Brokers

-   The first step is to add a new **server.properties**.    
-   We need to modify three properties to start up a multi broker set up.   
```
broker.id=<unique-broker-d>
listeners=PLAINTEXT://localhost:<unique-port>
log.dirs=/tmp/<unique-kafka-folder>
auto.create.topics.enable=false
```
-   Example config will be like below.
```
broker.id=1
listeners=PLAINTEXT://localhost:9093
log.dirs=/tmp/kafka-logs-1
auto.create.topics.enable=false
```
### Starting up the new Broker
-   Provide the new **server.properties** thats added.
```
./kafka-server-start.sh ../config/server-1.properties
```
```
./kafka-server-start.sh ../config/server-2.properties
```
# Advanced Kafka CLI operations:
-   Make sure you are inside the **bin/windows** directory.

## List the topics in a cluster
```
kafka-topics.bat --zookeeper localhost:2181 --list
```
## Describe topic
-   The below command can be used to describe all the topics.
```
kafka-topics.bat --zookeeper localhost:2181 --describe
```
-   The below command can be used to describe a specific topic.
```
kafka-topics.bat --zookeeper localhost:2181 --describe --topic <topic-name>
```
## Alter the min insync replica
```
kafka-topics.bat --alter --zookeeper localhost:2181 --topic library-events --config min.insync.replicas=2
```
## Delete a topic
```
kafka-topics.bat --zookeeper localhost:2181 --delete --topic <topic-name>
```
## How to view consumer groups
```
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list
```
### Consumer Groups and their Offset
```
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group console-consumer-27773
```
## Viewing the Commit Log
```
kafka-run-class.bat kafka.tools.DumpLogSegments --deep-iteration --files /tmp/kafka-logs/test-topic-0/00000000000000000000.log
```

## How to kill port
```

https://stackoverflow.com/questions/39632667/how-do-i-kill-the-process-currently-using-a-port-on-localhost-in-windows
```
