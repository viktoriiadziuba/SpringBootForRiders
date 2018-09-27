package com.rider.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HorseDTO {
	
	@JsonIgnore
	private Long id;
	
    private String horseId;
	
	private String name;
	 
	private String breed;
	
	private String height;
	
	private String coatColour;
	
	private int yearOfBirth;
	
	private String description;
	
	private String imageUrl;
	
	private BigDecimal price;
	
	private UserDTO user;

}
