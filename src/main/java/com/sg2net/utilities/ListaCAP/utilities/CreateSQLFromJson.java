package com.sg2net.utilities.ListaCAP.utilities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sg2net.utilities.ListaCAP.SQLScriptGenerator;
import com.sg2net.utilities.ListaCAP.json.JsonUtilties;

import freemarker.template.TemplateException;

public class CreateSQLFromJson {

	/**
	 * @param args
	 * @throws TemplateException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, TemplateException {
		SQLScriptGenerator scriptGen= new SQLScriptGenerator();
		scriptGen.generateScript(JsonUtilties.deserializeFrom(new File("data/comuni.json")),
				new File("data/comuni_cap.sql"), new File("data/cap.sql"));
	}

}
