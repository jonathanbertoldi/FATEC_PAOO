/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos.enums;

/**
 *
 * @author Jonathan
 */
public enum Sexo {
    MASCULINO("Masculino"),
    FEMININO("Feminino");
    
    private String descricao;
    
    private Sexo(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    public static Sexo compara(String valor) {
        if (valor.equals("Masculino"))
            return Sexo.MASCULINO;
        else
            return Sexo.FEMININO;
    }
}
