package com.rider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.BreechesEntity;

@Repository
public interface BreechesRepository extends JpaRepository<BreechesEntity, Long>{
	
	BreechesEntity findByBreechesId(String breechesId);

}
