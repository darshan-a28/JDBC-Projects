CREATE TABLE `hotel_mgmt`.`reservations` (
  `reservation_id` INT NOT NULL AUTO_INCREMENT,
  `guest_name` VARCHAR(225) NOT NULL,
  `room_number` INT NOT NULL,
  `contact_number` VARCHAR(10) NOT NULL,
  `reservation_date` TIMESTAMP NULL,
  PRIMARY KEY (`reservation_id`));
