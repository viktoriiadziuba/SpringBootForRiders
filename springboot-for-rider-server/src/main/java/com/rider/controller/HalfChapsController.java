package com.rider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rider.domain.HalfChapsDTO;
import com.rider.service.HalfChapsService;


@RestController
@RequestMapping("halfChaps")
public class HalfChapsController {
	
	@Autowired
	HalfChapsService halfChapsService;
	
	@PostMapping
	public ResponseEntity<Void> addHalfChaps(@RequestBody HalfChapsDTO halfChaps, @RequestHeader("Authorization") String authToken) {
		halfChapsService.create(halfChaps, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{halfChapsId}")
	public ResponseEntity<Void> updateHalfChaps (
			@PathVariable("halfChapsId") String halfChapsId, 
			@RequestBody HalfChapsDTO halfChapsDTO) {
		
		HalfChapsDTO halfChapsDTOFromDB = halfChapsService.findByHalfChapsId(halfChapsId);
		
		if(halfChapsDTOFromDB != null) {
			halfChapsDTO.setId(halfChapsDTOFromDB.getId());
			halfChapsDTO.setHalfChapsId(halfChapsId);;
			halfChapsService.update(halfChapsDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{halfChapsId}")
	public ResponseEntity<Void> deleteHalfChaps(@PathVariable("halfChapsId") String halfChapsId) {
		
		HalfChapsDTO halfChapsDTO = halfChapsService.findByHalfChapsId(halfChapsId);
		if(halfChapsDTO != null) {
			halfChapsService.delete(halfChapsId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<HalfChapsDTO>> getHalfChaps() {
		List<HalfChapsDTO> halfChaps = halfChapsService.findAll();
		return new ResponseEntity<List<HalfChapsDTO>>(halfChaps, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<HalfChapsDTO>> getHalfChapsByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<HalfChapsDTO>>(halfChapsService.findAllHalfChaps(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{halfChapsId}")
	public ResponseEntity<HalfChapsDTO> getHalfChapsById(@PathVariable("halfChapsId") String halfChapsId) {
		HalfChapsDTO halfChaps = halfChapsService.findByHalfChapsId(halfChapsId);
		return new ResponseEntity<HalfChapsDTO>(halfChaps, HttpStatus.OK);
	}
	
	@PostMapping("image/{halfChapsId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("halfChapsId") String halfChapsId,
			@RequestParam("image") MultipartFile file) {
		
		halfChapsService.uploadImage(file,halfChapsId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


}
