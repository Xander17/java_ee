package ru.geekbrains.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTables {
    static void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS `categories` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_da_0900_ai_ci NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS `products` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
                    "  `description` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n" +
                    "  `price` decimal(19,2) NOT NULL,\n" +
                    "  `category` int(11) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `cat_idx` (`category`),\n" +
                    "  CONSTRAINT `cat` FOREIGN KEY (`category`) REFERENCES `categories` (`id`) ON DELETE SET NULL\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS `orders` (" +
                    "`id` int(11) NOT NULL, " +
                    "`product_id` int(11) NOT NULL, " +
                    "`price` decimal(19,2) NOT NULL, " +
                    "`quantity` int(11) NOT NULL, " +
                    "KEY `product_idx` (`product_id`), " +
                    "CONSTRAINT `product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`));");
        }
    }
}
