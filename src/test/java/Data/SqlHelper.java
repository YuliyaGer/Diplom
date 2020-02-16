package Data;

import lombok.val;

import java.sql.*;


public class SqlHelper {
    private static String url = System.getProperty("db.url");
    private static String user = "app";
    private static String password = "pass";

    public static void cleanTables() throws SQLException {
        String deleteOrderEntity = "DELETE FROM order_entity;";
        String deletePaymentEntity = "DELETE FROM payment_entity;";
        String deleteCreditEntity = "DELETE FROM credit_request_entity;";

        try (
                Connection connectionMysql = DriverManager.getConnection(url, user, password);

                PreparedStatement statementOrderEntity = connectionMysql.prepareStatement(deleteOrderEntity);
                PreparedStatement statementPaymentEntity = connectionMysql.prepareStatement(deletePaymentEntity);
                PreparedStatement statementCreditEntity = connectionMysql.prepareStatement(deleteCreditEntity);
        ) {
            statementOrderEntity.executeUpdate();
            statementPaymentEntity.executeUpdate();
            statementCreditEntity.executeUpdate();
        }

    }

    public static String selectBuyStatus() throws SQLException {
        String stmt = "SELECT status FROM app.payment_entity";
        String status = "status";
        return getStatus(stmt, status);
    }

    public static String selectCreditStatus() throws SQLException {
        String stmt = "SELECT status FROM app.credit_request_entity";
        String status = "status";
        return getStatus(stmt, status);
    }
    // Доп

    public static String getStatus(String stmt, String status) throws SQLException {
        val connection = DriverManager.getConnection(url, user, password);
        val statement = connection.prepareStatement(stmt);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        val result = resultSet.getString(status);
        connection.close();
        return result;
    }
}
