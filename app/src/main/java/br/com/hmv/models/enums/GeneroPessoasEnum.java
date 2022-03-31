package br.com.hmv.models.enums;

public enum GeneroPessoasEnum {
    MASCULINO(1),
    FEMININO(2),
    OUTROS(3);

    private Integer codigoGeneroPessoa;

    GeneroPessoasEnum(Integer codigoGeneroPessoa) {
        this.codigoGeneroPessoa = codigoGeneroPessoa;
    }

    public Integer getCodigoGeneroPessoa() {
        return this.codigoGeneroPessoa;
    }

    public static GeneroPessoasEnum obterGeneroPessoa(Integer codigoGeneroPessoa) {
        for (GeneroPessoasEnum status : GeneroPessoasEnum.values()) {
            if (status.getCodigoGeneroPessoa() == codigoGeneroPessoa) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("codigo genero pessoa %i não conhecido ", codigoGeneroPessoa));
    }
}
