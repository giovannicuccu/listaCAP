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
