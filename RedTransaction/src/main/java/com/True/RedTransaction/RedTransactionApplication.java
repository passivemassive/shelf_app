package com.True.RedTransaction;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.True.RedTransaction.event.RedTransaction;

@SpringBootApplication
@EnableKafkaStreams
public class RedTransactionApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedTransactionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RedTransactionApplication.class, args);
	}

	@Bean
    public NewTopic redTransaction() {
        return TopicBuilder.name("red-transaction")
                .partitions(3)
                .build();
    }
	
	@Bean
    public NewTopic blueTransaction() {
        return TopicBuilder.name("blue-transaction")
                .partitions(3)
                .build();
    }
	
	 @Bean
	    public ProducerFactory<Integer, RedTransaction> stringProducerFactory() {
	        Map<String, Object> configProps = new HashMap<>();	        
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
	        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	        return new DefaultKafkaProducerFactory<>(configProps);
	    }
	
	@Bean
    public KafkaTemplate<Integer, RedTransaction> stringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }
	
	@Bean
    public KStream<Integer, RedTransaction> stream(StreamsBuilder builder) {
        JsonSerde<RedTransaction> redTransactionSerde = new JsonSerde<>(RedTransaction.class);
        KStream<Integer, RedTransaction> stream = builder
                .stream("red-transaction", Consumed.with(Serdes.Integer(), redTransactionSerde));        
        return stream;
    }
	
	@Bean
    public KTable<Integer, RedTransaction> table(StreamsBuilder builder) {
        KeyValueBytesStoreSupplier store =
                Stores.persistentKeyValueStore("red-transaction");
        JsonSerde<RedTransaction> redTransactionSerde = new JsonSerde<>(RedTransaction.class);
        KStream<Integer, RedTransaction> stream = builder
                .stream("red-transaction", Consumed.with(Serdes.Integer(), redTransactionSerde));
        return stream.toTable(Materialized.<Integer, RedTransaction>as(store)
                .withKeySerde(Serdes.Integer())
                .withValueSerde(redTransactionSerde));
    }
	
}
