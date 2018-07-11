package br.com.code.avenue.service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ProcessorService {
	String processTextList(List<String> textList);
	String processLine(String line);
	Map textListToMap(List<String> textList);
}
