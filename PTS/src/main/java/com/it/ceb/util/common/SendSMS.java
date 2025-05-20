package com.it.ceb.util.common;

import java.net.URL;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;



import javax.net.ssl.*;

import java.io.*;
import java.net.URL;
import java.security.SecureRandom;









//import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;


import com.it.ceb.util.common.SmsRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


public class SendSMS {
	 
	
	public boolean sendSMSWithDateAsString(List<SMSDataProjectCosting> smsList)
	{
		boolean isSuccess = false;
		System.out.println("sendSMSWithDateAsString start");
		try
		{
			String smsUrl="http://10.128.1.126/SMSServiceJobCosting/api/SaveSMSDetails/SaveSMSDetailsProjectCosting";  
 			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			/*
			//Add the Jackson Message converter
	        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	        // Note: here we are making this converter to process any kind of response, 
	        // not only application/*json, which is the default behaviour
	        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));   
	        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
	        messageConverters.add(jsonConverter);  
	        restTemplate.setMessageConverters(messageConverters); 
	        */
		    
	  	   JSONPObject jsonObj = new JSONPObject("smsDetailsList",smsList);
	  	     //jsonObj.put("smsDetailsList", smsList);
 			
	  	     HttpEntity requestEntity = new HttpEntity(jsonObj, headers);
	 			
	  	     RestTemplate restTemplate = new RestTemplate();
	  	     ResponseEntity<smsDetailsResponse> responseEntity = restTemplate.postForEntity(smsUrl, requestEntity, smsDetailsResponse.class);
		
	  	     System.out.println("response sendSMSWithDateAsString ex "+responseEntity.getBody().getEx());
	  	     System.out.println("response sendSMSWithDateAsString status "+responseEntity.getBody().isSuccess());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("sendSMSWithDateAsString end");
		
		return isSuccess;
	}
	
	/*public boolean sendSMS(List<SMSData> smsList)
	{
		boolean isSuccess = false;
		
		try
		{
			String smsUrl="http://10.128.1.126/SMSAPI/api/SaveSMSDetails/SaveSMSDetails";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			
			//Add the Jackson Message converter
	        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	        // Note: here we are making this converter to process any kind of response, 
	        // not only application/*json, which is the default behaviour
	        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));   
	        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
	        messageConverters.add(jsonConverter);  
	        restTemplate.setMessageConverters(messageConverters); 
	        
		    
			JSONPObject jsonObj = new JSONPObject("smsDetailsList",smsList);
		  	 //  jsonObj.put("smsDetailsList", smsList);
 			
	  	     HttpEntity requestEntity = new HttpEntity(jsonObj, headers);
	 			
	  	     RestTemplate restTemplate = new RestTemplate();
	  	     ResponseEntity<smsDetailsResponse> responseEntity = restTemplate.postForEntity(smsUrl, requestEntity, smsDetailsResponse.class);
		
	  	     System.out.println("response ex 3 "+responseEntity.getBody().getEx());
	  	     System.out.println("response status 3 "+responseEntity.getBody().isSuccess());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("calling sms end method 2");
		
		return isSuccess;
	}*/
	
	
	public String sendPostRequest(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
		url="http://10.128.1.126/SMSServiceJobCosting/api/SaveSMSDetails/SaveSMSDetailsProjectCosting";  
		URL obj = new URL(url);
	    
	    // Open a connection to the URL
	    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
	    
	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");
	    
	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header
	    
	    // Enable input and output streams
	    connection.setDoOutput(true);
	    
	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }
	    
	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();
	    
	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }
	    
	    // Close the connection
	    connection.disconnect();
	    
	    // Return the response as a string
	    return response.toString();
	}
	
	public String sendPostRequestCBRS(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
		url=url;  
		URL obj = new URL(url);
	    
	    // Open a connection to the URL
	    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
	    
	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");
	    
	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header
	    
	    // Enable input and output streams
	    connection.setDoOutput(true);
	    
	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }
	    
	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();
	    
	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }
	    
	    // Close the connection
	    connection.disconnect();
	    
	    // Return the response as a string
	    return response.toString();
	}
	
	
	/*public String sendPostRequestCBRS2(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
		url="https://msmsenterpriseapi.mobitel.lk/EnterpriseSMSV3/esmsproxyURL.php";  
		URL obj = new URL(url);
		
		
		
		// Install the custom Trust Manager
		TrustManager[] trustAllCerts = new TrustManager[] { new CustomTrustManager() };
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

	    
	    // Open a connection to the URL
	   // HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
	    
	    
	 // Open a connection to the URL
	    HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
	    
	    
	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");
	    
	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header
	    
	    // Enable input and output streams
	    connection.setDoOutput(true);
	    
	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }
	    
	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();
	    
	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }
	    
	    // Close the connection
	    connection.disconnect();
	    
	    // Return the response as a string
	    return response.toString();
	}
*/

	public void sendPostRequestCBRS4(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
		url="https://msmsenterpriseapi.mobitel.lk/EnterpriseSMSV3/esmsproxyURL.php";  
		URL obj = new URL(url);
	    
	    // Open a connection to the URL
	    //HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		
		// Install the custom Trust Manager
				TrustManager[] trustAllCerts = new TrustManager[] { new CustomTrustManager() };
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, trustAllCerts, new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			    
			    // Open a connection to the URL
			   // HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			    
			    
			 // Open a connection to the URL
			    HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
			    

		
	    
	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");
	    
	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header
	    
	    // Enable input and output streams
	    connection.setDoOutput(true);
	    
	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }
	    
	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();
	    
	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }
	    
	    // Close the connection
	    connection.disconnect();
	    
	    // Return the response as a string
	    response.toString();
	}
	
	public void sendPostRequestCBRS3(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
	  //url = "https://msmsenterpriseapi.mobitel.lk/EnterpriseSMSV3/esmsproxyURL.php";
		 url = "https://richcommunication.dialog.lk/api/sms/send";
         
	    URL obj = new URL(url);

	    // Install the custom Trust Manager
	    TrustManager[] trustAllCerts = new TrustManager[]{new CustomTrustManager()};
	    SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); // Use TLSv1.2
	    sslContext.init(null, trustAllCerts, new SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

	    // Open a connection to the URL
	    HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");

	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header

	    // Enable input and output streams
	    connection.setDoOutput(true);
	    

        Date currentDate = new Date();
        
        // Define the desired format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
        // Format the date
        String formattedDate = formatter.format(currentDate);
        
        // Print the formatted date
        System.out.println("New Date Format: " + formattedDate);
	    connection.setRequestProperty("USER", "user_ceb");
        connection.setRequestProperty("DIGEST", "31d62f54646c7967674c6ffa3f0be76f");
        
        connection.setRequestProperty("CREATED", formattedDate);


        
	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }

	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();

	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }

	    // Close the connection
	    connection.disconnect();

	    // Return the response as a string
	    response.toString();
	}
	
	/*public void sendPostRequestCBRS3(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
	  url = "https://msmsenterpriseapi.mobitel.lk/EnterpriseSMSV3/esmsproxyURL.php";
		// url = "https://richcommunication.dialog.lk/api/sms/send";
         
	    URL obj = new URL(url);

	    // Install the custom Trust Manager
	    TrustManager[] trustAllCerts = new TrustManager[]{new CustomTrustManager()};
	    SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); // Use TLSv1.2
	    sslContext.init(null, trustAllCerts, new SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

	    // Open a connection to the URL
	    HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");

	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header

	    // Enable input and output streams
	    connection.setDoOutput(true);
	    

        Date currentDate = new Date();
        
        // Define the desired format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
        // Format the date
        String formattedDate = formatter.format(currentDate);
        
        // Print the formatted date
        System.out.println("New Date Format: " + formattedDate);
	    connection.setRequestProperty("USER", "pravin_test");
        connection.setRequestProperty("DIGEST", "ec5e094a01c0bd33036b53b20b03ca8f");
        
        connection.setRequestProperty("CREATED", formattedDate);


        
	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }

	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();

	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }

	    // Close the connection
	    connection.disconnect();

	    // Return the response as a string
	    response.toString();
	}
*/	
	
	/*public void sendPostRequestCBRS3(String url, String requestBody) throws Exception {
	    // Create a URL object with the target URL
	    url = "https://msmsenterpriseapi.mobitel.lk/EnterpriseSMSV3/esmsproxyURL.php";
	    URL obj = new URL(url);

	    // Install the custom Trust Manager
	    TrustManager[] trustAllCerts = new TrustManager[]{new CustomTrustManager()};
	    SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); // Use TLSv1.2
	    sslContext.init(null, trustAllCerts, new SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

	    // Open a connection to the URL
	    HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

	    // Set the HTTP request method to POST
	    connection.setRequestMethod("POST");

	    // Set additional HTTP headers if needed
	    connection.setRequestProperty("Content-Type", "application/json"); // Example header

	    // Enable input and output streams
	    connection.setDoOutput(true);

	    // Write the request body data to the output stream
	    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
	        wr.writeBytes(requestBody);
	        wr.flush();
	    }

	    // Get the HTTP response code
	    int responseCode = connection.getResponseCode();

	    // Read the response from the server
	    StringBuilder response = new StringBuilder();
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	    }

	    // Close the connection
	    connection.disconnect();

	    // Return the response as a string
	    response.toString();
	}
*/
	class CustomTrustManager implements X509TrustManager {
	    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
	    }

	    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
	    }

	    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	}

	
	
	
	
	
}