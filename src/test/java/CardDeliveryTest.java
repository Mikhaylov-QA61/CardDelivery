import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import io.opentelemetry.sdk.metrics.data.Data;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withTagAndText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    String planningDate = generateDate(5, "dd.MM.yyyy");

    @Test
    void successfulFormSend() {

        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Самара");
        $(".menu").click();
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(planningDate);
        form.$("[data-test-id=name] input").setValue("Петров Иван");
        form.$("[data-test-id=phone] input").setValue("+79176666666");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".spin_visible").shouldBe(visible);
        $("[data-test-id=notification].notification_visible .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification].notification_visible .notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    void sendFormUsingWidget(){

        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Са");
        $$(".menu .menu-item__control").find(exactText("Самара")).click();
        form.$("[data-test-id=date] .icon-button").click();
        if(!generateDate(3,"MM").equals(generateDate(7,"MM"))){
            $(".calendar__title[data-step=1]").click();
        }
        String calendarDate = generateDate(5, "dd");
//        $(withTagAndText("td.calendar__day", String.valueOf(calendarDate))).click();
        $$(".calendar__day").find(exactText(calendarDate)).click();
        form.$("[data-test-id=name] input").setValue("Петров Иван");
        form.$("[data-test-id=phone] input").setValue("+79176666666");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".spin_visible").shouldBe(visible);
        $("[data-test-id=notification].notification_visible .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification].notification_visible .notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }
}
