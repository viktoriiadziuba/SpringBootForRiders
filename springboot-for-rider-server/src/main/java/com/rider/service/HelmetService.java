package com.rider.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.HelmetDTO;

public interface HelmetService {

	void create(HelmetDTO dto, String authToken);
	
	void update(HelmetDTO dto);
	
	void delete(String helmetId);
	
	List<HelmetDTO> findAll();
	
	List<HelmetDTO> findAllHelmets(Pageable pageable);
	
	HelmetDTO findByHelmetId(String helmetId);
	
	void uploadImage(MultipartFile file, String helmetId);

}
