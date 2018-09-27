package com.rider.service;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.BreechesDTO;

public interface BreechesService {
	
	void create(BreechesDTO dto, String authToken);
	
	void update(BreechesDTO dto);
	
	void delete(String breechesId);
	
	List<BreechesDTO> findAll();
	
	List<BreechesDTO> findAllBreeches(Pageable pageable);
	
	BreechesDTO findByBreechesId(String breechesId);
	
	void uploadImage(MultipartFile file, String horseId);

}
