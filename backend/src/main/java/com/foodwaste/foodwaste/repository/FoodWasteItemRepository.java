package com.foodwaste.foodwaste.repository;

import com.foodwaste.foodwaste.model.FoodWasteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodWasteItemRepository extends JpaRepository<FoodWasteItem, Long> {
    List<FoodWasteItem> findByDonorId(Long donorId);
    List<FoodWasteItem> findByProcessed(boolean processed);
    List<FoodWasteItem> findByWasteType(String wasteType);
}