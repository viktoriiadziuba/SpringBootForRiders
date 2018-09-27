package com.rider.entity;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "horse")
public class HorseEntity extends BaseEntity{
	
	@Column(nullable = false, unique = true)
	private String horseId;
	
	@Column(nullable = false)
	private String name;
	 
	private String breed;
	
	@Column(nullable = false)
	private String height;
	
	@Column(nullable = false)
	private String coatColour;
	
	@Column(nullable = false)
	private int yearOfBirth;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String imageUrl;
	
	@Column(columnDefinition = "DECIMAL(9,2)",nullable = false)
	private BigDecimal price;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	
	

}
