-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema didasepidos_v3
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema didasepidos_v3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `didasepidos_v3` DEFAULT CHARACTER SET utf8mb4 ;
USE `didasepidos_v3` ;

-- -----------------------------------------------------
-- Table `didasepidos_v3`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`locations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(3) NOT NULL,
  `division_unit` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`institutions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`institutions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `is_private` TINYINT NOT NULL,
  `location_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_institutions_locations1_idx` (`location_id` ASC) VISIBLE,
  CONSTRAINT `fk_institutions_locations1`
    FOREIGN KEY (`location_id`)
    REFERENCES `didasepidos_v3`.`locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`subjects` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`reviews` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author_id` CHAR(64) NOT NULL,
  `institution_id` INT NOT NULL,
  `datetime` VARCHAR(45) NOT NULL,
  `note` VARCHAR(4096) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_reviews_institutions1_idx` (`institution_id` ASC) VISIBLE,
  CONSTRAINT `fk_reviews_institutions1`
    FOREIGN KEY (`institution_id`)
    REFERENCES `didasepidos_v3`.`institutions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`institutions_subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`institutions_subjects` (
  `institution_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`institution_id`, `subject_id`),
  INDEX `fk_institutions_has_subjects_subjects1_idx` (`subject_id` ASC) VISIBLE,
  INDEX `fk_institutions_has_subjects_institutions_idx` (`institution_id` ASC) VISIBLE,
  CONSTRAINT `fk_institutions_has_subjects_institutions`
    FOREIGN KEY (`institution_id`)
    REFERENCES `didasepidos_v3`.`institutions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_institutions_has_subjects_subjects1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `didasepidos_v3`.`subjects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`review_components`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`review_components` (
  `review_id` INT NOT NULL,
  `subject_id` INT NOT NULL,
  `rating` INT NULL,
  PRIMARY KEY (`review_id`, `subject_id`),
  INDEX `fk_reviews_has_subjects_subjects1_idx` (`subject_id` ASC) VISIBLE,
  INDEX `fk_reviews_has_subjects_reviews1_idx` (`review_id` ASC) VISIBLE,
  CONSTRAINT `fk_reviews_has_subjects_reviews1`
    FOREIGN KEY (`review_id`)
    REFERENCES `didasepidos_v3`.`reviews` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reviews_has_subjects_subjects1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `didasepidos_v3`.`subjects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`review_votes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`review_votes` (
  `author_id` CHAR(64) NOT NULL,
  `isLike` TINYINT NULL,
  `review_id` INT NOT NULL,
  PRIMARY KEY (`author_id`, `review_id`),
  INDEX `fk_review_votes_reviews1_idx` (`review_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_votes_reviews1`
    FOREIGN KEY (`review_id`)
    REFERENCES `didasepidos_v3`.`reviews` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `didasepidos_v3`.`generated_authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `didasepidos_v3`.`generated_authors` (
  `origin` VARCHAR(64) NOT NULL,
  `datetime` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`origin`, `datetime`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
