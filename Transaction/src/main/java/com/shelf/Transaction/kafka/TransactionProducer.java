package com.shelf.Transaction.kafka;

//import java.util.Properties;

import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.shelf.Core.models.Transaction;


@Service
public class TransactionProducer {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProducer.class); 
	
	
	private KafkaTemplate<String, Transaction> kafkaTemplate;
	
	public TransactionProducer(NewTopic topic, KafkaTemplate<String,Transaction> kafkaTemplate) {		
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(Transaction transaction) {		
		
		LOGGER.info(String.format("Tansaction Placed %s", transaction.toString()));			
			
//		Properties props = new Properties();
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, "HelloProducer");
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        
		this.kafkaTemplate.send("transaction", transaction);															
	}
}