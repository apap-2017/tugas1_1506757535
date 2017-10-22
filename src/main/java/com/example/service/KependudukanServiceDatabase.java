package com.example.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KependudukanMapper;
import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KependudukanServiceDatabase implements KependudukanService {
	
	@Autowired
	private KependudukanMapper kependudukanMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info ("select penduduk with nik {}", nik);
        return kependudukanMapper.selectPenduduk (nik);
	}

	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info ("select keluarga with nkk {}", nkk);
        return kependudukanMapper.selectKeluarga (nkk);
	}

	@Override
	public List<PendudukModel> selectPendudukOfKeluarga(String nkk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PendudukModel> selectKeluargaOfKelurahan(BigInteger idKelurahan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
        penduduk.setNik(generateNik(penduduk));
        log.info ("add penduduk with nik {}", penduduk.getNik());
        kependudukanMapper.addPenduduk (penduduk);
	}

	@Override
	public void addKeluarga(AlamatModel alamat) {
        KeluargaModel keluarga = new KeluargaModel();
        keluarga.setIsTidakBerlaku(0);
        keluarga.setAlamat(alamat);
		BigInteger idKelurahan = kependudukanMapper.selectKelurahanByName(alamat.getNamaKelurahan());
		String kodeDomisili = kependudukanMapper.getKodeKelurahan(idKelurahan).substring(0, 6);
		keluarga.setIdKelurahan(idKelurahan);
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		String tanggalPembuatan = dateFormat.format(new Date());
		String latestNkk = kependudukanMapper.getNkkOfFamilyBefore(kodeDomisili + tanggalPembuatan + "%");
		if(latestNkk == null) {
        	latestNkk = "0000";
        } else {
        	latestNkk = latestNkk.substring(12);
        }
		String nomorUrut = String.format("%04d", Integer.parseInt(latestNkk) + 1);
		String nkk = kodeDomisili + tanggalPembuatan + nomorUrut;
		keluarga.setNomorKk(nkk);
		log.info("add keluarga with nkk {}", nkk);
		kependudukanMapper.addKeluarga(keluarga);
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		String nikLama = penduduk.getNik();
		penduduk.setNik(generateNik(penduduk));
		log.info("update penduduk with nik {}", nikLama);
		kependudukanMapper.updatePenduduk(nikLama, penduduk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		
	}

	public String generateNik(PendudukModel penduduk) {
		String tanggalLahir = penduduk.getTanggalLahir().toString(); //yyyy-mm-dd
        tanggalLahir = (penduduk.getJenisKelamin()==0?tanggalLahir.substring(8):String.valueOf(Integer.parseInt(tanggalLahir.substring(8))+40)) +
        		tanggalLahir.substring(5, 7) +	tanggalLahir.substring(2,4);
        String kodeDomisili = kependudukanMapper.getNkkOfKeluarga(penduduk.getIdKeluarga()).substring(0, 6);
        String latestNik = kependudukanMapper.getNikOfPersonBefore(kodeDomisili + tanggalLahir + "%");
        if(latestNik == null) {
        	latestNik = "0000";
        } else {
        	latestNik = latestNik.substring(12);
        }
        String nomorUrut = String.format("%04d", Integer.parseInt(latestNik) + 1);
        String nik = kodeDomisili + tanggalLahir + nomorUrut;
        return nik;
	}
}
