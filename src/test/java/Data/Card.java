package Data;


import lombok.Data;


@Data
public class Card {
        private String number;
        private String month;
        private String year;
        private String cvcCvv;
        private String owner;
}