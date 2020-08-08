package ru.netology.data.util;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private static String login= "app";
    private static String password = "pass";
    private static String connection= "jdbc:mysql: //localhost:3306/app";

    private DataHelper() {
    }
    @Value
    public static class AuthInfo {
        private String login; //= "app";
        private String password;// = "pass";
        static String connection = "jdbc:mysql: //localhost:3306/app";
    }
    public static void cleanDataBase() throws SQLException {
        val runner = new QueryRunner();
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";

        AuthInfo info = new AuthInfo("app", "pass");
        try (
                val conn = DriverManager.getConnection(
                        info.connection, info.login, info.password
                )
        ) {
            runner.update(conn, codes);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
        catch (SQLException ex)
        {

        }
    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo invalidLoginAuthInfo() {
        return new AuthInfo("vbnm", "qwerty123");
    }

    public static AuthInfo invalidPasswordAuthInfo() {
        return new AuthInfo("vasya", "123");
    }

    public static String invalidPassword() {
        return "asdfghjkl";
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static String verificationCodeForVasya() throws SQLException {
        val verificationCode = "SELECT code FROM auth_codes WHERE created = (SELECT MAX(created) FROM auth_codes);";
    AuthInfo info = getAuthInfo();
        try (
                val conn = DriverManager.getConnection(
                        info.connection, info.login, info.password
                );
                val countStmt = conn.createStatement();
        )
        {
            try (val rs = countStmt.executeQuery(verificationCode)) {
                if (rs.next()) {
                    // выборка значения по индексу столбца (нумерация с 1)
                    val code = rs.getString("code");
                    // TODO: использовать
                    return code;
                }
            }
        }
        catch (SQLException ex)
        {

        }
        return null;
    }

    public static String invalidVerificationCode() {
        return "12345678";
    }

}
