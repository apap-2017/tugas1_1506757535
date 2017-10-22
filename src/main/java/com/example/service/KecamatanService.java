package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.KecamatanModel;

public interface KecamatanService {
	KecamatanModel selectKecamatan(BigInteger idKecamatan);
	
	List<KecamatanModel> selectKecamatanByKota(BigInteger idKota);	
}
