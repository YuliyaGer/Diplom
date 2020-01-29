package Page;

import Data.Card;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class BuyPage {
    private SelenideElement buyNumberCard= $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement mouthBuy = $("[placeholder='08']");
    private SelenideElement yearBuy = $("[placeholder='22']");
    private SelenideElement owner = $(byText("Владелец"));
    private SelenideElement CvcCvv = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));



    public void BuyPage() {

        $(withText("Оплата по карте")).shouldBe(Condition.visible);
    }
    public BuyPage validData (Card card) {
     buyNumberCard.setValue();

    }


}
