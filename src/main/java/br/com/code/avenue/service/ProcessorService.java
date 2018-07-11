package br.com.code.avenue.service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ProcessorService {
	String processSettingsList(List<String> settingsList);
	String processSettingLine(String line);
	Map textListToMap(List<String> textList);
}
