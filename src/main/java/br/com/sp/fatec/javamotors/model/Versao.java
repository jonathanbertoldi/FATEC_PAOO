package br.com.sp.fatec.javamotors.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "versao")
public class Versao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private Float cilindradas;
	
	@Column(length = 50, nullable = false)
    private String nomeMotor;
	
	@Column(length = 50, nullable = false)
    private int valvulas;
	
	@Column(length = 50, nullable = false)
    private int portas;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
    private Combustivel combustivel;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
    private Cambio cambio;
	
	@ManyToOne
	@JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;
	
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

	public Float getCilindradas() {
		return cilindradas;
	}

	public void setCilindradas(Float cilindradas) {
		this.cilindradas = cilindradas;
	}

	public String getNomeMotor() {
		return nomeMotor;
	}

	public void setNomeMotor(String nomeMotor) {
		this.nomeMotor = nomeMotor;
	}

	public int getValvulas() {
		return valvulas;
	}

	public void setValvulas(int valvulas) {
		this.valvulas = valvulas;
	}

	public int getPortas() {
		return portas;
	}

	public void setPortas(int portas) {
		this.portas = portas;
	}

	public Combustivel getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(Combustivel combustivel) {
		this.combustivel = combustivel;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
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
        String retorno = Float.toString(getCilindradas() / 1000) + " " +
                getNomeMotor() + " " +
                Integer.toString(getValvulas()) + "V " +
                getCombustivel() + " " +
                Integer.toString(getPortas()) + "P " +
                getCambio();
        return retorno;
    }
}
