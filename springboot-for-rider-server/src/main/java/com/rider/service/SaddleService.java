package com.rider.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.SaddleDTO;

public interface SaddleService {

	void create(SaddleDTO dto, String authToken);
	
	void update(SaddleDTO dto);
	
	void delete(String saddleId);
	
	List<SaddleDTO> findAll();
	
	List<SaddleDTO> findAllSaddles(Pageable pageable);
	
	SaddleDTO findBySaddleId(String saddleId);
	
	List<SaddleDTO> findBySize(float size);
	
	void uploadImage(MultipartFile file, String saddleId);
}
