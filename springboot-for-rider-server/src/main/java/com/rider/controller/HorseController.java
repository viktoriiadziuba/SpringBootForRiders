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

import com.rider.domain.HorseDTO;
import com.rider.service.HorseService;

@RestController
@RequestMapping("horses")
public class HorseController {
	

	@Autowired
	HorseService horseService;
	
	@PostMapping
	public ResponseEntity<Void> addHorse(@RequestBody HorseDTO horse, @RequestHeader("Authorization") String authToken) {
		horseService.create(horse, authToken);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{horseId}")
	public ResponseEntity<Void> updateHorse (
			@PathVariable("horseId") String horseId, 
			@RequestBody HorseDTO horseDTO) {
		
		HorseDTO horseDTOFromDB = horseService.findByHorseId(horseId);
		
		if(horseDTOFromDB != null) {
			horseDTO.setId(horseDTOFromDB.getId());
			horseDTO.setHorseId(horseId);
			horseService.update(horseDTO);
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{horseId}")
	public ResponseEntity<Void> deleteHorse(@PathVariable("horseId") String horseId) {
		
		HorseDTO horseDTO = horseService.findByHorseId(horseId);
		if(horseDTO != null) {
			horseService.delete(horseId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<HorseDTO>> getHorses() {
		List<HorseDTO> horse = horseService.findAll();
		return new ResponseEntity<List<HorseDTO>>(horse, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<HorseDTO>> getHorsesByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<HorseDTO>>(horseService.findAllHorses(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/byId/{horseId}")
	public ResponseEntity<HorseDTO> getHorseById(@PathVariable("horseId") String horseId) {
		HorseDTO horse = horseService.findByHorseId(horseId);
		return new ResponseEntity<HorseDTO>(horse, HttpStatus.OK);
	}
	
	@GetMapping("/byBreed")///{horseBreed} @PathVariable("horseBreed")
	public ResponseEntity<List<HorseDTO>> getHorseByBreed(@RequestParam("name") String breed) {
		System.out.println(breed);
		List<HorseDTO> horses = horseService.findByHorseBreed(breed);
		return new ResponseEntity<List<HorseDTO>>(horses, HttpStatus.OK);
	}
	
	@PostMapping("image/{horseId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("horseId") String horseId,
			@RequestParam("image") MultipartFile file) {
		System.out.println(file.getOriginalFilename());
		horseService.uploadImage(file, horseId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


}
