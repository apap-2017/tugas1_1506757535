package com.example.service;

import java.math.BigInteger;
import java.util.List;

import com.example.model.KelurahanModel;

public interface KelurahanService {
	KelurahanModel selectKelurahan(BigInteger idKelurahan);
	
	List<KelurahanModel> selectKelurahanByKecamatan(BigInteger idKecamatan);
}
