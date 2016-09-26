package modelos;

import java.util.Calendar;

/**
 *
 * @author Jonathan
 */
public class Modelo {
    private int codigo;
    private String nome;
    private Long ano;
    private Marca marca;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getAno() {
        return ano;
    }

    public void setAno(Long ano) {
        this.ano = ano;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public boolean equals(Modelo obj) {
        return (obj.getCodigo() == this.codigo &&
                obj.getNome().equals(this.nome) &&
                obj.getAno() == this.getAno());
    }

    @Override
    public String toString() {
        return this.nome + " - " +this.ano.toString();
    }
}
