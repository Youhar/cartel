package com.cartel.model;

public class Item {
    
    private Euros prixTotal;
    private int qte;
    private Conso conso;

    /**
     * Construit un item avec une conso et sa quantite associee
     * 
     * @param cons la conso consideree
     * @param quantite la quantite
     */
    public Item(final Conso cons, final int quantite) {
    	setConso(cons);
    	setQte(quantite);
    	prixTotal=Euros.multiply(cons.getEurosPrix(),qte);
    }
    
    /**
     * Construit un item avec juste une conso : la quantite est initialisee a 1
     * 
     * @param la conso consideree
     */
    public Item(final Conso cons) {
    	this(cons,1);
    }
    

    /**
     * @return le prix total de l'item
     */
    public Euros getPrix() {
    	prixTotal=Euros.multiply(conso.getEurosPrix(), qte);
        return prixTotal;
    }

    /**
     * Permet d'incrementer la quantite de l'item
     */
    public void ajoutQte() {
    	qte++;
    	prixTotal=Euros.multiply(conso.getEurosPrix(), qte);
    	
    }
    
    public String toString(){
    	return "Conso :"+this.conso+"  "+ prixTotal;
    }

	public Euros getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(Euros prixTotal) {
		this.prixTotal = prixTotal;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public Conso getConso() {
		return conso;
	}

	public void setConso(Conso conso) {
		this.conso = conso;
	}

    
    
}

