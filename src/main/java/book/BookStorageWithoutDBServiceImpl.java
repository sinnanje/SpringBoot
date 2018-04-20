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
	 * Inserting books the first time to simulate a db
	 * @see book.BookStorageWithoutDBService#insertBooks()
	 */
	public void insertBooks(){
		
		
		bookMap.put(1234567, new BookVO(1234567,"Catch-22","Joseph Heller"));
		
		//ISBN = (int) Math.random();
		bookMap.put(1234662, new BookVO(1234662,"Blandings Castle","P G Wodehouse"));
		
		//ISBN = (int) Math.random();
		bookMap.put(23456789, new BookVO(23456789,"Outliers","Malcolm Gladwell"));
	}
	
	/*
	 * Saving a new book into the shelf. This will add a new book to HashMap 
	 * @see book.BookStorageWithoutDBService#saveBook(model.BookVO)
	 */
	public String saveBook(BookVO bookVO) {
		if(bookMap.containsKey(bookVO.getBookISBN())) return "Book exists on the shelf";
		else {
			bookMap.put(bookVO.getBookISBN(),bookVO);
			return bookVO.getBookISBN() + " has been added to shelf";
		}
	}
	
	/*
	 * Deleting an existing book on shelf. Removes an existing book on Hashmap.
	 * @see book.BookStorageWithoutDBService#deleteBook(java.lang.Integer)
	 */
	public String deleteBook(Integer ISBN){
		if(!bookMap.containsKey(ISBN)) return "Book is not on our shelf";
		bookMap.remove(ISBN);
		return ISBN + " has been removed";
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
