package com.foodwaste.foodwaste.controller;

import com.foodwaste.foodwaste.model.FoodDonor;
import com.foodwaste.foodwaste.service.FoodDonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*")
public class FoodDonorController {
    
    @Autowired
    private FoodDonorService donorService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodDonor>> getAllDonors() {
        List<FoodDonor> donors = donorService.getAllDonors();
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(donors);
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodDonor> getDonorById(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonorById(id));
    }
    
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodDonor>> getDonorsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(donorService.getDonorsByUserId(userId));
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodDonor> createDonor(@RequestBody FoodDonor donor) {
        return ResponseEntity.ok(donorService.createDonor(donor));
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodDonor> updateDonor(@PathVariable Long id, @RequestBody FoodDonor donorDetails) {
        return ResponseEntity.ok(donorService.updateDonor(id, donorDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}