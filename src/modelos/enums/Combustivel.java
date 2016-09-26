package modelos.enums;

/**
 *
 * @author Jonathan
 */
public enum Combustivel {
    ALCOOL("Álcool"),
    GASOLINA("Gasolina"),
    GAS_NATURAL("Gás Natural"),
    FLEX("Flex");
    
    private String descricao;
    
    private Combustivel(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    public static Combustivel compara(String valor) {
        if (valor.equals("Álcool"))
            return Combustivel.ALCOOL;
        else if (valor.equals("Gasolina"))
            return Combustivel.GASOLINA;
        else if (valor.equals("Gás Natural"))
            return Combustivel.GAS_NATURAL;
        else
            return Combustivel.FLEX;
    }
}
