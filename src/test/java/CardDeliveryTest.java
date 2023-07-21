import com.codeborne.selenide.SelenideElement;
import io.opentelemetry.sdk.metrics.data.Data;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {


    @Test
    void SuccessfulFormSend() {

        // определение текущей даты с отступом в 3 дня;
        var Data = new Date();
        var currentDay = Data.getDate();
        var currentMonth = Data.getMonth() + 1;
        var newDay = currentDay + 3;
        if (newDay > 31) {      // переход на следующий месяц с учетом 3-х дневного срока
            newDay = 05;
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

}
