package com.bank.account.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bank.account.entity.Account;
import com.bank.account.service.AccountServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AccountControllerTest {
	
	private static final String ID = "23jk4hid23hf";
	
	private static WebTestClient webTestClient;
	
	@Mock
	private static AccountServiceImpl accountService;
	
	@BeforeAll
	public static void setUp() {
		accountService = mock(AccountServiceImpl.class);
		webTestClient = WebTestClient.bindToController(new AccountController(accountService))
									 .configureClient()
									 .baseUrl("/account")
									 .build();
	}
	
	@Test
	void getAccountsTest() {
		Account account = new Account();
		account.setId(ID);
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Flux<Account> accounts = Flux.just(account, account);
		
		Mockito.when(accountService.getAll()).thenReturn(accounts);
		
		Flux<Account> responseBody = webTestClient.get()
												  .exchange()
												  .expectStatus().isOk()
												  .returnResult(Account.class)
												  .getResponseBody();
		
		StepVerifier.create(responseBody)
					.expectNext(account)
					.expectNext(account)
					.verifyComplete();
	}
	
	@Test
	void getByIdClientTest() {
		Account account = new Account();
		account.setId(ID);
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Flux<Account> accounts = Flux.just(account, account);
		
		Mockito.when(accountService.getAllByIdClient(account.getIdClient())).thenReturn(accounts);
		
		Flux<Account> responseBody = webTestClient.get()
												  .uri("/byClient/{id}", account.getIdClient())
												  .exchange()
												  .expectStatus().isOk()
												  .returnResult(Account.class)
												  .getResponseBody();
		
		StepVerifier.create(responseBody)
					.expectNext(account)
					.expectNext(account)
					.verifyComplete();
	}
	
	@Test
	void getAccountByIdTest() {
		Account account = new Account();
		account.setId(ID);
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mockito.when(accountService.getAccountById(ID)).thenReturn(Mono.just(account));
		
		webTestClient.get().uri("/{id}", ID)
						   .exchange()
						   .expectStatus().isOk();
	}
	
	@Test
	void postAccountTest() {
		Account account = new Account();
		account.setId(ID);
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mono<Account> accountDto = Mono.just(account);
		
		Mockito.when(accountService.save(account)).thenReturn(accountDto);
		
		webTestClient.post().body(Mono.just(account), Account.class)
							.exchange()
							.expectStatus().isOk();
	}
	
	@Test
	void updAccountTest() {
		Account account = new Account();
		account.setId(ID);
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mono<Account> accountDto = Mono.just(account);
		
		Mockito.when(accountService.save(account)).thenReturn(accountDto);
		
		webTestClient.put().body(Mono.just(account), Account.class)
							.exchange()
							.expectStatus().isOk();
	}
	
	@Test
	void associateCardTest() {
		Account account = new Account();
		account.setId(ID);
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mono<Account> accountDto = Mono.just(account);
		
		Mockito.when(accountService.associateWithCard(account.getId(), account.getIdCard())).thenReturn(accountDto);
		
		webTestClient.post().uri("/associate-card/{idAccount}/{idCard}", ID, account.getIdCard())
							.body(Mono.just(account), Account.class)
							.exchange()
							.expectStatus().isOk();
	}
	
	@Test
	void dltAccountTest() {
		Mockito.doNothing().when(accountService).delete(anyString());
		
		webTestClient.delete().uri("/{id}", ID)
							  .exchange()
							  .expectStatus().isOk();
	}
	
}
