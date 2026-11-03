package com.qa.mobile.ui.elements.base;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseElement {

  private static final String TEST_TIMEOUT_PROPERTY = "test.timeout.seconds";

  protected final String name;
  protected final By locator;

  protected BaseElement(By locator, String name) {
    this.locator = locator;
    this.name = name;
  }

  protected WebDriverWait getWait() {
    return new WebDriverWait(
        DriverManager.getDriver(),
        Duration.ofSeconds(ConfigManager.getIntProperty(TEST_TIMEOUT_PROPERTY))
    );
  }

  protected WebElement getElement() {
    return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public boolean isDisplayed() {
    return DriverManager.getDriver()
        .findElements(locator)
        .stream()
        .anyMatch(WebElement::isDisplayed);
  }

  public void waitUntilDisplayed() {
    getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public String getText() {
    return getElement().getText();
  }
}