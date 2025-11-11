package pages;

import org.openqa.selenium.By;

import java.util.ArrayList;

public class UserPage extends HeaderSection {
    private final By fullNameInputLocator = By.name("name");
    private final By emailInputLocator = By.name("email");
    private final By phoneInputLocator = By.name("phone");
    private final By addressInputLocator = By.name("address");

    public ArrayList<String> getUserInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add(find(fullNameInputLocator).getAttribute("value").trim());
        info.add(find(emailInputLocator).getAttribute("value").trim());
        info.add(find(phoneInputLocator).getAttribute("value").trim());
        info.add(find(addressInputLocator).getAttribute("value").trim());
        return info;
    }
}
