
package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getTextErrorInvalidCard() {
        return "Ошибка! Перевод на одну и ту же карту невозможен";
    }

    public static String getTextErrorLimit() {
        return "Выполнена попытка перевода суммы, превышающей остаток на карте списания";

    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");

    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");

    }

    @Value
    public static class AuthInfo {
        private String login;
        private String Password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String testId;

    }

}
