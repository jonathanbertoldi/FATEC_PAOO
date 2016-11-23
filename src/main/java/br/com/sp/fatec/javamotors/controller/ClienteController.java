package br.com.sp.fatec.javamotors.controller;

import java.time.LocalDate;
import java.util.List;

import br.com.sp.fatec.javamotors.dao.ClienteDAO;
import br.com.sp.fatec.javamotors.dao.MarcaDAO;
import br.com.sp.fatec.javamotors.model.Cliente;
import br.com.sp.fatec.javamotors.model.Marca;

public class ClienteController {

	private ClienteDAO clienteDao;
	
	public ClienteController() {
		clienteDao = new ClienteDAO();
	}
	
	public List<Cliente> index() {
		return clienteDao.findAll();
	}
	
	public Cliente show(Long clienteId) {
		return clienteDao.findById(clienteId);
	}
	
	public boolean create(Cliente cliente) {
		try {
			cliente.setCriadoEm(LocalDate.now());
			clienteDao.insert(cliente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean update(Cliente cliente) {
		try {
			cliente.setAtualizadoEm(LocalDate.now());
			clienteDao.update(cliente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean destroy(Cliente cliente) {
		try {
			cliente.setDeletadoEm(LocalDate.now());
			clienteDao.update(cliente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
