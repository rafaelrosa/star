package br.com.code.avenue.service;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ReaderService { 
	void readFromSource() throws Exception;
	Map<String, Map> getSettingsMap();
	Map<String, Map> getCharactersMap();
	Map<String, Long> getWordsMap();
}
