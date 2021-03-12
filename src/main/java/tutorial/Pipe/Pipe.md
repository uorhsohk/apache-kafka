# To Run the Pipe Application

> this application wants to show the pipe feature.
> It gets an input from terminal and with the hellp of the Pipe Application will send the input text to output.
> To be more precise, it gets the input from user from terminal from topic streams-plaintext-input and sends it to streams-pipe-output topic.

### Download and extract Kafka
```
tar -xzf kafka_2.13-2.7.0.tgz
cd kafka_2.13-2.7.0
```

---

### Start the Kafka Environment

```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

---

### Create Two Topic

First Topic: **streams-plaintext-input**
Second Topic: **streams-pipe-output**

```
bin/kafka-topics.sh --create --topic streams-plaintext-input --bootstrap-server localhost:9092
bin/kafka-topics.sh --describe --topic streams-pipe-output --bootstrap-server localhost:9092
```

---

### Writing Events to first Topic [streams-plaintext-input]

```
bin/kafka-console-producer.sh --topic streams-plaintext-input --bootstrap-server localhost:9092
```

---

### Reading the Events [streams-pipe-output]

```
bin/kafka-console-consumer.sh --topic streams-pipe-output --from-beginning --bootstrap-server localhost:9092
```

---

### Start the Pipe Application
