package br.com.code.avenue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.code.avenue.constants.Messages;
import br.com.code.avenue.model.HttpMessage;
import br.com.code.avenue.service.ProcessorService;
import br.com.code.avenue.service.ReaderService;
import br.com.code.avenue.service.StorageService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class MovieScriptController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private final StorageService storageService;
	
	@Autowired
	ReaderService readerService;
	
	@Autowired
	ProcessorService processorService;
	
	@Autowired
	public MovieScriptController(StorageService storageService) {
		this.storageService = storageService;
	}

	@ResponseBody
	@RequestMapping(
		value="/script"
	,	method=RequestMethod.POST
	,	produces="application/json"
	)
	public ResponseEntity<String> script(@RequestParam("file") MultipartFile file) {
		String filename = file.getOriginalFilename();
		try { 
			storageService.store(file);
		} catch(Exception e) {
			log.error("Error uploading file "+filename, e);
			return new ResponseEntity(new HttpMessage(Messages.UNEXPECTED_ERR).toJSON(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("File "+filename+" uploaded!");
		return new ResponseEntity(new HttpMessage(Messages.UPLOAD_OK).toJSON(), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(
		value="/settings"
	,	method=RequestMethod.GET
	,	produces="application/json"
	)
	public ResponseEntity<String> settings() {
		log.info("Starting analyzing settings...");
		
		List<String> settingsList = null;
		try {
			log.info("Reading file of settings...");
			settingsList = readerService.readFromSource();
			log.info("File of settings read!");
		} catch (Exception e) {
			log.error("error reading file", e);
			return new ResponseEntity(new HttpMessage(Messages.UNEXPECTED_ERR).toJSON() , HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(settingsList == null || settingsList.isEmpty()) {
			log.info("Settings not found in script.");
			return new ResponseEntity(new HttpMessage(Messages.settingNotFound(0)).toJSON(), HttpStatus.NOT_FOUND);
		}
		
		String settingsResult = processorService.processSettingsList(settingsList);
		
		log.info("Finishing analyzing settings!");
		return new ResponseEntity(settingsResult, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(
		value="/settings/{id}"
	,	method=RequestMethod.GET 
	,	produces="application/json"
	)
	public ResponseEntity<String> settingsOfGivenId( @PathVariable("id") long id ) {
		return new ResponseEntity(new HttpMessage(Messages.settingNotFound(1)).toJSON(), HttpStatus.NOT_IMPLEMENTED);
	}

	@ResponseBody
	@RequestMapping(
		value="/characters"
	,	method=RequestMethod.GET 
	,	produces="application/json"
	)
	public ResponseEntity<String> characters() {
		return new ResponseEntity(new HttpMessage(Messages.NOT_IMPLEMENTED), HttpStatus.NOT_IMPLEMENTED);
	}

	@ResponseBody
	@RequestMapping(
		value="/characters/{id}"
	,	method=RequestMethod.GET 
	,	produces="application/json"
	)
	public ResponseEntity<String> charactersOfGivenId( @PathVariable("id") long id ) {
		return new ResponseEntity(new HttpMessage(Messages.NOT_IMPLEMENTED), HttpStatus.NOT_IMPLEMENTED);
	}
}
