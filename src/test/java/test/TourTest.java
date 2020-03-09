package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.BuyPage;
import page.CreditPage;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TourTest {
    Card cardApproved = new Card();
    Card cardDecline = new Card();

    @AfterEach
    void cleanTables() {
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
    void shouldValidBuySuccess() {
        setCardApproved();
        openBuyPageAndFill(cardApproved).checkOperationOk();
        assertEquals(SqlHelper.selectBuyStatus(), "APPROVED");
    }

    @DisplayName("2. Оплата в кредит успешная и таблица заполняется approved")
    @Test
    void shouldValidCreditSuccess() {
        setCardApproved();
        openCreditPageAndFill(cardApproved).checkOperationOk();
        assertEquals(SqlHelper.selectCreditStatus(), "APPROVED");
    }

    @DisplayName("3.BuG Оплата в кредит не успешная дожно появляться окно с ошибкой и таблица заполняться Decline")
    @Test
    void shouldNotValidCreditWithError() {
        setCardDecline();
        openCreditPageAndFill(cardDecline).checkOperationError();
        assertEquals(SqlHelper.selectCreditStatus(), "DECLINE");
    }

    @DisplayName("4. BUG Оплата не в кредит, не успешная операция дожно появляться окно с ошибкой " +
            "и таблица заполняться Decline")
    @Test
    void shouldNotValidBuyWithError() {
        setCardDecline();
        openBuyPageAndFill(cardDecline).checkOperationError();
        assertEquals(SqlHelper.selectBuyStatus(), "DECLINE");
    }

    @DisplayName("5. Оплата не в кредит, не полный номер карты. Должна быть ошибка в поле")
    @Test
    void shouldNotValidBuyInvalidCardNumber() {
        setCardApproved();
        cardApproved.setNumber("4444 4444 4444");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.invalidCardFormatMessageShouldBeShown();
    }

    @DisplayName("6. Не заполнять поле месяц при покупке. Должна быть ошибка в поле")
    @Test
    void emptyMonthShouldErrorMessage() {
        setCardApproved();
        cardApproved.setMonth("");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.invalidCardFormatMessageShouldBeShown();
    }

    @DisplayName("7. Оплата в кредит, не верный номер карты. Должно всплыть сообщение об ошибке")
    @Test
    void numberCardErrorShouldErrorMessage() {
        setCardApproved();
        cardApproved.setNumber("1111 1111 1111 1111");
        CreditPage creditPage = openStartPage().creditPage();
        creditPage.validData(cardApproved);
        creditPage.checkOperationError();
    }

    @DisplayName("8. Заполнить поле месяц не полностью при покупке. Должна быть ошибка в поле")
    @Test
    void monthNoncompletelyShouldErrorMessage() {
        setCardApproved();
        cardApproved.setMonth("1");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.invalidCardFormatMessageShouldBeShown();
    }

    @DisplayName("9. Заполнить поле 'год' на 15 лет позже от текущей даты при покупке. Должна быть ошибка в поле")
    @Test
    void futureYearShouldErrorMessage() {
        setCardApproved();
        cardApproved.setYear(DataHelper.setFutureYear());
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.yearTermErrorMessageShouldBeShown();
    }

    @DisplayName("10. Заполнить поле 'год' на 2 года раньше текущей даты при покупке. Должна быть ошибка в поле")
    @Test
    void earlyYearShouldErrorMessage() {
        setCardApproved();
        cardApproved.setYear(DataHelper.setEarlyYear());
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.yearExpiredErrorMessageShouldBeShown();
    }

    @DisplayName("11. BUG Заполнить только имя при покупке. Должно всплыть сообщение об ошибке")
    @Test
    void onlyFillInNameShouldErrorMessage() {
        setCardApproved();
        cardApproved.setOwner("Иван");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationError();
    }

    @DisplayName("12. Заполнить поле именем через дефис и фамилией при покупке. Должно быть сообщение:Успешно")
    @Test
    void hyphenateNameShouldSuccessMessage() {
        setCardApproved();
        cardApproved.setOwner("Анна-Луиза Рыжова");
        BuyPage buyPage = openStartPage().buyPage();
        buyPage.validData(cardApproved);
        buyPage.checkOperationOk();
    }

    @DisplayName("13.BUG Заполнить поле владелец на английском языке при покупке в кредит. Должно быть сообщение:Успешно")
    @Test
    void nameOnlyEnglishShouldSuccessMessage() {
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
