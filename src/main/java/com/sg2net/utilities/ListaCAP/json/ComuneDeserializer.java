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

package com.sg2net.utilities.ListaCAP.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sg2net.utilities.ListaCAP.Comune;

public class ComuneDeserializer extends StdDeserializer<Comune> {
	
	private static Logger logger = LoggerFactory.getLogger(ComuneDeserializer.class);

	public ComuneDeserializer() {
		super(Comune.class);
	}

	@Override
	public Comune deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper)jsonParser.getCodec();
		ObjectNode root = (ObjectNode) mapper.readTree(jsonParser);
		JsonNode codiceIstatNode= root.get("codiceIstat");
		String codiceIstat = codiceIstatNode.asText();
		JsonNode codiceCatastaleNode= root.get("codiceCatastale");
		String codiceCatastale = codiceCatastaleNode.asText();
		JsonNode nomeNode= root.get("nome");
		String nome = nomeNode.asText();
		JsonNode provinciaNode= root.get("provincia");
		String provincia = provinciaNode.asText();
		JsonNode codiciCapNode= root.get("codiciCap");
		Collection<String> codiciCap= new ArrayList<>();
		Iterator<JsonNode> capNodes=codiciCapNode.elements();
		while (capNodes.hasNext()) {
			JsonNode codiceCapNode=capNodes.next();
			String codiceCap=codiceCapNode.asText();
			
			codiciCap.add(codiceCap);
		}
		Comune comune= new Comune(codiceIstat, codiceCatastale, nome, provincia);
		comune.setCodiciCap(codiciCap);
		logger.trace("comune =" + comune +  " deserializzato from json");
		return comune;
	}

}
