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

import com.rider.domain.SaddleDTO;
import com.rider.service.SaddleService;


@RestController
@RequestMapping("saddles")
public class SaddleController {
	
	@Autowired
	SaddleService saddleService;
	
	@PostMapping
	public ResponseEntity<Void> addSaddle(@RequestBody SaddleDTO saddle, @RequestHeader("Authorization") String authToken) {
		saddleService.create(saddle, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{saddleId}")
	public ResponseEntity<Void> updateSaddle (
			@PathVariable("saddleId") String saddleId, 
			@RequestBody SaddleDTO saddleDTO) {
		
		SaddleDTO saddleDTOFromDB = saddleService.findBySaddleId(saddleId);
		
		if(saddleDTOFromDB != null) {
			saddleDTO.setId(saddleDTOFromDB.getId());
			saddleDTO.setSaddleId(saddleId);;
			saddleService.update(saddleDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{saddleId}")
	public ResponseEntity<Void> deleteSaddle(@PathVariable("saddleId") String saddleId) {
		
		SaddleDTO saddleDTO = saddleService.findBySaddleId(saddleId);
		if(saddleDTO != null) {
			saddleService.delete(saddleId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<SaddleDTO>> getSaddles() {
		List<SaddleDTO> saddle = saddleService.findAll();
		return new ResponseEntity<List<SaddleDTO>>(saddle, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<SaddleDTO>> getBootsByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<SaddleDTO>>(saddleService.findAllSaddles(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{saddleId}")
	public ResponseEntity<SaddleDTO> getSaddleById(@PathVariable("saddleId") String saddleId) {
		SaddleDTO saddle = saddleService.findBySaddleId(saddleId);
		return new ResponseEntity<SaddleDTO>(saddle, HttpStatus.OK);
	}
	
	@GetMapping("/bySize")
	public ResponseEntity<List<SaddleDTO>> getSaddleBySize(@RequestParam("size") float size) {
		List<SaddleDTO> saddles = saddleService.findBySize(size);
		return new ResponseEntity<List<SaddleDTO>>(saddles, HttpStatus.OK);
	}
	
	@PostMapping("image/{saddleId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("saddleId") String saddleId,
			@RequestParam("image") MultipartFile file) {
		
		saddleService.uploadImage(file, saddleId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


}
