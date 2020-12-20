package com.kaldie.eveindustry.repository.type_id;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslatedString implements Serializable {
    
    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String de;

    @Lob
    private String en;

    @Lob
    private String fr;

    @Lob
    @Nationalized
    private String ja;

    @Lob
    @Nationalized
    private String ru;

    @Lob
    @Nationalized
    private String zh;

    @Lob
    @Nationalized
    private String es;

    @Lob
    @Nationalized
    private String it;
}