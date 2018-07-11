package br.com.code.avenue.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.code.avenue.config.StorageProperties;
import br.com.code.avenue.exception.StorageException;
import br.com.code.avenue.exception.StorageFileNotFoundException;
import br.com.code.avenue.service.StorageService;

@Service
public class FileSystemStorageService implements StorageService {
		
	private Logger log = LoggerFactory.getLogger(getClass());

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	
	public void store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			log.info("Getting input stream of uploaded file...");
			try (InputStream inputStream = file.getInputStream()) {
				log.info("Got input stream of uploaded file.");
				log.info("Starting file transfer to server..");
				Files.copy(inputStream, this.rootLocation.resolve(filename),
						StandardCopyOption.REPLACE_EXISTING);
				log.info("Finished file transfer to server!");
			} catch(Exception e) {
				log.error("Error storing file", e);
				throw e;
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		} 
	}

	
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

}
