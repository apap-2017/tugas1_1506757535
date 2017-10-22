package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {
	
	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	@Override
	public KecamatanModel selectKecamatan(BigInteger idKecamatan) {
		log.info ("select kecamatan with id {}", idKecamatan);
        return kecamatanMapper.selectKecamatan (idKecamatan);
	}
	
	@Override
	public List<KecamatanModel> selectKecamatanByKota(BigInteger idKota) {
		log.info ("select kecamatan in kota with id {}", idKota);
        return kecamatanMapper.selectKecamatanByKota (idKota);
	}
}
