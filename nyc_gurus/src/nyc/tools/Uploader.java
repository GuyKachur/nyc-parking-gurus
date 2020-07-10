package nyc.tools;

import nyc.dal.AirBNBDAO;
import nyc.dal.BusinessDAO;
import nyc.dal.GardenDAO;
import nyc.model.AirBNB;
import nyc.model.Business;
import nyc.model.Garden;

import java.io.*;
import java.security.cert.CertificateRevokedException;
import java.sql.SQLException;

public class Uploader {
    //id,name,host_id,host_name,neighbourhood_group,neighbourhood,latitude,longitude,room_type,price,minimum_nights,number_of_reviews,last_review,reviews_per_month,calculated_host_listings_count,availability_365

    public String standardizeBorough(String incomingBorough) {
        switch (incomingBorough.toLowerCase()) {
            case "1":
            case "m":
            case "manhattan":
                return "Manhattan";
            case "2":
            case "x":
            case "bronx":
                return "Bronx";
            case "3":
            case "b":
            case "brooklyn":
                return "Brooklyn";
            case "4":
            case "q":
            case "queens":
                return "Queens";

            case "5":
            case "s":
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
                return "Missing";
            default:
                return incomingBorough;
        }
    }

    public boolean uploadAirBnb() throws IOException {
        AirBNBDAO airBNBDAO = AirBNBDAO.getInstance();
        String path = new File("extras/RawData/parking_gurus_ab_nyc_2019.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        path = new File("parking_gurus_ab_nyc_2019-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));
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
        String path = new File("extras/RawData/business.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        path = new File("extras/RawData/business-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if(count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes
                Float lat=0.0F, lng = 0.0F;
                if (!data[25].isEmpty()) {
                    Float.parseFloat(data[25]);
                }
                if (!data[24].isEmpty()) {
                    Float.parseFloat(data[24]);
                }
                String phone = data[14].replaceAll("[^\\d]", "");
                String zip = data[13].replaceAll("\\.[0-9]+", "");

                Business businessToInsert = new Business(
                        0L,
                        lat,
                        lng,
                        data[5],
                        data[6].replaceAll("\"", ""),
                        data[8],
                        data[9],
                        data[11],
                        data[12],
                        zip.isEmpty()? 0 : Integer.parseInt(zip),
                        phone.isEmpty()? 0L : Long.parseLong(phone),
                        standardizeBorough(data[15])
                );
//                System.out.println("Parsed into Business: " + businessToInsert);
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

    public boolean uploadGarden() throws IOException {
        GardenDAO gardenDAO = GardenDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\gardens.csv").getAbsolutePath()));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\gardens-errors.csv")));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if(count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes

                String zip = data[13].replaceAll("\\.[0-9]+", "");


                float lat = data[10].isEmpty()? 0F : Float.parseFloat(data[10]);
                float lng = data[11].isEmpty()? 0F : Float.parseFloat(data[11]);
                String name = data[4];
                String address = data[4];
                String borough = standardizeBorough(data[1]);
                String neighborhood = data[8];

                Garden gardenToInsert = new Garden(0,lat, lng, name, zip.isEmpty()? 0 : Integer.parseInt(zip), address, neighborhood, borough);
//                System.out.println("Parsed into Garden: " + gardenToInsert);
                try {
                    gardenDAO.create(gardenToInsert);
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

    public boolean uploadCollision() throws IOException {
        CollisionDAO collisionDAO = CollisionDAO.getInstance();
        String path = new File("extras/RawData/parking_gurus_collisions.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File(path)));
        path = new File("extras/RawData/collisions-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null && count < 50) {
            count++;
            try {
                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // doesnt split , inside quotes
                Float lat=0.0F, lng = 0.0F;
                if (!data[5].isEmpty()) {
                    lat = Float.parseFloat(data[5]);
                }
                if (!data[6].isEmpty()) {
                    lng = Float.parseFloat(data[6]);
                }

                Collision collisionToInsert = new Collision(
                        0L,
                        lat,
                        lng,
                        new SimpleDateFormat("MM/DD/YYYY").parse(data[1]),
                        standardizeBorough(data[3]),
                        !data[4].isEmpty() ? Integer.parseInt(data[4]) : -1,
                        !data[11].isEmpty() ? Integer.parseInt(data[11]) : 0,
                        !data[12].isEmpty() ? Integer.parseInt(data[12]) : 0,
                        !data[13].isEmpty() ? Integer.parseInt(data[13]) : 0,
                        !data[14].isEmpty() ? Integer.parseInt(data[14]) : 0,
                        !data[15].isEmpty() ? Integer.parseInt(data[15]) : 0,
                        !data[16].isEmpty() ? Integer.parseInt(data[16]) : 0,
                        !data[17].isEmpty() ? Integer.parseInt(data[17]) : 0,
                        !data[18].isEmpty() ? Integer.parseInt(data[18]) : 0

                );
                System.out.println("Parsed into Collision: " + collisionToInsert);
                try {
                    collisionDAO.create(collisionToInsert);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    System.err.println("Couldn't write row to database, skipping:  " + row);
                    csvWriter.write(row);
                    csvWriter.newLine();
                }

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ParseException e) {
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

    public boolean uploadEmergencyResponse() throws IOException {
        EmergencyResponseDAO emergencyResponseDAO = EmergencyResponseDAO.getInstance();
        String path = new File("extras/RawData/emergency_response.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File(path)));
        path = new File("extras/RawData/emergency_response-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null && count < 50) {
            count++;
            try {
                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // doesnt split , inside quotes
                Float lat=0.0F, lng = 0.0F;
                if (!data[5].isEmpty()) {
                    lat = Float.parseFloat(data[5]);
                }
                if (!data[6].isEmpty()) {
                    lng = Float.parseFloat(data[6]);
                }

                EmergencyResponse emergencyResponseToInsert = new EmergencyResponse(
                        0L,
                        lat,
                        lng,
                        data[0],
                        data[1],
                        standardizeBorough(data[2]),
                        new SimpleDateFormat("MM/DD/YYYY hh:mm:ss").parse(data[3])

                );
                System.out.println("Parsed into EmergencyResponse: " + emergencyResponseToInsert);
                try {
                    emergencyResponseDAO.create(emergencyResponseToInsert);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    System.err.println("Couldn't write row to database, skipping:  " + row);
                    csvWriter.write(row);
                    csvWriter.newLine();
                }

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ParseException e) {
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

    public boolean uploadGraffiti() throws IOException {
        GraffitiDAO graffitiDAO = GraffitiDAO.getInstance();
        String path = new File("extras/RawData/graffiti.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File(path)));
        path = new File("extras/RawData/graffiti-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null && count < 50) {
            count++;
            try {
                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // doesnt split , inside quotes
                Float lat=0.0F, lng = 0.0F;
                if (!data[10].isEmpty()) {
                    lat = Float.parseFloat(data[10]);
                }
                if (!data[11].isEmpty()) {
                    lng = Float.parseFloat(data[11]);
                }

                Graffiti graffitiToInsert = new Graffiti(
                        0L,
                        lat,
                        lng,
                        data[0],
                        standardizeBorough(data[1]),
                        !data[14].isEmpty() ? Integer.parseInt(data[14]) : -1,
                        new SimpleDateFormat("MM/DD/YYYY").parse(data[6])

                );
                System.out.println("Parsed into Graffiti: " + graffitiToInsert);
                try {
                    graffitiDAO.create(graffitiToInsert);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    System.err.println("Couldn't write row to database, skipping:  " + row);
                    csvWriter.write(row);
                    csvWriter.newLine();
                }

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ParseException e) {
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
