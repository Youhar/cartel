package com.cartel.model;

import java.io.File;
import java.math.BigDecimal;

import javax.swing.JFileChooser;

public class Client {

	private Integer id;
	private String nom;
	private String prenom;
	private String delegation;
	private Euros solde;
	private byte[] photo;
	private String role;
	
	// Constructors
	public Client(){}
	
	public Client(String nom, String prenom, String delegation, BigDecimal solde, String role) {
		super();
		setNom(nom);
		setPrenom(prenom);
		setDelegation(delegation);
		setSolde(solde);
		setRole(role);
		System.out.println("Construct sans Photo");
	}

	public Client(String nom, String prenom, String delegation, BigDecimal solde, String role, byte[] photo) {
		super();
		setNom(nom);
		setPrenom(prenom);
		setDelegation(delegation);
		setSolde(solde);
		setPhoto(photo);
		setRole(role);
		System.out.println("Construct avec Photo");
	}

	// Getters / Setters
	public Integer getId() {
		return id;
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
	public String getDelegation() {
		return delegation;
	}
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public BigDecimal getSolde() {
		return solde.toBigDecimal();
	}

	public void setSolde(BigDecimal solde) {
		this.solde = new Euros(solde);
	}
	
	public Euros getEurosSolde() {
		return solde;
	}

	public void setEurosSolde(Euros solde) {
		this.solde = solde;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
        this.photo=photo;
	}
	
	public String getRole(){
		return role;
	}
	
	public void setRole(String role){
		this.role=role;
	}
	
	//Static photo methods
	
	public static String getPhotoPath(Integer id){
		String s = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		new File(s+"\\ObjectifCartel\\img\\clients").mkdirs();
		return s+"\\ObjectifCartel\\img\\clients\\client"+id+".jpg";
	}
	
	public static boolean photoExists(Integer id){
		File f = new File(Client.getPhotoPath(id));
		if(f.exists() && !f.isDirectory()) { 
		   return true;
		}
		return false;
	}
	
	
	
}
