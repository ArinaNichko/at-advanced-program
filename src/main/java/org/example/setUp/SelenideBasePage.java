package org.example.setUp;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.PropertiesHelper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SelenideBasePage {
    public static PropertiesHelper propertiesHelper;

    protected SelenideBasePage() {
        propertiesHelper = PropertiesHelper.getInstance();
    }

    public List<SelenideElement> getSelenideElements(ElementsCollection elementsCollection) {
        List<SelenideElement> elements = new ArrayList<>();
        for (SelenideElement element : elementsCollection) {
            elements.add(element);
        }
        return elements;
    }
}
