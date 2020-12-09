package de.hska.iwi.vslab.userroleservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String type;
    private int level;

    protected Role() {
    }

    public Role(String type, int level) {
        this.type = type;
        this.level = level;
    }

    @Override
    public String toString() {
        return String.format("Role[id=%d, type='%s', level=%d]", id, type, level);
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getLevel() {
        return level;
    }
}