package ru.netology.test;

import com.codeborne.selenide.Condition;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import ru.netology.page.BuyPage;
import ru.netology.page.StartPage;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class TourPayTest {

    StartPage startPage;
    BuyPage buyPage;


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        startPage=new StartPage();
        buyPage= startPage.goToBuyPage();
    }

    @AfterEach
    void TearDownAll(){
        cleanDatabase();
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //1.1
    @Test
    @DisplayName("Should approved buy")
    public void shouldApprovedBuy() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.geStatusInData());
    }
    //1.2
    @Test
    @DisplayName("Should declined buy")
    public void shouldDeclinedBuy() {
         buyPage.putData(DataHelper.getDeclinedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                 DataHelper.ownerInfo(),DataHelper.cvcInfo());
         buyPage.errorNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }
    //1.3
    @Test
    @DisplayName("Card Number is incorrect")
    public void shouldTestIncorrectCardNumber() {
        buyPage.putData(DataHelper.generateRandomCardNumber(),DataHelper.month(),
                DataHelper.generateYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.errorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.4
    @Test
    @DisplayName("Card Number is null")
    public void shouldTestCardNumberNull() {
        buyPage.putData("",DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.wrongCardNumberNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.5
    @Test
    @DisplayName("Test empty form")
    public void shouldTestEmptyForm() {
        buyPage.putData("","","","","");
        buyPage.wrongCardNumberNotificationWait();
        buyPage.wrongMonthNotificationWait();
        buyPage.wrongYearNotificationWait();
        buyPage.ownerEmptyNotificationWait();
        buyPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.6
    @Test
    @DisplayName("Card Number value is empty")
    public void shouldTestCardNumberIsEmpty() {
        buyPage.putData("",DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(), DataHelper.cvcInfo());
        buyPage.wrongCardNumberNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.7
    @Test
    @DisplayName("Month value is empty")
    public void shouldTestMonthIsEmpty() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),"",DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(), DataHelper.cvcInfo());
        buyPage.wrongMonthNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.8
    @Test
    @DisplayName("Year value is empty")
    public void shouldTestYearIsEmpty() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),"",DataHelper.ownerInfo(),
                DataHelper.cvcInfo());
        buyPage.wrongYearNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.9
    @Test
    @DisplayName("Owner value is empty")
    public void shouldTestOwnerIsEmpty() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                "", DataHelper.cvcInfo());
        buyPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.10
    @Test
    @DisplayName("CVC value is empty")
    public void shouldTestCVCIsEmpty() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),"");
        buyPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.11
    @Test
    @DisplayName("Month value is 13")
    public void shouldTestMonthNumber13() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),"13",DataHelper.generateYear("yy"),
                DataHelper.ownerInfoEng(),DataHelper.cvcInfo());
        buyPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.12
    @Test
    @DisplayName("Month value is not a two digit number")
    public void shouldTestMonthWithNotTwoDigitNumber() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.generateMonth(),
                DataHelper.generateYear("yy"),DataHelper.ownerInfoEng(),DataHelper.cvcInfo());
        buyPage.wrongMonthNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.13
    @Test
    @DisplayName("Year value should be past")
    public void shouldTestPastYear() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.lastYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.expiredCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.14
    @Test
    @DisplayName("Year value is not a two digit number")
    public void shouldTestYearWithOneDigitNumber() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.randomYear(),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        buyPage.wrongYearNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.15
    @Test
    @DisplayName("Owner value is with dot at the end")
    public void shouldTestOwnerWithDotAtTheEnd() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfoEng() + ".",DataHelper.cvcInfo());
        buyPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.16
    @Test
    @DisplayName("Owner value contains 2 letters")
    public void shouldTestOwnerWithTwoLetters() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                "A B",DataHelper.cvcInfo());
        buyPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.17
    @Test
    @DisplayName("Should not pay with holder cyrillic")
    public void shouldTestNotPayWithHolderCyrillic() {
        buyPage.putData(DataHelper.getDeclinedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.getUnValidHolderCyr(),DataHelper.cvcInfo());
        buyPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.18
    @Test
    @DisplayName("CVC value is one digit number")
    public void shouldTestCvcAsOneDigitNumber() {
        buyPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcNumber());
        buyPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    /*
    //1.19
    @Test
    @DisplayName("")
    public void shouldTest(){



    //1.20
    @Test
    @DisplayName("")
    public void shouldTest(){

    }
    */
}
