package com.shelf.KafkaStream.processor;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shelf.KafkaStream.event.Transaction;
import com.shelf.KafkaStream.serdes.TransactionSerdes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionProcessor {

	private static final Serde<String> STRING_SERDE = Serdes.String();
	
	@Autowired
	void buildPipeline(StreamsBuilder streamsBuilder) {
		
		KStream<String, Transaction> messageStream = streamsBuilder.stream("transaction", Consumed.with(STRING_SERDE, TransactionSerdes.serdes()))
				.peek((key, transaction)-> log.info("Transaction received key="+ key + " ."));
		
		
		messageStream.map((key , transaction) -> new KeyValue<>(Integer.toString(transaction.getTransactionId()) , transaction))
		.groupByKey(Grouped.with(STRING_SERDE, TransactionSerdes.serdes()))
		, Materialized.with(STRING_SERDE, TransactionSerdes.serdes()).as("finalTransaction"));
		
//		KTable<Integer, Transaction> transactionTable;
//		
//			
//		
//		transactionTable = (KTable<Integer, Transaction>) messageStream.map((key, transaction) -> new KeyValue<>(transaction.getTransactionId(), transaction));
		
		 
				
		
	}
}
