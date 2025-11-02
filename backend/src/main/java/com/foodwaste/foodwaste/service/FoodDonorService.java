package com.foodwaste.foodwaste.service;

import com.foodwaste.foodwaste.model.FoodDonor;
import com.foodwaste.foodwaste.repository.FoodDonorRepository;
import com.foodwaste.foodwaste.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodDonorService {
    
    @Autowired
    private FoodDonorRepository foodDonorRepository;
    
    public List<FoodDonor> getAllDonors() {
        return foodDonorRepository.findAll();
    }
    
    public FoodDonor getDonorById(Long id) {
        return foodDonorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food Donor not found with id: " + id));
    }
    
    public List<FoodDonor> getDonorsByUserId(Long userId) {
        return foodDonorRepository.findByUserId(userId);
    }
    
    public FoodDonor createDonor(FoodDonor donor) {
        return foodDonorRepository.save(donor);
    }
    
    public FoodDonor updateDonor(Long id, FoodDonor donorDetails) {
        FoodDonor donor = getDonorById(id);
        donor.setName(donorDetails.getName());
        donor.setAddress(donorDetails.getAddress());
        donor.setContactInfo(donorDetails.getContactInfo());
        return foodDonorRepository.save(donor);
    }
    
    public void deleteDonor(Long id) {
        FoodDonor donor = getDonorById(id);
        foodDonorRepository.delete(donor);
    }
}