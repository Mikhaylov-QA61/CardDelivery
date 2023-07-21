import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import io.opentelemetry.sdk.metrics.data.Data;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    @Test
    void successfulFormSend() {

        // определение текущей даты с отступом в 3 дня;
        var Data = new Date();
        var currentDay = Data.getDate();
        var currentMonth = Data.getMonth() + 1;
        var newDay = currentDay + 3;
        if (newDay > 31) {      // переход на следующий месяц с учетом 3-х дневного срока
            newDay = currentDay-31;
            currentMonth++;
        }
        if (currentMonth > 12) {
            currentMonth = 01;

        }

        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $(".menu").click();
        form.$("[data-test-id=date] input").setValue(newDay + "." + currentMonth + ".2023");
        form.$("[data-test-id=name] input").setValue("Петров Иван");
        form.$("[data-test-id=phone] input").setValue("+79176666666");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".spin_visible").shouldBe(visible);
        $("[data-test-id=notification].notification_visible .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));

    }

    @Test
    void sendFormUsingWidget(){

        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Са");
        $$(".menu .menu-item__control").find(exactText("Самара")).click();
        form.$("[data-test-id=date] .icon-button").click();
        var Data = new Date();
        var currentDay = Data.getDate();
        var newDay = currentDay + 7;
        if (newDay > 31) {
            newDay = currentDay-31;
        $(".calendar__title[data-step=1]").click();
        }
        $(byText(String.valueOf(newDay))).click();
        form.$("[data-test-id=name] input").setValue("Петров Иван");
        form.$("[data-test-id=phone] input").setValue("+79176666666");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".spin_visible").shouldBe(visible);
        $("[data-test-id=notification].notification_visible .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));

    }
}
