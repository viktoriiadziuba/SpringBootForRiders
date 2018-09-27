package com.rider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.RiderAmmunitionEntity;

@Repository
public interface RiderAmmunitionRepository extends JpaRepository<RiderAmmunitionEntity, Long>{
	
	RiderAmmunitionEntity findByRiderAmmunitionId(String riderAmmunitionId);
	
	List<RiderAmmunitionEntity> findByType(String type);

}
