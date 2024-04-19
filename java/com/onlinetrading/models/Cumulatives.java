package com.onlinetrading.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cumulatives {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int min;
    private int max;
    private int discount;

    public Cumulatives(int min, int max, int discount) {
        this.min = min;
        this.max = max;
        this.discount = discount;
    }
}
