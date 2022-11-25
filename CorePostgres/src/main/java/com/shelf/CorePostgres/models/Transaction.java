package com.shelf.CorePostgres.models;

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
	private Wallet wallet;
	private User user;
	private List<Boolean> userSelection;
	private LocalDateTime timeCreated;

}
