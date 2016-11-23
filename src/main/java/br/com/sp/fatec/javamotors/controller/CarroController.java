package br.com.sp.fatec.javamotors.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.sp.fatec.javamotors.dao.CarroDAO;
import br.com.sp.fatec.javamotors.dao.ClienteDAO;
import br.com.sp.fatec.javamotors.model.Carro;
import br.com.sp.fatec.javamotors.model.Cliente;

public class CarroController {

	private CarroDAO carroDao;
	
	public CarroController() {
		carroDao = new CarroDAO();
	}
	
	public List<Carro> index() {
		return carroDao.findAll();
	}
	
	public Carro show(Long carroId) {
		return carroDao.findById(carroId);
	}
	
	public boolean create(Carro carro) {
		try {
			carro.setDataAnuncio(LocalDate.now());
			carroDao.insert(carro);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean update(Carro carro) {
		try {
			carroDao.update(carro);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean sell(Carro carro) {
		try {
			carro.setDataVenda(LocalDate.now());
			carroDao.update(carro);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
