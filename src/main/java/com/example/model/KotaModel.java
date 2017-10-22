package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KotaModel {
	@NotNull
	private BigInteger id;
	
	@NotNull
	private String kodeKota;
	
	@NotNull
	private String namaKota;
}
