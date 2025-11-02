package com.foodwaste.foodwaste.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "processors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Double maxProcessingCapacity; // in kilograms
    
    @OneToMany(mappedBy = "processor", cascade = CascadeType.ALL)
    private List<CollectionCenter> collectionCenters = new ArrayList<>();
    
    @OneToMany(mappedBy = "processor", cascade = CascadeType.ALL)
    private List<FoodWasteItem> processedItems = new ArrayList<>();
}
