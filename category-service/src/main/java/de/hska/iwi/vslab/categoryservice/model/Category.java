package de.hska.iwi.vslab.categoryservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Category {
	@Id
	@GeneratedValue
	public Long id;

	public String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return ("id: " + this.id + " name: " + this.name);
	}
}