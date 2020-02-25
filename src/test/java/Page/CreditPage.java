package Page;

import Data.Card;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class CreditPage {
    private SelenideElement buyNumberCard = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement mouthBuy = $("[placeholder='08']");
    private SelenideElement yearBuy = $("[placeholder='22']");
    private SelenideElement owner = $(byText("Владелец")).parent().$(byCssSelector(".input__control"));
    private SelenideElement CvcCvv = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement notificationSuccess = $(byCssSelector(".notification_status_ok"));
    private SelenideElement notificationError = $(byCssSelector(".notification_status_error"));
    private SelenideElement invalidFormat = $(".input__sub");

    public CreditPage() {
        $(withText("Кредит по данным карты")).shouldBe(Condition.visible);
    }

    public CreditPage validData(Card card) {
        buyNumberCard.setValue(card.getNumber());
        mouthBuy.setValue(card.getMonth());
        yearBuy.setValue(card.getYear());
        owner.setValue(card.getOwner());
        CvcCvv.setValue(card.getCvcCvv());
        continueButton.click();
        return new CreditPage();
    }

    public void checkOperationOk() {
        notificationSuccess.waitUntil(Condition.visible, 15000);
    }

    public void checkOperationError() {
        notificationError.waitUntil(Condition.visible, 15000);
    }
    public boolean invalidCardFormat() {
        invalidFormat.shouldHave(Condition.exactText("Неверный формат"));
        return true;
    }
    public void continueButton() {
        continueButton.click();
    }
}


