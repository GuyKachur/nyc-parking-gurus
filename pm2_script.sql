CREATE DATABASE IF NOT EXISTS 5200_project;
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema 5200_project`
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema 5200_project`
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `5200_project` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema restdb
-- -----------------------------------------------------
USE `5200_project` ;

-- -----------------------------------------------------
-- Table `5200_project`.`Violation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Violation` (
  `idViolations` INT NOT NULL,
  `latitude` VARCHAR(45) NULL,
  `longitute` VARCHAR(45) NULL,
  PRIMARY KEY (`idViolations`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Parking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Parking` (
  `idParkingCameraViolations` INT NOT NULL,
  `Plate` VARCHAR(7) NULL,
  `State` VARCHAR(2) NULL,
  `LicenseType` VARCHAR(3) NULL,
  `Summons Number` VARCHAR(45) NULL,
  `Issue Date` VARCHAR(45) NULL,
  `Violation Time` VARCHAR(45) NULL,
  `Violation` VARCHAR(45) NULL,
  `Judgment Entry Date` VARCHAR(45) NULL,
  `Fine Amount` VARCHAR(45) NULL,
  `Penalty Amount` VARCHAR(45) NULL,
  `Interest Amount` VARCHAR(45) NULL,
  `Eduction Amount` VARCHAR(45) NULL,
  `Payment Amount` VARCHAR(45) NULL,
  `Amount Due` VARCHAR(45) NULL,
  `Precinct` VARCHAR(45) NULL,
  `County` VARCHAR(45) NULL,
  `Issuing Agency` VARCHAR(45) NULL,
  `Violation Status` VARCHAR(45) NULL,
  `Summons Image` VARCHAR(45) NULL,
  `Violations_idViolations` INT NOT NULL,
  PRIMARY KEY (`idParkingCameraViolations`, `Violations_idViolations`),
  INDEX `fk_ParkingCameraViolations_Violations1_idx` (`Violations_idViolations` ASC) VISIBLE,
  CONSTRAINT `fk_ParkingCameraViolations_Violations1`
    FOREIGN KEY (`Violations_idViolations`)
    REFERENCES `5200_project`.`Violation` (`idViolations`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`User` (
  `UserID` INT NOT NULL,
  `Username` VARCHAR(45) NULL,
  `passwordhash` VARCHAR(45) NULL,
  PRIMARY KEY (`UserID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Trip` (
  `tripID` INT NOT NULL,
  `start_date` DATE NULL,
  `end_date` DATE NULL,
  `User_UserID` INT NOT NULL,
  PRIMARY KEY (`tripID`),
  INDEX `fk_Trips_User_idx` (`User_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_Trips_User`
    FOREIGN KEY (`User_UserID`)
    REFERENCES `5200_project`.`User` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Business`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Business` (
  `BuisinessID` INT NOT NULL,
  `DCA Liscence` VARCHAR(45) NULL,
  `Liscence Type` VARCHAR(45) NULL,
  `Liscence Expiration Date` VARCHAR(45) NULL,
  `Licencse Status` VARCHAR(45) NULL,
  `License Creation Date` VARCHAR(45) NULL,
  `Industry` VARCHAR(45) NULL,
  `Buisness Name` VARCHAR(45) NULL,
  `Address building` VARCHAR(45) NULL,
  `Address St Name` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Phone Number` INT NULL,
  `Address Borough` VARCHAR(45) NULL,
  `Borough ID` INT NULL,
  `Community Board` VARCHAR(45) NULL,
  `Council District` VARCHAR(45) NULL,
  `BIN` INT NULL,
  `BBL` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  `Census Tract` VARCHAR(45) NULL,
  `Long` DECIMAL(20,10) NULL,
  `latitude` DECIMAL(20,10) NULL,
  `location` VARCHAR(45) NULL,
  PRIMARY KEY (`BuisinessID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Destination`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Destination` (
  `Trips_tripID` INT NOT NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  `DestinationID` INT NOT NULL,
  `Business_BuisinessID` INT NOT NULL,
  INDEX `fk_Destination_Trips1_idx` (`Trips_tripID` ASC) VISIBLE,
  PRIMARY KEY (`DestinationID`, `Business_BuisinessID`),
  INDEX `fk_Destination_Business1_idx` (`Business_BuisinessID` ASC) VISIBLE,
  CONSTRAINT `fk_Destination_Trips1`
    FOREIGN KEY (`Trips_tripID`)
    REFERENCES `5200_project`.`Trip` (`tripID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Destination_Business1`
    FOREIGN KEY (`Business_BuisinessID`)
    REFERENCES `5200_project`.`Business` (`BuisinessID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Event` (
  `idEvent` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Start Date` DATE NULL,
  `End Date` DATE NULL,
  `Agency` VARCHAR(45) NULL,
  `Type` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  `Steet Side` VARCHAR(45) NULL,
  `Street Closure Type` VARCHAR(45) NULL,
  `Community Board` VARCHAR(45) NULL,
  `Police precint` VARCHAR(45) NULL,
  `Destination_DestinationID` INT NOT NULL,
  PRIMARY KEY (`idEvent`, `Destination_DestinationID`),
  INDEX `fk_Event_Destination1_idx` (`Destination_DestinationID` ASC) VISIBLE,
  CONSTRAINT `fk_Event_Destination1`
    FOREIGN KEY (`Destination_DestinationID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`AirBNB`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`AirBNB` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `host_id` INT NOT NULL,
  `room_type` VARCHAR(45) NULL,
  `borough` VARCHAR(45) NULL,
  `neighborhood` VARCHAR(45) NULL,
  `reviews` DECIMAL(5,2) NULL,
  `overall_satisfaction` VARCHAR(45) NULL,
  `accommodates` VARCHAR(45) NULL,
  `bedrooms` INT NULL,
  `price` INT NULL,
  `minstay` INT NULL,
  `latitude` DECIMAL(20,10) NULL,
  `longitude` DECIMAL(20,10) NULL,
  `Destination_DestinationID` INT NOT NULL,
  `last_modified` DATETIME NULL,
  PRIMARY KEY (`room_id`, `Destination_DestinationID`),
  INDEX `fk_AirBNB_Destination1_idx` (`Destination_DestinationID` ASC) VISIBLE,
  UNIQUE INDEX `host_id_UNIQUE` (`host_id` ASC) VISIBLE,
  CONSTRAINT `fk_AirBNB_Destination1`
    FOREIGN KEY (`Destination_DestinationID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`BoroughBoundaries-lookup?`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`BoroughBoundaries-lookup?` (
  `idBoroughBoundaries` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Shape_length` VARCHAR(45) NULL,
  `Code` VARCHAR(45) NULL,
  `geometry` VARCHAR(45) NULL,
  `area` VARCHAR(45) NULL,
  PRIMARY KEY (`idBoroughBoundaries`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Park`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Park` (
  `Destination_DestinationID` INT NOT NULL,
  `park_name` VARCHAR(45) NULL,
  `the_geom` VARCHAR(1000) NULL,
  `feat_code` INT NULL,
  `source_id` INT NULL,
  `sub_code` VARCHAR(45) NULL,
  `landuse` VARCHAR(45) NULL,
  `park_num` VARCHAR(45) NULL,
  `status` VARCHAR(45) NULL,
  `system` VARCHAR(45) NULL,
  `shape_leng` VARCHAR(45) NULL,
  `shape_area` VARCHAR(45) NULL,
  PRIMARY KEY (`Destination_DestinationID`),
  CONSTRAINT `fk_Parks_Destination1`
    FOREIGN KEY (`Destination_DestinationID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `5200_project`.`Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Location` (
  `LocationID` INT NOT NULL,
  `Latitude` VARCHAR(45) NULL,
  `Longitude` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `Zip` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Locationcol` VARCHAR(45) NULL,
  `Bourough` VARCHAR(45) NULL,
  `Locationcol1` VARCHAR(45) NULL,
  PRIMARY KEY (`LocationID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`booking.com`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`booking.com` (
  `bookingID` INT NOT NULL,
  `city` VARCHAR(45) NULL,
  `distance` VARCHAR(45) NULL,
  `hotel_names` VARCHAR(45) NULL,
  `location` VARCHAR(45) NULL,
  `rate` VARCHAR(45) NULL,
  `review#` VARCHAR(45) NULL,
  PRIMARY KEY (`bookingID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Collision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Collision` (
  `CollisionID` INT NOT NULL,
  `Date` DATETIME NULL,
  `Time` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  `StreetName` VARCHAR(45) NULL,
  `PeopleInjured` VARCHAR(45) NULL,
  `PeopleKilled` VARCHAR(45) NULL,
  `Violations_idViolations` INT NOT NULL,
  PRIMARY KEY (`CollisionID`, `Violations_idViolations`),
  INDEX `fk_Collision_Violations1_idx` (`Violations_idViolations` ASC) VISIBLE,
  CONSTRAINT `fk_Collision_Violations1`
    FOREIGN KEY (`Violations_idViolations`)
    REFERENCES `5200_project`.`Violation` (`idViolations`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Request?`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Request?` (
  `idRequest?` INT NOT NULL,
  `requesturl` VARCHAR(45) NULL,
  `request user` VARCHAR(45) NULL,
  PRIMARY KEY (`idRequest?`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Vocational`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Vocational` (
  `Destination_DestinationID` INT NOT NULL,
  `organization_name` VARCHAR(45) NULL,
  `address1` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Borough` VARCHAR(45) NULL,
  `Neighborhood` VARCHAR(45) BINARY NULL,
  `Phone1` INT NULL,
  `Fax` INT NULL,
  `Website` VARCHAR(45) NULL,
  `Job_placement_services` VARCHAR(45) NULL,
  `Financial_aid_status` VARCHAR(45) NULL,
  `contact_first_name` VARCHAR(45) NULL,
  `contact_last_name` VARCHAR(45) NULL,
  `course_name` VARCHAR(45) NULL,
  `course_description` VARCHAR(45) NULL,
  `key_words` VARCHAR(45) NULL,
  `cost_total` INT NULL,
  `Cost_includes` VARCHAR(255) NULL,
  `Cost_does_not_include` VARCHAR(255) NULL,
  `Duration` INT NULL,
  `Duration_unit` VARCHAR(45) NULL,
  `Num_hours` VARCHAR(45) NULL,
  `Prerequisites` VARCHAR(45) NULL,
  `Max_class_size` INT NULL,
  `Years_course_offered` VARCHAR(45) NULL,
  `Instructor_credentials` VARCHAR(45) NULL,
  `Delivery_method` VARCHAR(45) NULL,
  `Schedule` VARCHAR(100) NULL,
  `Is_HRA` VARCHAR(10) NULL,
  `Is_SBS` VARCHAR(10) NULL,
  PRIMARY KEY (`Destination_DestinationID`),
  CONSTRAINT `fk_Vocational_Destination1`
    FOREIGN KEY (`Destination_DestinationID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Point_of_interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Point_of_interest` (
  `FID` VARCHAR(45) NULL,
  `SafType` VARCHAR(45) NULL,
  `Destination_DestinationID` INT NOT NULL,
  `ComplexID` INT NULL,
  `segmentID` INT NULL,
  `the_geom` VARCHAR(1000) NULL,
  `SOS` INT NULL,
  `Faci_dom` INT NULL,
  `bin` INT NULL,
  `borough` INT NULL,
  `created` DATETIME NULL,
  `modified` DATETIME NULL,
  `source` VARCHAR(10) NULL,
  `B7SC` INT NULL,
  `Pri_add` INT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`Destination_DestinationID`),
  INDEX `fk_points_of_interest_Destination1_idx` (`Destination_DestinationID` ASC) VISIBLE,
  CONSTRAINT `fk_points_of_interest_Destination1`
    FOREIGN KEY (`Destination_DestinationID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`WholesaleMakets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`WholesaleMakets` (
  `Destination_DestinationID` INT NOT NULL,
  `Destination_Business_BuisinessID` INT NOT NULL,
  `CREATED` DATETIME NULL,
  `BIC NUMBER` TEXT(30) NULL,
  `ACCOUNT NAME` VARCHAR(45) NULL,
  `TRADE NAME` VARCHAR(45) NULL,
  `ADDRESS` VARCHAR(45) NOT NULL,
  `CITY` VARCHAR(45) NULL,
  `STATE` VARCHAR(45) NULL,
  `POSTCODE` INT NULL,
  `PHONE` VARCHAR(11) NULL,
  `EMAIL` VARCHAR(45) NULL,
  `MARKET` VARCHAR(45) NULL,
  `APPLICATION TYPE` VARCHAR(45) NULL,
  `DISPOSITION DATE` VARCHAR(45) NULL,
  `EFFECTIVE DATE` VARCHAR(45) NULL,
  `EXPIRATION DATE` VARCHAR(45) NULL,
  `RENEWAL` VARCHAR(45) NULL,
  `EXPORT DATE` DATETIME NULL,
  `LATITUDE` VARCHAR(45) NULL,
  `LONGITUDE` VARCHAR(45) NULL,
  `COMMUNITY BOARD` VARCHAR(45) NULL,
  `COUNCIL DISTRICT` VARCHAR(45) NULL,
  `CENSUS TRACT` VARCHAR(45) NULL,
  `BIN` VARCHAR(45) NULL,
  `BBL` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  `BORO` VARCHAR(45) NULL,
  PRIMARY KEY (`Destination_DestinationID`, `Destination_Business_BuisinessID`),
  CONSTRAINT `fk_WholesaleMakets_Destination1`
    FOREIGN KEY (`Destination_DestinationID` , `Destination_Business_BuisinessID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID` , `Business_BuisinessID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`CommunityGarden`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`CommunityGarden` (
  `Destination_DestinationID` INT NOT NULL,
  `Destination_Business_BuisinessID` INT NOT NULL,
  `Type` TEXT(30) NULL,
  `Name` TEXT(30) BINARY NULL,
  `Location` TEXT(30) NULL,
  `Contact Information` TEXT(30) NULL,
  `Postcode` INT NULL,
  `Borough` TEXT(30) NULL,
  `Latitude` DECIMAL(30,20) NULL,
  `Longitude` DECIMAL(30,20) NULL,
  `Community Board` INT NULL,
  `Council District` INT NULL,
  `Census Tract` INT NULL,
  `BIN` INT NULL,
  `BBL` INT NULL,
  `NTA` TEXT(30) NULL,
  PRIMARY KEY (`Destination_DestinationID`, `Destination_Business_BuisinessID`),
  CONSTRAINT `fk_CommunityGarden_Destination1`
    FOREIGN KEY (`Destination_DestinationID` , `Destination_Business_BuisinessID`)
    REFERENCES `5200_project`.`Destination` (`DestinationID` , `Business_BuisinessID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`EmergencyResponse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`EmergencyResponse` (
  `Violation_idViolations` INT NOT NULL,
  `Incident Type` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Creation Date` DATETIME NULL,
  `Closed Date` DATETIME NULL,
  `Latitude` DECIMAL(30,20) NULL,
  `Longitude` DECIMAL(30,20) NULL,
  PRIMARY KEY (`Violation_idViolations`),
  CONSTRAINT `fk_EmergencyResponse_Violation1`
    FOREIGN KEY (`Violation_idViolations`)
    REFERENCES `5200_project`.`Violation` (`idViolations`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `5200_project`.`Graffiti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `5200_project`.`Graffiti` (
  `Violation_idViolations` INT NOT NULL,
  `Incident_address` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Community_Board` VARCHAR(45) NULL,
  `Police_Precinct` VARCHAR(45) NULL,
  `City_Council_District` VARCHAR(45) NULL,
  `BBL` VARCHAR(45) NULL,
  `Created_Date` DATETIME NULL,
  `Status` VARCHAR(45) NULL,
  `Resolution_Action` VARCHAR(45) NULL,
  `Closed_Date` DATETIME NULL,
  `X_Coordinate` VARCHAR(45) NULL,
  `Y_Coordinate` VARCHAR(45) NULL,
  `Latitude` VARCHAR(45) NULL,
  `Longitiude` VARCHAR(45) NULL,
  `Zip_Code` VARCHAR(5) NULL,
  `Census_Tract` VARCHAR(45) NULL,
  `BIN` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  `Location` VARCHAR(50) NULL,
  PRIMARY KEY (`Violation_idViolations`),
  CONSTRAINT `fk_Graffiti_Violation1`
    FOREIGN KEY (`Violation_idViolations`)
    REFERENCES `5200_project`.`Violation` (`idViolations`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

