package com.shelf.KafkaStream.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkastream")
public class TransactionController {

	@Autowired
	private StreamsBuilderFactoryBean factoryBean;
	
	@GetMapping("/{id}")
	public ResponseEntity<Long> getTransactions(@PathVariable int id){
		KafkaStreams kafkaStream = factoryBean.getKafkaStreams();
		ReadOnlyKeyValueStore<int>
	}
}
