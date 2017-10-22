package com.example.dao;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface KependudukanMapper {
	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = {
		@Result(property="nik", column="nik"),
		@Result(property="nama", column="nama"),
		@Result(property="tempatLahir", column="tempat_lahir"),
		@Result(property="tanggalLahir", column="tanggal_lahir",
				javaType=Date.class),
		@Result(property="jenisKelamin", column="jenis_kelamin"),
		@Result(property="isWni", column="is_wni"),
		@Result(property="idKeluarga", column="id_keluarga",
				javaType=BigInteger.class),
		@Result(property="agama", column="agama"),
		@Result(property="pekerjaan", column="pekerjaan"),
		@Result(property="statusPerkawinan", column="status_perkawinan"),
		@Result(property="statusDalamKeluarga", column="status_dalam_keluarga"),
		@Result(property="golonganDarah", column="golongan_darah"),
		@Result(property="isWafat", column="is_wafat"),
		@Result(property="alamat", column="id_keluarga",
				javaType=AlamatModel.class, many=@Many(select="selectAlamatOfKeluarga"))
	})
	PendudukModel selectPenduduk(@Param("nik") String nik);
	
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="nomorKk", column="nomor_kk"),
			@Result(property="isTidakBerlaku", column="is_tidak_berlaku"),
			@Result(property="idKelurahan", column="id_kelurahan"),
			@Result(property="alamat", column="id",
					javaType=AlamatModel.class, many=@Many(select="selectAlamatOfKeluarga")),
			@Result(property="anggotaKeluarga", column="id",
			javaType=List.class, many=@Many(select="selectAnggotaKeluarga"))
	})
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("select nomor_kk from keluarga where id = #{id}")
	String getNkkOfKeluarga(@Param("id") BigInteger id);
	
	List<PendudukModel> selectKeluargaOfKelurahan(BigInteger idKelurahan);
	
	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, "
			+ "agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) values "
			+ "(#{nik}, #{nama}, #{tempatLahir}, #{tanggalLahir}, #{jenisKelamin}, #{isWni}, #{idKeluarga}, "
			+ "#{agama}, #{pekerjaan}, #{statusPerkawinan}, #{statusDalamKeluarga}, #{golonganDarah}, #{isWafat})")
	void addPenduduk(@Valid PendudukModel penduduk);
	
	@Insert("insert into keluarga (nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) VALUES "
			+ "(#{nomorKk}, #{alamat.alamat}, #{alamat.rt}, #{alamat.rw}, #{idKelurahan},  #{isTidakBerlaku})")
	void addKeluarga(@Valid KeluargaModel keluarga);
	
	@Update("update penduduk set nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempatLahir}, "
			+ "tanggal_lahir = #{penduduk.tanggalLahir}, jenis_kelamin = #{penduduk.jenisKelamin}, is_wni = #{penduduk.isWni}, "
			+ "id_keluarga = #{penduduk.idKeluarga}, agama = #{penduduk.agama}, pekerjaan = #{penduduk.pekerjaan}, "
			+ "status_perkawinan = #{penduduk.statusPerkawinan}, status_dalam_keluarga = #{penduduk.statusDalamKeluarga}, "
			+ "golongan_darah = #{penduduk.golonganDarah}, is_wafat = #{penduduk.isWafat} where nik = #{nikLama}")
	void updatePenduduk(@Param("nikLama") String nikLama, @Param("penduduk") @Valid PendudukModel penduduk);
	
	void updateKeluarga(KeluargaModel keluarga);
	
	@Select("select kga.alamat as alamat, kga.RT as rt, kga.RW as rw, klr.nama_kelurahan as nama_kelurahan,"
			+ " kcm.nama_kecamatan as nama_kecamatan, kt.nama_kota as nama_kota " + 
			"from keluarga as kga, kelurahan as klr, kecamatan as kcm, kota as kt " + 
			"where kga.id_kelurahan = klr.id and klr.id_kecamatan = kcm.id and kcm.id_kota = kt.id and kga.id = #{id}")
	@Results(value = {
		@Result(property="alamat", column="alamat"),
		@Result(property="rt", column="rt"),
		@Result(property="rw", column="rw"),
		@Result(property="namaKelurahan", column="nama_kelurahan"),
		@Result(property="namaKecamatan", column="nama_kecamatan"),
		@Result(property="namaKota", column="nama_kota")
	})
	AlamatModel selectAlamatOfKeluarga(@Param("id") BigInteger id);
	
	@Select("select * from penduduk where id_keluarga = #{idKeluarga}")
	@Results(value = {
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="tempatLahir", column="tempat_lahir"),
			@Result(property="tanggalLahir", column="tanggal_lahir",
					javaType=Date.class),
			@Result(property="jenisKelamin", column="jenis_kelamin"),
			@Result(property="isWni", column="is_wni"),
			@Result(property="idKeluarga", column="id_keluarga",
					javaType=BigInteger.class),
			@Result(property="agama", column="agama"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="statusPerkawinan", column="status_perkawinan"),
			@Result(property="statusDalamKeluarga", column="status_dalam_keluarga"),
			@Result(property="golonganDarah", column="golongan_darah"),
			@Result(property="isWafat", column="is_wafat"),
			@Result(property="alamat", column="id_keluarga",
					javaType=AlamatModel.class, many=@Many(select="selectAlamatOfKeluarga"))
		})
	List<PendudukModel>selectAnggotaKeluarga(@Param("idKeluarga") BigInteger idKeluarga);
	
	@Select("select nik from penduduk where nik like #{kodeDomisili} order by nik desc limit 1")
	String getNikOfPersonBefore(@Param("kodeDomisili") String kodeDomisili);
	
	@Select("select nomor_kk from keluarga where nomor_kk like #{kodeDomisili} order by nomor_kk desc limit 1")
	String getNkkOfFamilyBefore(@Param("kodeDomisili") String kodeDomisili);
	
	@Select("select id from kelurahan where nama_kelurahan = #{namaKelurahan}")
	BigInteger selectKelurahanByName(@Param("namaKelurahan") String namaKelurahan);
	
	@Select("select kode_kelurahan from kelurahan where id = #{id}")
	String getKodeKelurahan(@Param("id") BigInteger id);
	
}
