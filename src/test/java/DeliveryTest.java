
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.Text;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    private Faker faker;


    @BeforeEach
    void setUpAll() {

        faker = new Faker(new Locale("ru"));
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        Configuration.holdBrowserOpen = true;
        $("[data-test-id = 'city'] input").setValue(validUser.getCity());
        $("[data-test-id = 'date'] input").sendKeys((Keys.CONTROL + "A"), Keys.DELETE);
        $("[data-test-id = 'date'] input").setValue(firstMeetingDate);
        $("[data-test-id = 'name'] input").setValue(validUser.getName());
        $("[data-test-id = 'phone'] input").setValue(validUser.getPhone());
        $("[data-test-id= 'agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id = 'success-notification'] .notification__title").
                shouldBe(visible,Duration.ofSeconds(5)).shouldHave(exactText("Успешно!"));
        $("[data-test-id = 'success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id = 'date'] input").sendKeys((Keys.CONTROL + "A"), Keys.DELETE);
        $("[data-test-id = 'date'] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id = 'replan-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(5)).shouldHave(exactText("Необходимо подтверждение"));
        $("[data-test-id = 'replan-notification'] .notification__content")
               .shouldBe(visible,Duration.ofSeconds(5))
                .shouldHave(new Text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id = 'success-notification'] .notification__title").
                shouldBe(visible,Duration.ofSeconds(5)).shouldHave(exactText("Успешно!"));
        $("[data-test-id = 'success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));




    }
}

