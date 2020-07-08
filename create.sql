CREATE SCHEMA IF NOT EXISTS `nyc` DEFAULT CHARACTER SET utf8 ;
USE `nyc` ;

-- Drop everything
DROP TABLE IF EXISTS `nyc`.`Trip` ;
DROP TABLE IF EXISTS `nyc`.`Graffiti` ;
DROP TABLE IF EXISTS `nyc`.`EmergencyResponse` ;
DROP TABLE IF EXISTS `nyc`.`CommunityGarden` ;
DROP TABLE IF EXISTS `nyc`.`AirBNB` ;
DROP TABLE IF EXISTS `nyc`.`Park` ;
DROP TABLE IF EXISTS `nyc`.`Business` ;
DROP TABLE IF EXISTS `nyc`.`Collision` ;
DROP TABLE IF EXISTS `nyc`.`Market` ;
DROP TABLE IF EXISTS `nyc`.`Point_of_Interest` ;
DROP TABLE IF EXISTS `nyc`.`Destination` ;
DROP TABLE IF EXISTS `nyc`.`Violation` ;
DROP TABLE IF EXISTS `nyc`.`User` ;

-- -----------------------------------------------------
-- Table `nyc`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`User` (
  `UserPK` INT NOT NULL,
  `Username` VARCHAR(45) NULL,
  `passwordhash` VARCHAR(45) NULL,
  PRIMARY KEY (`UserPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Destination`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Destination` (
  `DestinationPK` INT NOT NULL AUTO_INCREMENT,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  PRIMARY KEY (`DestinationPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`AirBNB`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`AirBNB` (
  `AirBNBPK` INT NOT NULL AUTO_INCREMENT,
  'Name' TEXT,
  `Host_Name` TEXT,
  `host_id` INT NULL,
  `room_type` VARCHAR(45) NULL,
  `borough` VARCHAR(45) NULL,
  `neighborhood` VARCHAR(45) NULL,
  `reviews_per_month` DECIMAL(5,2) NULL,
  `price` INT NULL,
 INDEX `DestinationKey4_idx` (`AirBNBPK` ASC) VISIBLE,
  PRIMARY KEY (`AirBNBPK`),
  CONSTRAINT `DestinationKey4`
    FOREIGN KEY (`AirBNBPK`)
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Park`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Park` (
  `ParkPK` INT NOT NULL AUTO_INCREMENT,
  `park_name` VARCHAR(45) NULL,
  `landuse` VARCHAR(45) NULL,
  INDEX `DestinationKey1_idx` (`ParkPK` ASC) VISIBLE,
  PRIMARY KEY (`ParkPK`),
  CONSTRAINT `DestinationKey1`
    FOREIGN KEY (`ParkPK`)
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Business`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Business` (
  `BusinessPK` INT NOT NULL AUTO_INCREMENT,
  `Industry` VARCHAR(45) NULL,
  `Buisness Name` VARCHAR(45) NULL,
  `Address building` VARCHAR(45) NULL,
  `Address St Name` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Phone Number` INT NULL,
  `Address Borough` VARCHAR(45) NULL,
  INDEX `DestinationKey2_idx` (`BusinessPK` ASC) VISIBLE,
  PRIMARY KEY (`BusinessPK`),
  CONSTRAINT `DestinationKey2`
    FOREIGN KEY (`BusinessPK`)
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Violation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Violation` (
  `ViolationPK` INT NOT NULL AUTO_INCREMENT,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
  PRIMARY KEY (`ViolationPK`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Collision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Collision` (
  `CollisionPK` INT NOT NULL AUTO_INCREMENT,
  `CollisionID` INT NULL,
  `Date` DATETIME NULL,
  `Time` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Zip` INT NULL,
  `Latitude` DECIMAL(20,10) NULL,
  `Longitude` DECIMAL(20,10) NULL,
    `PERSONS INJURED`     int    null,
    `PERSONS KILLED`      int    null,
    `PEDESTRIANS INJURED` int    null,
    `PEDESTRIANS KILLED`  int    null,
    `CYCLISTS INJURED`    int    null,
    `CYCLISTS KILLED`     int    null,
    `MOTORISTS INJURED`   int    null,
    `MOTORISTS KILLED`    int    null,
  INDEX `ViolationKey1_idx` (`CollisionPK` ASC) VISIBLE,
  PRIMARY KEY (`CollisionPK`),
  CONSTRAINT `ViolationKey1`
    FOREIGN KEY (`CollisionPK`)
    REFERENCES `nyc`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Point_of_Interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Point_of_Interest` (
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
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`EmergencyResponse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`EmergencyResponse` (
  `EmergencyResponsePK` INT NOT NULL,
  `IncidentType` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  `Borough` VARCHAR(45) NULL,
  `Creation Date` DATE NULL,
  INDEX `ViolationKey3_idx` (`EmergencyResponsePK` ASC) VISIBLE,
  PRIMARY KEY (`EmergencyResponsePK`),
  CONSTRAINT `ViolationKey3`
    FOREIGN KEY (`EmergencyResponsePK`)
    REFERENCES `nyc`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`Graffiti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Graffiti` (
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
  `ZipCode` VARCHAR(45) NULL,
  `CensusTract` VARCHAR(45) NULL,
  `BIN` VARCHAR(45) NULL,
  `NTA` VARCHAR(45) NULL,
  `Location` VARCHAR(45) NULL,
  INDEX `ViolationKey2_idx` (`GraffitiPK` ASC) VISIBLE,
  PRIMARY KEY (`GraffitiPK`),
  CONSTRAINT `ViolationKey2`
    FOREIGN KEY (`GraffitiPK`)
    REFERENCES `nyc`.`Violation` (`ViolationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`WholesaleMarket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`WholesaleMarket` (
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
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nyc`.`CommunityGarden`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`CommunityGarden` (
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
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `nyc`.`Trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`Trip` (
  `TripPK` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NULL,
  `end_date` DATE NULL,
  `User_UserID` INT NOT NULL,
  `Destination_DestinationPK` INT NOT NULL,
  PRIMARY KEY (`TripPK`),
  INDEX `fk_Trips_User_idx` (`User_UserID` ASC) VISIBLE,
  INDEX `DestinationKey7_idx` (`Destination_DestinationPK` ASC) VISIBLE,
  CONSTRAINT `fk_Trips_User`
    FOREIGN KEY (`User_UserID`)
    REFERENCES `nyc`.`User` (`UserPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `DestinationKey7`
    FOREIGN KEY (`Destination_DestinationPK`)
    REFERENCES `nyc`.`Destination` (`DestinationPK`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `nyc`.`latlngDistance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nyc`.`latlngDistance` (
    sourceLAT      float not null,
    sourceLNG      float not null,
    destinationLAT float not null,
    destinationLNG float not null,
    Distance       float not null)
ENGINE = InnoDB;

drop function if exists parking_gurus.distanceLATLNG;
drop function if exists parking_gurus.getDistance;

CREATE FUNCTION distanceLATLNG(lat1 FLOAT, lon1 FLOAT,
                               lat2 FLOAT, lon2 FLOAT) RETURNS float
    NO SQL
    DETERMINISTIC
    COMMENT 'Returns the distance in degrees on the Earth between two known points of latitude and longitude. To get miles, multiply by 3961, and km by 6373'

BEGIN
    set @distance = DEGREES(ACOS(
                    COS(RADIANS(lat1)) *
                    COS(RADIANS(lat2)) *
                    COS(RADIANS(lon2) - RADIANS(lon1)) +
                    SIN(RADIANS(lat1)) * SIN(RADIANS(lat2))
        )) * 6373; -- to get in km
    insert into latlngdistance value (lat1, lon1, lat2, lon2, @distance);
    return @distance;
END;



CREATE FUNCTION getDistance(lat1 FLOAT, lon1 FLOAT,
                            lat2 FLOAT, lon2 FLOAT) RETURNS FLOAT
    NO SQL
    DETERMINISTIC
    COMMENT 'saves a calculation do latlngDestionation table'

BEGIN
    -- results return more then one sometimes
    set @distance = (Select distance
                     from latlngdistance
                     where abs(sourceLAT - lat1) <= 0.01
                       and abs(sourceLNG - lon1) <= 0.01
                       and abs(destinationLAT - lat2) <= 0.01
                       and abs(destinationLNG - lon2) <= 0.01
                     limit 1);
    set @return = if(@distance is null, distanceLATLNG(lat1, lon1, lat2, lon2), @distance);
    return @return;
END;
