package com.rider.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.SaddleDTO;
import com.rider.entity.SaddleEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.SaddleRepository;
import com.rider.repository.UserRepository;
import com.rider.service.SaddleService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class SaddleServiceImpl implements SaddleService {
	
	@Autowired
	private SaddleRepository saddleRepository;
	
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
	public void create(SaddleDTO dto, String authToken) {
		dto.setSaddleId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		SaddleEntity saddle = modelMapper.map(dto, SaddleEntity.class);
		saddle.setUser(user);
		
		saddleRepository.save(saddle);
		
	}

	@Override
	public void update(SaddleDTO dto) {
		saddleRepository.save(modelMapper.map(dto, SaddleEntity.class));		
	}

	@Override
	public void delete(String saddleId) {
		SaddleEntity saddle = saddleRepository.findBySaddleId(saddleId);
		saddleRepository.delete(saddle);
		
	}

	@Override
	public List<SaddleDTO> findAll() {
		return modelMapper.mapAll(saddleRepository.findAll(), SaddleDTO.class);
	}

	@Override
	public List<SaddleDTO> findAllSaddles(Pageable pageable) {
		Page<SaddleEntity> saddlesPage = 
				saddleRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<SaddleEntity> saddleEntities = saddlesPage.getContent();
		List<SaddleDTO> saddleDTOs =
				modelMapper.mapAll(saddleEntities, SaddleDTO.class);
		return saddleDTOs;		
	}

	@Override
	public SaddleDTO findBySaddleId(String saddleId) {
		return modelMapper.map(saddleRepository.findBySaddleId(saddleId), SaddleDTO.class);
	}

	@Override
	public List<SaddleDTO> findBySize(float size) {
		return modelMapper.mapAll(saddleRepository.findBySize(size), SaddleDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String saddleId) {
		String imageUrl = cloudinaryService.uploadFile(file, "saddles");
		SaddleEntity saddleEntity = saddleRepository.findBySaddleId(saddleId);
		saddleEntity.setImageUrl(imageUrl);
		saddleRepository.save(saddleEntity);
		
	}
	
	


}
