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

import com.rider.domain.RiderAmmunitionDTO;
import com.rider.service.RiderAmmunitionService;


@RestController
@RequestMapping("rider_ammunition")
public class RiderAmmunitionController {
	
	@Autowired
	RiderAmmunitionService riderAmmunitionService;
	
	@PostMapping
	public ResponseEntity<Void> addRiderAmmunition(@RequestBody RiderAmmunitionDTO riderAmmunition, @RequestHeader("Authorization") String authToken) {
		riderAmmunitionService.create(riderAmmunition, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{riderAmmunitionId}")
	public ResponseEntity<Void> updateRiderAmmunition (
			@PathVariable("riderAmmunitionId") String riderAmmunitionId, 
			@RequestBody RiderAmmunitionDTO riderAmmunitionDTO) {
		
		RiderAmmunitionDTO riderAmmunitionDTOFromDB = riderAmmunitionService.findByRiderAmmunitionId(riderAmmunitionId);
		
		if(riderAmmunitionDTOFromDB != null) {
			riderAmmunitionDTO.setId(riderAmmunitionDTOFromDB.getId());
			riderAmmunitionDTO.setRiderAmmunitionId(riderAmmunitionId);;
			riderAmmunitionService.update(riderAmmunitionDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{riderAmmunitionId}")
	public ResponseEntity<Void> deleteRiderAmmunition(@PathVariable("riderAmmunitionId") String riderAmmunitionId) {
		
		RiderAmmunitionDTO riderAmmunitionDTO = riderAmmunitionService.findByRiderAmmunitionId(riderAmmunitionId);
		if(riderAmmunitionDTO != null) {
			riderAmmunitionService.delete(riderAmmunitionId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<RiderAmmunitionDTO>> getRiderAmmunition() {
		List<RiderAmmunitionDTO> riderAmmunition = riderAmmunitionService.findAll();
		return new ResponseEntity<List<RiderAmmunitionDTO>>(riderAmmunition, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<RiderAmmunitionDTO>> getRiderAmmunitionByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<RiderAmmunitionDTO>>(riderAmmunitionService.findAllRiderAmmunition(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{riderAmmunitionId}")
	public ResponseEntity<RiderAmmunitionDTO> getRiderAmmunitionById(@PathVariable("riderAmmunitionId") String riderAmmunitionId) {
		RiderAmmunitionDTO riderAmmunition = riderAmmunitionService.findByRiderAmmunitionId(riderAmmunitionId);
		return new ResponseEntity<RiderAmmunitionDTO>(riderAmmunition, HttpStatus.OK);
	}
	
	@GetMapping("/byType")
	public ResponseEntity<List<RiderAmmunitionDTO>> getRiderAmmunitionByType(@RequestParam("type") String type) {
		List<RiderAmmunitionDTO> riderAmmunition = riderAmmunitionService.findByType(type);
		return new ResponseEntity<List<RiderAmmunitionDTO>>(riderAmmunition, HttpStatus.OK);
	}
	
	@PostMapping("image/{riderAmmunitionId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("riderAmmunitionId") String riderAmmunitionId,
			@RequestParam("image") MultipartFile file) {
		
		riderAmmunitionService.uploadImage(file, riderAmmunitionId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}



}
