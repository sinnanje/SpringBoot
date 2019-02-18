package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import model.BookVO;

/*This class is created to service the book system. 
 * This has business logic for add, update, delete and creating a new
 * book on shelf. 
 * This currently creates initial books as we don't have a db.
 */
@Repository
public class BookStorageWithoutDBServiceImpl implements BookStorageWithoutDBService{
	
	HashMap<Integer,BookVO> bookMap = new HashMap<Integer,BookVO>();

	
	/*
	 * Saving a new book into the shelf. This will add a new book to HashMap 
	 * @see book.BookStorageWithoutDBService#saveBook(model.BookVO)
	 */
	public BookVO saveBook(BookVO bookVO) {
		if(bookMap.containsKey(bookVO.getBookISBN())) return null;
		else {
			bookMap.put(bookVO.getBookISBN(),bookVO);
			return bookVO;
		}
	}
	
	/*
	 * Deleting an existing book on shelf. Removes an existing book on Hashmap.
	 * @see book.BookStorageWithoutDBService#deleteBook(java.lang.Integer)
	 */
	public String deleteBook(Integer ISBN){
		if(!bookMap.containsKey(ISBN)) return "Book is not on our shelf";
		bookMap.remove(ISBN);
		return ISBN.toString();
	}
	
	/*
	 * Updating an existing book on HashMap. The details of BookVO will be updated
	 * @see book.BookStorageWithoutDBService#updateBook(model.BookVO)
	 */
	public String updateBook(BookVO bookVO) {
		if(!bookMap.containsKey(bookVO.getBookISBN())) return "Book is not on our shelf";
		else {
			bookMap.replace(bookVO.getBookISBN(), bookVO);
			return bookVO.getBookISBN() + " has been updated on our shelf";
		}
	}
	
	/*
	 * Getting a book based on ISBN key from map
	 * @see book.BookStorageWithoutDBService#getBookByISBN(java.lang.Integer)
	 */
	public BookVO getBookByISBN(Integer ISBN) {
		
		return bookMap.get(ISBN);
		
	}
	
	/*
	 * Getting all books of the map
	 * @see book.BookStorageWithoutDBService#getAllBooks()
	 */
	public List<BookVO> getAllBooks() {
		ArrayList<BookVO> arList = new ArrayList<BookVO>();

		for(Entry<Integer, BookVO> map : bookMap.entrySet()){

		     arList.add(map.getValue());

		}
		return arList;
	}
	
	
}
