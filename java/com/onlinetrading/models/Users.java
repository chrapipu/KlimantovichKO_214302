package com.onlinetrading.models;

import com.onlinetrading.models.enums.Role;
import com.onlinetrading.models.enums.UserAdd;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String avatar;
    private String name;
    private String date;
    private int discount;

    private boolean legal_personal;
    private boolean legal_cumulative;
    private boolean legal_coupon;


    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserAdd userAdd;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Carts> carts;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Apps> apps;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coupons> coupons;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    public Users(String username, String firstname, String lastname, String password, Role role, String avatar) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.discount = 1;
        this.userAdd = UserAdd.REJECT;
        this.date = "";
        this.name = "";
        this.legal_personal = true;
        this.legal_cumulative = true;
        this.legal_coupon = true;
    }

    public int getTotalBuyPrice() {
        return carts.stream().reduce(0, (i, cart) -> i + cart.getPrice(), Integer::sum);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }
}
