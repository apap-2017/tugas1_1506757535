package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select * from kota")
	@Results(value = {
			@Result(property="idKota", column="id_kota"),
			@Result(property="kodeKota", column="kode_kota"),
			@Result(property="namaKota", column="nama_kota")
	})
	List<KotaModel> selectAllKota();
	
	@Select("select * from kota where id = #{idKota}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKota", column="kode_kota"),
			@Result(property="namaKota", column="nama_kota")
	})
	KotaModel selectKota(@Param("idKota") BigInteger idKota);
}
