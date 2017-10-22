package com.example.model;

import java.math.BigInteger;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel implements Comparable<PendudukModel>{
	@NotNull
	private String nik;

	@NotNull
	private String nama;
	
	@NotNull
	private String tempatLahir;
	
	@NotNull
	private Date tanggalLahir;
	
	@NotNull
	private int jenisKelamin;
	
	@NotNull
	private int isWni;
	
	@NotNull
	private BigInteger idKeluarga;
	
	@NotNull
	private String agama;
	
	@NotNull
	private String pekerjaan;
	
	@NotNull
	private String statusPerkawinan;
	
	@NotNull
	private String statusDalamKeluarga;
	
	@NotNull
	private String golonganDarah;
	
	@NotNull
	private int isWafat;

	private AlamatModel alamat;

	@Override
	public int compareTo(PendudukModel pendudukLain) {
		return getTanggalLahir().compareTo(pendudukLain.getTanggalLahir());
	}
}