package com.cartel.model;

public class Operateur {

	private Integer id;
	private String nom;
	private String prenom;
	private String pseudo;
	private String telephone;
	private int role;
    /*
    0 pour Deconnecte
    1 pour Admin
	2 pour Crediteur
	3 pour Barman 1
	4 pour Barman 2
	*/
	
	public Integer getId() {
		return id;
	}
	
	public Operateur(){
		
	}
	
	public Operateur(String nom, String prenom, String pseudo, String telephone, int role) {
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		this.telephone = telephone;
		this.role = role;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
	@Override
	public String toString(){
		return this.pseudo;
	}
	
}
