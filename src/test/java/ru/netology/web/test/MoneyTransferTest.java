package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeAll
    static void setup() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080";


        FirefoxOptions options = new FirefoxOptions();
        // Отключение менеджера паролей и всплывающих окон
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autologin.proxy", false);
        options.addPreference("signon.privateBrowsingCaptureProfiles", false);
        options.addPreference("network.auth.use-sspi", false);
        options.addPreference("security.insecure_field_warning.contextual.enabled", false);
        options.addPreference("dom.disable_beforeunload", true);
        options.addPreference("browser.popups.showPopupBlocker", false);
        options.addPreference("dom.webnotifications.enabled", false);
        Configuration.browserCapabilities = options;
    }


    @BeforeEach
    public void setUp() {

        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    // Тест на проверку перевод с первой на вторую
    @Test
    public void testTransferMoneyOneToTwo() {

        // Обьявляем переменные для хранения номера карты
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();


        // Обьявляем переменные для хронения баланса карт
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);

        // ВЫЧИСЛЯЕМ сумму перевода, а не задаем жестко
        // Например, переводим половину от текущего баланса карты списания
        // Используем Math.floorDiv для целочисленного деления и избежания дробей
        int amount = Math.floorDiv(firstCardBalance, 2);

        // Если на карте 1 или 0 рублей, тест будет бессмысленным. Добавим проверку.
        // Assume.assumeTrue - если условие ложно, тест будет пропущен (status: skipped)
        Assumptions.assumeTrue(amount > 0, "На карте списания недостаточно средств для перевода (баланс: " + firstCardBalance + ")");

        // Ожидаемый результат суммы после перевода для баланса первой и второй карты
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;

        // Выполняем перевод средств на указанный номер карты
        var transfer = dashboardPage.selectCardToTransfer(secondCard);
        dashboardPage = transfer.validTransfer(String.valueOf(amount), firstCard);

        // Фактический результат суммы после перевода для баланса первой и второй карты
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        //Сравниваем Ожидаемый и Фактический результат
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);


    }

    // Тест на проверку перевод со вторрой на первую
    @Test
    public void testTransferMoneyTwoToOne() {

        // Обьявляем переменные для хранения номера карты
        var secondCard = DataHelper.getSecondCardInfo();
        var firstCard = DataHelper.getFirstCardInfo();

        // Обьявляем переменные для хранения номера карты
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);

        // ВЫЧИСЛЯЕМ сумму перевода, а не задаем жестко
        // Например, переводим половину от текущего баланса карты списания
        // Используем Math.floorDiv для целочисленного деления и избежания дробей
        int amount = Math.floorDiv(secondCardBalance, 2);

        // Если на карте 1 или 0 рублей, тест будет бессмысленным. Добавим проверку.
        // Assume.assumeTrue - если условие ложно, тест будет пропущен (status: skipped)
        Assumptions.assumeTrue(amount > 0, "На карте списания недостаточно средств для перевода (баланс: " + firstCardBalance + ")");

        // Ожидаемый результат суммы после перевода для баланса первой и второй карты
        var expectedFirstCardBalance = firstCardBalance + amount;
        var expectedSecondCardBalance = secondCardBalance - amount;

        // Выполняем перевод средств на указанный номер карты
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        dashboardPage = transfer.validTransfer(String.valueOf(amount), firstCard);

        // Фактический результат суммы после перевода для баланса первой и второй карты
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        //Сравниваем Ожидаемый и Фактический результат
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);


    }

    // Тест на проверку перевод с первой на первую
    @Test
    public void testTransferMoneyOneToOne() {

        // Обьявляем переменные для хранения номера карты
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        // Обьявляем переменные для хранения номера карты
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);

        // ВЫЧИСЛЯЕМ сумму перевода, а не задаем жестко
        // Например, переводим половину от текущего баланса карты списания
        // Используем Math.floorDiv для целочисленного деления и избежания дробей
        int amount = Math.floorDiv(firstCardBalance, 2);

        // Если на карте 1 или 0 рублей, тест будет бессмысленным. Добавим проверку.
        // Assume.assumeTrue - если условие ложно, тест будет пропущен (status: skipped)
        Assumptions.assumeTrue(amount > 0, "На карте списания недостаточно средств для перевода (баланс: " + firstCardBalance + ")");

        // Выполняем перевод средств на указанный номер карты
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        transfer.validTransfer(String.valueOf(amount), firstCard);

        // Проверяем название ошибки после выполнения операции перевода
        transfer.checkErrorMessage(DataHelper.getTextErrorInvalidCard());

        // Фактический результат суммы после перевода для баланса первой и второй карты
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        //Сравниваем Ожидаемый и Фактический результат
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);


    }

    // Тест на проверку перевода выше лимита
    @Test
    public void testErrorTransferHighlimit() {

        // Обьявляем переменные для хранения номера карты
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        // Обьявляем переменные для хранения номера карты
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);

        // ВЫЧИСЛЯЕМ сумму перевода, а не задаем жестко
        // Например, переводим половину от текущего баланса карты списания
        // Используем Math.floorDiv для целочисленного деления и избежания дробей
        int amount = (Math.floorDiv(secondCardBalance, 2)) * 3;

        // Если на карте 1 или 0 рублей, тест будет бессмысленным. Добавим проверку.
        // Assume.assumeTrue - если условие ложно, тест будет пропущен (status: skipped)
        Assumptions.assumeTrue(amount > 0, "На карте списания недостаточно средств для перевода (баланс: " + firstCardBalance + ")");

        // Выполняем перевод средств на указанный номер карты
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        transfer.validTransfer(String.valueOf(amount), secondCard);

        // Проверяем название ошибки после выполнения операции перевода выше лимита баланса карты
        transfer.checkErrorMessage(DataHelper.getTextErrorLimit());

        // Фактический результат суммы после перевода для баланса первой и второй карты
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        //Сравниваем Ожидаемый и Фактический результат
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);


    }

    // тест на проверку перевода с нулевым болансом
    @Test
    public void testErrorTransferNall() {

        // Обьявляем переменные для хранения номера карты
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        // Обьявляем переменные для хранения номера карты
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);

        // ВЫЧИСЛЯЕМ сумму перевода, а не задаем жестко
        // Например, переводим половину от текущего баланса карты списания
        // Используем Math.floorDiv для целочисленного деления и избежания дробей
        int amount = secondCardBalance - secondCardBalance + 001;

        // Если на карте 1 или 0 рублей, тест будет бессмысленным. Добавим проверку.
        // Assume.assumeTrue - если условие ложно, тест будет пропущен (status: skipped)
        Assumptions.assumeTrue(amount > 0, "На карте списания недостаточно средств для перевода (баланс: " + firstCardBalance + ")");

        // Выполняем перевод средств на указанный номер карты
        var transfer = dashboardPage.selectCardToTransfer(firstCard);
        transfer.validTransfer(String.valueOf(amount), secondCard);

        // Проверяем название ошибки после выполнения операции перевода с нулевым баланса карты
        transfer.checkErrorMessage(DataHelper.getTextErrorLimit());

        // Фактический результат суммы после перевода для баланса первой и второй карты
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        //Сравниваем Ожидаемый и Фактический результат
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);
    }
}

