package br.com.sp.fatec.javamotors.controller;

import java.util.List;

import br.com.sp.fatec.javamotors.dao.MarcaDAO;
import br.com.sp.fatec.javamotors.model.Marca;

public class MarcaController {
	
	private MarcaDAO marcaDao = new MarcaDAO();
	
	public List<Marca> index() {
		return marcaDao.findAll();
	}
	
	public Marca show(Long marcaId) {
		return marcaDao.findById(marcaId);
	}
}
