package modelos;

import modelos.enums.Cambio;
import modelos.enums.Combustivel;

/**
 *
 * @author Jonathan
 */
public class Versao {
    private int codigo;
    private Float cilindradas;
    private String nomeMotor;
    private int valvulas;
    private int portas;
    private Combustivel combustivel;
    private Cambio cambio;
    private Modelo modelo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
