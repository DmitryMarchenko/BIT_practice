package rbc;

import javax.persistence.*;

@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "rate")
    private Double rate;

    public Rate() {}

    public Rate(String date, Double rate) {
        this.date = date;
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Double getRate() {
        return rate;
    }
}
