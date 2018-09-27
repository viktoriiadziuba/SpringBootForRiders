package com.rider.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.HalfChapsDTO;


public interface HalfChapsService {
	
	void create(HalfChapsDTO dto, String authToken);
	
	void update(HalfChapsDTO dto);
	
	void delete(String halfChapsId);
	
	List<HalfChapsDTO> findAll();
	
	List<HalfChapsDTO> findAllHalfChaps(Pageable pageable);
	
	HalfChapsDTO findByHalfChapsId(String halfChapsId);
	
	void uploadImage(MultipartFile file, String halfChapsId);


}
