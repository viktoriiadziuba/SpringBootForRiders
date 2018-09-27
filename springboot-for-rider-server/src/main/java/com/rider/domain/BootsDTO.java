package com.rider.domain;

import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BootsDTO {

	@JsonIgnore
	private Long id;
	
	private String bootsId;
	
	private String type;
	
	private float size;
	
	private String color;
	
	private String description;
	
	private String imageUrl;
	
	private BigDecimal price;
	
	private UserDTO user;
	
}
