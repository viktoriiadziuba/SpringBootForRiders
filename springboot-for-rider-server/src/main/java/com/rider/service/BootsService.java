package com.rider.service;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.BootsDTO;

public interface BootsService {
	
	void create(BootsDTO dto, String authToken);
	
	void update(BootsDTO dto);
	
	void delete(String bootsId);
	
	List<BootsDTO> findAll();
	
	List<BootsDTO> findAllBoots(Pageable pageable);
	
	List<BootsDTO> findBySize(float size);
	
	BootsDTO findByBootsId(String bootsId);
	
	void uploadImage(MultipartFile file, String horseId);

}
