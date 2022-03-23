package br.com.hmv.models.enums;

public enum StatusConvenioEnum {
    ATIVO(1L),
    INATIVO(2L),
    ENCERRADO(3L);

    private long codigoStatus;

    StatusConvenioEnum(long codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public long getCodigoStatusConvenio(){
        return this.codigoStatus;
    }

    public static StatusConvenioEnum obterStatusConvenio(long codigoStatusConvenio) {
        for (StatusConvenioEnum status : StatusConvenioEnum.values()) {
            if (status.getCodigoStatusConvenio() == codigoStatusConvenio) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("Status convenio %i não conhecido ",codigoStatusConvenio));
    }
}
