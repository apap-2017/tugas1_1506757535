package com.example.dao;

import java.math.BigInteger;
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
import com.example.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="nomorKk", column="nomor_kk"),
			@Result(property="isTidakBerlaku", column="is_tidak_berlaku"),
			@Result(property="idKelurahan", column="id_kelurahan"),
			@Result(property="alamat", column="id",
					javaType=AlamatModel.class, many=@Many(select="selectAlamatOfKeluarga")),
			@Result(property="anggotaKeluarga", column="id",
			javaType=List.class, many=@Many(select="com.example.dao.PendudukMapper.selectAnggotaKeluarga"))
	})
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("select nomor_kk from keluarga where id = #{id}")
	String getNkkOfKeluarga(@Param("id") BigInteger id);
	
	@Select("select id from keluarga where id_kelurahan = #{idKelurahan}")
	List<BigInteger> selectKeluargaOfKelurahan(BigInteger idKelurahan);
	
	@Insert("insert into keluarga (nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) VALUES "
			+ "(#{nomorKk}, #{alamat.alamat}, #{alamat.rt}, #{alamat.rw}, #{idKelurahan},  #{isTidakBerlaku})")
	void addKeluarga(@Valid KeluargaModel keluarga);
	
	@Update("update keluarga set nomor_kk = #{keluarga.nomorKk}, alamat = #{keluarga.alamat.alamat}, rt = #{keluarga.alamat.rt}, "
			+ "rw = #{keluarga.alamat.rw}, id_kelurahan = #{keluarga.idKelurahan}, is_tidak_berlaku = #{keluarga.isTidakBerlaku}  "
			+ "where nomor_kk = #{nkkLama}")
	void updateKeluarga(@Param("nkkLama") String nkkLama, @Param("keluarga") @Valid KeluargaModel keluarga);
	
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
	
	@Select("select nomor_kk from keluarga where nomor_kk like #{kodeDomisili} order by nomor_kk desc limit 1")
	String getNkkOfFamilyBefore(@Param("kodeDomisili") String kodeDomisili);
	
	@Update("update keluarga set is_tidak_berlaku = 1 where nomor_kk = #{nkk}")
	void updateStatusKeluarga(String nkk);
}
