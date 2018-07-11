package br.com.code.avenue.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.code.avenue.model.MovieSettings;
import br.com.code.avenue.service.ProcessorService;

@Service
public class TextProcessorService implements ProcessorService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public String processTextList(List<String> textList) {
		if(textList == null || textList.isEmpty()) {
			log.info("Nothing to process!");
			return null;
		}
		long id = 0;
		StringBuilder sb = new StringBuilder("[");
		
		log.info("Starting text processing...");
		for(String data : textList) {
			sb.append( new MovieSettings(++id, processLine(data)).toJSON() ).append(", ");
		}
		int lastComma = sb.lastIndexOf(",");
		sb.deleteCharAt(lastComma).append("]");
		
		log.info("Finishing text processing...");		
		return sb.toString();
	}
	
	public String processLine(String line) {
		if(line.indexOf("-") != -1) {
			return line.substring( line.indexOf(" "), line.indexOf("-") ).trim();
		}
		
		return line.substring( line.indexOf(" ")+1 );
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map textListToMap(List<String> textList) {
		if(textList == null || textList.isEmpty()) {
			log.info("Nothing to process!");
			return null;
		}
		
		Map<String, Long> settingsMap = new HashMap();
		log.info("Starting text processing...");
		for(String data : textList) {
			String key = processLine(data);
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
