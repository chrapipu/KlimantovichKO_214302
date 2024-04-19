package com.onlinetrading.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int firstDis;

    private int pensionAge;
    private int pensionDis;

    private int birthday;
    private int birthdayDis;

    private int multi;
    private int multiDis;

    private boolean fiz_personal;
    private boolean fiz_cumulative;
    private boolean fiz_coupon;

    public Settings() {
        this.firstDis = 10;
        this.pensionAge = 60;
        this.pensionDis = 10;
        this.birthday = 10;
        this.birthdayDis = 10;
        this.multi = 50;
        this.multiDis = 10;
        this.fiz_personal = true;
        this.fiz_cumulative = true;
        this.fiz_coupon = true;
    }

    public boolean getCheckPension(String date) {
        if (date.isEmpty()) return false;
        String dateNow = LocalDateTime.now().toString().substring(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateNowDate = LocalDate.parse(dateNow, formatter);
        LocalDate dateDate = LocalDate.parse(date, formatter);

        return dateNowDate.getYear() - dateDate.getYear() >= 65;
    }

    public boolean getCheckBirthday(String date) {
        if (date.isEmpty()) return false;
        String dateNow = LocalDateTime.now().toString().substring(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateNowDate = LocalDate.parse(dateNow, formatter);
        LocalDate dateStart = LocalDate.parse(date, formatter);
        LocalDate dateEnd = LocalDate.parse(date, formatter);
        LocalDate dateDate = LocalDate.parse(date, formatter);

        dateStart = dateStart.plusYears(dateNowDate.getYear() - dateStart.getYear());
        dateEnd = dateEnd.plusYears(dateNowDate.getYear() - dateEnd.getYear());
        dateDate = dateDate.plusYears(dateNowDate.getYear() - dateDate.getYear());

        dateStart = dateStart.minusDays(birthday);
        dateEnd = dateEnd.plusDays(birthday);

        return dateStart.isBefore(dateDate) && dateEnd.isAfter(dateDate);
    }
}
