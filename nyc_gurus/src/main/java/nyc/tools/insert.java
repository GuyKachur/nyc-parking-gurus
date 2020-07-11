package nyc.tools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Inserter runs a sample set of actions against the connect {@link ConnectionManager} defines.
 *
 * @author Guy Kachur
 */
public class insert {
    private static final String library = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    private final static java.util.Random rand = new java.util.Random();

    /**
     * Used to generate random strings, cause why not
     *
     * @param count
     * @return Randomcharacters from predefined library of symbols to the length you specify.
     */
    private static String randomString(int count) {
        StringBuilder sb = new StringBuilder();
        while (count-- != 0) {
            sb.append(library.charAt(rand.nextInt(library.length())));
        }
        return sb.toString();
    }

    /**
     * Copies out the str to the amount of times you specify
     *
     * @param str
     * @param count
     * @return your string repeated to the count you specify
     */
    private static String toLength(String str, int count) {
        int cursor = 0;
        StringBuilder sb = new StringBuilder();
        while (cursor < count) {
            sb.append(str);
            cursor++;
        }
        return sb.toString();
    }

    /**
     * Runs the Program
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) {
        Uploader up = new Uploader();
        try {
            up.uploadAirBnb();
            up.uploadBusiness();
            up.uploadGarden();
            up.uploadMarket();
            up.uploadPark();
            up.uploadPointOfInterest();
            up.uploadCollision();
            up.uploadEmergencyResponse();
            up.uploadGraffiti();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}