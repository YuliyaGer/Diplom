package Data;


import lombok.Data;
import lombok.Value;


public class Card {


    @Data
    @Value
    class card {
        private String cardNumberApproved;
        private String cardNumberDecline;
        private int month;
        private int year;
        private String CvcCvv;
        private String owner;
    }
}