package com.foodwaste.foodwaste.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "food_waste_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodWasteItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double weight; // in kilograms
    
    @Column(nullable = false)
    private LocalDate expirationDate;
    
    @Column(nullable = false)
    private String wasteType; // vegetables, dairy, grains, etc.
    
    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    @JsonBackReference
    private FoodDonor donor;
    
    @ManyToOne
    @JoinColumn(name = "processor_id")
    private Processor processor;
    
    @Column(nullable = false)
    private boolean processed = false;
}