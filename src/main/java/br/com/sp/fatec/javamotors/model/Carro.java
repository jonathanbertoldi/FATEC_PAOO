package br.com.sp.fatec.javamotors.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carro")
public class Carro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "versao_id", nullable = false)
	private Versao versao;
	
	@Column(length = 10, nullable = false)
	private Float km;
	
	@Column(length = 10, nullable = false)
	private String cor;
	
	@ElementCollection
	@CollectionTable(name = "carro_fotos", joinColumns = {@JoinColumn(name = "carro_id")})
	@Column(name = "caminho_foto")
	private Set<String> fotos;
	
	@Column(name = "data_anuncio", nullable = false)
	private LocalDate dataAnuncio;
	
	@OneToOne
	@JoinColumn(name = "anunciante_id", nullable = false)
	private Cliente anunciante;
	
	@OneToOne
	@JoinColumn(name = "comprador_id")
	private Cliente comprador;
	
	@Column(name = "data_venda")
	private LocalDate dataVenda;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private Status status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Versao getVersao() {
		return versao;
	}
	public void setVersao(Versao versao) {
		this.versao = versao;
	}
	public Float getKm() {
		return km;
	}
	public void setKm(Float km) {
		this.km = km;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public Set<String> getFotos() {
		return fotos;
	}
	public void setFotos(Set<String> fotos) {
		this.fotos = fotos;
	}
	public LocalDate getDataAnuncio() {
		return dataAnuncio;
	}
	public void setDataAnuncio(LocalDate dataAnuncio) {
		this.dataAnuncio = dataAnuncio;
	}
	public Cliente getAnunciante() {
		return anunciante;
	}
	public void setAnunciante(Cliente anunciante) {
		this.anunciante = anunciante;
	}
	public Cliente getComprador() {
		return comprador;
	}
	public void setComprador(Cliente comprador) {
		this.comprador = comprador;
	}
	public LocalDate getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
