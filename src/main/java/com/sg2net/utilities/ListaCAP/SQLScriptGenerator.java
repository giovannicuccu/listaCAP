package com.sg2net.utilities.ListaCAP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SQLScriptGenerator {
	private static Logger logger = LoggerFactory.getLogger(SQLScriptGenerator.class);
	private final String comuniSQLTemplateName="comuni.ftl";
	private final String capSQLTemplateName="cap.ftl";
	
	private final String SQL_CHARSET="UTF-8";
	private Configuration freeMarkerConfig = new Configuration();
	private Template comuniTemplate;
	Template capTemplate;
	
	public SQLScriptGenerator() {
	
		ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(getClass(), "/");
		freeMarkerConfig.setTemplateLoader(classTemplateLoader);
		freeMarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		try {
			comuniTemplate = freeMarkerConfig.getTemplate(comuniSQLTemplateName);
			capTemplate = freeMarkerConfig.getTemplate(capSQLTemplateName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e); 
		}
		
	}
	
	public void generateScript(Collection<Comune> comuni,File fileComuni, File fileCap) throws JsonParseException, JsonMappingException, IOException, TemplateException {

		
		Map<String,Object> templateMap=new HashMap<>();
		templateMap.put("comuni", comuni);
		templateMap.put("charset", SQL_CHARSET);
		
		try ( 
			FileOutputStream outStream=new FileOutputStream(fileComuni);
			OutputStreamWriter comuniWriter= new OutputStreamWriter(outStream, SQL_CHARSET); 
		) {
			comuniTemplate.process(templateMap, comuniWriter);
		}
		
		try ( 
			FileOutputStream outStream = new FileOutputStream(fileCap);
			OutputStreamWriter capWriter= new OutputStreamWriter(outStream, SQL_CHARSET); 
		) {
			capTemplate.process(templateMap, capWriter);
		}
		
	}
	
}
