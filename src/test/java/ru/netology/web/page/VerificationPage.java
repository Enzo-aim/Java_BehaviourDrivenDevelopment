package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=\"code\"] input");
    private SelenideElement button = $("[data-test-id=\"action-verify\"]");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        button.click();
        $("[data-test-id=\"dashboard\"]").shouldBe(Condition.visible, Duration.ofSeconds(10));
        return new DashboardPage();

    }

}
