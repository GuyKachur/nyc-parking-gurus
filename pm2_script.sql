CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- Drop everything
DROP TABLE IF EXISTS `mydb`.`Parking` ;
DROP TABLE IF EXISTS `mydb`.`Event` ;
DROP TABLE IF EXISTS `mydb`.`AirBNB` ;
DROP TABLE IF EXISTS `mydb`.`Park` ;
DROP TABLE IF EXISTS `mydb`.`Business` ;
DROP TABLE IF EXISTS `mydb`.`Collision` ;
DROP TABLE IF EXISTS `mydb`.`Vocational` ;
DROP TABLE IF EXISTS `mydb`.`Point_of_Interest` ;
DROP TABLE IF EXISTS `mydb`.`Destination` ;
DROP TABLE IF EXISTS `mydb`.`Trip` ;
DROP TABLE IF EXISTS `mydb`.`Violation` ;
DROP TABLE IF EXISTS `mydb`.`User` ;

-- -----------------------------------------------------
-- Table `mydb`.`Violation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Violation` (
  `ViolationPK` INT NOT NULL AUTO_INCREMENT,
  `latitude` VARCHAR(45) NULL,
  `longitute` VARCHAR(45) NULL,
  PRIMARY KEY (`ViolationPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Parking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Parking` (
  `ParkingPK` INT NOT NULL AUTO_INCREMENT,
  `idParkingCameraViolations` INT NULL,
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
  INDEX `ViolationKey2_idx` (`ParkingPK` ASC) VISIBLE,
  PRIMARY KEY (`ParkingPK`),
  CONSTRAINT `ViolationKey2`
    FOREIGN KEY (`ParkingPK`)
    REFERENCES `mydb`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `UserPK` INT NOT NULL,
  `Username` VARCHAR(45) NULL,
  `passwordhash` VARCHAR(45) NULL,
  PRIMARY KEY (`UserPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Trip` (
  `TripPK` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NULL,
  `end_date` DATE NULL,
  `User_UserID` INT NOT NULL,
  PRIMARY KEY (`TripPK`),
  INDEX `fk_Trips_User_idx` (`User_UserID` ASC) VISIBLE,
  CONSTRAINT `fk_Trips_User`
    FOREIGN KEY (`User_UserID`)
    REFERENCES `mydb`.`User` (`UserPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Destination`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Destination` (
  `DestinationPK` INT NOT NULL AUTO_INCREMENT,
  `Trips_tripID` INT NOT NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  INDEX `fk_Destination_Trips1_idx` (`Trips_tripID` ASC) VISIBLE,
  PRIMARY KEY (`DestinationPK`),
  CONSTRAINT `fk_Destination_Trips1`
    FOREIGN KEY (`Trips_tripID`)
    REFERENCES `mydb`.`Trip` (`TripPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Event` (
  `EventPK` INT NOT NULL AUTO_INCREMENT,
  `idEvent` INT NULL,
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
  INDEX `DestinationKey5_idx` (`EventPK` ASC) VISIBLE,
  PRIMARY KEY (`EventPK`),
  CONSTRAINT `DestinationKey5`
    FOREIGN KEY (`EventPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`AirBNB`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`AirBNB` (
  `AirBNBPK` INT NOT NULL AUTO_INCREMENT,
  `room_id` INT NULL,
  `host_id` INT NULL,
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
  `last_modified` DATETIME NULL,
  INDEX `DestinationKey4_idx` (`AirBNBPK` ASC) VISIBLE,
  PRIMARY KEY (`AirBNBPK`),
  CONSTRAINT `DestinationKey4`
    FOREIGN KEY (`AirBNBPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Park`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Park` (
  `ParkPK` INT NOT NULL AUTO_INCREMENT,
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
  INDEX `DestinationKey1_idx` (`ParkPK` ASC) VISIBLE,
  PRIMARY KEY (`ParkPK`),
  CONSTRAINT `DestinationKey1`
    FOREIGN KEY (`ParkPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Business`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Business` (
  `BusinessPK` INT NOT NULL AUTO_INCREMENT,
  `BuisinessID` INT NULL,
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
  INDEX `DestinationKey2_idx` (`BusinessPK` ASC) VISIBLE,
  PRIMARY KEY (`BusinessPK`),
  CONSTRAINT `DestinationKey2`
    FOREIGN KEY (`BusinessPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Collision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Collision` (
  `CollisionPK` INT NOT NULL AUTO_INCREMENT,
  `CollisionID` INT NULL,
  `Date` DATETIME NULL,
  `Time` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  `StreetName` VARCHAR(45) NULL,
  `PeopleInjured` VARCHAR(45) NULL,
  `PeopleKilled` VARCHAR(45) NULL,
  INDEX `ViolationKey1_idx` (`CollisionPK` ASC) VISIBLE,
  PRIMARY KEY (`CollisionPK`),
  CONSTRAINT `ViolationKey1`
    FOREIGN KEY (`CollisionPK`)
    REFERENCES `mydb`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Vocational`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Vocational` (
  `VocationalPK` INT NOT NULL AUTO_INCREMENT,
  `organization_name` VARCHAR(45) NULL,
  `address1` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Borough` VARCHAR(45) NULL,
  `Neighborhood` VARCHAR(45) NULL,
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
  INDEX `DestinationKey6_idx` (`VocationalPK` ASC) VISIBLE,
  PRIMARY KEY (`VocationalPK`),
  CONSTRAINT `DestinationKey6`
    FOREIGN KEY (`VocationalPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Point_of_Interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Point_of_Interest` (
  `Point_of_InterestPK` INT NOT NULL AUTO_INCREMENT,
  `FID` VARCHAR(45) NULL,
  `SafType` VARCHAR(45) NULL,
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
  INDEX `DestinationKey3_idx` (`Point_of_InterestPK` ASC) VISIBLE,
  PRIMARY KEY (`Point_of_InterestPK`),
  CONSTRAINT `DestinationKey3`
    FOREIGN KEY (`Point_of_InterestPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
