package Page;

import Data.Card;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class BuyPage {
    private SelenideElement buyNumberCard = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement mouthBuy = $("[placeholder='08']");
    private SelenideElement yearBuy = $("[placeholder='22']");
    private SelenideElement owner = $(byText("Владелец")).parent().$(byCssSelector(".input__control"));
    private SelenideElement CvcCvv = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement notificationSuccess = $(byCssSelector(".notification_status_ok"));
    private SelenideElement notificationError = $(byCssSelector(".notification_status_error"));
    private SelenideElement invalidFormat = $(".input__sub");


    public BuyPage() {
        $(withText("Оплата по карте")).shouldBe(Condition.visible);
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


    public boolean invalidCardFormat() {
        invalidFormat.shouldHave(Condition.exactText("Неверный формат"));
        return true;
    }

    public void continueButton() {

        continueButton.click();
    }

    public boolean yearExpiredError() {
        invalidFormat.shouldHave(Condition.exactText("Истёк срок действия карты"));
        return true;

    }
    public boolean yearTermError() {
        invalidFormat.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
        return true;

    }
}
