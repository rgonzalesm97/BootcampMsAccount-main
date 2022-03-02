package com.bank.account.proxy;

import org.springframework.web.reactive.function.client.WebClient;

import com.bank.account.model.Client;
import com.bank.account.model.DebitCard;

import reactor.core.publisher.Mono;

public class AccountProxy {
	
	private final WebClient.Builder webClientBuilder = WebClient.builder();
	
	public Mono<Client> getClient(String idClient){
		return webClientBuilder.build()
								.get()
								.uri("http://localhost:8090/client/{idClient}", idClient)
								.retrieve()
								.bodyToMono(Client.class);
	}
	
	public Mono<DebitCard> getDebitCard(String idDebitCard){
		return webClientBuilder.build()
								.get()
								.uri("http://localhost:8090/debit-card/{id}", idDebitCard)
								.retrieve()
								.bodyToMono(DebitCard.class);
	}

}
