package com.example.model;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {	
	@NotNull
	private BigInteger id;
	
	@NotNull
	private String kodeKelurahan;
	
	@NotNull
	private BigInteger idKecamatan;
	
	@NotNull
	private String namaKelurahan;
	
	@NotNull
	private String kodePos;
}
