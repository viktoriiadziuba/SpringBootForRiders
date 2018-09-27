package com.rider.domain;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RiderAmmunitionDTO {
	
	@JsonIgnore
	private Long id;
	
	private String riderAmmunitionId;
	
	private String type;
	
	private String size;
	
	private String color;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String imageUrl;
	
	private BigDecimal price;
	
	private UserDTO user;

}
