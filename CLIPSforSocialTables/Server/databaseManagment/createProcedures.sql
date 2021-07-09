DELIMITER //


CREATE PROCEDURE dropTables()
BEGIN
  ALTER TABLE `BattleshipAttacks` DROP FOREIGN KEY `fk_BattleshipAttacks_BattleshipMatches`;
  ALTER TABLE `BattleshipAttacks` DROP FOREIGN KEY `fk_BattleshipAttacks_Profiles`;
  ALTER TABLE `BattleshipAttacks` DROP FOREIGN KEY `fk_BattleshipAttacks_BattleshipPositions`;
  ALTER TABLE `BattleshipShipComponents` DROP FOREIGN KEY `fk_BattleshipShipComponents_BattleshipShips`;
  ALTER TABLE `BattleshipShips` DROP FOREIGN KEY `fk_BattleshipShips_Profiles`;
  ALTER TABLE `BattleshipShips` DROP FOREIGN KEY `fk_BattleshipShips_BattleshipPositions`;
  ALTER TABLE `BattleshipShips` DROP FOREIGN KEY `fk_BattleshipShips_Teams`;
  ALTER TABLE `BattleshipCells` DROP FOREIGN KEY `fk_BattleshipCells_BattleshipPositions`;
  ALTER TABLE `BattleshipCells` DROP FOREIGN KEY `fk_BattleshipCells_Teams`;
  ALTER TABLE `TeamLeaders` DROP FOREIGN KEY `fk_TeamLeaders_Profiles`;
  ALTER TABLE `TeamLeaders` DROP FOREIGN KEY `fk_TeamLeaders_Teams`;
  ALTER TABLE `Teams` DROP FOREIGN KEY `fk_Teams_BattleshipMatches`;
  ALTER TABLE `BattleshipMatches` DROP FOREIGN KEY `fk_BattleshipMatches_Teams`;
  ALTER TABLE `Profiles` DROP FOREIGN KEY `fk_Profiles_Teams`;
  ALTER TABLE `Teams` DROP FOREIGN KEY `fk_Teams_Tables`;
  ALTER TABLE `GameScores` DROP FOREIGN KEY `fk_GameScores_Locals`;
  ALTER TABLE `GameScores` DROP FOREIGN KEY `fk_GameScores_Games`;
  ALTER TABLE `Tables` DROP FOREIGN KEY `fk_Tables_Locals`;
  ALTER TABLE `Tables` DROP FOREIGN KEY `fk_Tables_Beacons`;
  ALTER TABLE `BattleshipNumberOfShots` DROP FOREIGN KEY `fk_BattleshipNumberOfShots_Profiles`;
  ALTER TABLE `Profiles` DROP FOREIGN KEY `fk_Profiles_Beacons`;
  ALTER TABLE `Profiles` DROP FOREIGN KEY `fk_Profiles_Sessions`;
  DROP TABLE IF EXISTS `BattleshipAttacks`;
  DROP TABLE IF EXISTS `BattleshipShipComponents`;
  DROP TABLE IF EXISTS `BattleshipShips`;
  DROP TABLE IF EXISTS `BattleshipPositions`;
  DROP TABLE IF EXISTS `BattleshipCells`;
  DROP TABLE IF EXISTS `TeamLeaders`;
  DROP TABLE IF EXISTS `BattleshipMatches`;
  DROP TABLE IF EXISTS `Teams`;
  DROP TABLE IF EXISTS `GameScores`;
  DROP TABLE IF EXISTS `Games`;
  DROP TABLE IF EXISTS `Tables`;
  DROP TABLE IF EXISTS `Locals`;
  DROP TABLE IF EXISTS `BattleshipNumberOfShots`;
  DROP TABLE IF EXISTS `Profiles`;
  DROP TABLE IF EXISTS `Beacons`;
  DROP TABLE IF EXISTS `Sessions`;
END //


CREATE PROCEDURE createTables()
BEGIN
  CREATE TABLE `Sessions`(
  `sessionId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `lastAccess` DATETIME NOT NULL
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `Beacons`(
  `beaconId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `uuid` VARCHAR(36),
  `major` SMALLINT UNSIGNED,
  `minor` SMALLINT UNSIGNED
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `Profiles`(
  `profileId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL,
  `sessionId` SMALLINT UNSIGNED UNIQUE,
  `beaconId` SMALLINT UNSIGNED,
  `teamId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_Profiles_Sessions` FOREIGN KEY (`sessionId`) REFERENCES `Sessions`(`sessionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Profiles_Beacons` FOREIGN KEY (`beaconId`) REFERENCES `Beacons`(`beaconId`) ON DELETE SET NULL ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `BattleshipNumberOfShots`(
  `profileId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `numberOfShots` TINYINT NOT NULL,
  CONSTRAINT `fk_BattleshipNumberOfShots_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles`(`profileId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `Locals`(
  `localId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `uuid` VARCHAR(36) UNIQUE,
  `description` VARCHAR(100)
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `Tables`(
  `tableId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `tableDescription` VARCHAR(50),
  `beaconId` SMALLINT UNSIGNED,
  `localId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_Tables_Beacons` FOREIGN KEY (`beaconId`) REFERENCES `Beacons`(`beaconId`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_Tables_Locals` FOREIGN KEY (`localId`) REFERENCES `Locals`(`localId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `Games`(
  `gameId` SMALLINT UNSIGNED PRIMARY KEY,
  `gameName` VARCHAR(50)
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `GameScores`(
  `gameScoreId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `teamName` VARCHAR(50),
  `score` SMALLINT,
  `gameId` SMALLINT UNSIGNED,
  `localId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_GameScores_Games` FOREIGN KEY (`gameId`) REFERENCES `Games`(`gameId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GameScores_Locals` FOREIGN KEY (`localId`) REFERENCES `Locals`(`localId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `Teams`(
  `teamId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `teamName` VARCHAR(50),
  `tableId` SMALLINT UNSIGNED NOT NULL,
  `matchId` SMALLINT UNSIGNED,
  `ready` BOOLEAN DEFAULT FALSE,
  CONSTRAINT `fk_Teams_Tables` FOREIGN KEY (`tableId`) REFERENCES `Tables`(`tableId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  ALTER TABLE `Profiles` ADD CONSTRAINT `fk_Profiles_Teams` FOREIGN KEY (`teamId`) REFERENCES `Teams`(`teamId`) ON DELETE SET NULL ON UPDATE CASCADE;
  
  CREATE TABLE `BattleshipMatches`(
  `battleshipMatchId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `attackingTeamId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_BattleshipMatches_Teams` FOREIGN KEY (`attackingTeamId`) REFERENCES `Teams`(`teamId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  ALTER TABLE `Teams` ADD CONSTRAINT `fk_Teams_BattleshipMatches` FOREIGN KEY (`matchId`) REFERENCES `BattleshipMatches`(`battleshipMatchId`) ON DELETE SET NULL ON UPDATE CASCADE;
  
  CREATE TABLE `TeamLeaders`(
  `teamId` SMALLINT UNSIGNED UNIQUE,
  `profileId` SMALLINT UNSIGNED UNIQUE,
  PRIMARY KEY (`teamId`, `profileId`),
  CONSTRAINT `fk_TeamLeaders_Teams` FOREIGN KEY (`teamId`) REFERENCES `Teams`(`teamId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_TeamLeaders_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles`(`profileId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `BattleshipPositions`(
  `battleshipPositionId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `x` TINYINT UNSIGNED,
  `y` TINYINT UNSIGNED
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `BattleshipCells`(
  `teamId` SMALLINT UNSIGNED,
  `state` VARCHAR(50),
  `battleshipPositionId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_BattleshipCells_Teams` FOREIGN KEY (`teamId`) REFERENCES `Teams`(`teamId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_BattleshipCells_BattleshipPositions` FOREIGN KEY (`battleshipPositionId`) REFERENCES `BattleshipPositions`(`battleshipPositionId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `BattleshipShips`(
  `battleshipShipId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `vertical` BOOLEAN,
  `teamId` SMALLINT UNSIGNED,
  `battleshipPositionId` SMALLINT UNSIGNED,
  `profileId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_BattleshipShips_Teams` FOREIGN KEY (`teamId`) REFERENCES `Teams`(`teamId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_BattleshipShips_BattleshipPositions` FOREIGN KEY (`battleshipPositionId`) REFERENCES `BattleshipPositions`(`battleshipPositionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_BattleshipShips_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles`(`profileId`) ON DELETE SET NULL ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `BattleshipShipComponents`(
  `battleshipShipId` SMALLINT UNSIGNED,
  `componentNumber` TINYINT UNSIGNED,
  `destroyed` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`battleshipShipId`, `componentNumber`),
  CONSTRAINT `fk_BattleshipShipComponents_BattleshipShips` FOREIGN KEY (`battleshipShipId`) REFERENCES `BattleshipShips`(`battleshipShipId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
  
  CREATE TABLE `BattleshipAttacks`(
  `battleshipAttackId` SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `battleshipPositionId` SMALLINT UNSIGNED,
  `profileId` SMALLINT UNSIGNED,
  `battleshipMatchId` SMALLINT UNSIGNED,
  CONSTRAINT `fk_BattleshipAttacks_BattleshipPositions` FOREIGN KEY (`battleshipPositionId`) REFERENCES `BattleshipPositions`(`battleshipPositionId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_BattleshipAttacks_Profiles` FOREIGN KEY (`profileId`) REFERENCES `Profiles`(`profileId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_BattleshipAttacks_BattleshipMatches` FOREIGN KEY (`battleshipMatchId`) REFERENCES `BattleshipMatches`(`battleshipMatchId`) ON DELETE CASCADE ON UPDATE CASCADE
  )ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_unicode_ci;
END //


CREATE PROCEDURE populateReset()
BEGIN
  CALL dropTables;
  CALL createTables;
END //

CREATE PROCEDURE populateDefault()
BEGIN
  INSERT INTO `Games` (`gameId`,`gameName`) VALUES 
  (1,'Battleship');  
END //

CREATE PROCEDURE populateTest1()
BEGIN
  SET foreign_key_checks = 0;
  INSERT INTO `Sessions` (`sessionId`,`lastAccess`) VALUES 
  (1,NOW()),
  (2,NOW()),
  (3,NOW()),
  (4,NOW()),
  (5,NOW()),
  (6,NOW()),
  (7,NOW()),
  (8,NOW()),
  (9,NOW());
  
  INSERT INTO `Beacons` (`beaconId`,`uuid`,`major`,`minor`) VALUES 
  (1,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',1,1),
  (2,'2b826da6-4fa2-4e98-8024-bc5b71e0893e',1,1),
  (3,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',2,1),
  (4,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',1,2),
  (5,'2b826da6-4fa2-4e98-8024-bc5b71e0893e',2,1),
  (6,'2b826da6-4fa2-4e98-8024-bc5b71e0893e',1,2);
  
  INSERT INTO `Profiles` (`profileId`,`username`,`sessionId`,`beaconId`,`teamId`) VALUES 
  (1,'1_TestUsername',1,1,1),
  (2,'2_TestUsername',2,1,1),
  (3,'3_TestUsername',3,2,3),
  (4,'4_TestUsername',4,3,2),
  (5,'5_TestUsername',5,3,NULL),
  (6,'6_TestUsername',6,5,NULL),
  (7,'7_TestUsername',7,6,NULL),
  (8,'8_TestUsername',8,6,NULL),
  (9,'9_TestUsername',9,6,NULL);
  
  INSERT INTO `BattleshipNumberOfShots` (`profileId`,`numberOfShots`) VALUES 
  (1,1),
  (2,1),
  (4,2);
  
  INSERT INTO `Locals` (`localId`,`uuid`,`description`) VALUES 
  (1,'1b826da6-4fa2-4e98-8024-bc5b71e0893e','Acquario'),
  (2,'2b826da6-4fa2-4e98-8024-bc5b71e0893e','Pollaio');
  
  INSERT INTO `Tables` (`tableId`,`tableDescription`,`beaconId`,`localId`) VALUES 
  (1,'1_TestTable',1,1),
  (2,'2_TestTable',2,2),
  (3,'3_TestTable',3,1),
  (4,'4_TestTable',6,2);
  
  INSERT INTO `Games` (`gameId`,`gameName`) VALUES 
  (1,'Battleship');
  
  INSERT INTO `GameScores` (`gameScoreId`,`teamName`,`score`,`gameId`,`localId`) VALUES 
  (1,'DigitalSlots',425,1,1),
  (2,'2_TestTeam',250,1,2),
  (3,'3_TestTeam',100,1,1);
  
  INSERT INTO `Teams` (`teamId`,`teamName`,`tableId`,`matchId`,`ready`) VALUES 
  (1,'1_PlayingTeam',1,1,FALSE),
  (2,'2_PlayingTeam',3,1,FALSE),
  (3,'3_PlayingTeam',2,NULL,TRUE);
  
  INSERT INTO `BattleshipMatches` (`battleshipMatchId`,`attackingTeamId`) VALUES 
  (1,2);
  
  INSERT INTO `TeamLeaders` (`teamId`,`profileId`) VALUES 
  (1,2),
  (2,4),
  (3,3);
  
  INSERT INTO `BattleshipPositions` (`battleshipPositionId`,`x`,`y`) VALUES 
  (1,1,1),
  (2,1,2),
  (3,2,1),
  (4,2,2);
  
  INSERT INTO `BattleshipCells` (`teamId`,`state`,`battleshipPositionId`) VALUES 
  (1,"HIT",1),
  (1,"UNKNOWN",1),
  (1,"UNKNOWN",1),
  (1,"MISS",1),
  (2,"UNKNOWN",2),
  (2,"MISS",2),
  (2,"UNKNOWN",2),
  (2,"UNKNOWN",2);
  
  INSERT INTO `BattleshipShips` (`battleshipShipId`,`vertical`,`teamId`,`battleshipPositionId`,`profileId`) VALUES 
  (1,FALSE,1,1,1),
  (2,TRUE,2,1,4);
  
  INSERT INTO `BattleshipShipComponents` (`battleshipShipId`,`componentNumber`,`destroyed`) VALUES 
  (1,1,FALSE),
  (1,2,FALSE),
  (2,1,TRUE),
  (2,2,FALSE);
  
  INSERT INTO `BattleshipAttacks` (`battleshipAttackId`,`battleshipPositionId`,`profileId`,`battleshipMatchId`) VALUES 
  (1,4,4,1);
  
  SET foreign_key_checks = 1;
  
END //


CREATE PROCEDURE populateTest2()
BEGIN
  SET foreign_key_checks = 0;
  INSERT INTO `Sessions` (`sessionId`,`lastAccess`) VALUES 
  (1,NOW()),
  (2,NOW()),
  (3,NOW()),
  (4,NOW()),
  (5,NOW()),
  (6,NOW()),
  (7,NOW()),
  (8,NOW()),
  (9,NOW());
  
  INSERT INTO `Beacons` (`beaconId`,`uuid`,`major`,`minor`) VALUES 
  (1,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',1,1),
  (2,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',1,2),
  (3,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',1,3),
  (4,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',2,1),
  (5,'1b826da6-4fa2-4e98-8024-bc5b71e0893e',2,2);
  
  INSERT INTO `Profiles` (`profileId`,`username`,`sessionId`,`beaconId`,`teamId`) VALUES 
  (1,'1_TestUsername',1,1,1),
  (2,'2_TestUsername',2,1,1),
  (3,'3_TestUsername',3,2,2),
  (4,'4_TestUsername',4,2,2),
  (5,'5_TestUsername',5,2,2),
  (6,'6_TestUsername',6,3,3),
  (7,'7_TestUsername',7,4,4),
  (8,'8_TestUsername',8,4,NULL),
  (9,'9_TestUsername',9,5,NULL);
  
  #INSERT INTO `BattleshipNumberOfShots` (`profileId`,`numberOfShots`) VALUES 
  #(1,1),
  #(2,1),
  #(4,2);
  
  INSERT INTO `Locals` (`localId`,`uuid`,`description`) VALUES 
  (1,'1b826da6-4fa2-4e98-8024-bc5b71e0893e','1_TestLocal');
  
  INSERT INTO `Tables` (`tableId`,`tableDescription`,`beaconId`,`localId`) VALUES 
  (1,'1_TestTable',1,1),
  (2,'2_TestTable',2,1),
  (3,'3_TestTable',3,1),
  (4,'4_TestTable',4,1),
  (5,'5_TestTable',5,1);
  
  INSERT INTO `Games` (`gameId`,`gameName`) VALUES 
  (1,'Battleship');
  
  #INSERT INTO `GameScores` (`gameScoreId`,`teamName`,`score`,`gameId`,`localId`) VALUES 
  #(1,'DigitalSlots',425,1,1),
  #(2,'2_TestTeam',250,1,2),
  #(3,'3_TestTeam',100,1,1);
  
  INSERT INTO `Teams` (`teamId`,`teamName`,`tableId`,`matchId`,`ready`) VALUES 
  (1,'1_TestTeam',1,NULL,FALSE),
  (2,'2_TestTeam',2,NULL,FALSE),
  (3,'3_TestTeam',3,NULL,TRUE),
  (4,'4_TestTeam',4,NULL,FALSE);
  
  #INSERT INTO `BattleshipMatches` (`battleshipMatchId`,`attackingTeamId`) VALUES 
  #(1,2);
  
  INSERT INTO `TeamLeaders` (`teamId`,`profileId`) VALUES 
  (1,1),
  (2,3),
  (3,6),
  (4,7);
  
  #INSERT INTO `BattleshipPositions` (`battleshipPositionId`,`x`,`y`) VALUES 
  #(1,1,1),
  #(2,1,2),
  #(3,2,1),
  #(4,2,2);
  
  #INSERT INTO `BattleshipCells` (`teamId`,`state`,`battleshipPositionId`) VALUES 
  #(1,"HIT",1),
  #(1,"UNKNOWN",1),
  #(1,"UNKNOWN",1),
  #(1,"MISS",1),
  #(2,"UNKNOWN",2),
  #(2,"MISS",2),
  #(2,"UNKNOWN",2),
  #(2,"UNKNOWN",2);
  
  #INSERT INTO `BattleshipShips` (`battleshipShipId`,`vertical`,`teamId`,`battleshipPositionId`,`profileId`) VALUES 
  #(1,FALSE,1,1,1),
  #(2,TRUE,2,1,4);
  
  #INSERT INTO `BattleshipShipComponents` (`battleshipShipId`,`componentNumber`,`destroyed`) VALUES 
  #(1,1,FALSE),
  #(1,2,FALSE),
  #(2,1,TRUE),
  #(2,2,FALSE);
  
  #INSERT INTO `BattleshipAttacks` (`battleshipAttackId`,`battleshipPositionId`,`profileId`,`battleshipMatchId`) VALUES 
  #(1,4,4,1);
  
  SET foreign_key_checks = 1;
  
END //
  
  
DELIMITER ;
