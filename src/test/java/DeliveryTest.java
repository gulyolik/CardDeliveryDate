import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

import java.util.Locale;

public class DeliveryTest {
    private Faker faker;

    @BeforeEach
    void setUpAll() {

        faker = new Faker(new Locale("ru"));
    }
}
