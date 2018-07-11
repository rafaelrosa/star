package br.com.code.avenue.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.code.avenue.service.ProcessorService;
import br.com.code.avenue.service.ReaderService;

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FileReaderService implements ReaderService { 
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProcessorService processorService;
	
	private static final String FILENAME = "upload-dir\\screenplay.txt";
	private static final String CHAR_INIT = "                      ";
	private static final String TALK_INIT = "          ";
	
	private Map<String, Map> settingsMap = new HashMap(); 
	
	private Map<String, Map> charactersMap = new HashMap();
	
	private Map<String, Long> wordsMap = new HashMap();
	
	public void readFromSource( /*InputStream inputStream */ ) throws Exception {
		FileReader fileReader = new FileReader(FILENAME);
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(fileReader);
			String line;
			
			String oldSetting = "";
			String oldChar = "";
			
			while ((line = br.readLine()) != null) {
				if(line.startsWith("INT") || line.startsWith("EXT")) {
					line = processorService.processLine(line);
					
					if(!wordsMap.isEmpty()) {
						Map oldCharWordsMap = charactersMap.get(oldChar);
						if(oldCharWordsMap != null) {
							oldCharWordsMap.putAll(wordsMap);
						} else {
							oldCharWordsMap = wordsMap;
						}
						
						charactersMap.replace(oldChar, oldCharWordsMap);
						oldChar = "";
					}
					
					if(!charactersMap.isEmpty()) {
						Map oldSettingCharsMap = settingsMap.get(oldSetting);
						if(oldSettingCharsMap != null) {
							oldSettingCharsMap.putAll(charactersMap);
						} else {
							oldSettingCharsMap = charactersMap;
						}
						
						settingsMap.replace(oldSetting, oldSettingCharsMap);
						oldSetting = "";
					}
					
					oldSetting = line;
					charactersMap = new HashMap();
				} else if(line.startsWith(CHAR_INIT)) {
					line = line.substring(CHAR_INIT.length());
					
					if(!wordsMap.isEmpty()) {
						Map oldCharWordsMap = charactersMap.get(oldChar);
						if(oldCharWordsMap != null) {
							oldCharWordsMap.putAll(wordsMap);
						} else {
							oldCharWordsMap = wordsMap;
						}
						
						charactersMap.replace(oldChar, oldCharWordsMap);
					}
					
					oldChar = line; 
					wordsMap = new HashMap();
				} else if(line.startsWith(TALK_INIT) && line.charAt(TALK_INIT.length()) != ' ' ) {
					line = line.substring(TALK_INIT.length()).replace(",", "").replace(".", "").replace("?", "").replace("-", "").replace("!", "").replace(";", "").toLowerCase();
					String[] wordsList = line.split(" ");
					for (String word : wordsList) {
						if(wordsMap.containsKey(word.toLowerCase())) {
							Long wordCount = wordsMap.get(word);
							wordsMap.replace(word, ++wordCount);
						} else {
							wordsMap.put(word, 1L);
						}
					}
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
//		 return resultStringBuilder.toString();
//		return settingsList;
	}
	
	@SuppressWarnings("unused")
	private boolean isSameTwoListTails(List list1, List list2) {
		int lastElem1 = list1.size()-1;
		int lastElem2 = list2.size()-1;
		
		return list1.get(lastElem1).equals( list2.get(lastElem2) );
	}

	public Map<String, Map> getSettingsMap() {
		return settingsMap;
	}

	public Map<String, Map> getCharactersMap() {
		return charactersMap;
	}

	public Map<String, Long> getWordsMap() {
		return wordsMap;
	}
	
	
}
