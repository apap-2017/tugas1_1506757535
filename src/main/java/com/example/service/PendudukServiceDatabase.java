package com.example.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Autowired
	private KeluargaService keluargaDAO;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk (nik);
	}
	
	@Override
	public List<PendudukModel> selectWargaKelurahan(BigInteger idKelurahan) {
		log.info ("select penduduk in kelurahan with id {}", idKelurahan);
        List<BigInteger> listIdKeluarga = keluargaMapper.selectKeluargaOfKelurahan(idKelurahan);
        List<PendudukModel> pendudukKelurahan = new ArrayList<>();
        for(int i = 0; i < listIdKeluarga.size(); i++) {
        	pendudukKelurahan.addAll(pendudukMapper.selectAnggotaKeluarga(listIdKeluarga.get(i)));
        }
        if(pendudukKelurahan.isEmpty()) {
        	return null;
        } else {
            Collections.sort(pendudukKelurahan);
    		return pendudukKelurahan;
        }

	}

	@Override
	public String addPenduduk(PendudukModel penduduk) {
        penduduk.setNik(generateNik(penduduk));
        log.info ("add penduduk with nik {}", penduduk.getNik());
        penduduk.setPekerjaan(penduduk.getPekerjaan().toUpperCase());
        pendudukMapper.addPenduduk (penduduk);
        return penduduk.getNik();
	}

	@Override
	public boolean updatePenduduk(PendudukModel penduduk) {
		String nikLama = penduduk.getNik();
		penduduk.setNik(generateNik(penduduk, nikLama));
		log.info("update penduduk with nik {}", nikLama);
		penduduk.setPekerjaan(penduduk.getPekerjaan().toUpperCase());
        pendudukMapper.updatePenduduk(nikLama, penduduk);
        return true;
	}
	
	@Override
	public boolean updateStatusKematian(String nik) {
		log.info("update status kematian of penduduk with nik {}", nik);
		pendudukMapper.updateStatusKematian(nik);
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(keluargaMapper.getNkkOfKeluarga(selectPenduduk(nik).getIdKeluarga()));
		Boolean allDead = true;
		List<PendudukModel> anggotaKeluarga = keluarga.getAnggotaKeluarga();
		for(int i = 0; i < anggotaKeluarga.size(); i++) {
			if(anggotaKeluarga.get(i).getIsWafat() == 0) {
				allDead = false;
			}
		}
		
		if(allDead) {
			keluargaMapper.updateStatusKeluarga(keluarga.getNomorKk());
			log.info("update status berlaku keluarga with nkk {}", keluarga.getNomorKk());
		}
		
		return true;
	}

	@Override
	public String generateNik(PendudukModel penduduk) {
		String tanggalLahir = penduduk.getTanggalLahir().toString(); //yyyy-mm-dd
        tanggalLahir = (penduduk.getJenisKelamin()==0?tanggalLahir.substring(8):String.valueOf(Integer.parseInt(tanggalLahir.substring(8))+40)) +
        		tanggalLahir.substring(5, 7) +	tanggalLahir.substring(2,4);
        String kodeDomisili = keluargaMapper.getNkkOfKeluarga(penduduk.getIdKeluarga()).substring(0, 6);
        String latestNik = pendudukMapper.getNikOfPersonBefore(kodeDomisili + tanggalLahir + "%");
        if(latestNik == null) {
        	latestNik = "0000";
        } else {
        	latestNik = latestNik.substring(12);
        }
        String nomorUrut = String.format("%04d", Integer.parseInt(latestNik) + 1);
        String nik = kodeDomisili + tanggalLahir + nomorUrut;
        return nik;
	}
	
	@Override
	public String generateNik(PendudukModel penduduk, String nikLama) {
		String tanggalLahir = penduduk.getTanggalLahir().toString(); //yyyy-mm-dd
        tanggalLahir = (penduduk.getJenisKelamin()==0?tanggalLahir.substring(8):String.valueOf(Integer.parseInt(tanggalLahir.substring(8))+40)) +
        		tanggalLahir.substring(5, 7) +	tanggalLahir.substring(2,4);
        String kodeDomisili = keluargaMapper.getNkkOfKeluarga(penduduk.getIdKeluarga()).substring(0, 6);
        String latestNik = pendudukMapper.getNikOfPersonBefore(kodeDomisili + tanggalLahir + "%");
        if(latestNik == null) {
        	latestNik = "0000";
        } else if(latestNik.equals(nikLama)) {
        	return nikLama;
        } else {
        	latestNik = latestNik.substring(12);
        }
        String nomorUrut = String.format("%04d", Integer.parseInt(latestNik) + 1);
        String nik = kodeDomisili + tanggalLahir + nomorUrut;
        return nik;
	}
}
