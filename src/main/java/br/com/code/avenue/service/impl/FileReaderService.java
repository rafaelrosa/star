package br.com.code.avenue.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.code.avenue.service.ProcessorService;
import br.com.code.avenue.service.ReaderService;

@Service
public class FileReaderService implements ReaderService { 
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProcessorService processorService;
	
	private static final String FILENAME = "upload-dir\\screenplay.txt";
	
	public void readFromSource( /*InputStream inputStream */ ) throws Exception {
		FileReader fileReader = new FileReader(FILENAME);
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(fileReader);
			String line;
			
			while ((line = br.readLine()) != null) {
				if(line.startsWith("INT") || line.startsWith("EXT")) {
					line = processorService.processSettingLine(line);
				} 
			}
		} catch(Exception e) {
			log.error("error processing file", e);
			throw e;
		} finally {
			try {
				if (br != null) br.close();

				if (fileReader != null) fileReader.close();
			} catch(IOException e) {
				log.error("error closing resources", e);
			}
		}
	}	
}
