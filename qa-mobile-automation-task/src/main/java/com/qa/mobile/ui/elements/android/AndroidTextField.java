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

public class AndroidTextField extends BaseElement implements TextField {

  public AndroidTextField(By locator, String name) {
    super(locator, name);
  }

  @Override
  public void enterText(String text) {
    var element = getElement();
    element.clear();
    element.sendKeys(text);
  }

  @Override
  public void pasteText(String text) {
    AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();

    String encodedText = Base64.getEncoder()
        .encodeToString(text.getBytes(StandardCharsets.UTF_8));

    driver.setClipboard(
        ClipboardContentType.PLAINTEXT,
        encodedText.getBytes(StandardCharsets.UTF_8)
    );

    getElement().click();
    driver.pressKey(new KeyEvent(AndroidKey.PASTE));
  }

  @Override
  public void clear() {
    getElement().clear();
  }

  @Override
  public String getValue() {
    WebElement element = getElement();

    if ("true".equals(element.getAttribute("showing-hint"))) {
      return "";
    }

    return element.getAttribute("text");
  }

  @Override
  public void typeText(String text) {
    AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();

    getElement().click();

    driver.executeScript(
        "mobile: type",
        Map.of("text", text)
    );
  }

  @Override
  public boolean isMasked() {
    return Boolean.parseBoolean(getElement().getAttribute("password"));
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