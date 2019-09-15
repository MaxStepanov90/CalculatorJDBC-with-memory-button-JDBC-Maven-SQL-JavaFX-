package sample;

import java.sql.*;
import java.util.Objects;

public class Jdbc {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/calcjdbc";
    static final String USER = "root";
    static final String PASSWORD = "root";

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Long result;


    public Jdbc() {
        Connection connection = this.connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Загрузка драйвера...");
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось загрузить драйвер");
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось присоединиться");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Long select() {

        String selectsql = "select value from memoryvalue where id = 1";
        try {
            preparedStatement = connection.prepareStatement(selectsql);
            System.out.println("Создание запроса select");
        } catch (SQLException e) {
            System.out.println("Ошибка в создании запроса select");
        }
        try {
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println("Не удалось сделать запрос");
        }
        try {
            while (resultSet.next()) {
                result = resultSet.getLong("value");
                System.out.println("Установка значения");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в установке значения");
        }
        if (result == null) {
            return Long.valueOf(0);
        } else
            return result;

    }

    public void update(Long sum) {
        String updatesql = "UPDATE memoryvalue SET value = ? where id = 1";
        try {
            preparedStatement = connection.prepareStatement(updatesql);
            System.out.println("Создание запроса update");
        } catch (SQLException e) {
            System.out.println("Ошибка в cоздании запроса update");
        }
        try {
            preparedStatement.setLong(1, sum);
            preparedStatement.executeUpdate();
            System.out.println("Запрос update отправлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.close();
            System.out.println("Соединение update закрыто");
        } catch (SQLException e) {
            System.out.println("Не удалось закрыть соединение update");
        }
    }


    public void delete() {
        String deletesql = "delete from memoryvalue where id = 1";

        try {
            preparedStatement = connection.prepareStatement(deletesql);
            System.out.println("Создание запроса delete");
        } catch (SQLException e) {
            System.out.println("Ошибка в создании запроса delete");
        }
        try {
            preparedStatement.close();
            System.out.println("Соединение delete закрыто");
        } catch (SQLException e) {
            System.out.println("Не удалось закрыь соединение delete");
        }
    }
}

