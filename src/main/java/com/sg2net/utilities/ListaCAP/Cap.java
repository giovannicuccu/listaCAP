package com.sg2net.utilities.ListaCAP;

public class Cap {
	private String cap;
	private String comune;
	private String provincia;
	
	
	public Cap(String cap, String comune, String provincia) {
		super();
		this.cap = cap;
		this.comune = comune;
		this.provincia = provincia;
	}
	
	public String getCap() {
		return cap;
	}
	public String getComune() {
		return comune;
	}
	public String getProvincia() {
		return provincia;
	}
	
}
