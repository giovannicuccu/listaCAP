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
