package com.task.entity;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class AbstractIdentification implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
}
