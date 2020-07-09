package model;

import java.util.Date;

/**
 * Represents a credit card in the database
 *
 * @author Guy Kachur
 */
public class CreditCard {
    protected long creditCardKey;
    protected String nameOnCard;
    protected String cardNum;
    protected Date cardExpiration;
    protected String userName;

    @Override
    public String toString() {
        return "[ ID:" + creditCardKey + " , NAME:" + nameOnCard + ", EXPIRES: " + cardExpiration + "]";
    }

    public CreditCard(long creditCardKey2, String nameOnCard2, String cardNum2, Date cardExpiration2,
                      String userName2) {
        assert (nameOnCard2 != null);
        assert (cardNum2 != null);
        assert (cardExpiration2 != null);
        assert (userName2 != null);

        creditCardKey = creditCardKey2;
        nameOnCard = nameOnCard2;
        cardNum = cardNum2;
        cardExpiration = cardExpiration2;
        userName = userName2;
    }

    public long getCreditCardKey() {
        return creditCardKey;
    }

    public void setCreditCardKey(long creditCardKey) {
        this.creditCardKey = creditCardKey;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Date getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(Date cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
