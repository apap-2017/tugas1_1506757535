package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface KependudukanService {
	PendudukModel selectPenduduk(String nik);
	
	KeluargaModel selectKeluarga(String nkk);
	
	List<PendudukModel> selectPendudukOfKeluarga(String nkk);
	
	List<PendudukModel> selectKeluargaOfKelurahan(BigInteger idKelurahan);
	
	void addPenduduk(PendudukModel penduduk);
	
	void addKeluarga(AlamatModel alamat);
	
	void updatePenduduk(PendudukModel penduduk);
	
	void updateKeluarga(KeluargaModel keluarga);
	
}
