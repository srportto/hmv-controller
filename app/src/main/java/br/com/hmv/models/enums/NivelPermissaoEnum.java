package br.com.hmv.models.enums;

public enum NivelPermissaoEnum {
    ROLE_PACIENTE(1L),
    ROLE_MEDICO(2L),
    ROLE_ADMINISTRATIVO(3L),
    ROLE_MASTER(4L);

    private long nivelPermissao;

    NivelPermissaoEnum(long nivelPermissao) {
        this.nivelPermissao = nivelPermissao;
    }

    public long getNivelPermissao() {
        return this.nivelPermissao;
    }

    public static NivelPermissaoEnum obterNomePermissao(long nivelPermissao) {
        for (NivelPermissaoEnum permissao : NivelPermissaoEnum.values()) {
            if (permissao.getNivelPermissao() == nivelPermissao) {
                return permissao;
            }
        }
        throw new IllegalArgumentException(String.format("Nivel de permisão %i não conhecido ", nivelPermissao));
    }
}
