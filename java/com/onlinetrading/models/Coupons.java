package com.onlinetrading.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int discount;
    private int count;
    private boolean unlimited;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Coupons(String name, int discount, int count, Users owner) {
        this.name = name;
        this.discount = discount;
        this.owner = owner;
        this.count = count;
        unlimited = count == 0;
    }
}
