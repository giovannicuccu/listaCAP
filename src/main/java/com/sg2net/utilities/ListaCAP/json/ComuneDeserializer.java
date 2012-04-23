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
