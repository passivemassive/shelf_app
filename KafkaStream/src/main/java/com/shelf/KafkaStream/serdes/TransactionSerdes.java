package com.shelf.KafkaStream.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import com.shelf.KafkaStream.event.*;

public class TransactionSerdes extends Serdes.WrapperSerde<Transaction>{
	
	public TransactionSerdes() {
		super(new JsonSerializer<>(), new JsonDeserializer<>(Transaction.class));
	}
	
	public static Serde<Transaction> serdes() {
        JsonSerializer<Transaction> serializer = new JsonSerializer<>();
        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

}
