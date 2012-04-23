package com.sg2net.utilities.ListaCAP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ListaComuniCapFromInternet {
	private static Logger logger = LoggerFactory.getLogger(ListaComuniCapFromInternet.class);
	private final int BUFFER = 2048;
	private final String HTML_ENCODING="ISO-8859-1";
	private final String ZIP_URL="http://lab.comuni-italiani.it/files/listacomuni.zip";
	private final int CODICE_ISTAT_POS=0;
	private final int CODICE_CATASTALE_POS=6;
	private final int NOME_POS=1;
	private final int PROVINCIA_POS=2;
	private final int CAP_POS=5;
	private final int URL_COMUNE_POS=8;
	private final Pattern pattern = Pattern.compile("(.*)<td class=ivoce>CAP</td><td class=ival><b>(\\d+)-(\\d+)</b>(.*)",Pattern.DOTALL);
	
	
	private Collection<String> getListaCAPFromHTMLPage(String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream instream = entity.getContent();
		Reader reader=new InputStreamReader(instream,HTML_ENCODING);
		StringWriter writer= new StringWriter();
		int charsRead=0;
		char[] buffer=new char[BUFFER];
		while((charsRead=reader.read(buffer))>0) {
			writer.write(buffer, 0, charsRead);
		}
		instream.close();
		String content=writer.toString();
		Collection<String> capList= new HashSet<>();
		Matcher m = pattern.matcher(content);
		if(m.matches()) {
			Integer minCap=Integer.parseInt(m.group(2));
			Integer maxCap=Integer.parseInt(m.group(3));
			for (int i=minCap;i<=maxCap;i++) {
				capList.add(i+"");
			}
			logger.trace("trovati cap nella pagina html " + url);
		} else {
			logger.info("cap non trovati nella pagina html " + url);
			logger.info(content);
		}
		return capList;
		
		
	}
	
	public Collection<Comune> donwloadAndParse() {
		try {
			List<Comune> comuni= new ArrayList<>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(ZIP_URL);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try ( InputStream instream = entity.getContent(); ) {
				    ZipInputStream zipInputStream= new ZipInputStream(instream);
				    ZipEntry entry = zipInputStream.getNextEntry();
				    
				    if(entry != null) {
				    	ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
			            byte data[] = new byte[BUFFER];
			            int count=0;
			            while ((count = zipInputStream.read(data, 0, BUFFER)) != -1) {	
			            	outputStream.write(data, 0, count);
			            }
			            StringReader reader= new StringReader(new String(outputStream.toByteArray(),HTML_ENCODING));
			            LineNumberReader lineReader= new LineNumberReader(reader);
			            String line;
			            int lineNumber=0;
			            while ((line=lineReader.readLine())!=null) {
			            	logger.trace("line " + (lineNumber+1) + " from zip file=" + line);
			            	if (lineNumber>0) {
			            		String[] values=line.split(";");
			            		if (values.length>=9) {
			            			Comune comune= new Comune(values[CODICE_ISTAT_POS], values[CODICE_CATASTALE_POS],
			            					values[NOME_POS],values[PROVINCIA_POS]);
			            			comuni.add(comune);
			            			String capStr=values[CAP_POS];
			            			Collection<String> capList;
			            			if (capStr.endsWith("x")) {
			            				capList=getListaCAPFromHTMLPage(values[URL_COMUNE_POS]);
			            			} else {
			            				capList= new HashSet<>();
			            				capList.add(capStr);
			            			}
			            			comune.setCodiciCap(capList);
			            		}
			            	}
			            	lineNumber++;
			            }
				    }
				}
			}
			return comuni;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e); 			
		}
	}
}
