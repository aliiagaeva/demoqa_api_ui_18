package tests;

import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import steps.BookAPISteps;

import static tests.TestData.*;

public class BookCollectionTests extends TestBase {
    @Test
    @WithLogin
    @DisplayName("Delete book")
    public void deleteBookInProfile() {
        BookAPISteps bookSteps = new BookAPISteps();
        ProfilePage profilePage = new ProfilePage();
        bookSteps.deleteBooks()
                .addBook(testIsbn);

        profilePage.openProfilePage()
                .verifyUserName(userName)
                .verifyBookInTable(bookName)
                .deleteBook()
                .verifyBookNotInTable(testIsbn);
    }
}


