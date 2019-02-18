package model;

/*
 * The model object used for Book details. The data in this is used for 
 * adding, updating, deleting books.
 */
public class BookVO {
	
	private Integer bookISBN;
	private String bookTitle;
	private String bookAuthor;
	
	public BookVO() {
		
	}
	
	public BookVO(Integer bookISBN,String bookTitle,String bookAuthor) {
		this.bookISBN = bookISBN;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public Integer getBookISBN() {
		return bookISBN;
	}
	public void setBookISBN(Integer bookISBN) {
		this.bookISBN = bookISBN;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
}
