package nyc.tools;

import nyc.dal.*;
import nyc.model.*;

import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_ab_nyc_2019.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_ab_nyc_2019-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));
        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
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
        System.out.println("Total AirBNBs: " + count);
        return true;
    }

    public boolean uploadBusiness() throws IOException {
        BusinessDAO businessDAO = BusinessDAO.getInstance();
        String path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\business.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\business-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes
                float lat = 0.0F, lng = 0.0F;
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
                        zip.isEmpty() ? 0 : Integer.parseInt(zip),
                        phone.isEmpty() ? 0L : Long.parseLong(phone),
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
        System.out.println("Total Businesses: " + count);

        return true;
    }

    public boolean uploadGarden() throws IOException {
        GardenDAO gardenDAO = GardenDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\gardens.csv").getAbsolutePath()));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\gardens-errors.csv")));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes

                String zip = data[13].replaceAll("\\.[0-9]+", "");


                float lat = data[10].isEmpty() ? 0F : Float.parseFloat(data[10]);
                float lng = data[11].isEmpty() ? 0F : Float.parseFloat(data[11]);
                String name = data[4];
                String address = data[4];
                String borough = standardizeBorough(data[1]);
                String neighborhood = data[8];

                Garden gardenToInsert = new Garden(0, lat, lng, name, zip.isEmpty() ? 0 : Integer.parseInt(zip), address, neighborhood, borough);
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
        System.out.println("Total Gardens: " + count);

        return true;
    }

    public boolean uploadCollision() throws IOException {
        CollisionDAO collisionDAO = CollisionDAO.getInstance();
        String path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_collisions.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File(path)));
        path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parking_gurus_collisions-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // doesnt split , inside quotes
                float lat = 0.0F, lng = 0.0F;
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
//                System.out.println("Parsed into Collision: " + collisionToInsert);
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
        System.out.println("Total Collisions: " + count);
        return true;
    }

    public boolean uploadEmergencyResponse() throws IOException {
        EmergencyResponseDAO emergencyResponseDAO = EmergencyResponseDAO.getInstance();
        String path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\emergency_response.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File(path)));
        path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\emergency_response-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // doesnt split , inside quotes
                float lat = 0.0F, lng = 0.0F;
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
//                System.out.println("Parsed into EmergencyResponse: " + emergencyResponseToInsert);
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
        System.out.println("Total EmergencyResponses: " + count);
        return true;
    }

    public boolean uploadGraffiti() throws IOException {
        GraffitiDAO graffitiDAO = GraffitiDAO.getInstance();
        String path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\graffiti.csv").getAbsolutePath();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File(path)));
        path = new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\graffiti-errors.csv").getAbsolutePath();
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File(path)));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // doesnt split , inside quotes
                float lat = 0.0F, lng = 0.0F;
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
                        !data[14].isEmpty() ? Integer.parseInt(data[14].replaceAll("\\.[0-9]+", "")) : -1,
                        new SimpleDateFormat("MM/DD/YYYY").parse(data[6])

                );
//                System.out.println("Parsed into Graffiti: " + graffitiToInsert);
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
        System.out.println("Total Graffiti: " + count);
        return true;
    }

    public boolean uploadMarket() throws IOException {
        MarketDAO marketDAO = MarketDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\markets.csv").getAbsolutePath()));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\markets-errors.csv")));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes


                float lat = data[17].isEmpty() ? 0.0F : Float.parseFloat(data[17]);
                float lng = data[18].isEmpty() ? 0.0F : Float.parseFloat(data[18]);
                String zip = data[7].replaceAll("\\-[0-9]+", "");

                String accountName = data[2];
                String tradeName = data[3];
                String address = data[4];
                String city = data[5];
                String state = data[6];

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date created = null;
                try {
                    created = df.parse(data[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String phone = data[14].replaceAll("[^\\d]", "");
                String email = data[9];
                String market = data[10];
                String marketType = data[11];
                Market marketToInsert = new Market(0,
                        lat, lng, created,
                        accountName.replaceAll("\"", ""), tradeName,
                        address.replaceAll("\"", ""), city, state, zip.isEmpty() ? 0 : Integer.parseInt(zip),
                        phone,
                        email, market, marketType);
//                System.out.println("Parsed into Market: " + marketToInsert);
                try {
                    marketDAO.create(marketToInsert);
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
        System.out.println("Total Markets: " + count);
        return true;
    }


    public float getLongAverage(String polygon) {
        if (polygon.isEmpty()) {
            return 0.0F;
        }
        Pattern pattern = Pattern.compile("-[0-9]+.[0-9]+");
        Matcher matcher = pattern.matcher(polygon);
        //average the polygon shape out
        float num = 0.0F;
        float total = 0.0F;
        if (matcher.find()) {
            num++;
            total += Float.parseFloat(matcher.group(0));

        } else {
            return num;
        }
        return total / num;
    }

    public float getLatAverage(String polygon) {
        if (polygon.isEmpty()) {
            return 0.0F;
        }
        Pattern pattern = Pattern.compile("[0-9]+.[0-9]+,");
        Matcher matcher = pattern.matcher(polygon);
        //average the polygon shape out
        float num = 0.0F;
        float total = 0.0F;
        if (matcher.find()) {
            num++;
            total += Float.parseFloat(matcher.group(0).replaceAll(",", ""));

        } else {
            return num;
        }
        return total / num;
    }


    public void uploadPark() throws IOException {
        ParkDAO parkDAO = ParkDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parks.csv").getAbsolutePath()));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\parks-errors.csv")));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes

                float lat = getLatAverage(data[1]);
                float lng = getLongAverage(data[1]);
                String name = data[0].replaceAll("\"", "");
                String landuse = data[5];
                Park parkToInsert = new Park(0, lat, lng, name, landuse);
//                System.out.println("Parsed into Park: " + parkToInsert);
                try {
                    parkDAO.create(parkToInsert);
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
        System.out.println("Total Parks: " + count);
    }


    public float getLongFromPoint(String point) {
        if (point.isEmpty()) {
            return 0.0F;
        }
        Pattern pattern = Pattern.compile("-[0-9]+.[0-9]+");
        Matcher matcher = pattern.matcher(point);
        if (matcher.find()) {
            return Float.parseFloat(matcher.group(0));

        }
        return 0.0F;
    }

    public float getLatFromPoint(String point) {
        if (point.isEmpty()) {
            return 0.0F;
        }
        Pattern pattern = Pattern.compile(" [0-9]+.[0-9]+");
        Matcher matcher = pattern.matcher(point);
        if (matcher.find()) {
            return Float.parseFloat(matcher.group(0).replaceAll(" ", ""));

        }
        return 0.0F;
    }
    public String standardizeType(String input) {
        switch (input.trim().toLowerCase()) {
            case "1":
                return "Residential";
            case "2":
                return "Education Facility";
            case "3":
                return "Cultural Facility";
            case "4":
                return "Recreational Facility";
            case "5":
                return "Social Services";
            case "6":
                return "Transportation Facility";
            case "7":
                return "Commercial";
            case "8":
                return "Government Facility";
            case "9":
                return "Religious Institution";
            case "10":
                return "Health Services";
            case "11":
                return "Public Safety";
            case "12":
                return "Water";
            case "13":
                return "Miscellaneous";
            default:
                return "Unknown";
        }

    }
    public void uploadPointOfInterest() throws IOException {
        PointOfInterestDAO pointOfInterestDAO = PointOfInterestDAO.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\POI.csv").getAbsolutePath()));
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(new File("D:\\GitHub\\nyc-parking-gurus\\nyc_gurus\\extras\\RawData\\POI-errors.csv")));

        String row = csvReader.readLine(); //discard the first row of headers
        int count = 0;
        while ((row = csvReader.readLine()) != null) {
            if (count % 1000 == 0) {
                System.out.println("Processed " + count);
            }
            count++;
            try {
//                System.out.println("Parsing row: " + row);
                String[] data = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //doesnt split , inside quotes


                float lat = getLatFromPoint(data[3]);
                float lng = getLongFromPoint(data[3]);
                String name = data[14];
                String borough = standardizeBorough(data[8]);

                int sideOfStreet = data[4].isEmpty() ? 0 : Integer.parseInt(data[4].replaceAll("\\.[0-9]+", ""));
                String POIType = standardizeType(data[10]);
                PointOfInterest pointOfInterest = new PointOfInterest(0,
                        lat, lng, name, borough, sideOfStreet, POIType);
//                System.out.println("Parsed into POI: " + pointOfInterest);
                try {
                    pointOfInterestDAO.create(pointOfInterest);
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
        System.out.println("Total PointsOfInterest: " + count);

    }

}
