package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select id from kelurahan where nama_kelurahan = #{namaKelurahan}")
	BigInteger selectKelurahanByName(@Param("namaKelurahan") String namaKelurahan);
	
	@Select("select kode_kelurahan from kelurahan where id = #{id}")
	String getKodeKelurahan(@Param("id") BigInteger id);

	@Select("select * from kelurahan where id_kecamatan = #{idKecamatan}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKelurahan", column="kode_kelurahan"),
			@Result(property="namaKelurahan", column="nama_kelurahan"),
			@Result(property="idKecamatan", column="id_kecamatan"),
			@Result(property="kodePos", column="kode_pos"),
	})
	List<KelurahanModel> selectKelurahanByKecamatan(@Param("idKecamatan") BigInteger idKecamatan);

	@Select("select * from kelurahan where id = #{idKelurahan}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKelurahan", column="kode_kelurahan"),
			@Result(property="namaKelurahan", column="nama_kelurahan"),
			@Result(property="idKecamatan", column="id_kecamatan"),
			@Result(property="kodePos", column="kode_pos"),
	})
	KelurahanModel selectKelurahan(@Param("idKelurahan") BigInteger idKelurahan);
}
