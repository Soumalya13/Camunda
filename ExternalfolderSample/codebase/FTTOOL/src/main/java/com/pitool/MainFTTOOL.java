package com.pitool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class MainFTTOOL implements CommandLineRunner {
	
	@Value("${config.url}")
	private String values;
	
		   
	 
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(MainFTTOOL.class);
	    app.run(args);
	}


	@Override
	public void run(String... args) throws Exception {
	 
		//System.out.println(values);
		// upload
		
		getTestFile().forEach(file -> uploadExternalBPMFiles(file));
		
		
		getCountOfProcess();
		
	}


	private void uploadExternalBPMFiles(FileSystemResource file){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body
		  = new LinkedMultiValueMap<>();
		body.add("file", file);
		HttpEntity<MultiValueMap<String, Object>> requestEntity
		 = new HttpEntity<>(body, headers);

		String serverUrl = "http://localhost:8080/engine-rest/deployment/create";

		RestTemplate restTemplate1 = new RestTemplate();
		ResponseEntity<String> response1 = restTemplate1
		  .postForEntity(serverUrl, requestEntity, String.class);
		//System.out.println(response1.getBody());
	}


	private void getCountOfProcess() {
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
		  = "http://localhost:8080/engine-rest/process-definition/count";
		ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl, String.class);
		//System.out.println(response.getBody());
	}
	
	
	private List<FileSystemResource> getTestFile() throws IOException {
		
		FileSystemResource resource = new FileSystemResource(values);
		List<FileSystemResource> files = new ArrayList<FileSystemResource>();
		Properties properties = new Properties();
		properties.load(resource.getInputStream());
		String bpmFiles = properties.getProperty("BPM");
		if (StringUtils.hasText(bpmFiles)) {
			String[] bpmns = bpmFiles.split(",");
			for (String bpmn : bpmns) {
				files.add(new FileSystemResource(bpmn));
			}
		}
		String dmnfiles = properties.getProperty("DMN");
		if (StringUtils.hasText(dmnfiles)) {
			String[] dmns = dmnfiles.split(",");
			for (String dmn : dmns) {
				files.add(new FileSystemResource(dmn));
			}
		}
		return files;
	}
	


}
