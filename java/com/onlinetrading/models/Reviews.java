package com.onlinetrading.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 5000)
    private String review;
    private String date;
    private int score;
    @ManyToOne(fetch = FetchType.LAZY)
    private Products product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Reviews(String review, String date, int score, Products product, Users owner) {
        this.review = review;
        this.date = date;
        this.score = score;
        this.product = product;
        this.owner = owner;
    }
}
