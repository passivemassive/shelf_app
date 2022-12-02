package com.True.RedTransaction.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.True.RedTransaction.event.RedTransaction;

@RestController
@RequestMapping("/transaction")
public class RedTransactionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedTransactionController.class);
	private KafkaTemplate<Integer, RedTransaction> template;
    private StreamsBuilderFactoryBean kafkaStreamsFactory;
    
    public RedTransactionController(KafkaTemplate<Integer, RedTransaction> template, StreamsBuilderFactoryBean kafkaStreamsFactory) {
    	this.kafkaStreamsFactory = kafkaStreamsFactory;
    	this.template = template;
    }
    
    @PostMapping("/create")
    public RedTransaction create(@RequestBody RedTransaction redTransaction) {
    	ReadOnlyKeyValueStore<Integer, RedTransaction> redTransactionStore = kafkaStreamsFactory.
    			getKafkaStreams().
    			store(StoreQueryParameters.fromNameAndType("red-transaction",
                QueryableStoreTypes.keyValueStore()));
    	redTransaction.setTimeCreated(LocalDateTime.now());
    	if(redTransactionStore.get(redTransaction.getRedMessageId())!=null)
    	{
    		int k =redTransaction.getRedMessageId();
    		long seconds = ChronoUnit.SECONDS.between(redTransactionStore.get(k).getTimeCreated(), redTransaction.getTimeCreated());
    		if(seconds>2)
    		{    			
    			LOGGER.info(String.format("This is a payload update for id %d and value %s", redTransaction.getRedMessageId(),redTransaction.getRedMessage()));
    		}
    		else
    			LOGGER.info(String.format("Payload update for id %d is very frequent ", k));
    		template.send("red-transaction", redTransaction.getRedMessageId(), redTransaction);    		
    	}
    	else {
    		template.send("red-transaction", redTransaction.getRedMessageId(), redTransaction);
        	LOGGER.info(String.format("Sent Payload with id %d and value %s", redTransaction.getRedMessageId(),redTransaction.getRedMessage()));
    	}    	
    	return redTransaction;
    }
    
    @GetMapping("/getall")
    public List<RedTransaction> getAll(){
    	List<RedTransaction> transactions = new ArrayList<>();   
    	ReadOnlyKeyValueStore<Integer, RedTransaction> redTransactionStore = kafkaStreamsFactory.
    			getKafkaStreams().
    			store(StoreQueryParameters.fromNameAndType("red-transaction",
                QueryableStoreTypes.keyValueStore()));
    	KeyValueIterator<Integer, RedTransaction> it = redTransactionStore.all();
        it.forEachRemaining(kv -> transactions.add(kv.value));
        return transactions;    	
    }

}
