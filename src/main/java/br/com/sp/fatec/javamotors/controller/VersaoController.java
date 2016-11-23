package br.com.sp.fatec.javamotors.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.sp.fatec.javamotors.dao.VersaoDAO;
import br.com.sp.fatec.javamotors.model.Modelo;
import br.com.sp.fatec.javamotors.model.Versao;

public class VersaoController {
private VersaoDAO versaoDao;
	
	public VersaoController() {
		versaoDao = new VersaoDAO();
	}
	
	public List<Versao> index() {
		return versaoDao.findAll();
	}
	
	public Versao show(Long versaoId) {
		return versaoDao.findById(versaoId);
	}
	
	public List<Versao> findByModelo(Modelo modelo) {
		return versaoDao.findByModelo(modelo.getId());
	}
	
	public boolean create(Versao versao) {
		try {
			versao.setCriadoEm(LocalDate.now());
			versaoDao.insert(versao);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Versao versao) {
		try {
			versao.setAtualizadoEm(LocalDate.now());
			versaoDao.update(versao);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean destroy(Versao versao) {
		try {
			versao.setDeletadoEm(LocalDate.now());
			versaoDao.update(versao);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
