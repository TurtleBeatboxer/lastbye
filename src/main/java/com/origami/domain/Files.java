package com.origami.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.origami.domain.enumeration.FileType;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Files.
 */
@Entity
@Table(name = "files")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private FileType fileType;

    @Column(name = "format")
    private String format;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JsonIgnoreProperties(value = { "personalities", "files" }, allowSetters = true)
    private Profile profile;
    // jhipster-needle-entity-add-field - JHipster will add fields here

}
