/*
 *  Copyright 2012 Giovanni Cuccu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.sg2net.utilities.ListaCAP;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Comune {
	private String codiceIstat;
	private String codiceCatastale;
	private String nome;
	private String provincia;
	private Collection<String> cap= new HashSet<>();
	
	public Comune(String codiceIstat, String codiceCatastale, String nome, String provincia) {
		super();
		this.codiceIstat = codiceIstat;
		this.codiceCatastale = codiceCatastale;
		this.nome = nome;
		this.provincia = provincia;
	}
	
	public String getCodiceIstat() {
		return codiceIstat;
	}

	public String getCodiceCatastale() {
		return codiceCatastale;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	@JsonIgnore
	public Collection<Cap> getCapAssociati() {
		Collection<Cap> listaCap= new HashSet<Cap>();
		for (String codiceCap : cap) {
			Cap cap=new Cap(codiceCap, nome, provincia);
			listaCap.add(cap);
		}
		return listaCap;
	}
	

	public Collection<String> getCodiciCap() {
		return Collections.unmodifiableCollection(cap);
	}
	public void setCodiciCap(Collection<String> capList) {
		cap.clear();
		cap.addAll(capList);
	}

	@Override
	public String toString() {
		return "Comune [codiceIstat=" + codiceIstat + ", codiceCatastale=" + codiceCatastale + ", nome=" + nome
				+ ", provincia=" + provincia + ", cap=" + cap + "]";
	}
	
}
