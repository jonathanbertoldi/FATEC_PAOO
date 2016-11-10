package br.com.sp.fatec.javamotors.model;

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
