package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.example.setUp.SelenideBasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class SelenideLoginPage extends SelenideBasePage {

    private final String baseUrl = propertiesHelper.getProperty("baseUrl");

    public SelenideLoginPage enterLogin(String login) {
        SelenideElement loginPlaceholder = $(By.xpath("//input[@placeholder='Login']"));
        loginPlaceholder.sendKeys(login);
        return this;
    }

    public SelenideLoginPage enterPassword(String password) {
        SelenideElement passwordPlaceholder = $(By.xpath("//input[@placeholder='Password']"));
        passwordPlaceholder.sendKeys(password);
        return this;
    }

    public void clickLogin() {
        SelenideElement loginButton =
                $(By.xpath("//div[contains(@class, 'login-button')]/button"));
        loginButton.click();
        log.info("Login was clicked");
    }

    public SelenideLoginPage goToLoginPage() {
        open(baseUrl);
        log.info("Opened {} url", baseUrl);
        return this;
    }

}
