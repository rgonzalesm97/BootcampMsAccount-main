package com.bank.account.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.bank.account.entity.Account;
import com.bank.account.model.Client;
import com.bank.account.model.DebitCard;
import com.bank.account.proxy.AccountProxy;
import com.bank.account.repository.IAccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AccountServiceImplTest {

	@Mock
	private static IAccountRepository accountRepo;
	
	@Mock
	private static AccountProxy proxy;
	
	private static AccountServiceImpl service;
	
	@BeforeAll
	public static void setUp() {
		accountRepo = mock(IAccountRepository.class);
		proxy = mock(AccountProxy.class);
		service = new AccountServiceImpl(accountRepo, proxy);
	}
		
	@Test
	void getAllTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mockito.when(accountRepo.findAll()).thenReturn(Flux.just(account, account));
		
		Flux<Account> result = service.getAll();
		
		StepVerifier.create(result)
		.expectNext(account)
		.expectNext(account)
		.verifyComplete();
	}
	
	@Test
	void getAccountByIdTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mockito.when(accountRepo.findById(account.getId())).thenReturn(Mono.just(account));
		
		Mono<Account> result = service.getAccountById(account.getId());
		
		StepVerifier.create(result).expectNext(account).verifyComplete();
	}
	
	@Test
	void getAllByIdClientTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("23jk4htypeAccount23hf");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mockito.when(accountRepo.findAllByIdClient(account.getIdClient())).thenReturn(Flux.just(account, account));
		
		Flux<Account> result = service.getAllByIdClient(account.getIdClient());
		
		StepVerifier.create(result)
		.expectNext(account)
		.expectNext(account)
		.verifyComplete();
	}
	
	@Test
	void saveCuentaAhorroTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("cuenta de ahorro");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Client client = new Client();
		client.setId("23jk4hidClient23hf");
		client.setName("Rodrigo");
		client.setProfile("VIP");
		client.setType("personal");
		
        Mockito.when(accountRepo.save(account)).thenReturn(Mono.just(account));
        Mockito.when(proxy.getClient(account.getIdClient())).thenReturn(Mono.just(client));
        Mockito.when(accountRepo.findByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount())).thenReturn(Mono.empty());
		
		Mono<Account> result = service.save(account);
		
		StepVerifier.create(result).expectNext(account).verifyComplete();
	}
	
	@Test
	void saveCuentaCorrienteTest() {//empresa
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("cuenta corriente");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Client client = new Client();
		client.setId("23jk4hidClient23hf");
		client.setName("Rodrigo");
		client.setProfile("PYME");
		client.setType("business");
		
        Mockito.when(accountRepo.save(account)).thenReturn(Mono.just(account));
        Mockito.when(proxy.getClient(account.getIdClient())).thenReturn(Mono.just(client));
		
		Mono<Account> result = service.save(account);
		
		StepVerifier.create(result).expectNext(account).verifyComplete();
	}
	
	@Test
	void saveCuentaPlazoFijoTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("cuenta plazo fijo");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Client client = new Client();
		client.setId("23jk4hidClient23hf");
		client.setName("Rodrigo");
		client.setProfile("VIP");
		client.setType("personal");
		
        Mockito.when(accountRepo.save(account)).thenReturn(Mono.just(account));
        Mockito.when(proxy.getClient(account.getIdClient())).thenReturn(Mono.just(client));
        Mockito.when(accountRepo.findByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount())).thenReturn(Mono.empty());
		
		Mono<Account> result = service.save(account);
		
		StepVerifier.create(result).expectNext(account).verifyComplete();
	}
	
	@Test
	void getAccountByIdClientAndTypeAccountTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("23jk4hidCard23hf");
		account.setTypeAccount("cuenta de ahorro");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		Mockito.when(accountRepo.findByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount())).thenReturn(Mono.just(account));
		
		Mono<Account> result = service.getAccountByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount());
		
		StepVerifier.create(result).expectNext(account).verifyComplete();
	}
	
	@Test
	void associateWithCardTest() {
		Account account = new Account();
		account.setId("23jk4hid23hf");
		account.setIdClient("23jk4hidClient23hf");
		account.setIdCard("");
		account.setTypeAccount("cuenta de ahorro");
		account.setAccountNumber("23jk4haccountNumber23hf");
		account.setBalance(Double.valueOf(28561.0));
		account.setMaintenance(Double.valueOf(35.0));
		account.setMonthlyMovements(Integer.valueOf(3));
		account.setMaintenance(Double.valueOf(46.5));
		
		DebitCard debitCard = new DebitCard();
		debitCard.setId("anifheIDjanilejs");
		debitCard.setIdClient("23jk4hidClient23hf");
		debitCard.setCardNumber("anifheCARDNUMBERjanilejs");
		debitCard.setIdProduct("anifheIDPRODUCTjanilejs");
		
		Client client = new Client();
		client.setId("23jk4hidClient23hf");
		client.setName("Rodrigo");
		client.setProfile("VIP");
		client.setType("personal");
		
		Mockito.when(accountRepo.findById(account.getId())).thenReturn(Mono.just(account));
		Mockito.when(proxy.getDebitCard(anyString())).thenReturn(Mono.just(debitCard));
		
		Mockito.when(accountRepo.save(account)).thenReturn(Mono.just(account));
        Mockito.when(proxy.getClient(account.getIdClient())).thenReturn(Mono.just(client));
        Mockito.when(accountRepo.findByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount())).thenReturn(Mono.empty());
		
		Mono<Account> result = service.associateWithCard(account.getId(), debitCard.getId());
		
		StepVerifier.create(result).expectNext(account).verifyComplete();
	}

}
