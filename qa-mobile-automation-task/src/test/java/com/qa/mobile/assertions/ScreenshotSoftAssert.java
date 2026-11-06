package com.qa.mobile.assertions;

import com.qa.mobile.driver.DriverManager;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class ScreenshotSoftAssert extends SoftAssert {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenshotSoftAssert.class);
    private int screenshotNumber;

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        super.onAssertFailure(assertCommand, ex);
        attachScreenshot(assertCommand.getMessage());
    }

    private void attachScreenshot(String assertionMessage) {
        try {
            byte[] screenshot = DriverManager.getDriver()
                    .getScreenshotAs(OutputType.BYTES);

            screenshotNumber++;

            String attachmentName = String.format(
                    "Failure screenshot %d - %s",
                    screenshotNumber,
                    extractStepNumber(assertionMessage)
            );

            Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(screenshot), ".png"
            );

            LOGGER.info("Screenshot attached to Allure report: {}", attachmentName);

        } catch (Exception e) {
            LOGGER.error("Failed to capture or attach screenshot", e);

            Allure.addAttachment("Screenshot capture error", e.toString());
        }
    }

    private String extractStepNumber(String assertionMessage) {
        if (assertionMessage == null || !assertionMessage.startsWith("[Step ")) {
            return "Unknown step";
        }

        int endIndex = assertionMessage.indexOf(']');

        if (endIndex == -1) {
            return "Unknown step";
        }

        return assertionMessage.substring(1, endIndex);
    }
}