package com.rider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.SaddleEntity;

@Repository
public interface SaddleRepository extends JpaRepository<SaddleEntity, Long> {
	
	SaddleEntity findBySaddleId(String saddleId);
	
	List<SaddleEntity> findBySize(float size);

}
