package de.hska.iwi.vslab.categoryservice.model;

@javax.persistence.Entity
public class Category {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue
	private Long id;

	private String name;

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
}