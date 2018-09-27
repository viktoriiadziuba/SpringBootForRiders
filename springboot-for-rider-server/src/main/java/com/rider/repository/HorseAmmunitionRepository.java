package com.rider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.HorseAmmunitionEntity;

@Repository
public interface HorseAmmunitionRepository extends JpaRepository<HorseAmmunitionEntity, Long>{
	
	HorseAmmunitionEntity findByHorseAmmunitionId(String horseAmmunitionId);
	
	List<HorseAmmunitionEntity> findByType(String type);

}
