package br.com.hmv.services.validation.administrativo.funcionario.criacao;

import br.com.hmv.dtos.request.administrativo.FuncionarioInsertRequestDTO;
import br.com.hmv.exceptions.FieldMessage;
import br.com.hmv.repositories.FuncionarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class FuncionarioInsertValidator implements ConstraintValidator<FuncionarioInsertValid, FuncionarioInsertRequestDTO> {

    private FuncionarioRepository repository;

    @Override
    public void initialize(FuncionarioInsertValid ann) {
    }

    @Override
    public boolean isValid(FuncionarioInsertRequestDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        var funcionarioValidaEmail = repository.findFuncionarioByEmail(dto.getEmail());

        if (funcionarioValidaEmail != null) {
            list.add(new FieldMessage("email", "Não é possível cadastrar este email, está em uso por outro usuário"));
        }

        var funcionarioValidaCpf = repository.findFuncionarioByCpf(dto.getCpf());
        if (funcionarioValidaCpf != null) {
            list.add(new FieldMessage("cpf", "Não é possível cadastrar este cpf, está em uso por outro usuário"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        //Retorno booleano (true/false) de acordo com a resposta da pergunta se a lista esta preenchida ou nao
        return list.isEmpty();
    }
}
