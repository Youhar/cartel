package com.cartel.model;

import java.util.List;
import java.util.Vector;


public class Panier extends Vector<Item> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 411345287546717932L;
	/**
	 *  quantites: 0:Alcool 1:Soft 2:Food 3: Other
	 */
	private int[] qtes = {0,0,0,0};
	
	public Panier(){
		super();
	}

	
	/**
	 * Permet de calculer et donner le prix total du Panier
	 * 
	 * @return le prix total du Panier
	 */
	@SuppressWarnings("static-access")
	public Euros getPrix(){
		Euros prix = new Euros(0,0,0);
		for (int i=0;i<this.size();i++){
			prix=prix.add(prix,this.get(i).getPrix());
		}
		return prix;
	}

	/**Permet d'ajouter une conso au panier. Ajoute un nouvel item si c'est un nouveau type de Conso dans le panier.
	 * 
	 * @param c conso a ajouter
	 */
	public void addConso(Conso c){
		switch (c.getType()){
		case "A":
			qtes[0]+=1;
			break;
		case "S":
			qtes[1]+=1;
			break;
		case "F":
			qtes[2]+=1;
			break;
		case "E":
			qtes[3]+=1;
			break;
		}
		for (int i = 0; i<this.size();i++){
			if (this.get(i).getConso().getId()==c.getId()){
				this.get(i).ajoutQte();
				return;
			}
		}
		if(this.size()<6){
			this.add(new Item(c,1));
		}
		return;
	}
	
	/** Supprime un item de la liste
	 * @param itemNb numero de l'item a supprimer
	 */
	public void deleteItem(int itemNb){
		switch (get(itemNb).getConso().getType()){
		case "A":
			qtes[0]-=get(itemNb).getQte();
			break;
		case "S":
			qtes[1]-=get(itemNb).getQte();
			break;
		case "F":
			qtes[2]-=get(itemNb).getQte();
			break;
		case "E":
			qtes[3]-=get(itemNb).getQte();
			break;
		}
		this.remove(itemNb);
	}
	
	
	public int[] getQtes() {
		return qtes;
	}
	
	public void setQte(int type, int qte){
		qtes[type]=qte;
	}


	/** Decode la representation String d'un panier en un Panier
	 * @param s la representation String d'un panier
	 * @return le Panier decode
	 */
	public static Panier sToPanier(String s, List<Conso> liste){
		Panier p = new Panier();
		Conso tempC;
		for (int i=0;i<s.length();i+=4){
			tempC=null;
			for (Conso c : liste){
				if(c.getId()==Integer.parseInt(s.substring(i, i+2))){
					tempC=c;
				}
			}
			switch (tempC.getType()){
			case "A":
				p.setQte(0, p.getQtes()[0]+Integer.parseInt(s.substring(i+2, i+4)));
				break;
			case "S":
				p.setQte(1, p.getQtes()[1]+Integer.parseInt(s.substring(i+2, i+4)));
				break;
			case "F":
				p.setQte(2, p.getQtes()[2]+Integer.parseInt(s.substring(i+2, i+4)));
				break;
			case "E":
				p.setQte(2, p.getQtes()[3]+Integer.parseInt(s.substring(i+2, i+4)));
				break;	
			}
			p.add(new Item(tempC,Integer.parseInt(s.substring(i+2, i+4))));
		}
		
		return p;
	}
	
	/** Code un panier en sa representation String
	 * @param p le panier a coder
	 * @return la representation String du panier
	 */
	public static String pToString(Panier p){
		String pan="";
		String consoId;
		String consoNb;
		
		for (int i=0;i<p.size();i++){
			if (p.get(i).getConso().getId()<10){
				consoId="0"+p.get(i).getConso().getId();
			}else{
				consoId=p.get(i).getConso().getId()+"";
			}
			if (p.get(i).getQte()<10){
				consoNb="0"+p.get(i).getQte();
			}else{
				consoNb=p.get(i).getQte()+"";
			}
			
			pan=pan+consoId+consoNb;
		}
		
		return pan;
		
	}
}

