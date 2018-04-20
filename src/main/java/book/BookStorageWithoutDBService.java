package book;

import java.util.List;

import model.BookVO;

/*
 * Interface for service layer. This has the methods which
 * needs to be implemented to provide business services for book system.
 */
public interface BookStorageWithoutDBService {
	void insertBooks();
	
	String saveBook(BookVO bookVO);
	
	String deleteBook(Integer ISBN);
	
	String updateBook(BookVO bookVO);
	
	BookVO getBookByISBN(Integer ISBN);
	
	List<BookVO> getAllBooks();
}
