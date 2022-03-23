package br.com.hmv.services.validation.administrativo.especialidade.atualizacao_status;

import br.com.hmv.dtos.request.administrativo.EspecialidadeAtualizaStatusRequestDTO;
import br.com.hmv.exceptions.FieldMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class EspecialidadeAtualizaStatusValidator implements ConstraintValidator<EspecialidadeAtualizaStatusValid, EspecialidadeAtualizaStatusRequestDTO> {


    @Override
    public void initialize(EspecialidadeAtualizaStatusValid ann) {
    }

    @Override
    public boolean isValid(EspecialidadeAtualizaStatusRequestDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        //Retorno booleano (true/false) de acordo com a resposta da pergunta se a lista esta preenchida ou nao
        return list.isEmpty();
    }
}
