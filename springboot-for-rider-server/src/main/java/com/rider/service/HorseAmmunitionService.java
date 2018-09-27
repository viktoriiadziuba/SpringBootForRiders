package com.rider.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.HorseAmmunitionDTO;


public interface HorseAmmunitionService {
	
	void create(HorseAmmunitionDTO dto, String authToken);
	
	void update(HorseAmmunitionDTO dto);
	
	void delete(String horseAmmunitionId);
	
	List<HorseAmmunitionDTO> findAll();
	
	List<HorseAmmunitionDTO> findAllHorseAmmunition(Pageable pageble);
	
	HorseAmmunitionDTO findByHorseAmmunitionId(String horseAmmunitionId);
	
	List<HorseAmmunitionDTO> findByType(String type);
	
	void uploadImage(MultipartFile file, String horseAmmunitionId);


}
