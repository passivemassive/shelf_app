package com.shelf.Transaction.kafka;

import org.apache.kafka.clients.admin.NewTopic;
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
			
		this.kafkaTemplate.send("transaction", transaction);															
	}
}