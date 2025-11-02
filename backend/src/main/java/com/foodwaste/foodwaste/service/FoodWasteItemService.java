package com.foodwaste.foodwaste.service;

import com.foodwaste.foodwaste.model.FoodWasteItem;
import com.foodwaste.foodwaste.repository.FoodWasteItemRepository;
import com.foodwaste.foodwaste.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodWasteItemService {
    
    @Autowired
    private FoodWasteItemRepository wasteItemRepository;
    
    public List<FoodWasteItem> getAllWasteItems() {
        return wasteItemRepository.findAll();
    }
    
    public FoodWasteItem getWasteItemById(Long id) {
        return wasteItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Waste Item not found with id: " + id));
    }
    
    public List<FoodWasteItem> getWasteItemsByDonor(Long donorId) {
        return wasteItemRepository.findByDonorId(donorId);
    }
    
    public List<FoodWasteItem> getWasteItemsByProcessedStatus(boolean processed) {
        return wasteItemRepository.findByProcessed(processed);
    }
    
    public List<FoodWasteItem> getWasteItemsByType(String wasteType) {
        return wasteItemRepository.findByWasteType(wasteType);
    }
    
    public FoodWasteItem createWasteItem(FoodWasteItem wasteItem) {
        return wasteItemRepository.save(wasteItem);
    }
    
    public FoodWasteItem updateWasteItem(Long id, FoodWasteItem itemDetails) {
        FoodWasteItem item = getWasteItemById(id);
        item.setWeight(itemDetails.getWeight());
        item.setExpirationDate(itemDetails.getExpirationDate());
        item.setWasteType(itemDetails.getWasteType());
        item.setProcessed(itemDetails.isProcessed());
        return wasteItemRepository.save(item);
    }
    
    public FoodWasteItem markAsProcessed(Long id, Long processorId) {
        FoodWasteItem item = getWasteItemById(id);
        item.setProcessed(true);
        return wasteItemRepository.save(item);
    }
    
    public void deleteWasteItem(Long id) {
        FoodWasteItem item = getWasteItemById(id);
        wasteItemRepository.delete(item);
    }
}