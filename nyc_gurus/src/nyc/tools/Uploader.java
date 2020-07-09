package nyc.tools;

import nyc.dal.AirBNBDAO;
import nyc.model.AirBNB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Uploader {
    //id,name,host_id,host_name,neighbourhood_group,neighbourhood,latitude,longitude,room_type,price,minimum_nights,number_of_reviews,last_review,reviews_per_month,calculated_host_listings_count,availability_365

    public String standardizeBorough(String incomingBorough) {
        switch (incomingBorough.toLowerCase()) {
            case "1":
            case "manhattan":
                return "Manhattan";
            case "2":
            case "bronx":
                return "Bronx";
            case "3":
            case "brooklyn":
                return "Brooklyn";
            case "4":
            case "queens":
                return "Queens";

            case "5":
            case "staten island":
                return "Staten Island";

            case "6":
            case "nassau county":
                return "Nassau County";

            case "7":
            case "westchester":
                return "Westchester";

            case "8":
            case "new jersey":
                return "New Jersey";
            default:
                return "Unknown";
        }
    }

    public boolean uploadAirBnb() throws IOException {
        AirBNBDAO airBNBDAO = AirBNBDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_ab_nyc_2019.csv"));
        String row = csvReader.readLine(); //discard the first row of headers
        boolean add = true;
        while ((row = csvReader.readLine()) != null) {
            System.out.println("Parsing row: " + row);

            String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes
            String name = data[1];

            String hostName = data[3];
            String borough = standardizeBorough(data[4]);
            String neighborhood = data[5];
            String roomType = data[8]; //TODO addRoomType enum?


            float lat=0;
            try {
                lat = Float.parseFloat(data[6]);
            } catch (NumberFormatException e) {
                add = false;
                System.err.println("Error parsing lat: " + data[6]);
            }
            float lng= 0;
            try {
                 lng = Float.parseFloat(data[7]);
            } catch (NumberFormatException e) {
                add = false;
                System.err.println("Error parsing lng: " + data[7]);
            }

            int host_id = 0;
            try{
                host_id = Integer.parseInt(data[2]);
            } catch (NumberFormatException e) {
                add = false;
                System.err.println("Error parsing host_id: " + data[2]);
            }

            int price = Integer.parseInt(data[9]);
            int reviews = Integer.parseInt(data[11]);
            float reviewsPerMonth;
            if (data[13].isEmpty()) {
                reviewsPerMonth = 0.0F;
            } else {
                reviewsPerMonth = Float.parseFloat(data[13]);
            }
            if(add) {
                AirBNB ai = new AirBNB(0, lat, lng, borough, neighborhood, price, reviewsPerMonth, roomType, host_id, hostName, name, reviews);
                try {
                    airBNBDAO.create(ai);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        }
        return true;
    }
}
