package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
    @Getter
    protected SelenideElement buyButton = $(byText("Купить"));
    protected SelenideElement creditButton = $(byText("Купить в кредит"));
    protected SelenideElement buyNumberCard = $("[placeholder='0000 0000 0000 0000']");
    protected SelenideElement mouthBuy = $("[placeholder='08']");
    protected SelenideElement yearBuy = $("[placeholder='22']");
    protected SelenideElement owner = $(byText("Владелец")).parent().$(byCssSelector(".input__control"));
    protected SelenideElement CvcCvv = $("[placeholder='999']");
    protected SelenideElement continueButton = $(byText("Продолжить"));
    protected SelenideElement notificationSuccess = $(byCssSelector(".notification_status_ok"));
    protected SelenideElement notificationError = $(byCssSelector(".notification_status_error"));
    protected SelenideElement invalidFormat = $(".input__sub");

    public BuyPage buyPage() {
        buyButton.click();
        return new BuyPage();
    }

    public CreditPage creditPage() {
        creditButton.click();
        return new CreditPage();
    }

    public void validData(Card card) {
        buyNumberCard.setValue(card.getNumber());
        mouthBuy.setValue(card.getMonth());
        yearBuy.setValue(card.getYear());
        owner.setValue(card.getOwner());
        CvcCvv.setValue(card.getCvcCvv());
        continueButton.click();
    }

    public void checkOperationOk() {

        notificationSuccess.waitUntil(Condition.visible, 15000);
    }

    public void checkOperationError() {

        notificationError.waitUntil(Condition.visible, 15000);
    }

    public boolean invalidCardFormatMessageShouldBeShown() {
        invalidFormat.shouldHave(Condition.exactText("Неверный формат"));
        return true;
    }

    public boolean yearExpiredErrorMessageShouldBeShown() {
        invalidFormat.shouldHave(Condition.exactText("Истёк срок действия карты"));
        return true;
    }

    public boolean yearTermErrorMessageShouldBeShown() {
        invalidFormat.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
        return true;
    }
}