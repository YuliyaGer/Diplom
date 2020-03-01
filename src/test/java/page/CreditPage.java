package page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class CreditPage extends StartPage {


    public CreditPage() {
        $(withText("Кредит по данным карты")).shouldBe(Condition.visible);
    }


}


