CREATE TABLE IF NOT EXISTS `account` (
    `account_id` INT NOT NULL AUTO_INCREMENT,
    `social_id` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `nickname` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `birthdate` DATE DEFAULT NULL,
    `refresh_token` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `gender` ENUM('MAN','WOMAN','N') COLLATE utf8mb4_general_ci DEFAULT NULL,
    `social_type` ENUM('NAVER','GOOGLE','KAKAO','APPLE') COLLATE utf8mb4_general_ci DEFAULT NULL,
    `role` ENUM('USER','ADMIN') COLLATE utf8mb4_general_ci DEFAULT NULL,
    `email` VARCHAR(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
