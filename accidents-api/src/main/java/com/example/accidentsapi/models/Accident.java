package com.example.accidentsapi.models;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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


    public Accident(String date, String time, String borough, String zipCode, Double latitude, Double longitude, String notes) {
        this.date = date;
        this.time = time;
        this.borough = borough;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.notes = notes;
    }


}
