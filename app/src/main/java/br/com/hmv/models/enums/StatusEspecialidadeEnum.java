package br.com.hmv.models.enums;

public enum StatusEspecialidadeEnum {
    ATIVA(1L),
    INATIVA(2L),
    ENCERRADA(3L);

    private long codigoStatus;

    StatusEspecialidadeEnum(long codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public long getCodigoStatusEspecialidade() {
        return this.codigoStatus;
    }

    public static StatusEspecialidadeEnum obterStatusEspecialidade(long codigoStatus) {
        for (StatusEspecialidadeEnum status : StatusEspecialidadeEnum.values()) {
            if (status.getCodigoStatusEspecialidade() == codigoStatus) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("Status especialidade %i não conhecido ", codigoStatus));
    }
}
