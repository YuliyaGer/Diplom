package page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class BuyPage extends StartPage {

    public BuyPage() {

        $(withText("Оплата по карте")).shouldBe(Condition.visible);
    }
}
