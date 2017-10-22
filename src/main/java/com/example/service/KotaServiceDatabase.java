package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KotaMapper;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService {
	
	@Autowired
	private KotaMapper kotaMapper;
	
	@Override
	public KotaModel selectKota(BigInteger idKota) {
		log.info ("select kota with id {}", idKota);
        return kotaMapper.selectKota (idKota);
	}
	
	@Override
	public List<KotaModel> selectAllKota() {
		log.info ("select all kota");
        return kotaMapper.selectAllKota ();
	}
}
