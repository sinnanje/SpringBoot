package book;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BookSystemClient2 {
	
	private static int isbnBookNumber2 = 10001;
	private int isbnToDelete;
	private String username = "client2";
	private String password = "12345";
	
	@Scheduled(fixedDelay=35000)
    public void getBooks() {
		
		System.out.println("-----Getting Books request from Client 2--------");
		String url = "http://localhost:8080/books";
		RestTemplate restTemplate = new RestTemplate();
		try {
	        HttpHeaders headers = createHttpHeader(username,password); //the username and password is considered as an id to distinguish clients
	        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        System.out.println("All books on shelf ---- "+ new JSONArray(response.getBody()) + ". Response code: " + response.getStatusCode() + "------");
	        System.out.println("End client 2 call");
	    }
	    catch (Exception e) {
	        System.out.println("Exception: "+ e.getMessage());
	    }
		
	}
	
	@Scheduled(fixedDelay=40000)
    public void createBook() {
		
		System.out.println("----Adding a new book to shelf from Client 2-------");
		String url = "http://localhost:8080/books/createBook";
		RestTemplate restTemplate = new RestTemplate();
		try {
	        HttpHeaders headers = createHttpHeader(username,password); //the username and password is considered as an id to distinguish clients
	        
	        JSONObject json = new JSONObject();
	        json.put("bookISBN", isbnBookNumber2);
	        json.put("bookAuthor", "James Patterson");
	        json.put("bookTitle", "Along came the spider");
	        isbnBookNumber2++;
	        String jsonString = json.toString();       
	        isbnToDelete = isbnBookNumber2;
	        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	        System.out.println("New book added to shelf ---- "+ response.getBody() + ". Response code: " + response.getStatusCode() + "------");
	        System.out.println("End client 2 call");
	    }
	    catch (Exception e) {
	        System.out.println("Exception: "+ e.getMessage());
	    }
		
	}
	
	@Scheduled(fixedDelay=250000)
    public void deleteBookByISBN() {
		System.out.println("----Deleting a book from shelf for a particular ISBN from Client 2-------");
		RestTemplate restTemplate = new RestTemplate();
		try {
	        HttpHeaders headers = createHttpHeader(username,password); //the username and password is considered as an id to distinguish clients
	        String uri = "http://localhost:8080/books/getBookByISBN/{ISBN}";
	        Map<String, Integer> uriParam = new HashMap<String,Integer>();
	        uriParam.put("ISBN",isbnToDelete);
	        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
	                    .build();   
	     	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity, String.class,uriParam);
	        System.out.println("Removed book from shelf for " + response.getBody() + ". Response code: " + response.getStatusCode() + "------");
	        System.out.println("End client 2 call");
	    }
	    catch (Exception e) {
	        System.out.println("Exception: "+ e.getMessage());
	    }
		
	}
	
	
	private HttpHeaders createHttpHeader(String user, String password)
	{
	    String notEncoded = user + ":" + password;
	    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Basic " + encodedAuth);
	    return headers;
	}
}
