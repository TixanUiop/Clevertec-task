package org.evgeny.Integration;

import lombok.extern.slf4j.Slf4j;
import org.evgeny.Utill.GetConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;



@Slf4j
public abstract class H2DataBaseCreator {


    private String DROP_TABLE_DISCOUNT_CARD = "drop table if exists discount_card";
    private String DROP_TABLE_PRODUCT= "drop table if exists product";

    private String CREATE_TABLE_DISCOUNT_CARD = """
            
            CREATE TABLE discount_card (
                id SERIAL PRIMARY KEY,
                code VARCHAR(10) NOT NULL CHECK (length(code) > 3)
            );
            
            """;

    private String CREATE_TABLE_PRODUCT = """
            
            CREATE TABLE product (
                id SERIAL PRIMARY KEY,
                description VARCHAR(128) NOT NULL,
                price NUMERIC(5,3) NOT NULL,
                isSale BOOLEAN NOT NULL,
                sale_price NUMERIC
            );
            """;

    private String INSERT_INTO_CARD = """
     
            INSERT INTO discount_card (code) VALUES
                ('0001'),
                ('1000'),
                ('2000'),
                ('3000'),
                ('4000');
            
            """;

    private String INSERT_INTO_PRODUCT= """
     
            INSERT INTO product (description, price, isSale, sale_price) VALUES
                ('Молоко "Весёлый молочник"', 1.500, false, NULL),
                ('Хлеб "Дарницкий"', 0.800, false, NULL),
                ('Шоколад "Аленка"', 2.300, true, 1.900),
                ('Масло сливочное 82%', 3.200, false, NULL),
                ('Сыр "Российский"', 4.500, true, 3.800),
                ('Кофе "Nescafe"', 4.000, false, NULL),
                ('Чай "Greenfield"', 2.750, false, NULL),
                ('Сахар-песок 1кг', 1.100, false, NULL),
                ('Макароны "Barilla"', 2.000, true, 1.700),
                ('Мука пшеничная 2кг', 1.600, false, NULL),
                ('Яблоки Гала 1кг', 1.200, false, NULL),
                ('Апельсины 1кг', 1.400, false, NULL),
                ('Бананы 1кг', 1.300, false, NULL),
                ('Картофель молодой 1кг', 0.900, false, NULL),
                ('Помидоры 1кг', 2.200, true, 1.900),
                ('Огурцы 1кг', 1.800, false, NULL),
                ('Курица целая 1кг', 3.000, true, 2.500),
                ('Свинина 1кг', 5.000, false, NULL),
                ('Говядина 1кг', 6.000, true, 5.200),
                ('Рыба "Форель" 1кг', 8.500, true, 7.500);
            
            """;



        @BeforeEach
        public void initializeDatabase() {

            try (Connection connection = GetConnection.getConnection();
                 Statement preparedStatement = connection.createStatement()) {

                preparedStatement.execute(DROP_TABLE_DISCOUNT_CARD);
                preparedStatement.execute(DROP_TABLE_PRODUCT);


                preparedStatement.execute(CREATE_TABLE_DISCOUNT_CARD);
                preparedStatement.execute(CREATE_TABLE_PRODUCT);
                preparedStatement.execute(INSERT_INTO_CARD);
                preparedStatement.execute(INSERT_INTO_PRODUCT);


            }
            catch (SQLException e) {
                log.error("Ошибка соединения с H2DB" ,e.getMessage());
            }
        }



}
