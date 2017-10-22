package com.example.service;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nkk);
	
	String addKeluarga(AlamatModel alamat);
	
	boolean updateKeluarga(KeluargaModel keluarga);
	
	String generateNkk(String kodeDomisili);

	String generateNkk(String kodeDomisili, String nkkLama);
}
