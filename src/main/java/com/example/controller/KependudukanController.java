package com.example.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

@Controller
public class KependudukanController {
	
	@Autowired
	PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;
	
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk")
    public String viewPenduduk (Model model,
            @RequestParam(value = "nik", required = false) String nik,
            @ModelAttribute("updateKematian") String updateKematian)
    {
		if(updateKematian.equals("update")) {
			model.addAttribute("nik", nik);
			return "success-deactivate-penduduk";
		}
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);

        if (penduduk != null) {
            model.addAttribute ("penduduk", penduduk);
            return "viewPenduduk";
        } else {
            model.addAttribute ("nik", nik);
            return "not-found-penduduk";
        }
    }
	
	@RequestMapping("/penduduk/tambah")
    public String addPenduduk(Model model) {
		model.addAttribute("penduduk", new PendudukModel());
		return "addPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String addPendudukSubmit (Model model, PendudukModel penduduk)
    {
        String nik = pendudukDAO.addPenduduk (penduduk);
        model.addAttribute("nik", nik);
        return "success-add-penduduk";
    }
	
	@RequestMapping("/penduduk/cari")
    public String searchPenduduk(Model model, 
    		@RequestParam(value = "kt", required = false) String kota,
    		@RequestParam(value = "kc", required = false) String kecamatan,
    		@RequestParam(value = "kl", required = false) String kelurahan
    ) {
		if(kota != null) {
			KotaModel modelKota = kotaDAO.selectKota(new BigInteger(kota));
			model.addAttribute("modelKota", modelKota);
			
			if(kecamatan != null) {
				KecamatanModel modelKecamatan = kecamatanDAO.selectKecamatan(new BigInteger(kecamatan));
				model.addAttribute("modelKecamatan", modelKecamatan);
				
				if(kelurahan != null) {
					KelurahanModel modelKelurahan = kelurahanDAO.selectKelurahan(new BigInteger(kelurahan));
					model.addAttribute("modelKelurahan", modelKelurahan);
					List<PendudukModel> listWargaKelurahan = pendudukDAO.selectWargaKelurahan(modelKelurahan.getId());
					if(listWargaKelurahan == null) {
						model.addAttribute("listKosong", true);
					} else {
						model.addAttribute("listWargaKelurahan", listWargaKelurahan);
						model.addAttribute("lastIndex", listWargaKelurahan.size()-1);
					}
						
				} else {
					List<KelurahanModel> listKelurahan = kelurahanDAO.selectKelurahanByKecamatan(modelKecamatan.getId());
					model.addAttribute("listKelurahan", listKelurahan);
				}
				
			} else {
				List<KecamatanModel> listKecamatan = kecamatanDAO.selectKecamatanByKota(modelKota.getId());
				model.addAttribute("listKecamatan", listKecamatan);
			}
			
		} else {
			List<KotaModel> listKota = kotaDAO.selectAllKota();
			model.addAttribute("listKota", listKota);
		}
		
		return "searchPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}")
    public String updatePenduduk (Model model, @PathVariable(value = "nik") String nik)
    {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		model.addAttribute("penduduk", penduduk);
		if (penduduk != null) {
            model.addAttribute ("penduduk", penduduk);
            return "updatePenduduk";
        } else {
            model.addAttribute ("nik", nik);
            return "not-found-penduduk";
        }
    }
	
	@RequestMapping(value = "/penduduk/ubah/{nomorNik}", method = RequestMethod.POST)
    public String updatePendudukSubmit (Model model, PendudukModel penduduk, @PathVariable(value = "nomorNik") String nomorNik)
    {
		pendudukDAO.updatePenduduk(penduduk);
		model.addAttribute("nik", nomorNik);
		return "success-update-penduduk";
    }
	
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
    public String updateStatusKematian (HttpServletRequest request, String nik, RedirectAttributes rattr)
    {
        pendudukDAO.updateStatusKematian(nik);
		String referer = request.getHeader("Referer");
		rattr.addFlashAttribute("updateKematian", "update");
        return "redirect:" + referer;
    }	
	
	@RequestMapping("/keluarga")
    public String viewKeluarga (Model model,
            @RequestParam(value = "nkk", required = false) String nkk)
    {
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

        if (keluarga != null) {
            model.addAttribute ("keluarga", keluarga);
            return "viewKeluarga";
        } else {
            model.addAttribute ("nkk", nkk);
            return "not-found-keluarga";
        }
    }
	
	@RequestMapping("/keluarga/tambah")
    public String addKeluarga(Model model) {
		model.addAttribute("alamat", new AlamatModel());
		return "addKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String addKeluargaSubmit (Model model, AlamatModel alamat)
    {
        String nkk = keluargaDAO.addKeluarga (alamat);
        if(nkk == null) {
        	model.addAttribute("alamat", alamat);
        	return "failed-add-keluarga";
        } else {
	        model.addAttribute("nkk", nkk);
	        return "success-add-keluarga";
        }
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}")
    public String updateKeluarga (Model model, @PathVariable(value = "nkk") String nkk)
    {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		model.addAttribute("keluarga", keluarga);
		if (keluarga != null) {
            model.addAttribute ("keluarga", keluarga);
            return "updateKeluarga";
        } else {
            model.addAttribute ("nkk", nkk);
            return "not-found-keluarga";
        }
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nomorKk}", method = RequestMethod.POST)
    public String updateKeluargaSubmit (Model model, KeluargaModel keluarga, @PathVariable(value = "nomorKk") String nomorKk)
    {
		boolean success = keluargaDAO.updateKeluarga(keluarga);
		if(success) {	
			model.addAttribute("nkk", nomorKk);
			return "success-update-keluarga";
		} else {
			model.addAttribute("alamat", keluarga.getAlamat());
			return "failed-update-keluarga";
		}
    }
}
