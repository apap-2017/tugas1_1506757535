package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KelurahanMapper;
import com.example.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {
	
	@Autowired
	private KelurahanMapper kelurahanMapper;
		
	@Override
	public KelurahanModel selectKelurahan(BigInteger idKelurahan) {
		log.info ("select kelurahan with id {}", idKelurahan);
        return kelurahanMapper.selectKelurahan (idKelurahan);
	}

	@Override
	public List<KelurahanModel> selectKelurahanByKecamatan(BigInteger idKecamatan) {
		log.info ("select kelurahan in kecamatan with id {}", idKecamatan);
        return kelurahanMapper.selectKelurahanByKecamatan (idKecamatan);
	}
}
