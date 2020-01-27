package Data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("ru"));
    private static String cardNumberApproved = "4444 4444 4444 4441";
    private static String cardNumberDecline = "4444 4444 4444 4442";
    private static int month = faker.number().numberBetween(1,12);
    private static int year = faker.number().numberBetween(20,25);
    private int CvcCvv = faker.number().numberBetween(001,999);
}
