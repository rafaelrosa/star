package br.com.code.avenue.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.code.avenue.model.MovieSettings;
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
		long id = 0;
		List<MovieSettings> movieSettingsList = new ArrayList();
		StringBuilder sb = new StringBuilder("[");
		
		log.info("Starting text processing...");
		for(String data : settingsList) {
			// sb.append( new MovieSettings(++id, processSettingLine(data)).toJSON() ).append(", ");
			MovieSettings settingItem = new MovieSettings(++id, processSettingLine(data));
			int indexOfItem = -1;
			if(!movieSettingsList.contains(settingItem)) {
				movieSettingsList.add(settingItem);
				indexOfItem = movieSettingsList.indexOf(settingItem);
			} else {
				--id;
			}
			
			if(indexOfItem != -1) {
				sb.append( settingItem.toJSON() ).append(", ");
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
