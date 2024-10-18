package helpers;

import com.codeborne.selenide.WebDriverRunner;

public class Cookie {
    public static String getCookieValue(String value) {
        String cookieValue = String.valueOf(WebDriverRunner.getWebDriver().manage().getCookieNamed(value));
        return cookieValue.substring(cookieValue.indexOf("=") + 1, cookieValue.indexOf(";"));
    }
}
