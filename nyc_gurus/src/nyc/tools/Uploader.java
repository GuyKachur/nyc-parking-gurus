package nyc.tools;

import nyc.dal.AirBNBDAO;
import nyc.dal.BusinessDAO;
import nyc.model.AirBNB;
import nyc.model.Business;

import java.io.*;
import java.security.cert.CertificateRevokedException;
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
            case "":
                return "Unknown";
            default:
                return incomingBorough;
        }
    }

    public boolean uploadAirBnb() throws IOException {
        AirBNBDAO airBNBDAO = AirBNBDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_ab_nyc_2019.csv"));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_ab_nyc_2019-errors.csv")));
        String row = csvReader.readLine(); //discard the first row of headers
        boolean add = true;
        while ((row = csvReader.readLine()) != null) {
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes
                String name = data[1];

                String hostName = data[3];
                String borough = standardizeBorough(data[4]);
                String neighborhood = data[5];
                String roomType = data[8]; //TODO addRoomType enum?

                float lat = Float.parseFloat(data[6]);
                float lng = Float.parseFloat(data[7]);
                int host_id = Integer.parseInt(data[2]);
                int price = Integer.parseInt(data[9]);
                int reviews = Integer.parseInt(data[11]);
                float reviewsPerMonth;
                if (data[13].isEmpty()) {
                    reviewsPerMonth = 0.0F;
                } else {
                    reviewsPerMonth = Float.parseFloat(data[13]);
                }

                AirBNB ai = new AirBNB(0, lat, lng, borough, neighborhood, price, reviewsPerMonth, roomType, host_id, hostName, name, reviews);
                try {
                    airBNBDAO.create(ai);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    System.err.println("Couldn't write row to database, skipping:  " + row);
                    csvWriter.write(row);
                    csvWriter.newLine();
                }

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Couldn't write row to database, skipping:  " + row);
                csvWriter.write(row);
                csvWriter.newLine();
                e.printStackTrace();
            }
        }
        csvWriter.close();
        csvReader.close();
        return true;
    }

    public boolean uploadBusiness() throws IOException {
        BusinessDAO businessDAO = BusinessDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\business.csv"));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\business-errors.csv")));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null && count < 50) {
            count++;
            try {
                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes
                Float lat=0.0F, lng = 0.0F;
                if (!data[25].isEmpty()) {
                    Float.parseFloat(data[25]);
                }
                if (!data[24].isEmpty()) {
                    Float.parseFloat(data[24]);
                }

                Business businessToInsert = new Business(
                        0L,
                        lat,
                        lng,
                        data[5],
                        data[6],
                        data[8],
                        data[9],
                        data[11],
                        data[12],
                        Integer.parseInt(data[13].substring(0, data[13].indexOf("."))),
                        Long.parseLong(data[14].replaceAll("[^\\d]", "")),
                        standardizeBorough(data[15])
                );
                System.out.println("Parsed into Business: " + businessToInsert);
                try {
                    businessDAO.create(businessToInsert);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    System.err.println("Couldn't write row to database, skipping:  " + row);
                    csvWriter.write(row);
                    csvWriter.newLine();
                }

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error Parsing row:  " + row);
                csvWriter.write(row);
                csvWriter.newLine();
                e.printStackTrace();
            }
        }
        csvWriter.close();
        csvReader.close();
        return true;
    }
}
