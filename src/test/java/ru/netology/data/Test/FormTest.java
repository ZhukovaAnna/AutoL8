package ru.netology.data.Test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class FormTest {
    @AfterAll
    public static void cleanTables() throws SQLException {
        DataHelper.setUp();
    }

    @Test
    void shouldEnterWhenValidData() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        String verificationCode = DataHelper.verificationCodeForVasya();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotEnterWhenInvalidLogin() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.invalidLoginAuthInfo();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    void shouldNotEnterWhenInvalidPassword() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.invalidPasswordAuthInfo();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    void shouldNotEnterWhenInvalidCode() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = DataHelper.invalidVerificationCode();
        verificationPage.invalidVerify(verificationCode);
    }

    @Test
    void shouldBlockWhenThreeInvalidPasswords() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.invalidPasswordAuthInfo();
        loginPage.invalidAuth(authInfo);
        val invalidPassword = DataHelper.invalidPassword();
        loginPage.sendInvalidPassword(invalidPassword);
        loginPage.sendInvalidPasswordThirdTime(invalidPassword);
    }
}
