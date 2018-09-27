package com.rider.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.BreechesDTO;
import com.rider.entity.BreechesEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.BreechesRepository;
import com.rider.repository.UserRepository;
import com.rider.service.BreechesService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class BreechesServiceImpl implements BreechesService {
	
	@Autowired
	private BreechesRepository breechesRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ObjectMapperUtils modelMapper;
	
	@Autowired
	private StringUtils stringUtils;
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Override
	public void create(BreechesDTO dto, String authToken) {
		dto.setBreechesId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		BreechesEntity breeches = modelMapper.map(dto, BreechesEntity.class);
		breeches.setUser(user);
		
		breechesRepository.save(breeches);	
		
	}

	@Override
	public void update(BreechesDTO dto) {
		breechesRepository.save(modelMapper.map(dto, BreechesEntity.class));
		
	}

	@Override
	public void delete(String breechesId) {
		BreechesEntity breeches = breechesRepository.findByBreechesId(breechesId);
		breechesRepository.delete(breeches);
		
	}

	@Override
	public List<BreechesDTO> findAll() {
		return modelMapper.mapAll(breechesRepository.findAll(), BreechesDTO.class);
	}

	@Override
	public List<BreechesDTO> findAllBreeches(Pageable pageable) {
		Page<BreechesEntity> breechesPage = 
				breechesRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<BreechesEntity> breechesEntities = breechesPage.getContent();
		List<BreechesDTO> breechesDTOs =
				modelMapper.mapAll(breechesEntities, BreechesDTO.class);
		return breechesDTOs;
	}

	@Override
	public BreechesDTO findByBreechesId(String breechesId) {
		return modelMapper.map(breechesRepository.findByBreechesId(breechesId), BreechesDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String breechesId) {
		String imageUrl = cloudinaryService.uploadFile(file, "breeches");
		BreechesEntity breechesEntity = breechesRepository.findByBreechesId(breechesId);
		breechesEntity.setImageUrl(imageUrl);
		breechesRepository.save(breechesEntity);
		
	}
	
	

}
