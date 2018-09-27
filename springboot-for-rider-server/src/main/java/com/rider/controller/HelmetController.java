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

import com.rider.domain.HelmetDTO;
import com.rider.service.HelmetService;


@RestController
@RequestMapping("helmets")
public class HelmetController {
	
	@Autowired
	HelmetService helmetService;
	
	@PostMapping
	public ResponseEntity<Void> addHelmet(@RequestBody HelmetDTO helmet, @RequestHeader("Authorization") String authToken) {
		helmetService.create(helmet, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{helmetId}")
	public ResponseEntity<Void> updateHelmet (
			@PathVariable("helmetId") String helmetId, 
			@RequestBody HelmetDTO helmetDTO) {
		
		HelmetDTO helmetDTOFromDB = helmetService.findByHelmetId(helmetId);
		
		if(helmetDTOFromDB != null) {
			helmetDTO.setId(helmetDTOFromDB.getId());
			helmetDTO.setHelmetId(helmetId);;
			helmetService.update(helmetDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{helmetId}")
	public ResponseEntity<Void> deleteHelmet(@PathVariable("helmetId") String helmetId) {
		
		HelmetDTO helmetDTO = helmetService.findByHelmetId(helmetId);
		if(helmetDTO != null) {
			helmetService.delete(helmetId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<HelmetDTO>> getHelmet() {
		List<HelmetDTO> helmet = helmetService.findAll();
		return new ResponseEntity<List<HelmetDTO>>(helmet, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<HelmetDTO>> getHelmetByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<HelmetDTO>>(helmetService.findAllHelmets(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{helmetId}")
	public ResponseEntity<HelmetDTO> getHelmetById(@PathVariable("helmetId") String helmetId) {
		HelmetDTO helmet = helmetService.findByHelmetId(helmetId);
		return new ResponseEntity<HelmetDTO>(helmet, HttpStatus.OK);
	}
	
	
	@PostMapping("image/{helmetId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("helmetId") String helmetId,
			@RequestParam("image") MultipartFile file) {
		
		helmetService.uploadImage(file, helmetId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}


}
