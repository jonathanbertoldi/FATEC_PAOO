package modelos.enums;

/**
 *
 * @author Jonathan
 */
public enum Status {
    VENDIDO("Vendido"),
    A_VENDA("A Venda");
    
    private String descricao;
    
    private Status(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    public static Status compara(String valor) {
        if (valor.equals("Vendido"))
            return Status.VENDIDO;
        else
            return Status.A_VENDA;
    }
}
