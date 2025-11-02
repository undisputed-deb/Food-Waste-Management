package com.foodwaste.foodwaste.controller;

import com.foodwaste.foodwaste.model.CollectionCenter;
import com.foodwaste.foodwaste.service.CollectionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-centers")
@CrossOrigin(origins = "*")
public class CollectionCenterController {
    
    @Autowired
    private CollectionCenterService centerService;
    
    @GetMapping
    public List<CollectionCenter> getAllCenters() {
        return centerService.getAllCenters();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CollectionCenter> getCenterById(@PathVariable Long id) {
        return ResponseEntity.ok(centerService.getCenterById(id));
    }
    
    @GetMapping("/processor/{processorId}")
    public List<CollectionCenter> getCentersByProcessorId(@PathVariable Long processorId) {
        return centerService.getCentersByProcessorId(processorId);
    }
    
    @PostMapping
    public CollectionCenter createCenter(@RequestBody CollectionCenter center) {
        return centerService.createCenter(center);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CollectionCenter> updateCenter(@PathVariable Long id, @RequestBody CollectionCenter centerDetails) {
        return ResponseEntity.ok(centerService.updateCenter(id, centerDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return ResponseEntity.noContent().build();
    }
}