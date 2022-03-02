package com.bank.account.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("account")
public class Account {
    @Id
    private String id;
    private String idClient;
    private String idCard;
    private String typeAccount;
    private String accountNumber;
    private Double balance;
    private Double maintenance;
    private Integer monthlyMovements;
    private Double commission;

}
