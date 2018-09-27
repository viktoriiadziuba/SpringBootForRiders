package com.rider.service.impl;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.HorseDTO;
import com.rider.entity.HorseEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.HorseRepository;
import com.rider.repository.UserRepository;
import com.rider.service.HorseService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class HorseServiceImpl implements HorseService {

	@Autowired
	private HorseRepository horseRepository;
	
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
	public void create(HorseDTO dto, String authToken) {
		dto.setHorseId(stringUtils.generate());

		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		HorseEntity horse = modelMapper.map(dto, HorseEntity.class);
		horse.setUser(user);
		
		horseRepository.save(horse);		
	}

	@Override
	public void update(HorseDTO dto) {
		horseRepository.save(modelMapper.map(dto, HorseEntity.class));
		
	}

	@Override
	public void delete(String horseId) {
		HorseEntity horse = horseRepository.findByHorseId(horseId);
		horseRepository.delete(horse);
		
	}

	@Override
	public List<HorseDTO> findAll() {
		List<HorseEntity> horses=horseRepository.findAll();
		
		horses.forEach(h -> System.out.println(h.getUser()));
		
		return modelMapper.mapAll(horses, HorseDTO.class);
	}

	@Override
	public List<HorseDTO> findAllHorses(Pageable pageable) {
		Page<HorseEntity> horsesPage = 
				horseRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<HorseEntity> horseEntities = horsesPage.getContent();
		List<HorseDTO> horseDTOs =
				modelMapper.mapAll(horseEntities, HorseDTO.class);
		return horseDTOs;
	}

	@Override
	public List<HorseDTO> findByHorseBreed(String breed) {
		return modelMapper.mapAll(horseRepository.findByBreed(breed), HorseDTO.class);
	}
	
	

	@Override
	public void uploadImage(MultipartFile file, String horseId) {
		String imageUrl = cloudinaryService.uploadFile(file, "horses");
		HorseEntity horseEntity = horseRepository.findByHorseId(horseId);
		horseEntity.setImageUrl(imageUrl);
		horseRepository.save(horseEntity);
	}

	@Override
	public HorseDTO findByHorseId(String horseId) {
		return modelMapper.map(horseRepository.findByHorseId(horseId), HorseDTO.class);
	}
	
	
}
