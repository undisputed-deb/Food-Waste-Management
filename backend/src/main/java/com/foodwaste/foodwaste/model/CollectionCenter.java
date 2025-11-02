package com.foodwaste.foodwaste.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collection_centers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Double maxCapacity; // in kilograms
    
    @Column(nullable = false)
    private Double currentCapacity = 0.0;
    
    @ManyToMany(mappedBy = "collectionCenters")
    private List<FoodDonor> donors = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "processor_id")
    private Processor processor;
}
