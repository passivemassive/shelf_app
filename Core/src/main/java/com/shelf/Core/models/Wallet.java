package com.shelf.Core.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

	private int walletId;
	private List<User> userList;
	private double amount;
	
}
