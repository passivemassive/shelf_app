package com.shelf.Transaction.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelf.Core.models.Transaction;
import com.shelf.Core.models.User;
import com.shelf.Core.models.Wallet;
import com.shelf.Transaction.kafka.TransactionProducer;
import com.google.common.primitives.Booleans;

@RestController
@RequestMapping("/api")
public class TransactionController {

	private TransactionProducer transactionProducer;	
	private Transaction transaction;	
	private List<User> userList;
	private Wallet wallet;
	private List<Boolean> userSelection;	
	
	public TransactionController(TransactionProducer transactionProducer) {
		this.transactionProducer = transactionProducer;
	}	
	
	
	@PostMapping("/orders")
	public String Usertransaction2(@RequestBody User user) {
		
		//Hardcoded values for now
		//refer to core Postgres which uses db and services to access data
		transaction = new Transaction();
		userList = new ArrayList<User>();
		this.userSelection = new ArrayList<Boolean>();
		transaction.setUser(user);
		transaction.setTransactionId(user.getUserId());		
		userList.add(user);
		user= new User(1,"burd",300);
		userList.add(user);
		user= new User(3,"snoop",500);
		userList.add(user);
		wallet = new Wallet(1,userList,1000);
		transaction.setWallet(wallet);
		boolean userBoolean[] = { true, true, false};
		this.userSelection = Booleans.asList(userBoolean);
		transaction.setUserSelection(userSelection);
		transaction.setTimeCreated(LocalDateTime.now());					
		transactionProducer.sendMessage(transaction);				
		return "Transaction is successful";			
	}
}
