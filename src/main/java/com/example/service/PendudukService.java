package com.example.service;

import java.math.BigInteger;
import java.util.List;
import com.example.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	
	List<PendudukModel> selectWargaKelurahan(BigInteger idKelurahan);
	
	String addPenduduk(PendudukModel penduduk);
	
	boolean updatePenduduk(PendudukModel penduduk);
	
	boolean updateStatusKematian(String nik);
	
	String generateNik(PendudukModel penduduk);
	
	String generateNik(PendudukModel penduduk, String nikLama);
}
