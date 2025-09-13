package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private final SelenideElement amountInputNew = $("[data-test-id=\"amount\"] input");
    private final SelenideElement fromInput = $("[data-test-id=\"from\"] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement errorMessage = $("[data-test-id=\"error-notification\"]");

    public TransferPage() {
        transferHead.shouldBe(Condition.visible);
    }

    public void transfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInputNew.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();

    }


    public DashboardPage validTransfer(String amauntToTransfer, DataHelper.CardInfo cardInfo) {
        transfer(amauntToTransfer, cardInfo);
        return new DashboardPage();

    }
    public void errorLimit(){
        $(errorMessage).should(Condition.exactText("Выполнена попытка перевода суммы, превышающей остаток на карте списания"));
    }
    public void invalidCard(){
        $(errorMessage).should(Condition.text("Ошибка! Перевод на одну и ту же карту невозможен"));
    }


}

