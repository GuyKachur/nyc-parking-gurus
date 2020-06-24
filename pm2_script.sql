CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- Drop everything
DROP TABLE IF EXISTS `mydb`.`Graffiti` ;
DROP TABLE IF EXISTS `mydb`.`EmergencyResponse` ;
DROP TABLE IF EXISTS `mydb`.`CommunityGarden` ;
DROP TABLE IF EXISTS `mydb`.`AirBNB` ;
DROP TABLE IF EXISTS `mydb`.`Park` ;
DROP TABLE IF EXISTS `mydb`.`Business` ;
DROP TABLE IF EXISTS `mydb`.`Collision` ;
DROP TABLE IF EXISTS `mydb`.`WholesaleMarket` ;
DROP TABLE IF EXISTS `mydb`.`Point_of_Interest` ;
DROP TABLE IF EXISTS `mydb`.`Destination` ;
DROP TABLE IF EXISTS `mydb`.`Trip` ;
DROP TABLE IF EXISTS `mydb`.`Violation` ;
DROP TABLE IF EXISTS `mydb`.`User` ;

-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User` ;

CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `UserPK` INT NOT NULL,
  `Username` VARCHAR(45) NULL,
  `passwordhash` VARCHAR(45) NULL,
  PRIMARY KEY (`UserPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Trip`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Trip` ;

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
DROP TABLE IF EXISTS `mydb`.`Destination` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Destination` (
  `DestinationPK` INT NOT NULL AUTO_INCREMENT,
  `Trips_tripID` INT NOT NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  INDEX `fk_Destination_Trips1_idx` (`Trips_tripID` ASC) VISIBLE,
  PRIMARY KEY (`DestinationPK`),
  CONSTRAINT `fk_Destination_Trips1`
    FOREIGN KEY (`Trips_tripID`)
    REFERENCES `mydb`.`Trip` (`TripPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`AirBNB`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`AirBNB` ;

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
DROP TABLE IF EXISTS `mydb`.`Park` ;

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
DROP TABLE IF EXISTS `mydb`.`Business` ;

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
-- Table `mydb`.`Violation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Violation` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Violation` (
  `ViolationPK` INT NOT NULL AUTO_INCREMENT,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  PRIMARY KEY (`ViolationPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Collision`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Collision` ;

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
-- Table `mydb`.`Point_of_Interest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Point_of_Interest` ;

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


-- -----------------------------------------------------
-- Table `mydb`.`EmergencyResponse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`EmergencyResponse` ;

CREATE TABLE IF NOT EXISTS `mydb`.`EmergencyResponse` (
  `EmergencyResponsePK` INT NOT NULL,
  `IncidentType` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Creation Date` DATE NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  INDEX `ViolationKey3_idx` (`EmergencyResponsePK` ASC) VISIBLE,
  PRIMARY KEY (`EmergencyResponsePK`),
  CONSTRAINT `ViolationKey3`
    FOREIGN KEY (`EmergencyResponsePK`)
    REFERENCES `mydb`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Graffiti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Graffiti` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Graffiti` (
  `GraffitiPK` INT NOT NULL,
  `IncidentAdress` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `CommunityBoard` VARCHAR(45) NULL,
  `PolicePrecinct` VARCHAR(45) NULL,
  `CityCouncilDistrict` VARCHAR(45) NULL,
  `BBL` VARCHAR(45) NULL,
  `CreatedDate` DATE NULL,
  `Status` VARCHAR(45) NULL,
  `ResolutionAction` VARCHAR(45) NULL,
  `ClosedDate` DATE NULL,
  `XCoordinate` VARCHAR(45) NULL,
  `YCoordinate` VARCHAR(45) NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  `ZipCode` VARCHAR(45) NULL,
  `CensusTract` VARCHAR(45) NULL,
  `BIN` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  INDEX `ViolationKey2_idx` (`GraffitiPK` ASC) VISIBLE,
  PRIMARY KEY (`GraffitiPK`),
  CONSTRAINT `ViolationKey2`
    FOREIGN KEY (`GraffitiPK`)
    REFERENCES `mydb`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`WholesaleMarket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`WholesaleMarket` ;

CREATE TABLE IF NOT EXISTS `mydb`.`WholesaleMarket` (
  `WholesaleMarketPK` INT NOT NULL,
  `Created` DATETIME NULL,
  `BICNumber` VARCHAR(45) NULL,
  `AccountName` VARCHAR(45) NULL,
  `TradeName` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Postcode` VARCHAR(45) NULL,
  `Phone` VARCHAR(45) NULL,
  `Email` VARCHAR(45) NULL,
  `Market` VARCHAR(45) NULL,
  `ApplicationType` VARCHAR(45) NULL,
  `DispositionDate` VARCHAR(45) NULL,
  `EffectiveDate` VARCHAR(45) NULL,
  `ExpirationDate` VARCHAR(45) NULL,
  `Renewal` VARCHAR(45) NULL,
  `ExportDate` DATE NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  `ComunityBoard` VARCHAR(45) NULL,
  `CouncilDistrict` VARCHAR(45) NULL,
  `CensusTract` VARCHAR(45) NULL,
  `BIN` VARCHAR(45) NULL,
  `BBL` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  `BORO` VARCHAR(45) NULL,
  INDEX `DestinationKey6_idx` (`WholesaleMarketPK` ASC) VISIBLE,
  PRIMARY KEY (`WholesaleMarketPK`),
  CONSTRAINT `DestinationKey6`
    FOREIGN KEY (`WholesaleMarketPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CommunityGarden`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`CommunityGarden` ;

CREATE TABLE IF NOT EXISTS `mydb`.`CommunityGarden` (
  `CommunityGardenPK` INT NOT NULL,
  `Type` VARCHAR(45) NULL,
  `Name` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  `ContactInformation` VARCHAR(45) NULL,
  `Postcode` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  `CommunityBoard` VARCHAR(45) NULL,
  `CouncilDistrict` VARCHAR(45) NULL,
  `CensusTract` VARCHAR(45) NULL,
  `BIN` VARCHAR(45) NULL,
  `BBL` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  INDEX `DestinationKey5_idx` (`CommunityGardenPK` ASC) VISIBLE,
  PRIMARY KEY (`CommunityGardenPK`),
  CONSTRAINT `DestinationKey5`
    FOREIGN KEY (`CommunityGardenPK`)
    REFERENCES `mydb`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
