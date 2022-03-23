package br.com.hmv.models.enums;

public enum StatusUnidadeHospitalEnum {
    ATIVA(1L),
    INATIVA(2L),
    ENCERRADA(3L);

    private long codigoStatus;

    StatusUnidadeHospitalEnum(long codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public long getCodigoStatusHospitalUnidade(){
        return this.codigoStatus;
    }

    public static StatusUnidadeHospitalEnum obterStatusUnidadeHospital(long codigoStatusUnidadeHospital) {
        for (StatusUnidadeHospitalEnum status : StatusUnidadeHospitalEnum.values()) {
            if (status.getCodigoStatusHospitalUnidade() == codigoStatusUnidadeHospital) {
                return status;
            }
        }
        throw new IllegalArgumentException(String.format("Status unidade hospital %i não conhecido ",codigoStatusUnidadeHospital));
    }
}
