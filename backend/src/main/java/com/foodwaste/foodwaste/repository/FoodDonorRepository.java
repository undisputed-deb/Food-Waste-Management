package com.foodwaste.foodwaste.repository;

import com.foodwaste.foodwaste.model.FoodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodDonorRepository extends JpaRepository<FoodDonor, Long> {
    List<FoodDonor> findByUserId(Long userId);
}