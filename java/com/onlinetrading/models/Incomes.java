package com.onlinetrading.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Incomes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int count;
    private int income;
    @OneToOne(fetch = FetchType.LAZY)
    private Products product;
    public Incomes(Products product) {
        this.count = 0;
        this.income = 0;
        this.product = product;
    }
}
