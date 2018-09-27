package com.rider.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.HorseAmmunitionDTO;
import com.rider.entity.HorseAmmunitionEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.HorseAmmunitionRepository;
import com.rider.repository.UserRepository;
import com.rider.service.HorseAmmunitionService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class HorseAmmunitionServiceImpl implements HorseAmmunitionService {

	@Autowired
	private HorseAmmunitionRepository horseAmmunitionRepository;
	
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
	public void create(HorseAmmunitionDTO dto, String authToken) {
		dto.setHorseAmmunitionId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		HorseAmmunitionEntity horseAmmunition = modelMapper.map(dto, HorseAmmunitionEntity.class);
		horseAmmunition.setUser(user);
		
		horseAmmunitionRepository.save(horseAmmunition);	
		
	}

	@Override
	public void update(HorseAmmunitionDTO dto) {
		horseAmmunitionRepository.save(modelMapper.map(dto, HorseAmmunitionEntity.class));		
	}

	@Override
	public void delete(String horseAmmunitionId) {
		HorseAmmunitionEntity horseAmmunition = horseAmmunitionRepository.findByHorseAmmunitionId(horseAmmunitionId);
		horseAmmunitionRepository.delete(horseAmmunition);
		
	}

	@Override
	public List<HorseAmmunitionDTO> findAll() {
		return modelMapper.mapAll(horseAmmunitionRepository.findAll(), HorseAmmunitionDTO.class);
	}

	@Override
	public List<HorseAmmunitionDTO> findAllHorseAmmunition(Pageable pageable) {
		Page<HorseAmmunitionEntity> horsesAmmunitionPage = 
				horseAmmunitionRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<HorseAmmunitionEntity> horseAmmunitionEntities = horsesAmmunitionPage.getContent();
		List<HorseAmmunitionDTO> horseAmmunitionDTOs =
				modelMapper.mapAll(horseAmmunitionEntities, HorseAmmunitionDTO.class);
		return horseAmmunitionDTOs;
	}

	@Override
	public HorseAmmunitionDTO findByHorseAmmunitionId(String horseAmmunitionId) {
		return modelMapper.map(horseAmmunitionRepository.findByHorseAmmunitionId(horseAmmunitionId), HorseAmmunitionDTO.class);
	}

	@Override
	public List<HorseAmmunitionDTO> findByType(String type) {
		return modelMapper.mapAll(horseAmmunitionRepository.findByType(type), HorseAmmunitionDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String horseAmmunitionId) {
		String imageUrl = cloudinaryService.uploadFile(file, "horseAmmunition");
		HorseAmmunitionEntity horseAmmunitionEntity = horseAmmunitionRepository.findByHorseAmmunitionId(horseAmmunitionId);
		horseAmmunitionEntity.setImageUrl(imageUrl);
		horseAmmunitionRepository.save(horseAmmunitionEntity);
		
	}
	
	
}
