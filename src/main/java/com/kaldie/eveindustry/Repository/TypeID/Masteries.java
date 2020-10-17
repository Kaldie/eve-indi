package com.kaldie.eveindustry.Repository.TypeID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Masteries implements Serializable {
    
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @JsonProperty("0")
    @ElementCollection(targetClass=Long.class)
    private List<Long> zero;
    
    @JsonProperty("1")
    @ElementCollection(targetClass=Long.class)
    private List<Long> first;
    
    @JsonProperty("2")
    @ElementCollection(targetClass=Long.class)
    private List<Long> two;
    
    @JsonProperty("3")
    @ElementCollection(targetClass=Long.class)
    private List<Long> three;
    
    @JsonProperty("4")
    @ElementCollection(targetClass=Long.class)
    private List<Long> four;

}
