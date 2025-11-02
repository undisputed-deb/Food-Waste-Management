package com.foodwaste.foodwaste.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_donors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDonor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String contactInfo;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FoodWasteItem> wasteItems = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "donor_collection_center",
        joinColumns = @JoinColumn(name = "donor_id"),
        inverseJoinColumns = @JoinColumn(name = "collection_center_id")
    )
    private List<CollectionCenter> collectionCenters = new ArrayList<>();
}