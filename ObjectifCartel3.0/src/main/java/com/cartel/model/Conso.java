package com.cartel.model;

import java.io.File;
import java.math.BigDecimal;

import javax.swing.JFileChooser;

public class Conso {
    private String nom;
    private Integer id;
    private Euros prix;
    /**
     * Values : Alcool : "A", Softs Drinks : "S", Food : "F", Other : "E"
     */
    private String type;
    private byte[] photo;
    
    public Conso(){
    	
    }
    
    public Conso(final String nom, final BigDecimal prix, String type) {
    	super();
    	setNom(nom);
    	setPrix(prix);
    	setType(type);
    }
    
    public Conso(final String nom, final BigDecimal prix, String type, byte[] photo) {
    	super();
    	setNom(nom);
    	setPrix(prix);
    	setType(type);
    	setPhoto(photo);
    }

    
    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public BigDecimal getPrix(){
		return getEurosPrix().toBigDecimal();
	}
	
	public void setPrix(BigDecimal d){
		setEurosPrix(new Euros(d));
	}

	public Euros getEurosPrix() {
		return prix;
	}

	public void setEurosPrix(Euros prix) {
		this.prix = prix;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
        this.photo=photo;
	}

	public String toString(){
    	return "Nom "+nom+" id "+id+ " prix "+ prix;
    }
	
	//Static photo methods
	
	public static String getPhotoPath(Integer id){
		String s = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		new File(s+"\\ObjectifCartel\\img\\consos").mkdirs();
		return s+"\\ObjectifCartel\\img\\consos\\conso"+id+".jpg";
	}
	
	public static boolean photoExists(Integer id){
		File f = new File(Conso.getPhotoPath(id));
		if(f.exists() && !f.isDirectory()) { 
		   return true;
		}
		return false;
	}
}