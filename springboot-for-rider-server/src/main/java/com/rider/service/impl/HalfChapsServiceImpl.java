package com.rider.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.HalfChapsDTO;
import com.rider.entity.HalfChapsEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.HalfChapsRepository;
import com.rider.repository.UserRepository;
import com.rider.service.HalfChapsService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class HalfChapsServiceImpl implements HalfChapsService {
	
	@Autowired
	private HalfChapsRepository halfChapsRepository;
	
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
	public void create(HalfChapsDTO dto, String authToken) {
		dto.setHalfChapsId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		HalfChapsEntity halfChaps = modelMapper.map(dto, HalfChapsEntity.class);
		halfChaps.setUser(user);
		
		halfChapsRepository.save(halfChaps);
		
	}

	@Override
	public void update(HalfChapsDTO dto) {
		halfChapsRepository.save(modelMapper.map(dto, HalfChapsEntity.class));
		
	}

	@Override
	public void delete(String halfChapsId) {
		HalfChapsEntity halfChaps = halfChapsRepository.findByHalfChapsId(halfChapsId);
		halfChapsRepository.delete(halfChaps);
		
	}

	@Override
	public List<HalfChapsDTO> findAll() {
		return modelMapper.mapAll(halfChapsRepository.findAll(), HalfChapsDTO.class);
	}

	@Override
	public List<HalfChapsDTO> findAllHalfChaps(Pageable pageable) {
		Page<HalfChapsEntity> halfChapsPage = 
				halfChapsRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<HalfChapsEntity> halfChapsEntities = halfChapsPage.getContent();
		List<HalfChapsDTO> halfChapsDTOs =
				modelMapper.mapAll(halfChapsEntities, HalfChapsDTO.class);
		return halfChapsDTOs;
	}

	@Override
	public HalfChapsDTO findByHalfChapsId(String halfChapsId) {
		return modelMapper.map(halfChapsRepository.findByHalfChapsId(halfChapsId), HalfChapsDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String halfChapsId) {
		String imageUrl = cloudinaryService.uploadFile(file, "halfChaps");
		HalfChapsEntity halfChapsEntity = halfChapsRepository.findByHalfChapsId(halfChapsId);
		halfChapsEntity.setImageUrl(imageUrl);
		halfChapsRepository.save(halfChapsEntity);
		
	}
	
	

}
