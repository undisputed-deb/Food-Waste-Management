package com.foodwaste.foodwaste.controller;

import com.foodwaste.foodwaste.model.FoodWasteItem;
import com.foodwaste.foodwaste.service.FoodWasteItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waste-items")
@CrossOrigin(origins = "*")
public class FoodWasteItemController {
    
    @Autowired
    private FoodWasteItemService wasteItemService;
    
    @GetMapping
    public List<FoodWasteItem> getAllWasteItems() {
        return wasteItemService.getAllWasteItems();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FoodWasteItem> getWasteItemById(@PathVariable Long id) {
        return ResponseEntity.ok(wasteItemService.getWasteItemById(id));
    }
    
    @GetMapping("/donor/{donorId}")
    public List<FoodWasteItem> getWasteItemsByDonor(@PathVariable Long donorId) {
        return wasteItemService.getWasteItemsByDonor(donorId);
    }
    
    @GetMapping("/processed/{status}")
    public List<FoodWasteItem> getWasteItemsByProcessedStatus(@PathVariable boolean status) {
        return wasteItemService.getWasteItemsByProcessedStatus(status);
    }
    
    @GetMapping("/type/{wasteType}")
    public List<FoodWasteItem> getWasteItemsByType(@PathVariable String wasteType) {
        return wasteItemService.getWasteItemsByType(wasteType);
    }
    
    @PostMapping
    public FoodWasteItem createWasteItem(@RequestBody FoodWasteItem wasteItem) {
        return wasteItemService.createWasteItem(wasteItem);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FoodWasteItem> updateWasteItem(@PathVariable Long id, @RequestBody FoodWasteItem itemDetails) {
        return ResponseEntity.ok(wasteItemService.updateWasteItem(id, itemDetails));
    }
    
    @PatchMapping("/{id}/process/{processorId}")
    public ResponseEntity<FoodWasteItem> markAsProcessed(@PathVariable Long id, @PathVariable Long processorId) {
        return ResponseEntity.ok(wasteItemService.markAsProcessed(id, processorId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteItem(@PathVariable Long id) {
        wasteItemService.deleteWasteItem(id);
        return ResponseEntity.noContent().build();
    }
}