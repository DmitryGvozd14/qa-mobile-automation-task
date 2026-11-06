package com.qa.mobile.ui.elements.android;

import com.qa.mobile.driver.DriverManager;
import com.qa.mobile.ui.elements.base.BaseElement;
import com.qa.mobile.ui.elements.interfaces.TextField;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.clipboard.ClipboardContentType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidTextField extends BaseElement implements TextField {

    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidTextField.class);

    public AndroidTextField(By locator, String name) {
        super(locator, name);
    }

    @Override
    public void enterText(String text) {
        LOGGER.info("Entering text into field: {}", name);

        var element = getElement();
        element.clear();
        element.sendKeys(text);

        LOGGER.info("Text entered successfully into field: {}", name);
    }

    @Override
    public void pasteText(String text) {
        LOGGER.info("Pasting text into field: {}", name);

        AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();

        String encodedText = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));

        driver.setClipboard(
                ClipboardContentType.PLAINTEXT,
                encodedText.getBytes(StandardCharsets.UTF_8)
        );

        getElement().click();
        driver.pressKey(new KeyEvent(AndroidKey.PASTE));

        LOGGER.info("Text pasted successfully into field: {}", name);
    }

    @Override
    public void clear() {
        LOGGER.info("Clearing field: {}", name);

        getElement().clear();

        LOGGER.info("Field cleared successfully: {}", name);
    }

    @Override
    public String getValue() {
        LOGGER.info("Getting value from field: {}", name);

        WebElement element = getElement();

        if ("true".equals(element.getAttribute("showing-hint"))) {
            return "";
        }

        return element.getAttribute("text");
    }

    @Override
    public void typeText(String text) {
        LOGGER.info("Typing text into field: {}", name);

        AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();

        getElement().click();

        driver.executeScript(
                "mobile: type",
                Map.of("text", text)
        );

        LOGGER.info("Text typed successfully into field: {}", name);
    }

    @Override
    public boolean isMasked() {
        boolean masked = Boolean.parseBoolean(
                getElement().getAttribute("password")
        );

        LOGGER.info("Password visibility: {}", masked ? "disabled" : "enabled");

        return masked;
    }

    @Override
    public void clearAndPaste(String text) {
        clear();
        pasteText(text);
    }

    @Override
    public void clearAndType(String text) {
        clear();
        typeText(text);
    }
}