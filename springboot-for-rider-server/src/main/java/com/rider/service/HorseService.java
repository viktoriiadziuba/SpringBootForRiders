package com.rider.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.HorseDTO;

public interface HorseService {
	
	void create(HorseDTO dto, String authToken);
	
	void update(HorseDTO dto);
	
	void delete(String horseId);
	
	List<HorseDTO> findAll();
	
	List<HorseDTO> findAllHorses(Pageable pageable);
	
	HorseDTO findByHorseId(String horseId);
	
	List<HorseDTO> findByHorseBreed(String breed);
	
	void uploadImage(MultipartFile file, String horseId);

}
