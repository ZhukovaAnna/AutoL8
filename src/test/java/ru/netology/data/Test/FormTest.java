package ru.netology.data.Test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.util.DataHelper;
import ru.netology.data.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class FormTest {

//    @BeforeEach
//    void init() {
//       val loginPage = open("http://localhost:9999", LoginPage.class);
//    }

    @AfterAll
    public static void cleanTables() throws SQLException {
        DataHelper.cleanDataBase();
    }

    @Test
    void shouldRegistrationWhenValidData() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        String verificationCode = DataHelper.verificationCodeForVasya();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotRegistrationWhenInvalidPassword() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.invalidPasswordAuthInfo();
        loginPage.invalidAuth(authInfo);
    }

    @Test
    void shouldNotRegistrationWhenInvalidLogin() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.invalidLoginAuthInfo();
        loginPage.invalidAuth(authInfo);
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

    @Test
    void shouldNotRegistrationWhenInvalidCode() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validAuth(authInfo);
        val verificationCode = DataHelper.invalidVerificationCode();
        verificationPage.invalidVerify(verificationCode);
    }
}
