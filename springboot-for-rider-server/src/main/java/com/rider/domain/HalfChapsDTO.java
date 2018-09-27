package com.rider.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HalfChapsDTO {
	
	@JsonIgnore
	private Long id;
	
	private String halfChapsId;
	
	private String size;
	
	private String color;
	
	private String description;
	
	private String imageUrl;
	
	private BigDecimal price;
	
	private UserDTO user;


}
