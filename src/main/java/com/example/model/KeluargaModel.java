package com.example.model;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	@NotNull
	private String nomorKk;

	@NotNull
	private AlamatModel alamat;
	
//	@NotNull
//	private String alamat;
//
//	@NotNull
//	private String rt;
//
//	@NotNull
//	private String rw;

	@NotNull
	private BigInteger idKelurahan;

	@NotNull
	private int isTidakBerlaku;
	
	private List<PendudukModel> anggotaKeluarga; 
}
