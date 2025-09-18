package user;

import com.github.javafaker.Faker;
import data.User;
import java.util.Locale;

public class UserGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    public static User getValidUser() {
        return new User()
                .setName(faker.name().fullName())
                .setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password(6, 12)); //Пароль 6-12 символов
    }

    public static User getUserWithShortPassword() {
        return new User()
                .setName(faker.name().fullName())
                .setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password(1, 5)); //Пароль 1-5 символов
    }
}