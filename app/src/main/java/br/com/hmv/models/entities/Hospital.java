package br.com.hmv.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_hospitais")
public class Hospital implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_unidade", nullable = false, unique = true, length = 36)
    private String codigoUnidade;

    @Column(name = "nome_unidade", nullable = false)
    private String nomeUnidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    private Long codigoStatusUnidade;

    @ManyToMany
    @JoinTable(name = "tb_hospitais_especialidades", //nome da tabela de relacionamento do manyToMany(uma 3° tab. so pro relacionamento)
            joinColumns = @JoinColumn(name ="hospital_id" ),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    Set<Especialidade> especialidades = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;
}
