package com.rider.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rider.config.jwt.JWTTokenProvider;
import com.rider.domain.HelmetDTO;
import com.rider.entity.HelmetEntity;
import com.rider.entity.UserEntity;
import com.rider.repository.HelmetRepository;
import com.rider.repository.UserRepository;
import com.rider.service.HelmetService;
import com.rider.service.cloudinary.CloudinaryService;
import com.rider.service.utils.ObjectMapperUtils;
import com.rider.service.utils.StringUtils;

@Service
public class HelmetServiceImpl implements HelmetService {
	
	@Autowired
	private HelmetRepository helmetRepository;
	
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
	public void create(HelmetDTO dto, String authToken) {
		dto.setHelmetId(stringUtils.generate());
		
		String username = jwtTokenProvider.getUsername(authToken.substring(7, authToken.length()));				
		UserEntity user = userRepository.findByUsername(username);
		
		HelmetEntity helmet = modelMapper.map(dto, HelmetEntity.class);
		helmet.setUser(user);
		
		helmetRepository.save(helmet);
		
	}

	@Override
	public void update(HelmetDTO dto) {
		helmetRepository.save(modelMapper.map(dto, HelmetEntity.class));
		
	}

	@Override
	public void delete(String helmetId) {
		HelmetEntity helmet = helmetRepository.findByHelmetId(helmetId);
		helmetRepository.delete(helmet);
		
	}

	@Override
	public List<HelmetDTO> findAll() {
		return modelMapper.mapAll(helmetRepository.findAll(), HelmetDTO.class);
	}

	@Override
	public List<HelmetDTO> findAllHelmets(Pageable pageable) {
		Page<HelmetEntity> helmetsPage = 
				helmetRepository.findAll(
						PageRequest.of(pageable.getPageNumber(), 
								       pageable.getPageSize(),
								       pageable.getSort())
						);
		List<HelmetEntity> helmetEntities = helmetsPage.getContent();
		List<HelmetDTO> helmetDTOs =
				modelMapper.mapAll(helmetEntities, HelmetDTO.class);
		return helmetDTOs;
	}

	@Override
	public HelmetDTO findByHelmetId(String helmetId) {
		return modelMapper.map(helmetRepository.findByHelmetId(helmetId), HelmetDTO.class);
	}

	@Override
	public void uploadImage(MultipartFile file, String helmetId) {
		String imageUrl = cloudinaryService.uploadFile(file, "helmets");
		HelmetEntity helmetEntity = helmetRepository.findByHelmetId(helmetId);
		helmetEntity.setImageUrl(imageUrl);
		helmetRepository.save(helmetEntity);
		
	}
	
	

}
