package com.foodwaste.foodwaste.service;

import com.foodwaste.foodwaste.model.CollectionCenter;
import com.foodwaste.foodwaste.repository.CollectionCenterRepository;
import com.foodwaste.foodwaste.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollectionCenterService {
    
    @Autowired
    private CollectionCenterRepository collectionCenterRepository;
    
    public List<CollectionCenter> getAllCenters() {
        return collectionCenterRepository.findAll();
    }
    
    public CollectionCenter getCenterById(Long id) {
        return collectionCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection Center not found with id: " + id));
    }
    
    public List<CollectionCenter> getCentersByProcessorId(Long processorId) {
        return collectionCenterRepository.findByProcessorId(processorId);
    }
    
    public CollectionCenter createCenter(CollectionCenter center) {
        return collectionCenterRepository.save(center);
    }
    
    public CollectionCenter updateCenter(Long id, CollectionCenter centerDetails) {
        CollectionCenter center = getCenterById(id);
        center.setLocation(centerDetails.getLocation());
        center.setMaxCapacity(centerDetails.getMaxCapacity());
        center.setCurrentCapacity(centerDetails.getCurrentCapacity());
        return collectionCenterRepository.save(center);
    }
    
    public void deleteCenter(Long id) {
        CollectionCenter center = getCenterById(id);
        collectionCenterRepository.delete(center);
    }
}