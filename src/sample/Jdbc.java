package sample;

import java.sql.*;

public class Jdbc {

    static final String URL = "jdbc:mysql://localhost:3306/calcjdbc";
    static final String USER = "root";
    static final String PASSWORD = "root";

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Long result;


    public Jdbc() {

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

    public Long select() {

        String selectsql = "select value from memoryvalue where id = 1";
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(selectsql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getLong("value");
            }
            connection.close();
            preparedStatement.close();
            System.out.println("Запрос select успешно выполнен");
        } catch (SQLException e) {
            System.out.println("Ошибка в создании запроса select");


        }if (result == null) {
            return Long.valueOf(0);
        } else
            return result;
        }


    public void update(Long sum) {
        String updatesql = "UPDATE memoryvalue SET value = ? where id = 1";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(updatesql);
            preparedStatement.setLong(1, sum);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
            System.out.println("Запрос update успешно выполнен");
        } catch (SQLException e) {
            System.out.println("Ошибка в cоздании запроса update");
        }
    }


    public void delete() {
        String deletesql = "update memoryvalue set value = ? ";

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(deletesql);
            preparedStatement.setLong(1, 0);
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();

            System.out.println("Запрос delete успешно выполнен");
        } catch (SQLException e) {
            System.out.println("Ошибка в создании запроса delete");
        }
    }
}