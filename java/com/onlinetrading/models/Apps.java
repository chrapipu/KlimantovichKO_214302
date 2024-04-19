package com.onlinetrading.models;

import com.onlinetrading.models.enums.AppStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Apps {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 5000)
    private String text;
    @Column(length = 5000)
    private String reason;
    private int discount;
    @Enumerated(EnumType.STRING)
    private AppStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Apps(String text, int discount, Users owner) {
        this.text = text;
        this.reason = "";
        this.discount = discount;
        this.status = AppStatus.WAITING;
        this.owner = owner;
    }
}
