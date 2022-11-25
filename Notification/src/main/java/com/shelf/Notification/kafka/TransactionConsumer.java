package com.shelf.Notification.kafka;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.shelf.Core.models.Transaction;


@Service
public class TransactionConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConsumer.class);
	

	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	
	private long seconds;
	
	private LocalDateTime time1;
	private LocalDateTime time2;
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void Consume(@Payload Transaction transaction) {		
		
		transactions.add(transaction);
		LOGGER.info(String.format("Length of the queue is  %d",transactions.size()));
		List<Transaction> transactionOne = transactions.stream().filter(t -> t.getTransactionId()==2).collect(Collectors.toList());		
		if(transactionOne.size()>=2) {			
			
				time1 = transactionOne.get(transactionOne.size()-1).getTimeCreated();
				time2 = transactionOne.get(transactionOne.size()-2).getTimeCreated();		
				seconds = ChronoUnit.SECONDS.between(time2, time1);
				if(seconds<2)
				{
					LOGGER.info(String.format(" Transactions are rapidly happening (within %d seconds)",seconds));					
				}	
				else {
					LOGGER.info(String.format(" last transaction for this transaction Id was  %d seconds before",seconds));	
					LOGGER.info(String.format("Transaction with the current ID succesful %s",transactionOne.get(transactions.size()-1).toString()));
				}
			}		
		else {				
				LOGGER.info(String.format("Transaction is succesful %s",transactions.get(transactions.size()-1).toString()));
			}			
		
		}			
	}		


