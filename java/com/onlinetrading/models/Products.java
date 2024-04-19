package com.onlinetrading.models;

import com.onlinetrading.models.enums.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String file;
    private int count;
    private int price;
    private int discount;

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @OneToOne(cascade = CascadeType.ALL)
    private Incomes income;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Carts> carts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Reviews> reviews;
    @ManyToOne
    private Category category;

    public Products(String name, int count, int price, int discount, String file, Category category) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.discount = discount;
        this.file = file;
        this.income = new Incomes(this);
        this.category = category;
    }

    public int getScore() {
        if (reviews.isEmpty()) return 0;
        return reviews.stream().reduce(0, (i, review) -> i + review.getScore(), Integer::sum) / reviews.size();
    }

    public int getPriceWithDiscount() {
        if (discount == 0) {
            return price;
        } else {
            return price - (price / discount);
        }
    }
}
