package Data;


import com.github.javafaker.Faker;

import java.util.Locale;


public class DataHelper {

    public static Faker faker = new Faker(new Locale("ru"));

    public static String approvedCardNumber() {

        return "4444444444444441";
    }

    public static String declinedCardNumber() {

        return "4444444444444442";
    }
    public static String invalidCardNumber() {

        return "4444 4444 4444";
    }

    public static String name() {

        return faker.name().fullName();
    }

    public static String validMonth() {
        int mouth = faker.number().numberBetween(1, 12);
        return Integer.toString(mouth);
    }

    public static String validYear() {
        int year = faker.number().numberBetween(20, 25);
        return Integer.toString(year);
    }

    public static String validCvcCvv() {
        int cvcCvv = faker.number().numberBetween(001, 999);
        return Integer.toString(cvcCvv);
    }


}

