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

import com.rider.domain.BootsDTO;
import com.rider.service.BootsService;

@RestController
@RequestMapping("boots")
public class BootsController {
	
	@Autowired
	BootsService bootsService;
	
	@PostMapping
	public ResponseEntity<Void> addBoots(@RequestBody BootsDTO boots, @RequestHeader("Authorization") String authToken) {
		bootsService.create(boots, authToken);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{bootsId}")
	public ResponseEntity<Void> updateBoots (
			@PathVariable("bootsId") String bootsId, 
			@RequestBody BootsDTO bootsDTO) {
		
		BootsDTO bootsDTOFromDB = bootsService.findByBootsId(bootsId);
		
		if(bootsDTOFromDB != null) {
			bootsDTO.setId(bootsDTOFromDB.getId());
			bootsDTO.setBootsId(bootsId);;
			bootsService.update(bootsDTO);;
			return new ResponseEntity<Void>(HttpStatus.OK);			
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/{bootsId}")
	public ResponseEntity<Void> deleteBoots(@PathVariable("bootsId") String bootsId) {
		
		BootsDTO bootsDTO = bootsService.findByBootsId(bootsId);
		if(bootsDTO != null) {
			bootsService.delete(bootsId);;
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<BootsDTO>> getBoots() {
		List<BootsDTO> boots = bootsService.findAll();
		return new ResponseEntity<List<BootsDTO>>(boots, HttpStatus.OK);
	}
	
	@GetMapping("/pages")
	public ResponseEntity<List<BootsDTO>> getBootsByPage(@PageableDefault Pageable pageable) {
				
		return new ResponseEntity<List<BootsDTO>>(bootsService.findAllBoots(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{bootsId}")
	public ResponseEntity<BootsDTO> getBootsById(@PathVariable("bootsId") String bootsId) {
		BootsDTO boots = bootsService.findByBootsId(bootsId);
		return new ResponseEntity<BootsDTO>(boots, HttpStatus.OK);
	}
	
	@GetMapping("/bySize")
	public ResponseEntity<List<BootsDTO>> getBootsBySize(@RequestParam("size") float size) {
		List<BootsDTO> boots = bootsService.findBySize(size);
		return new ResponseEntity<List<BootsDTO>>(boots, HttpStatus.OK);
	}
	
	@PostMapping("image/{bootsId}")
	public ResponseEntity<Void> uploadImage(
			@PathVariable("bootsId") String bootsId,
			@RequestParam("image") MultipartFile file) {
		
		bootsService.uploadImage(file, bootsId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

}
