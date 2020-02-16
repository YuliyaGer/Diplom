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
    private SelenideElement owner = $(byText("Владелец"));
    private SelenideElement CvcCvv = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement notificationSuccess = $(byCssSelector(".notification_status_ok"));
    private SelenideElement notificationError = $(byCssSelector(".notification_status_error"));

    public CreditPage() {

        $(withText("Оплата по карте")).shouldBe(Condition.visible);
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

    public void checkOperationApproved() {
        notificationSuccess.waitUntil(Condition.visible, 15000);
    }

    public void checkOperationDeclined() {
        notificationError.waitUntil(Condition.visible, 15000);
    }
}


