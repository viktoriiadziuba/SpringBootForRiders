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

import com.rider.domain.HorseAmmunitionDTO;
import com.rider.service.HorseAmmunitionService;


@RestController
@RequestMapping("horse_ammunition")
public class HorseAmmunitionController {

	@Autowired
	HorseAmmunitionService horseAmmunitionService;
	
	@PostMapping
	public ResponseEntity<Void> addHorseAmmunition(@RequestBody HorseAmmunitionDTO horseAmmunition, @RequestHeader("Authorization") String authToken) {
		horseAmmunitionService.create(horseAmmunition, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{horseAmmunitionId}")
	public ResponseEntity<Void> updateHorseAmmunition (
			@PathVariable("horseAmmunitionId") String horseAmmunitionId, 
			@RequestBody HorseAmmunitionDTO horseAmmunitionDTO) {
		
		HorseAmmunitionDTO horseAmmunitionDTOFromDB = horseAmmunitionService.findByHorseAmmunitionId(horseAmmunitionId);
		
		if(horseAmmunitionDTOFromDB != null) {
			horseAmmunitionDTO.setId(horseAmmunitionDTOFromDB.getId());
			horseAmmunitionDTO.setHorseAmmunitionId(horseAmmunitionId);;
			horseAmmunitionService.update(horseAmmunitionDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{horseAmmunitionId}")
	public ResponseEntity<Void> deleteHorseAmmunition(@PathVariable("horseAmmunitionId") String horseAmmunitionId) {
		
		HorseAmmunitionDTO horseAmmunitionDTO = horseAmmunitionService.findByHorseAmmunitionId(horseAmmunitionId);
		if(horseAmmunitionDTO != null) {
			horseAmmunitionService.delete(horseAmmunitionId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<HorseAmmunitionDTO>> getHorseAmmunition() {
		List<HorseAmmunitionDTO> horseAmmunition = horseAmmunitionService.findAll();
		return new ResponseEntity<List<HorseAmmunitionDTO>>(horseAmmunition, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<HorseAmmunitionDTO>> getHorseAmmunitionByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<HorseAmmunitionDTO>>(horseAmmunitionService.findAllHorseAmmunition(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{horseAmmunitionId}")
	public ResponseEntity<HorseAmmunitionDTO> getHorseAmmunitionById(@PathVariable("horseAmmunitionId") String horseAmmunitionId) {
		HorseAmmunitionDTO horseAmmunition = horseAmmunitionService.findByHorseAmmunitionId(horseAmmunitionId);
		return new ResponseEntity<HorseAmmunitionDTO>(horseAmmunition, HttpStatus.OK);
	}
	
	@GetMapping("/byType")
	public ResponseEntity<List<HorseAmmunitionDTO>> getHorseAmmunitionByType(@RequestParam("type") String type) {
		List<HorseAmmunitionDTO> horseAmmunition = horseAmmunitionService.findByType(type);
		return new ResponseEntity<List<HorseAmmunitionDTO>>(horseAmmunition, HttpStatus.OK);
	}
	
	@PostMapping("image/{horseAmmunitionId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("horseAmmunitionId") String horseAmmunitionId,
			@RequestParam("image") MultipartFile file) {
		
		horseAmmunitionService.uploadImage(file, horseAmmunitionId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


}
