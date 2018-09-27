package com.rider.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.RiderAmmunitionDTO;
import com.rider.entity.RiderAmmunitionEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.RiderAmmunitionRepository;
import com.rider.repository.UserRepository;
import com.rider.service.RiderAmmunitionService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class RiderAmmunitionServiceImpl implements RiderAmmunitionService {
	
	@Autowired
	private RiderAmmunitionRepository riderAmmunitionRepository;
	
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
	public void create(RiderAmmunitionDTO dto, String authToken) {
		dto.setRiderAmmunitionId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		RiderAmmunitionEntity riderAmmunition = modelMapper.map(dto, RiderAmmunitionEntity.class);
		riderAmmunition.setUser(user);
		
		riderAmmunitionRepository.save(riderAmmunition);
	}

	@Override
	public void update(RiderAmmunitionDTO dto) {
		riderAmmunitionRepository.save(modelMapper.map(dto, RiderAmmunitionEntity.class));
		
	}

	@Override
	public void delete(String riderAmmunitionId) {
		RiderAmmunitionEntity riderAmmunition = riderAmmunitionRepository.findByRiderAmmunitionId(riderAmmunitionId);
		riderAmmunitionRepository.delete(riderAmmunition);
		
	}

	@Override
	public List<RiderAmmunitionDTO> findAll() {
		return modelMapper.mapAll(riderAmmunitionRepository.findAll(), RiderAmmunitionDTO.class);
	}

	@Override
	public List<RiderAmmunitionDTO> findAllRiderAmmunition(Pageable pageable) {
		Page<RiderAmmunitionEntity> ridersAmmunitionPage = 
				riderAmmunitionRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<RiderAmmunitionEntity> riderAmmunitionEntities = ridersAmmunitionPage.getContent();
		List<RiderAmmunitionDTO> riderAmmunitionDTOs =
				modelMapper.mapAll(riderAmmunitionEntities, RiderAmmunitionDTO.class);
		return riderAmmunitionDTOs;
	}

	@Override
	public RiderAmmunitionDTO findByRiderAmmunitionId(String riderAmmunitionId) {
		return modelMapper.map(riderAmmunitionRepository.findByRiderAmmunitionId(riderAmmunitionId), RiderAmmunitionDTO.class);
	}

	@Override
	public List<RiderAmmunitionDTO> findByType(String type) {
		return modelMapper.mapAll(riderAmmunitionRepository.findByType(type), RiderAmmunitionDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String riderAmmunitionId) {
		String imageUrl = cloudinaryService.uploadFile(file, "horseAmmunition");
		RiderAmmunitionEntity riderAmmunitionEntity = riderAmmunitionRepository.findByRiderAmmunitionId(riderAmmunitionId);
		riderAmmunitionEntity.setImageUrl(imageUrl);
		riderAmmunitionRepository.save(riderAmmunitionEntity);
		
	}
	
	

}
