package com.bank.account.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bank.account.entity.Account;


public class AccountFactory {
	
	static List<String> types = new ArrayList<String>();
	static {
		types.add("cuenta de ahorro");
		types.add("cuenta corriente");
		types.add("cuenta plazo fijo");
	}
	
	public static Optional<Account> validateAccount(Account account){
		return Optional.ofNullable(types.contains(account.getTypeAccount())?account:null);
	}
}
