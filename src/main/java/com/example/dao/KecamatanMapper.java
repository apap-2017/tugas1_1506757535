package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {	
	@Select("select * from kecamatan where id_kota = #{idKota}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKecamatan", column="kode_kecamatan"),
			@Result(property="namaKecamatan", column="nama_kecamatan"),
			@Result(property="idKota", column="id_kota")
	})
	List<KecamatanModel> selectKecamatanByKota(@Param("idKota") BigInteger idKota);
	
	@Select("select * from kecamatan where id = #{idKecamatan}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKecamatan", column="kode_kecamatan"),
			@Result(property="namaKecamatan", column="nama_kecamatan"),
			@Result(property="idKota", column="id_kota")
	})
	KecamatanModel selectKecamatan(@Param("idKecamatan") BigInteger idKecamatan);
}
