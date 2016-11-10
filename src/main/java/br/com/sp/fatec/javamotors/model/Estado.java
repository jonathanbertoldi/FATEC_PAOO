package br.com.sp.fatec.javamotors.model;

public enum Estado {
    RO("Rondônia"),
    AC("Acre"),
    AM("Amazonas"),
    RR("Roraima"),
    PA("Pará"),
    AP("Amapá"),
    TO("Tocantins"),
    MA("Maranhão"),
    PI("Piauí"),
    CE("Ceará"),
    RN("Rio Grande do Norte"),
    PB("Paraíba"),
    PE("Pernambuco"),
    AL("Alagoas"),
    SE("Sergipe"),
    BA("Bahia"),
    MG("Minas Gerais"),
    ES("Espírito Santo"),
    RJ("Rio de Janeiro"),
    SP("São Paulo"),
    PR("Paraná"),
    SC("Santa Catarina"),
    RS("Rio Grande do Sul"),
    MS("Mato Grosso do Sul"),
    MT("Mato Grosso"),
    GO("Goiás"),
    DF("Distrito Federal");

    private String descricao;

    private Estado(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    public static Estado compara(String valor) {
        for (Estado estado : Estado.values()) {
            if (estado.toString().equals(valor))
                return estado;
        }
        return null;
    }
}