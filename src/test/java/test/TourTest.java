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

    @DisplayName("1. Оплата не в кредит успешная и таблица заполняется approved")
    @Test
    void shouldValidBuy() throws SQLException {
        setCardApproved();
        openBuyPageAndFill(cardApproved).checkOperationOk();
        assertEquals(SqlHelper.selectBuyStatus(), "APPROVED");
    }

    @DisplayName("2. Оплата в кредит успешная и таблица заполняется approved")
    @Test
    void shouldValidCredit() throws SQLException {
        setCardApproved();
        openCreditPageAndFill(cardApproved).checkOperationOk();
        assertEquals(SqlHelper.selectCreditStatus(), "APPROVED");
    }

    @DisplayName("3.BuG Оплата в кредит не успешная дожно появляться окно с ошибкой и таблица заполняться Decline")
    @Test
    void shouldNoValidCredit() throws SQLException {
        setCardDecline();
        openCreditPageAndFill(cardDecline).checkOperationError();
        assertEquals(SqlHelper.selectCreditStatus(), "DECLINE");
    }

    @DisplayName("4. BUG Оплата не в кредит, не успешная операция дожно появляться окно с ошибкой " +
            "и таблица заполняться Decline")
    @Test
    void shouldNoValidBuy() throws SQLException {
        setCardDecline();
        openBuyPageAndFill(cardDecline).checkOperationError();
        assertEquals(SqlHelper.selectBuyStatus(), "DECLINE");
    }

    @DisplayName("5. Оплата не в кредит, не полный номер карты. Должна быть ошибка в поле")
    @Test
    void shouldNoValidBuyInvalidCard() {
        setCardApproved();
        cardApproved.setNumber("4444 4444 4444");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.invalidCardFormat();
    }

    @DisplayName("6. Не заполнять поле месяц при покупке. Должна быть ошибка в поле")
    @Test
    void emptyMonth() {
        setCardApproved();
        cardApproved.setMonth("");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.invalidCardFormat();
    }

    @DisplayName("7. Оплата в кредит, не верный номер карты. Должно всплыть сообщение об ошибке")
    @Test
    void numberCardError() {
        setCardApproved();
        cardApproved.setNumber("1111 1111 1111 1111");
        CreditPage creditPage = openStartPage().creditPage();
        creditPage.validData(cardApproved);
        creditPage.checkOperationError();
    }
    @DisplayName("8. Заполнить поле месяц не полностью при покупке. Должна быть ошибка в поле")
    @Test
    void monthNoncompletely() {
        setCardApproved();
        cardApproved.setMonth("1");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.invalidCardFormat();
    }
    @DisplayName("9. Заполнить поле 'год' на 15 лет позже от текущей даты при покупке. Должна быть ошибка в поле")
    @Test
    void futureYear() {
        setCardApproved();
        cardApproved.setYear(DataHelper.setFutureYear());
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.yearTermError();
    }
    @DisplayName("10. Заполнить поле 'год' на 2 года раньше текущей даты при покупке. Должна быть ошибка в поле")
    @Test
    void earlyYear() {
        setCardApproved();
        cardApproved.setYear(DataHelper.setEarlyYear());
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.yearExpiredError();
    }
    @DisplayName("11. BUG Заполнить только имя при покупке. Должно всплыть сообщение об ошибке")
    @Test
    void onlyName() {
        setCardApproved();
        cardApproved.setOwner("Иван");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationError();
    }
    @DisplayName("12. Заполнить поле именем через дефис и фамилией при покупке. Должно быть сообщение:Успешно")
    @Test
    void name() {
        setCardApproved();
        cardApproved.setOwner("Анна-Луиза Рыжова");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationOk();

    }
    @DisplayName("13.BUG Заполнить поле владелец на английском языке при покупке в кредит. Должно быть сообщение:Успешно")
    @Test
    void nameEnglish() {
        setCardApproved();
        cardApproved.setOwner("Ivan Ivanov");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationError();
    }





    /////

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

    void setCardDecline() {
        cardDecline.setNumber(DataHelper.declinedCardNumber());
        cardDecline.setMonth(DataHelper.validMonth());
        cardDecline.setYear(DataHelper.validYear());
        cardDecline.setOwner(DataHelper.name());
        cardDecline.setCvcCvv(DataHelper.validCvcCvv());
    }
}
