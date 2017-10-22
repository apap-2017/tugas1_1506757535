package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlamatModel {
	@NotNull
	private String alamat;
	
	@NotNull
	private String rt;
	
	@NotNull
	private String rw;
	
	@NotNull
	private String namaKelurahan;
	
	@NotNull
	private String namaKecamatan;
	
	@NotNull
	private String namaKota;
}
