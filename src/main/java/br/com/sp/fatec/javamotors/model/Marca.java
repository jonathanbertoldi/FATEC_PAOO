package br.com.sp.fatec.javamotors.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "marca")
public class Marca {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@Column(length = 50, nullable = false)
	private String pais;
	
	@Column(length = 200, nullable = false)
	private String logo;
	
	@Column(name = "criado_em", nullable = false)
	private LocalDate criadoEm;
	
	@Column(name = "autalizado_em")
	private LocalDate atualizadoEm;
	
	@Column(name = "deletado_em")
	private LocalDate deletadoEm;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public LocalDate getCriadoEm() {
		return criadoEm;
	}
	public void setCriadoEm(LocalDate criadoEm) {
		this.criadoEm = criadoEm;
	}
	public LocalDate getAtualizadoEm() {
		return atualizadoEm;
	}
	public void setAtualizadoEm(LocalDate atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}
	public LocalDate getDeletadoEm() {
		return deletadoEm;
	}
	public void setDeletadoEm(LocalDate deletadoEm) {
		this.deletadoEm = deletadoEm;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
