package Page;

import Page.BuyPage;
import Page.CreditButton;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
private SelenideElement buyButton = $(byText("Купить"));
private SelenideElement creditButton = $(byText("Купить в кредит"));

public BuyPage buyPage(){
    buyButton.click();
    //$(withText("Оплата по карте")).shouldBe(Condition.visible);
    return new BuyPage();
}
public CreditButton creditButton(){
        creditButton.click();
        return new CreditButton();
    }

}