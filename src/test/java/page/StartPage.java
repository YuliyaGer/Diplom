package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
    @Getter protected SelenideElement buyButton = $(byText("Купить"));
    @Getter protected SelenideElement creditButton = $(byText("Купить в кредит"));
    @Getter protected SelenideElement buyNumberCard = $("[placeholder='0000 0000 0000 0000']");
    @Getter protected SelenideElement mouthBuy = $("[placeholder='08']");
    @Getter protected SelenideElement yearBuy = $("[placeholder='22']");
    @Getter protected SelenideElement owner = $(byText("Владелец")).parent().$(byCssSelector(".input__control"));
    @Getter protected SelenideElement CvcCvv = $("[placeholder='999']");
    @Getter protected SelenideElement continueButton = $(byText("Продолжить"));
    @Getter protected SelenideElement notificationSuccess = $(byCssSelector(".notification_status_ok"));
    @Getter protected SelenideElement notificationError = $(byCssSelector(".notification_status_error"));
    @Getter protected SelenideElement invalidFormat = $(".input__sub");


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
    public boolean invalidCardFormat() {
        invalidFormat.shouldHave(Condition.exactText("Неверный формат"));
        return true;
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