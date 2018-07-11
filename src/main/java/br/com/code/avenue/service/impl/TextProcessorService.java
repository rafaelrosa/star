package br.com.code.avenue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.code.avenue.model.MovieSetting;
import br.com.code.avenue.service.ProcessorService;

@Service("processorService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TextProcessorService implements ProcessorService { 
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public String processSettingsList(List<String> settingsList) {
		if(settingsList == null || settingsList.isEmpty()) {
			log.info("Nothing to process!");
			return null;
		}
		long id = 1;
		List<MovieSetting> movieSettingsList = new ArrayList();
		StringBuilder sb = new StringBuilder("[");
		
		log.info("Starting text processing...");
		for(String data : settingsList) {
			MovieSetting settingItem = new MovieSetting(id, processSettingLine(data));
			
			if(!movieSettingsList.contains(settingItem)) {
				movieSettingsList.add(settingItem);
				sb.append( settingItem.toJSON() ).append(", ");
				id++;
			} 
		}
		int lastComma = sb.lastIndexOf(",");
		sb.deleteCharAt(lastComma).append("]");
		
		log.info("Finishing text processing...");		
		return sb.toString();
	}
	
	public String processSettingLine(String line) {
		if(line.indexOf("-") != -1) {
			return line.substring( line.indexOf(" "), line.indexOf("-") ).trim();
		}
		
		return line.substring( line.indexOf(" ")+1 );
	}
	
	public Map textListToMap(List<String> textList) {
		if(textList == null || textList.isEmpty()) {
			log.info("Nothing to process!");
			return null;
		}
		
		Map<String, Long> settingsMap = new HashMap();
		log.info("Starting text processing...");
		for(String data : textList) {
			String key = processSettingLine(data);
			if(settingsMap.get(key) == null) {
				settingsMap.put(key, 1L);
			} else {
				Long qtd = settingsMap.get(key);
				qtd++;
				settingsMap.replace(key, qtd);
			}
		}
		
		log.info("Finishing text processing...");		
		return settingsMap;
	}
}
