
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import java.util.List;

public class BookServiceTest {

    private BookService bookService;
    private User mockUser;
    private Book mockBook;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Executed once, before the start of all tests
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Executed once, after all tests are finished
    }

    @Before
    public void setUp() throws Exception {
        // Executed before each test
        bookService = new BookService();
        mockUser = Mockito.mock(User.class);
        mockBook = Mockito.mock(Book.class);
    }

    @After
    public void tearDown() throws Exception {
        // Executed after each test
    }

    @Test
    public void testSearchBook_Positive() {
        Book book = new Book("Title", "Author", "Genre");
        bookService.addBook(book);
        List<Book> foundBooks = bookService.searchBook("Title");
        assertEquals(1, foundBooks.size());
        assertEquals(book, foundBooks.get(0));
    }

    @Test
    public void testSearchBook_Negative() {
        List<Book> foundBooks = bookService.searchBook("NonExistingTitle");
        assertEquals(0, foundBooks.size());
    }

    @Test
    public void testSearchBook_EdgeCase() {
        List<Book> foundBooks = bookService.searchBook("");
        assertEquals(0, foundBooks.size());

        foundBooks = bookService.searchBook(null);
        assertEquals(0, foundBooks.size());
    }

    @Test
    public void testPurchaseBook_Positive() {
        bookService.addBook(mockBook);
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(mockBook));
        assertTrue(bookService.purchaseBook(mockUser, mockBook));
    }

    @Test
    public void testPurchaseBook_Negative() {
        assertFalse(bookService.purchaseBook(mockUser, mockBook)); // Book not in database
    }

    @Test
    public void testPurchaseBook_EdgeCase() {
        assertFalse(bookService.purchaseBook(null, mockBook));  // Null user
        assertFalse(bookService.purchaseBook(mockUser, null));  // Null book
    }

    @Test
    public void testAddBookReview_Positive() {
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(mockBook));
        assertTrue(bookService.addBookReview(mockUser, mockBook, "Great book!"));
    }

    @Test
    public void testAddBookReview_Negative() {
        when(mockUser.getPurchasedBooks()).thenReturn(List.of());
        assertFalse(bookService.addBookReview(mockUser, mockBook, "Great book!")); // User has not purchased this book
    }

    @Test
    public void testAddBookReview_EdgeCase() {
        assertFalse(bookService.addBookReview(mockUser, mockBook, ""));  // Empty review
        assertFalse(bookService.addBookReview(mockUser, mockBook, null)); // Null review
    }

    @Test
    public void testAddBook_Positive() {
        assertTrue(bookService.addBook(mockBook));
    }

    @Test
    public void testAddBook_Negative() {
        bookService.addBook(mockBook);
        assertFalse(bookService.addBook(mockBook)); // Book already in the database
    }

    @Test
    public void testAddBook_EdgeCase() {
        assertFalse(bookService.addBook(null)); // Null book
    }

    @Test
    public void testRemoveBook_Positive() {
        bookService.addBook(mockBook);
        assertTrue(bookService.removeBook(mockBook)); // Successfully removed
    }

    @Test
    public void testRemoveBook_Negative() {
        assertFalse(bookService.removeBook(mockBook)); // Book not in the database
    }

    @Test
    public void testRemoveBook_EdgeCase() {
        assertFalse(bookService.removeBook(null)); // Null book
    }
}
