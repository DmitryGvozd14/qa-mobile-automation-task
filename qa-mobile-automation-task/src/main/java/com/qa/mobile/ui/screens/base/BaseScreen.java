package com.qa.mobile.ui.screens.base;

import com.qa.mobile.ui.ElementFactoryProvider;
import com.qa.mobile.ui.elements.factory.ElementFactory;
import com.qa.mobile.ui.screens.interfaces.Screen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import com.qa.mobile.config.ConfigManager;
import com.qa.mobile.driver.DriverManager;

public abstract class BaseScreen implements Screen {

  private static final String TEST_TIMEOUT_PROPERTY = "test.timeout.seconds";

  protected final ElementFactory elementFactory;
  private final By locator;
  private final String name;

  protected BaseScreen(By locator, String name) {
    this.locator = locator;
    this.name = name;
    this.elementFactory = ElementFactoryProvider.getFactory();
  }

  protected WebDriverWait getWait() {
    return new WebDriverWait(
        DriverManager.getDriver(),
        Duration.ofSeconds(ConfigManager.getIntProperty(TEST_TIMEOUT_PROPERTY))
    );
  }

  @Override
  public boolean isDisplayed() {
    return DriverManager.getDriver()
        .findElements(locator)
        .stream()
        .anyMatch(WebElement::isDisplayed);
  }

  @Override
  public void waitUntilDisplayed() {
    getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected ElementFactory getElementFactory() {
    return elementFactory;
  }
}