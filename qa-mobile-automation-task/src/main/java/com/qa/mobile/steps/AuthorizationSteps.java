package com.qa.mobile.steps;

import com.qa.mobile.enums.InputMethod;
import com.qa.mobile.ui.screens.interfaces.LoginScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizationSteps {

    private final LoginScreen loginScreen;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationSteps.class);

    public AuthorizationSteps(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
    }

    public void enterLogin(String login, InputMethod inputMethod) {
        LOGGER.info("Entering login using {}", inputMethod);
        switch (inputMethod) {
            case TYPING -> loginScreen.enterUsername(login);
            case PASTING -> loginScreen.pasteUsername(login);
        }

        LOGGER.info("Login entered successfully");
    }

    public void enterPassword(String password, InputMethod inputMethod) {
        LOGGER.info("Entering password using {}", inputMethod);
        switch (inputMethod) {
            case TYPING -> loginScreen.enterPassword(password);
            case PASTING -> loginScreen.pastePassword(password);
        }

        LOGGER.info("Password entered successfully");
    }

    public void enterLogin(String login, InputMethod inputMethod, boolean needsClear) {
        LOGGER.info("Entering login using {} (clear field: {})", inputMethod, needsClear);
        switch (inputMethod) {
            case TYPING -> loginScreen.enterUsername(login, needsClear);
            case PASTING -> loginScreen.pasteUsername(login, needsClear);
        }

        LOGGER.info("Login entered successfully");
    }

    public void enterPassword(String password, InputMethod inputMethod, boolean needsClear) {
        LOGGER.info("Entering password using {} (clear field: {})", inputMethod, needsClear);
        switch (inputMethod) {
            case TYPING -> loginScreen.enterPassword(password, needsClear);
            case PASTING -> loginScreen.pastePassword(password, needsClear);
        }

        LOGGER.info("Password entered successfully");
    }
}
