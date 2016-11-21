package br.com.sp.fatec.javamotors.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.sp.fatec.javamotors.dao.ModeloDAO;
import br.com.sp.fatec.javamotors.model.Marca;
import br.com.sp.fatec.javamotors.model.Modelo;

public class ModeloController {

	private ModeloDAO modeloDao;
	
	public ModeloController() {
		modeloDao = new ModeloDAO();
	}
	
	public List<Modelo> index() {
		return modeloDao.findAll();
	}
	
	public Modelo show(Long modeloId) {
		return modeloDao.findById(modeloId);
	}
	
	public List<Modelo> findByMarca(Marca marca) {
		return modeloDao.findByMarca(marca.getId());
	}
	
	public boolean create(Modelo modelo) {
		try {
			modelo.setCriadoEm(LocalDate.now());
			modeloDao.insert(modelo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Modelo modelo) {
		try {
			modelo.setAtualizadoEm(LocalDate.now());
			modeloDao.update(modelo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean destroy(Modelo modelo) {
		try {
			modelo.setDeletadoEm(LocalDate.now());
			modeloDao.update(modelo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
