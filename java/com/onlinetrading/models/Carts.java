package com.onlinetrading.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int count;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Products product;

    public Carts(int count, int price, Users owner, Products product) {
        this.count = count;
        this.price = price;
        this.owner = owner;
        this.product = product;
    }
}
