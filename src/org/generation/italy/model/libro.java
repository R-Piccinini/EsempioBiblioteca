package org.generation.italy.model;

public class libro {
	
	public int id;		
	public String titolo;
	public String autore;
	public String genere;
	public int qnt;
	@Override
	public String toString() {
		return "[id=" + id + ", titolo=" + titolo + ", autore=" + autore + ", genere=" + genere + ", qnt=" + qnt
				+ "]";
	}

}
