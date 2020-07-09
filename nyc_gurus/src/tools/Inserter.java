package tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dal.CompanyDao;
import dal.CreditCardDao;
import dal.FoodCartRestaurantDao;
import dal.RecommendationDao;
import dal.ReservationDao;
import dal.RestaurantDao;
import dal.ReviewDao;
import dal.SitDownRestaurantDao;
import dal.TakeOutRestaurantDao;
import dal.UserDao;
import model.*;

import static model.Restaurant.CuisineType.*;

/**
 * Inserter runs a sample set of actions against the connect {@link ConnectionManager} defines.
 *
 * @author Guy Kachur
 */
public class Inserter {
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
    public static void main(String[] args) throws SQLException {

        //instances
        CompanyDao companyDAO = CompanyDao.getInstance();
        CreditCardDao creditcardDAO = CreditCardDao.getInstance();
        FoodCartRestaurantDao foodCartRestaurantDAO = FoodCartRestaurantDao.getInstance();
        RecommendationDao recommendationDAO = RecommendationDao.getInstance();
        ReservationDao reservationDAO = ReservationDao.getInstance();
        RestaurantDao restaurantDAO = RestaurantDao.getInstance();
        ReviewDao reviewDAO = ReviewDao.getInstance();
        SitDownRestaurantDao sitDownRestaurantDAO = SitDownRestaurantDao.getInstance();
        TakeOutRestaurantDao TakeOutRestaurantDAO = TakeOutRestaurantDao.getInstance();
        UserDao userDao = UserDao.getInstance();


        //Lists to keep track of items, and use for querying later
        List<User> userList = new ArrayList<>(5);
        List<Company> companyList = new ArrayList<>(5);
        List<Restaurant> restaurantList = new ArrayList<>(5);
        List<SitDownRestaurant> sitDownRestaurantList = new ArrayList<>(5);
        List<TakeOutRestaurant> takeOutRestaurants = new ArrayList<>(5);
        List<FoodCartRestaurant> foodCartRestaurants = new ArrayList<>(5);
        List<Review> reviewList = new ArrayList<>(5);
        List<Recommendation> recommendationList = new ArrayList<>(5);
        List<Reservation> reservations = new ArrayList<>(5);
        List<CreditCard> creditCards = new ArrayList<>(5);


        //then enter data, first create POJO then call instances create method

        //Users
        for (int i = 1; i < 6; i++) {
            User user = new User(toLength(String.valueOf(i), i), randomString(i + 5), randomString(i + 10), randomString(i + 10), String.valueOf(i), randomString(30));
            userDao.create(user);
            System.out.println("Creating User: " + user);
            userList.add(user);
            if (i == 3) {
                User otheruser = userDao.getUserByUserName(toLength(String.valueOf(i - 1), i - 1));
                System.out.println("User:" + user + " ->contacting-> User: " + otheruser);
            }
        }

        //Company
        for (int i = 1; i < 6; i++) {
            Company company = new Company(i, toLength(String.valueOf(i), i), randomString(i * 5));
            companyDAO.create(company);
            System.out.println("Creating Company: " + company);
            companyList.add(company);
            if (i == 3) {

                Company corporateTakeover = companyDAO.getCompanyByCompanyName(companyList.get(1).getName());
                companyDAO.updateAbout(company, corporateTakeover.getDescription());

                System.out.println("Company:" + company + " has some updates for the description of new subsidiary: " + corporateTakeover);
            }

        }

        Restaurant.CuisineType[] cuisines = new Restaurant.CuisineType[]{african, american, asian, european, hispanic};

        //Restaurant
        for (int i = 1; i < 6; i++) {
            Restaurant rest = new Restaurant(i, toLength(String.valueOf(i), i),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    i % 2 == 0,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(2),
                    randomString(5),
                    cuisines[i - 1],
                    companyList.get(i - 1).getCompanyKey());
            restaurantDAO.create(rest);
            System.out.println("Creating Restaurant: " + rest);
            restaurantList.add(rest);
            if (i == 3) {
                System.out.println("Restaurant with ID: " + restaurantList.get(1).getRestaurantKey() + " ->" +
                        restaurantDAO.getRestaurantByID(restaurantList.get(1).getRestaurantKey()));
                String compName = companyList.get(1).getName();
                System.out.println("Getting restaurants with CompanyName= " + compName + ": " + restaurantDAO.getRestaurantsByCompanyName(compName));
                System.out.println("American Restaurants: \n" + restaurantDAO.getRestaurantsByCuisine(cuisines[1]));
            }
        }
        //SitDownRestaurant
        for (int i = 1; i < 6; i++) {
            SitDownRestaurant sdr = new SitDownRestaurant(i,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    i % 2 == 0,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(2),
                    randomString(5),
                    cuisines[i - 1],
                    companyList.get(i - 1).getCompanyKey(),
                    i
            );
            sitDownRestaurantDAO.create(sdr);
            System.out.println("Creating SDR: " + sdr);
            sitDownRestaurantList.add(sdr);
            if (i > 2) {
                System.out.println("Previous SDR and RestaurantByCompany (should match)"
                        + sitDownRestaurantDAO.getSitDownRestaurantByID(sitDownRestaurantList.get(i - 1).getRestaurantKey())
                        + " : "
                        + sitDownRestaurantDAO.getSitDownRestaurantByCompanyName(companyList.get(i - 1).getName()));
            }

        }
        //TakeOutRestaurant
        for (int i = 1; i < 6; i++) {
            TakeOutRestaurant takeOutRestaurant = new TakeOutRestaurant(i,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    i % 2 == 0,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(2),
                    randomString(5),
                    cuisines[i - 1],
                    companyList.get(i - 1).getCompanyKey(),
                    i
            );
            TakeOutRestaurantDAO.create(takeOutRestaurant);
            System.out.println("Creating TOR: " + takeOutRestaurant);
            takeOutRestaurants.add(takeOutRestaurant);
            if (i == 3) {
                System.out.println("Should return the 3rd restaurant twice. " + takeOutRestaurant);
                System.out.println(TakeOutRestaurantDAO.getTakeOutRestaurantByCompanyName(companyList.get(2).getName()));
                System.out.println(TakeOutRestaurantDAO.getTakeOutRestaurantByID(takeOutRestaurants.get(2).getTakeOutRestaurantKey()));
            }

        }
        //FoodCartRestaurant
        for (int i = 1; i < 6; i++) {
            FoodCartRestaurant foodCartRestaurant = new FoodCartRestaurant(i,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    i % 2 == 0,
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(2),
                    randomString(5),
                    cuisines[i - 1],
                    companyList.get(i - 1).getCompanyKey(),
                    i % 2 == 0);
            foodCartRestaurantDAO.create(foodCartRestaurant);
            System.out.println("Creating FCR: " + foodCartRestaurant);
            foodCartRestaurants.add(foodCartRestaurant);

            if (i == 4) {
                System.out.println("Should be 1st and 2nd: " +
                        foodCartRestaurantDAO.getFoodCartRestaurantByCompanyName(companyList.get(0).getName()) +
                        foodCartRestaurantDAO.getFoodCartRestaurantByID(foodCartRestaurants.get(1).getFoodCartKey()));
            }

        }

        //Review
        for (int i = 1; i < 6; i++) {
            Review review = new Review(i,
                    userList.get(i - 1).getUserName(),
                    restaurantList.get(rand.nextInt(4)).getRestaurantKey(),
                    new Date(),
                    randomString(i * 5),
                    i);
            reviewDAO.create(review);
            System.out.println("Creating Review: " + review);
            reviewList.add(review);
            if (i == 4) {
                System.out.println("Lets print 4th by ID, :" +
                        reviewDAO.getReviewByID(reviewList.get(3).getReviewKey()));
                System.out.println("Second by ID" + reviewDAO.getReviewsByRestaurantID(reviewList.get(1).getRestaurantKey()));
                System.out.println("1st by Username" + reviewDAO.getReviewsByUserName(userList.get(0).getUserName()));
            }
        }


        //Recommendation
        for (int i = 1; i < 6; i++) {
            Recommendation rec = new Recommendation(i, userList.get(i - 1).getUserName(), restaurantList.get(i - 1).getRestaurantKey());
//            System.out.println("Creating Recommendation: " + recommendationDAO.create(rec));
            recommendationDAO.create(rec);
            System.out.println("Creating Recommendation: " + rec);
            recommendationList.add(rec);

            if (i == 4) {
                System.out.println("This should reprint whats up there (3)" +
                        recommendationDAO.getRecommendationByID(recommendationList.get(2).getRecommendationKey()) +
                        recommendationDAO.getRecommendationsByRestaurantID(restaurantList.get(2).getRestaurantKey()) +
                        recommendationDAO.getRecommendationsByUserName(userList.get(2).getUserName()));
            }
        }

        //Reservation
        for (int i = 1; i < 6; i++) {
            Reservation reservation = new Reservation(i,
                    userList.get(i - 1).getUserName(),
                    sitDownRestaurantList.get(i - 1).getRestaurantKey(),
                    new Date(),
                    new Date(),
                    i);
            reservationDAO.create(reservation);
            System.out.println("Creating Reservation: " + reservation);
            reservations.add(reservation);
            if (i == 3) {
                System.out.println(": " +
                        reservationDAO.getReservationByID(reservations.get(2).getReservationKey()) +
                        reservationDAO.getReservationsBySitDownRestaurantID(restaurantList.get(2).getRestaurantKey()) +
                        reservationDAO.getReservationsByUserName(userList.get(2).getUserName()));
            }
        }
        //Credit Cards
        for (int i = 1; i < 6; i++) {
            CreditCard cc = new CreditCard(i,
                    randomString(i * 3),
                    toLength("8", 40),
                    new Date(),
                    userList.get(i - 1).getUserName()
            );
            creditcardDAO.create(cc);
            System.out.println("Creating CreditCard: " + cc);
            creditCards.add(cc);
            if (i == 3) {
                System.out.println("Should return the third credit card, then update the expiration and print again " +
                        creditcardDAO.getCreditCardByCardNumber(creditCards.get(2).getCreditCardKey()) +
                        creditcardDAO.getCreditCardsByUserName(creditCards.get(2).getUserName()) +
                        creditcardDAO.updateExpiration(creditCards.get(2), new Date()));

            }
        }

        for (CreditCard cc : creditCards) {
            System.out.println("Wiping CC: " +
                    cc + "-----" +
                    creditcardDAO.delete(cc));
        }

        for (Reservation res : reservations) {
            System.out.println("Wiping RES: " +
                    res + "-----" +
                    reservationDAO.delete(res));
        }

        for (Recommendation rec : recommendationList) {
            System.out.println("Wiping REC: " +
                    rec + "-----" +
                    recommendationDAO.delete(rec));
        }

        for (Review review : reviewList) {
            System.out.println("Wiping REV: " +
                    review + "-----" +
                    reviewDAO.delete(review));
        }

        for (FoodCartRestaurant FCR : foodCartRestaurants) {
            System.out.println("Wiping FCR: " +
                    FCR + "-----" +
                    foodCartRestaurantDAO.delete(FCR));
        }


        for (TakeOutRestaurant tor : takeOutRestaurants) {
            System.out.println("Wiping TOR: " +
                    tor + "-----" +
                    TakeOutRestaurantDAO.delete(tor));
        }


        for (SitDownRestaurant sdr : sitDownRestaurantList) {
            System.out.println("Wiping SDR: " +
                    sdr + "-----" +
                    sitDownRestaurantDAO.delete(sdr));
        }


        for (Restaurant rest : restaurantList) {
            System.out.println("Wiping REST: " +
                    rest + "-----" +
                    restaurantDAO.delete(rest));
        }

        //delete companies
        for (Company comp : companyList) {
            System.out.println("Wiping COMP: " +
                    comp + "-----" +
                    companyDAO.delete(comp));
        }

        //delete Users
        for (User user : userList) {
            System.out.println("Wiping USER: " +
                    user + "-----" +
                    userDao.delete(user));
        }

    }
}