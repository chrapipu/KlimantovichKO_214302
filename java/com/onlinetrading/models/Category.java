package com.onlinetrading.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Products> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public int getIncome() {
        return products.stream().reduce(0, (i, product) -> i + product.getIncome().getIncome(), Integer::sum);
    }

}
