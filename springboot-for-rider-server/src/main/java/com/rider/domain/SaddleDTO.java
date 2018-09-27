package com.rider.domain;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaddleDTO {
	
	@JsonIgnore
	private Long id;
	
	private String saddleId;
	
	private String brand;
	
	private float size;
	
	private String color;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String imageUrl;
	
	private BigDecimal price;
	
	private UserDTO user;

}
