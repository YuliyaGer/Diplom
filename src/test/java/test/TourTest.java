package test;

import Data.Card;
import Data.DataHelper;
import Data.SqlHelper;
import Page.BuyPage;
import Page.CreditPage;
import Page.StartPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TourTest {
    Card cardApproved = new Card();
    Card cardDecline = new Card();

    @AfterEach
    void cleanTables() throws SQLException {
        SqlHelper.cleanTables();
    }

    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }
    @DisplayName("Оплата не в кредит успешная и таблица заполняется approved")
    @Test
    void shouldValidBuy() throws SQLException {
        setCardApproved();
        openBuyPageAndFill(cardApproved).checkOperationApproved();
        assertEquals(SqlHelper.selectBuyStatus(), "APPROVED");
    }
    @DisplayName("Оплата в кредит успешная и таблица заполняется approved")
    @Test
    void shouldValidCredit() throws SQLException {
        setCardApproved();
        openCreditPageAndFill(cardApproved).checkOperationApproved();
        assertEquals(SqlHelper.selectCreditStatus(), "APPROVED");
    }

    @DisplayName("1.BuG Оплата в кредит не успешная дожно появляться окно с ошибкой и таблица заполняться Decline")
    @Test
    void shouldNoValidCredit() throws SQLException {
        setCardDecline();
        openCreditPageAndFill(cardDecline).checkOperationDeclined();
        assertEquals(SqlHelper.selectCreditStatus(), "DECLINE");
    }
    @DisplayName("2 BUG Оплата не в кредит, не успешная операция дожно появляться окно с ошибкой " +
            "и таблица заполняться Decline")
    @Test
    void shouldNoValidBuy() throws SQLException {
        setCardDecline();
        openBuyPageAndFill(cardApproved).checkOperationDeclined();
        assertEquals(SqlHelper.selectBuyStatus(), "DECLINE");
    }




//Допы

    public StartPage openStartPage() {
        open("http://localhost:8080/");
        val startPage = new StartPage();
        return startPage;
    }

    public BuyPage openBuyPageAndFill(Card card) {
        val buyPage = openStartPage().buyPage();
        buyPage.validData(card);
        return buyPage;
    }

    public CreditPage openCreditPageAndFill(Card card) {
        val creditPage = openStartPage().creditPage();
        creditPage.validData(card);
        return creditPage;
    }
    void setCardApproved() {
        cardApproved.setNumber(DataHelper.approvedCardNumber());
        cardApproved.setMonth(DataHelper.validMonth());
        cardApproved.setYear(DataHelper.validYear());
        cardApproved.setOwner(DataHelper.name());
        cardApproved.setCvcCvv(DataHelper.validCvcCvv());
    }
    private void setCardDecline(){
        cardDecline.setNumber(DataHelper.declinedCardNumber());
        cardDecline.setMonth(DataHelper.validMonth());
        cardDecline.setYear(DataHelper.validYear());
        cardDecline.setOwner(DataHelper.name());
        cardDecline.setCvcCvv(DataHelper.validCvcCvv());
    }
}
