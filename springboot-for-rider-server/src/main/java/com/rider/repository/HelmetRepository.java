package com.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.HelmetEntity;

@Repository
public interface HelmetRepository extends JpaRepository<HelmetEntity, Long> {
	
	HelmetEntity findByHelmetId(String helmetId);

}
