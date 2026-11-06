package com.qa.mobile.ui.screens.factory;

import com.qa.mobile.ui.screens.interfaces.Screen;
import org.picocontainer.PicoContainer;

public class ScreenFactory {

    private final PicoContainer container;

    public ScreenFactory(PicoContainer container) {
        this.container = container;
    }

    public <T extends Screen> T getScreen(Class<T> screenType) {
        T screen = container.getComponent(screenType);

        if (screen == null) {
            throw new IllegalArgumentException(
                    "Unsupported screen type: " + screenType.getName());
        }

        return screen;
    }
}