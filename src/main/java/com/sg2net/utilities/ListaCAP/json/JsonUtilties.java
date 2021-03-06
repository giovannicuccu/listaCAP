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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sg2net.utilities.ListaCAP.Comune;

public class JsonUtilties {
	private static Logger logger = LoggerFactory.getLogger(JsonUtilties.class);
	private static ObjectMapper JSONMapper= new ObjectMapper();
	static {
	    SimpleModule module =  new SimpleModule("ComuneDeserializerModule",  
		    	new Version(1, 0, 0, "Comune","comune","comune"));  
		    module.addDeserializer(Comune.class, new ComuneDeserializer()); 	
		    JSONMapper.registerModule(module);		
	}
	
	private JsonUtilties() {
	}
	
	public static void serializeTo(Collection<Comune> comuni, File file) {
		try {
			JSONMapper.writeValue(file, comuni);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e); 
		}		
	}
	
	public static Collection<Comune> deserializeFrom(File file) {
		if (file==null) {
			throw new IllegalArgumentException("File is null");
		}
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			throw new IllegalArgumentException("File " + file.getName() + " is not file or is not readable ");
		}		
		try {
			return JSONMapper.readValue(file, new TypeReference<List<Comune>>(){});
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e); 
		}		
	}	
}
