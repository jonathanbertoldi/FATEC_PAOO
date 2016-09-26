package modelos;

import java.time.LocalDate;
import modelos.enums.Status;

/**
 *
 * @author Jonathan
 */
public class Carro {
    private Integer codigo;
    private Versao versao;
    private Float km;
    private String cor;
    private String[] fotos;
    private LocalDate dataAnuncio;
    private Cliente anunciante;
    private Cliente comprador;
    private LocalDate dataVenda;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public String[] getFotos() {
        return fotos;
    }

    public void setFotos(String[] fotos) {
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
}
