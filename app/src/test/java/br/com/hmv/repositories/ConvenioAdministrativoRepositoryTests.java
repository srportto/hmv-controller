package br.com.hmv.repositories;

import br.com.hmv.factory.ConvenioFactory;
import br.com.hmv.models.entities.ConvenioAdministrativo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ConvenioAdministrativoRepositoryTests {

    @Autowired
    private ConvenioAdministrativoRepository repository;

    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        ConvenioAdministrativo entity = ConvenioFactory.createConvenio();
        entity.setId(null);

        entity = repository.save(entity);
        Optional<ConvenioAdministrativo> result = repository.findById(entity.getId());

        Assertions.assertNotNull(entity.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), entity);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        ConvenioAdministrativo entity = ConvenioFactory.createConvenio();
        entity.setId(null);

        var entitySalva = repository.save(entity);
        var idSalvo = entitySalva.getId();

        Optional<ConvenioAdministrativo> result = repository.findById(idSalvo);
        ConvenioAdministrativo entityRegistroConsulrada = null;

        if (result.isPresent()) {
            entityRegistroConsulrada = result.get();
        } else {
            throw new RuntimeException("registro nao encontrado");
        }

        repository.deleteById(entityRegistroConsulrada.getId());
        Optional<ConvenioAdministrativo> resultBuscaPosDelecao = repository.findById(idSalvo);
        Assertions.assertFalse(resultBuscaPosDelecao.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }
}
