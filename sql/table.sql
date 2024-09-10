CREATE DATABASE  IF NOT EXISTS `coffee_shop`;
USE `coffee_shop`;

--
-- Table structure for table `employee`
--


CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `login` (
	`login_id` int NOT NULL AUTO_INCREMENT,
    `user_name` varchar(45) NOT NULL UNIQUE,
    `password` varchar(45) NOT NULL,
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `user_id` int,
    primary key(`login_id`),
	constraint fk_login_user foreign key (`user_id`) references user(`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `role` ( 
    `role_id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(20) NOT NULL,
    `user_name` varchar(45) NOT NULL,
    primary key(`role_id`),
    constraint fk_role_login foreign key (`user_name`) references `login`(`user_name`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
    


