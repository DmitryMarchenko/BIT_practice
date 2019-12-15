package app.entity;

import javax.persistence.*;

@Entity
@Table(name = "temperature")
public class Temperature {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "temperature")
    private Double temperature;

    public Temperature() {}

    public Temperature(String date, Double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Double getTemperature() {
        return temperature;
    }
}
