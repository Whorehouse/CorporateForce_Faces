package org.corporateforce.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.corporateforce.client.config.Config;
import org.corporateforce.server.model.Resources;
import org.corporateforce.server.model.Users;
import org.primefaces.model.UploadedFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ClientFileUploader {
    public static File prepareTemporaryFile (UploadedFile file) throws IOException {
    	File targetFolder = new File(System.getProperty("java.io.tmpdir"));
    	InputStream inputStream = file.getInputstream();
        OutputStream out = new FileOutputStream(new File(targetFolder, file.getFileName()));
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        inputStream.close();
        out.flush();
        out.close();
        return new File(targetFolder, file.getFileName());
    }
    
    public static Resources postUpload(String relativeUri, UploadedFile upFile, MultiValueMap<String, Object> formData) throws IOException {
    	Resource file = new FileSystemResource(prepareTemporaryFile(upFile));
    	formData.add("file", file);
    	HttpHeaders requestHeaders = new HttpHeaders();
    	requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    	HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);        	
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.postForObject(Config.getUriServer()+relativeUri, requestEntity, Resources.class);
    }
        
    public static Resources uploadFile(UploadedFile file, Users users) throws IOException {
    	MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
    	formData.add("users", users);
    	return postUpload("Resources/uploadFile", file, formData);    	
    }
    
    public static Resources uploadFile(UploadedFile file, String type, Users users) throws IOException {
    	MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
    	formData.add("users", users);
    	return postUpload("Resources/uploadFile", file, formData);    	
    }
    
    public static Resources uploadAvatar(UploadedFile file, Users users) throws IOException {
    	MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
    	formData.add("users", users);
    	return postUpload("Avatars/uploadAvatar", file, formData);
    }
}
