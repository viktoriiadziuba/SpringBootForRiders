package com.rider.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.RiderAmmunitionDTO;

public interface RiderAmmunitionService {
	
	void create(RiderAmmunitionDTO dto, String authToken);
	
	void update(RiderAmmunitionDTO dto);
	
	void delete(String riderAmmunitionId);
	
	List<RiderAmmunitionDTO> findAll();
	
	List<RiderAmmunitionDTO> findAllRiderAmmunition(Pageable pageable);
	
	RiderAmmunitionDTO findByRiderAmmunitionId(String riderAmmunitionId);
	
	List<RiderAmmunitionDTO> findByType(String type);
	
	void uploadImage(MultipartFile file, String riderAmmunitionId);

}
