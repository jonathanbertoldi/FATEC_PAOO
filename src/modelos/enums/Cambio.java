package modelos.enums;

/**
 *
 * @author Jonathan
 */
public enum Cambio {
    MANUAL("Manual"),
    AUTOMATICO("Automático");
    
    private String descricao;
    
    private Cambio(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    public static Cambio compara(String valor) {
        if (valor.equals("Manual"))
            return Cambio.MANUAL;
        else
            return Cambio.AUTOMATICO;
    }
}
