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

import com.rider.domain.BreechesDTO;
import com.rider.service.BreechesService;

@RestController
@RequestMapping("breeches")
public class BreechesController {
	
	@Autowired
	BreechesService breechesService;
	
	@PostMapping
	public ResponseEntity<Void> addBreeches(@RequestBody BreechesDTO breeches, @RequestHeader("Authorization") String authToken) {
		breechesService.create(breeches, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{breechesId}")
	public ResponseEntity<Void> updateBreeches(
			@PathVariable("breechesId") String breechesId, 
			@RequestBody BreechesDTO breechesDTO) {
		
		BreechesDTO breechesDTOFromDB = breechesService.findByBreechesId(breechesId);
		
		if(breechesDTOFromDB != null) {
			breechesDTO.setId(breechesDTOFromDB.getId());
			breechesDTO.setBreechesId(breechesId);;
			breechesService.update(breechesDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{breechesId}")
	public ResponseEntity<Void> deleteBreeches(@PathVariable("breechesId") String breechesId) {
		
		BreechesDTO breechesDTO = breechesService.findByBreechesId(breechesId);
		if(breechesDTO != null) {
			breechesService.delete(breechesId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<BreechesDTO>> getBreeches() {
		List<BreechesDTO> breeches = breechesService.findAll();
		return new ResponseEntity<List<BreechesDTO>>(breeches, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<BreechesDTO>> getBreechesByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<BreechesDTO>>(breechesService.findAllBreeches(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{breechesId}")
	public ResponseEntity<BreechesDTO> getBreechesById(@PathVariable("breechesId") String breechesId) {
		BreechesDTO breeches = breechesService.findByBreechesId(breechesId);
		return new ResponseEntity<BreechesDTO>(breeches, HttpStatus.OK);
	}
	
	
	@PostMapping("image/{breechesId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("breechesId") String breechesId,
			@RequestParam("image") MultipartFile file) {
		
		breechesService.uploadImage(file, breechesId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


}
