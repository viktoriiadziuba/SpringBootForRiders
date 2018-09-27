package com.rider.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.HorseEntity;

@Repository
public interface HorseRepository extends JpaRepository<HorseEntity, Long>{
	
	List<HorseEntity> findByBreed(String breed);
	
	HorseEntity findByHorseId(String horseId);

}
