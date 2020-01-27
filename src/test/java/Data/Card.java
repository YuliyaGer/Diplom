package Data;

import com.github.javafaker.Faker;
import lombok.Data;

import java.util.Locale;

public class Card {


    @Data
    class card {
        private String cardNumberApproved;
        private String cardNumberDecline;
        private int month;
        private int year;
        private String CvcCvv;
        private String owner;
    }
}