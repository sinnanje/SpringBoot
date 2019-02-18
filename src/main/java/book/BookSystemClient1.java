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

/*Authentication is defined by giving a username and password for each client request. 
 * The username and password are stored in a map for this purpose. This should be in DB later*/

@Component
public class BookSystemClient1 {
	
	private static int isbnBookNumber1 = 15999;
	private String username = "client1";
	private String password = "1234";
		
	@Scheduled(fixedDelay=15000) //This defines the scheduled polling of server rest apis
    public void getBooks() {
		
		System.out.println("-----Getting Books request from Client 1--------");
		String url = "http://localhost:8080/books";
		RestTemplate restTemplate = new RestTemplate();
		try {
	        HttpHeaders headers = createHttpHeader(username,password); //the username and password is considered as an id to distinguish clients
	        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        System.out.println("All books on shelf ---- "+ new JSONArray(response.getBody()) + ". Response code: " + response.getStatusCode() + "------");
	        System.out.println("End client 1 call");
	    }
	    catch (Exception e) {
	        System.out.println("Exception: "+ e.getMessage());
	    }
		
	}
	
	@Scheduled(fixedDelay=10000)
    public void createBook() {
		
		System.out.println("----Adding a new book to shelf from Client 1-------");
		String url = "http://localhost:8080/books/createBook";
		RestTemplate restTemplate = new RestTemplate();
	
		try {
	        HttpHeaders headers = createHttpHeader(username,password); //the username and password is considered as an id to distinguish clients
	        headers.setContentType(MediaType.APPLICATION_JSON);	        
	        JSONObject json = new JSONObject();
	        json.put("bookISBN", isbnBookNumber1);
	        json.put("bookAuthor", "John Grisham");
	        json.put("bookTitle", "Firm");
	        isbnBookNumber1++;
	        String jsonString = json.toString();
	        HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	        System.out.println("New book added to shelf ---- "+ response.getBody() + ". Response code: " + response.getStatusCode() + "------");
	        System.out.println("End client 1 call");
	    }
	    catch (Exception e) {
	        System.out.println("Exception: "+ e.getMessage());
	    }
		
	}
	
	@Scheduled(fixedDelay=25000)
    public void getBookByISBN() {
		
		System.out.println("----Getting a book from shelf for a partoicular ISBN from Client 1-------");
		RestTemplate restTemplate = new RestTemplate();
		try {
	        HttpHeaders headers = createHttpHeader(username,password); //the username and password is considered as an id to distinguish clients
	        String uri = "http://localhost:8080/books/getBookByISBN/{ISBN}";
	        Map<String, Integer> uriParam = new HashMap<String,Integer>();
	        uriParam.put("ISBN", 16001);
	        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).build();
	     	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class,uriParam);
	        System.out.println("Get book from shelf for " + isbnBookNumber1 + " ---- "+ response.getBody() + ". Response code: " + response.getStatusCode() + "------");
	        System.out.println("End client 1 call");
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
