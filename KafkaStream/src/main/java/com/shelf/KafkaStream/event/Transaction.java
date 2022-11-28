package com.shelf.KafkaStream.event;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	private int transactionId;
	private List<Boolean> userSelection;	
	private LocalDateTime timeCreated;

}
