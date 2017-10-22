package com.example.dao;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.AlamatModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
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
				javaType=AlamatModel.class, many=@Many(select="com.example.dao.KeluargaMapper.selectAlamatOfKeluarga"))
	})
	PendudukModel selectPenduduk(@Param("nik") String nik);

	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, "
			+ "agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) values "
			+ "(#{nik}, #{nama}, #{tempatLahir}, #{tanggalLahir}, #{jenisKelamin}, #{isWni}, #{idKeluarga}, "
			+ "#{agama}, #{pekerjaan}, #{statusPerkawinan}, #{statusDalamKeluarga}, #{golonganDarah}, #{isWafat})")
	void addPenduduk(@Valid PendudukModel penduduk);
	
	@Update("update penduduk set nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempatLahir}, "
			+ "tanggal_lahir = #{penduduk.tanggalLahir}, jenis_kelamin = #{penduduk.jenisKelamin}, is_wni = #{penduduk.isWni}, "
			+ "id_keluarga = #{penduduk.idKeluarga}, agama = #{penduduk.agama}, pekerjaan = #{penduduk.pekerjaan}, "
			+ "status_perkawinan = #{penduduk.statusPerkawinan}, status_dalam_keluarga = #{penduduk.statusDalamKeluarga}, "
			+ "golongan_darah = #{penduduk.golonganDarah}, is_wafat = #{penduduk.isWafat} where nik = #{nikLama}")
	void updatePenduduk(@Param("nikLama") String nikLama, @Param("penduduk") @Valid PendudukModel penduduk);
		
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
					javaType=AlamatModel.class, many=@Many(select="com.example.dao.KeluargaMapper.selectAlamatOfKeluarga"))
	})
	List<PendudukModel>selectAnggotaKeluarga(@Param("idKeluarga") BigInteger idKeluarga);
	
	@Select("select nik from penduduk where nik like #{kodeDomisili} order by nik desc limit 1")
	String getNikOfPersonBefore(@Param("kodeDomisili") String kodeDomisili);
	
	@Update("update penduduk set is_wafat = 1 where nik = #{nik}")
	void updateStatusKematian(String nik);
}
