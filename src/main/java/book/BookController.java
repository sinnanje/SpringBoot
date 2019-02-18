package book;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.BookVO;

@RestController
@RequestMapping(value="/books")
public class BookController {
	
	private HashMap<String,String> users = new HashMap<String,String>();
	
	private BookStorageWithoutDBService bookStorage;
		
	
	@Autowired
	public BookController(BookStorageWithoutDBService bookStorageWithoutDB) {
		this.bookStorage = bookStorageWithoutDB;
		users.put("client1","1234");
		users.put("client2","12345");
	}
	
	/*
	 * Controller to get all the books in the book storage.
	 */
	@GetMapping
	public ResponseEntity<List> getAll(@RequestHeader(value="Authorization") String authDetail){
		
		try {
			boolean authorized = checkAuthorization(authDetail);
			if(authorized)  {
				List<BookVO> lstBooks = bookStorage.getAllBooks();
				ResponseEntity<List> response = new ResponseEntity<List>(lstBooks,HttpStatus.OK);
				return response;
			} else {
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);		
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);			
	}
	
	/*
	 * This is used to get a book by a particular ISBN.
	 * We can do the same for Name,Author. Right now Hashmap is built around ISBN 
	 * for easier search
	 */
	@RequestMapping(value = "/getBookByISBN/{ISBN}",method = RequestMethod.GET)
	public ResponseEntity<BookVO> getBookByISBN(@PathVariable("ISBN") Integer ISBN,@RequestHeader(value="Authorization") String authDetail) {

		try {
			boolean authorized = checkAuthorization(authDetail);
			if(authorized)  {	
				BookVO bookVO1 = bookStorage.getBookByISBN(ISBN);	
				ResponseEntity<BookVO> response = new ResponseEntity<BookVO>(bookVO1,HttpStatus.OK);
				return response;
			} else {
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);		
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);	
	}
	
	/*
	 * Removing a particular book based on ISBN number
	 */
	@DeleteMapping(value="/removeBook/{ISBN}")
	public ResponseEntity<String> removeBookFromShelf(@PathVariable("ISBN") Integer ISBN,@RequestHeader(value="Authorization") String authDetail) {	
		try {
			boolean authorized = checkAuthorization(authDetail);
			if(authorized)  {
				String stringBookISBN = bookStorage.deleteBook(ISBN);
				ResponseEntity<String> response = new ResponseEntity<String>(stringBookISBN,HttpStatus.OK);
				return response;
			} else {
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);		
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);	
	}
	
	
	/*
	 * Creating a new book and saving it on the shelf
	 */
	//@RequestMapping(value = "/createBook", method=RequestMethod.POST)
		//public String saveBookToShelf(@ModelAttribute(value="BookVO") BookVO bookVO) {
	@PostMapping(value="/createBook",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookVO> saveBookToShelf(@RequestBody String jsonString,@RequestHeader(value="Authorization") String authDetail) {
		JSONtoBookConverter jsonConverter = new JSONtoBookConverter();
		BookVO bookVO = jsonConverter.convert(jsonString);
		try {
			boolean authorized = checkAuthorization(authDetail);
			if(authorized)  {
				BookVO bookVO1 = bookStorage.saveBook(bookVO);	
				ResponseEntity<BookVO> response = new ResponseEntity<BookVO>(bookVO1,HttpStatus.OK);
				return response;
			} else {
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);		
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);		
	}
	
	/*
	 * Updating an existing book details on shelf
	 */
	@PutMapping(value="/updateBook",consumes=MediaType.APPLICATION_JSON_VALUE)
	public String updateBookOnShelf(@RequestBody BookVO bookVO,@RequestHeader(value="Authorization") String authDetail) {
		return bookStorage.updateBook(bookVO);
	}
	
	private boolean checkAuthorization(String authDetail) throws UnsupportedEncodingException {
		String[] authDetailArray = authDetail.split(" ");
		byte[] decodedCreds = Base64.getDecoder().decode(authDetailArray[1]);
		String authorizationString = new String(decodedCreds, "UTF-8");
		if (authorizationString.indexOf(":") > 0) {
			String[] creds = authorizationString.split(":");
			String username = creds[0];
			String password = creds[1];		
			if (users.containsKey(username) && users.get(username).equals(password))
				return true;
		}
		return false;

	}
	

}
