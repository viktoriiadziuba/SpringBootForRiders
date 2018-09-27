package com.rider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rider.entity.BootsEntity;

@Repository
public interface BootsRepository extends JpaRepository<BootsEntity, Long>{
	
	BootsEntity findByBootsId(String bootsId);
	
	List<BootsEntity> findBySize(float size);

}
