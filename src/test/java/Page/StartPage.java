package Page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));



    public BuyPage buyPage() {
        buyButton.click();
        return new BuyPage();
    }

    public CreditPage creditPage() {
        creditButton.click();
        return new CreditPage();
    }

}