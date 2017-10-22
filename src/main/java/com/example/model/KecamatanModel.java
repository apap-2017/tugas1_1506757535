package com.example.model;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {	
	@NotNull
	private BigInteger id;
	
	@NotNull
	private String kodeKecamatan;
	
	@NotNull
	private BigInteger idKota;
	
	@NotNull
	private String namaKecamatan;
}
