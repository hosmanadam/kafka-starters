# Customize zookeeper.properties
dataDir=C:/Kafka/data/zookeeper

# Customize server.properties
log.dirs=C:/Kafka/data/kafka-logs
num.partitions=3

# Start Zookeeper
zookeeper-server-start.bat $KAFKA_HOME/config/zookeeper.properties

# Start Broker
kafka-server-start.bat $KAFKA_HOME/config/server.properties

# Create topic
kafka-topics.bat --bootstrap-server localhost:9092 --topic topic_1 --create --partitions 3 --replication-factor 1

# List topics
kafka-topics.bat --bootstrap-server localhost:9092 --list

# Describe topic
kafka-topics.bat --bootstrap-server localhost:9092 --topic topic_1 --describe

# Start producer
kafka-console-producer.bat --broker-list localhost:9092 --topic topic_1

# Start 1 consumer
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic topic_1 --from-beginning

# Create group AND
# Start multiple consumers (repeat command in new terminals)
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic topic_1 --group group_1

# List groups
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list

# Burn the house down (because Windows)
rm -r $KAFKA_HOME/data/kafka-logs $KAFKA_HOME/data/zookeeper
