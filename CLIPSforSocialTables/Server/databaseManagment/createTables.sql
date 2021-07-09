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