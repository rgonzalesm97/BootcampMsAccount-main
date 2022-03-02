package com.bank.account.model;


import lombok.Data;

@Data
public class DebitCard {

	private String id;
	private String idClient;
	private String cardNumber;
	private String idProduct;
}
