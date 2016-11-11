package br.com.sp.fatec.javamotors.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.sp.fatec.javamotors.dao.MarcaDAO;
import br.com.sp.fatec.javamotors.model.Marca;

public class MarcaController {
	
	private MarcaDAO marcaDao;
	
	public MarcaController() {
		marcaDao = new MarcaDAO();
	}
	
	public List<Marca> index() {
		return marcaDao.findAll();
	}
	
	public Marca show(Long marcaId) {
		return marcaDao.findById(marcaId);
	}
	
	public boolean create(Marca marca) {
		try {
			marca.setCriadoEm(LocalDate.now());
			marcaDao.insert(marca);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean update(Marca marca) {
		try {
			marca.setAtualizadoEm(LocalDate.now());
			marcaDao.update(marca);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean destroy(Marca marca) {
		try {
			marca.setDeletadoEm(LocalDate.now());
			marcaDao.update(marca);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
