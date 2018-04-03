package com.example.cityofnewyorkbackend.models;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity @Table(name = "ACCIDENTS")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE")
    private String date;

    @Column(name = "TIME")
    private String time;

    @Column(name = "BOROUGH")
    private String borough;

    @Column(name= "ZIP_CODE")
    private String zipCode;

    @Column(name="LATITUDE")
    private Double latitude;

    @Column(name="LONGITUDE")
    private Double longitude;

    @Column(name="NOTES")
    private String notes;

    @Column(name="number_of_persons_injured")
    private Integer number_of_persons_injured;

    @Column(name="number_of_persons_killed")
    private Integer number_of_persons_killed;

    public Accident(String date, String time, String borough, String zipCode, Double latitude, Double longitude, String notes, Integer number_of_persons_injured, Integer number_of_persons_killed) {
        this.date = date;
        this.time = time;
        this.borough = borough;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.notes = notes;
        this.number_of_persons_injured = number_of_persons_injured;
        this.number_of_persons_killed = number_of_persons_killed;
    }


}
