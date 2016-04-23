package com.example.model;

import javax.persistence.*;

/**
 * @author <a href="mailto:john.scattergood@navis.com">John Scattergood</a> 4/22/2016
 */
@Entity
public class Father {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @ManyToOne
    User owner;
    Boolean isDeleted = false;
}
