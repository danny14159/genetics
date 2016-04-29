package com.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bean.Disease;
import bean.Rna;
import bean.RnaDisease;
import orm.daoutils.Cnd;
import orm.daoutils.DBUtil;
import orm.daoutils.Strings;

@Controller
@SpringBootApplication
public class MainController extends SpringBootServletInitializer{
	
	DBUtil<Rna> rna = new DBUtil<Rna>("tb_rna", Rna.class);
	DBUtil<Disease> disease = new DBUtil<Disease>("tb_disease", Disease.class);
	DBUtil<RnaDisease> rel = new DBUtil<RnaDisease>("tb_rna_disease", RnaDisease.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainController.class);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainController.class, args);
	}

	@RequestMapping("/")
	public String index(Model model,String sRna,String sDisease){
		
		if(Strings.isNotBlank(sRna)){
			
			model.addAttribute("rna", rna.list(Cnd.where("rna", "=", sRna))); //only one
			model.addAttribute("rel", rel.listBySql("select rel.rna rna,rel.disease disease from tb_rna_disease rel INNER JOIN tb_rna r on rel.rna=r.id where r.rna=?",sRna));
			model.addAttribute("disease", disease.listBySql("SELECT	d.id id,d.disease disease FROM	tb_rna r inner JOIN tb_rna_disease rel ON r.id = rel.rna inner JOIN tb_disease d ON d.id = rel.disease WHERE r.rna = ?",sRna));
			return "main";
		}
		if(Strings.isNotBlank(sDisease)){
			
			model.addAttribute("disease", disease.list(Cnd.where("disease", "=", sDisease))); //only one
			model.addAttribute("rel", rel.listBySql("select rel.rna rna,rel.disease disease from tb_rna_disease rel INNER JOIN tb_disease d on rel.disease=d.id where d.disease=?",sDisease));
			model.addAttribute("rna", rna.listBySql("select r.id id,r.rna rna from tb_disease d INNER JOIN tb_rna_disease rel on d.id = rel.disease INNER join tb_rna r on r.id=rel.rna where d.disease = ?",sDisease));
			return "main";
		}
		
		model.addAttribute("rel", rel.list(null));
		model.addAttribute("rna", rna.list(null));
		model.addAttribute("disease", disease.list(null));
		return "main";
	}
}
