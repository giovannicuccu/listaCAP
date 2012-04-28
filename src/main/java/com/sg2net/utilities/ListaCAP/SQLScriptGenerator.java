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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class SQLScriptGenerator {
	private static Logger logger = LoggerFactory.getLogger(SQLScriptGenerator.class);
	private final String comuniSQLTemplateName="comuni.ftl";
	private final String capSQLTemplateName="cap.ftl";
	
	private final String SQL_CHARSET="UTF-8";
	private Configuration freeMarkerConfig = new Configuration();
	private Template comuniTemplate;
	private Template capTemplate;
	
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
	
	public void generateContent(Collection<Comune> comuni,File fileComuni, File fileCap) {

		
		Map<String,Object> templateMap=new HashMap<>();
		templateMap.put("comuni", comuni);
		templateMap.put("charset", SQL_CHARSET);
		
		try ( 
			FileOutputStream outStream=new FileOutputStream(fileComuni);
			OutputStreamWriter comuniWriter= new OutputStreamWriter(outStream, SQL_CHARSET); 
		) {
			comuniTemplate.process(templateMap, comuniWriter);
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		try ( 
			FileOutputStream outStream = new FileOutputStream(fileCap);
			OutputStreamWriter capWriter= new OutputStreamWriter(outStream, SQL_CHARSET); 
		) {
			capTemplate.process(templateMap, capWriter);
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}
	
}
