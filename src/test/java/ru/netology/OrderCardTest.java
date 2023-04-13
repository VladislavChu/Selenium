package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {

    @BeforeEach
    void shouldOpenBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов-Залесский Василий");
        form.$("[data-test-id=phone] input").setValue("+79001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldRequestInvalidNameInput() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ivanov Ivan");
        form.$("[data-test-id=phone] input").setValue("+79001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldRequestInvalidPhoneInput() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("89001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldRequestIfCheckboxNotSelected() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79001234567");
        form.$("button").click();
        form.$("[data-test-id=agreement]").should(cssClass("input_invalid"));
    }

    @Test
    void shouldRequestIfNameNotEntered() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+79001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRequestIfPhoneNotEntered() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRequestIfEnteredPhoneInName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id='name'] input").setValue("+79641234567");
        form.$("[data-test-id='phone'] input").setValue("+79641234567");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldRequestIfEnteredNameInPhone() {
        SelenideElement form = $(".form");
        form.$("[data-test-id='name'] input").setValue("Иванов Иван");
        form.$("[data-test-id='phone'] input").setValue("Иванов Иван");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}