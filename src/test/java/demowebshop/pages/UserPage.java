package demowebshop.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class UserPage {

    private final SelenideElement firstNameInput = $("#FirstName");
    private final SelenideElement lastNameInput = $("#LastName");
    private final SelenideElement emailInput = $("#Email");
    private final SelenideElement passwordInput = $("#Password");
    private final SelenideElement confirmPasswordInput = $("#ConfirmPassword");
    private final SelenideElement submitRegistrationButton = $("#register-button");
    private final SelenideElement openProfileLink = $(".account");
    private final SelenideElement saveProfileLink = $(".save-customer-info-button");
    private final SelenideElement registrationResult = $(".result");


    public void checkUserCreated(String email) {
        registrationResult.should(appear).shouldHave(text("Your registration completed"));
        openProfileLink.shouldBe(visible).shouldHave(text(email));
    }

    public void checkChangesSaved(String anotherEmail, String anotherName, String anotherLastName) {
        firstNameInput.shouldHave(value(anotherName));
        lastNameInput.shouldHave(value(anotherLastName));
        emailInput.shouldHave(value(anotherEmail));
        openProfileLink.shouldBe(visible).shouldHave(text(anotherEmail));
    }

    public UserPage setFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    public UserPage setLastName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    public UserPage setEmail(String email) {
        emailInput.setValue(email);
        return this;
    }

    public UserPage setPassword(String password) {
        passwordInput.setValue(password);
        confirmPasswordInput.setValue(password);
        return this;
    }

    public void sendRegistrationForm() {
        submitRegistrationButton.click();
    }

    public void openProfile() {
        openProfileLink.click();
    }

    public void saveProfile() {
        saveProfileLink.click();
    }

}
