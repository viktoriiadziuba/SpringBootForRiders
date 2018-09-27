package com.rider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.BootsDTO;
import com.rider.entity.BootsEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.BootsRepository;
import com.rider.repository.UserRepository;
import com.rider.service.BootsService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class BootsServiceImpl implements BootsService {
	
	@Autowired
	private BootsRepository bootsRepository;
	
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
	public void create(BootsDTO dto, String authToken) {
		dto.setBootsId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));
		UserEntity user = userRepository.findByUsername(username);
		
		BootsEntity boots = modelMapper.map(dto, BootsEntity.class);
		boots.setUser(user);
		
		bootsRepository.save(boots);	
		
	}

	@Override
	public void update(BootsDTO dto) {
		bootsRepository.save(modelMapper.map(dto, BootsEntity.class));
		
	}

	@Override
	public void delete(String bootsId) {
		BootsEntity boots = bootsRepository.findByBootsId(bootsId);
		bootsRepository.delete(boots);
		
	}

	@Override
	public List<BootsDTO> findAll() {
		return modelMapper.mapAll(bootsRepository.findAll(), BootsDTO.class);
	}

	@Override
	public List<BootsDTO> findAllBoots(Pageable pageable) {
		Page<BootsEntity> bootsPage = 
				bootsRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<BootsEntity> bootsEntities = bootsPage.getContent();
		List<BootsDTO> bootsDTOs =
				modelMapper.mapAll(bootsEntities, BootsDTO.class);
		return bootsDTOs;
	}

	@Override
	public List<BootsDTO> findBySize(float size) {
		return modelMapper.mapAll(bootsRepository.findBySize(size), BootsDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String bootsId) {
		String imageUrl = cloudinaryService.uploadFile(file, "boots");
		BootsEntity bootsEntity = bootsRepository.findByBootsId(bootsId);
		bootsEntity.setImageUrl(imageUrl);
		bootsRepository.save(bootsEntity);
		
	}

	@Override
	public BootsDTO findByBootsId(String bootsId) {
		return modelMapper.map(bootsRepository.findByBootsId(bootsId), BootsDTO.class);
	}
	
	

}
