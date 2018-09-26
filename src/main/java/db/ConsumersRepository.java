package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Constants.PASS;
import static utils.Constants.URL;
import static utils.Constants.USER;

public class ConsumersRepository {
    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public String[] getPhones() throws SQLException {
        List<String> resultPhones = new ArrayList<>();
        Connection connection = null;
        try {
            connection = openConnection();

            Statement statement = connection.createStatement();

            ResultSet phones = statement.executeQuery("SELECT phone FROM consumer");
            System.out.println("SELECT all phones FROM consumer");

            while (phones.next()) {
                resultPhones.add(phones.getString(1));
            }

            closeConnection(connection);
        } catch (SQLException e) {
            Objects.requireNonNull(connection).rollback();
            e.printStackTrace();
        }

        return resultPhones.toArray(new String[0]);
    }
}
