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

package com.sg2net.utilities.ListaCAP.utilities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sg2net.utilities.ListaCAP.ListaComuniCapFromInternet;
import com.sg2net.utilities.ListaCAP.json.JsonUtilties;


public class DownloadAndWriteToJson {
	
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		ListaComuniCapFromInternet lczs= new ListaComuniCapFromInternet();
		JsonUtilties.serializeTo(lczs.donwloadAndParse(), new File("data/comuni.json"));
	}
}
