package com.example.model;

import javax.persistence.*;

/**
 * @author <a href="https://github.com/jscattergood">John Scattergood</a> 4/22/2016
 */
@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @ManyToOne
    Father father;
    @ManyToOne
    Mother mother;
    Boolean isDeleted = false;
}
