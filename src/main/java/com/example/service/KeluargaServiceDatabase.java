package com.example.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.KelurahanMapper;
import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Autowired
	private KelurahanMapper kelurahanMapper;
	
	@Autowired
	private PendudukService pendudukDAO;
	
	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info ("select keluarga with nkk {}", nkk);
        return keluargaMapper.selectKeluarga (nkk);
	}

	@Override
	public String addKeluarga(AlamatModel alamat) {
        KeluargaModel keluarga = new KeluargaModel();
        keluarga.setIsTidakBerlaku(0);
        keluarga.setAlamat(alamat);
		BigInteger idKelurahan = kelurahanMapper.selectKelurahanByName(alamat.getNamaKelurahan());
		if(idKelurahan == null) {
			return null;
		} else {
			String kodeDomisili = kelurahanMapper.getKodeKelurahan(idKelurahan).substring(0, 6);
			keluarga.setIdKelurahan(idKelurahan);
			keluarga.setNomorKk(generateNkk(kodeDomisili));
			log.info("add keluarga with nkk {}", keluarga.getNomorKk());
			keluargaMapper.addKeluarga(keluarga);
			return keluarga.getNomorKk();
		}
	}
	
	@Override
	public boolean updateKeluarga(KeluargaModel keluarga) {
		String nkkLama = keluarga.getNomorKk();
		BigInteger idKelurahan = kelurahanMapper.selectKelurahanByName(keluarga.getAlamat().getNamaKelurahan());
		if(idKelurahan == null) {
			return false;
		} else {
			String kodeDomisili = kelurahanMapper.getKodeKelurahan(idKelurahan).substring(0, 6);
			keluarga.setIdKelurahan(idKelurahan);
			keluarga.setNomorKk(generateNkk(kodeDomisili, nkkLama));
			log.info("update keluarga with nkk {}", nkkLama);
			keluargaMapper.updateKeluarga(nkkLama, keluarga);
			keluarga = keluargaMapper.selectKeluarga(keluarga.getNomorKk());
			List<PendudukModel> anggotaKeluarga = keluarga.getAnggotaKeluarga();
			for(int i = 0; i < anggotaKeluarga.size(); i++) {
				pendudukDAO.updatePenduduk(anggotaKeluarga.get(i));
			}
			return true;
		}
	}
	
	public String generateNkk(String kodeDomisili) {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		String tanggalPembuatan = dateFormat.format(new Date());
		String latestNkk = keluargaMapper.getNkkOfFamilyBefore(kodeDomisili + tanggalPembuatan + "%");
		if(latestNkk == null) {
        	latestNkk = "0000";
        } else {
        	latestNkk = latestNkk.substring(12);
        }
		String nomorUrut = String.format("%04d", Integer.parseInt(latestNkk) + 1);
		String nkk = kodeDomisili + tanggalPembuatan + nomorUrut;
		return nkk;
	}
	
	public String generateNkk(String kodeDomisili, String nkkLama) {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		String tanggalPembuatan = dateFormat.format(new Date());
		String latestNkk = keluargaMapper.getNkkOfFamilyBefore(kodeDomisili + tanggalPembuatan + "%");
		if(latestNkk == null) {
        	latestNkk = "0000";
        } else if(latestNkk.equals(nkkLama)) {
        	return nkkLama;
        } else {
        	latestNkk = latestNkk.substring(12);
        }
		
		String nomorUrut = String.format("%04d", Integer.parseInt(latestNkk) + 1);
		String nkk = kodeDomisili + tanggalPembuatan + nomorUrut;
		return nkk;
	}
}
