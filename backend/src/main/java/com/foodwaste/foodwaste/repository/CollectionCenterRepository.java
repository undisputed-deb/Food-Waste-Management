package com.foodwaste.foodwaste.repository;

import com.foodwaste.foodwaste.model.CollectionCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CollectionCenterRepository extends JpaRepository<CollectionCenter, Long> {
    List<CollectionCenter> findByProcessorId(Long processorId);
}