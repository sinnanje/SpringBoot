package book;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.BookVO;

@RestController
@RequestMapping(value="/books")
public class BookController {
	
	
	private BookStorageWithoutDBService bookStorage;
	
	@Autowired
	public BookController(BookStorageWithoutDBService bookStorageWithoutDB) {
		this.bookStorage = bookStorageWithoutDB;
	}
	
	/*
	 * Controller to get all the books in the book storage.
	 */
	@GetMapping
	public List<BookVO> getAll(){
		
		List<BookVO> bookVO= bookStorage.getAllBooks();
		return bookVO;
		
	}
	
	/*
	 * One time insertion of books as we don't have a db.
	 */
	@GetMapping(value="/insertBooks")
	public void insertFirstTime() {
		
		bookStorage.insertBooks();
		
	}
	
	/*
	 * This is used to get a book by a particular ISBN.
	 * We can do the same for Name,Author. Right now Hashmap is built around ISBN 
	 * for easier search
	 */
	@RequestMapping(value = "/getBookByISBN",method = RequestMethod.GET)
	public BookVO getBookByISBN(@RequestParam("ISBN") Integer ISBN) {
		return bookStorage.getBookByISBN(ISBN); 
	}
	
	/*
	 * Removing a particular book based on ISBN number
	 */
	@DeleteMapping(value="/removeBook/{ISBN}")
	public String removeBookFromShelf(@PathVariable("ISBN") Integer ISBN) {
		return bookStorage.deleteBook(ISBN);
	}
	
	/*
	 * Creating a new book and saving it on the shelf
	 */
	//@RequestMapping(value = "/createBook", method=RequestMethod.POST)
		//public String saveBookToShelf(@ModelAttribute(value="BookVO") BookVO bookVO) {
	@PostMapping(value="/createBook",consumes=MediaType.APPLICATION_JSON_VALUE)
	public String saveBookToShelf(@RequestBody BookVO bookVO) {
		return bookStorage.saveBook(bookVO);
	}
	
	/*
	 * Updating an existing book details on shelf
	 */
	@PutMapping(value="/updateBook",consumes=MediaType.APPLICATION_JSON_VALUE)
	public String updateBookOnShelf(@RequestBody BookVO bookVO) {
		return bookStorage.updateBook(bookVO);
	}
}
