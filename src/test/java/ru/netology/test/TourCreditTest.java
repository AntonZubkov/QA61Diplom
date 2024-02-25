package ru.netology.test;

//import com.codeborne.selenide.Condition;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;


import ru.netology.page.CreditPage;
import ru.netology.page.StartPage;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;

//import java.time.Duration;

//import static com.codeborne.selenide.Condition.*;
//import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
//import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class TourCreditTest {

    StartPage startPage;
    CreditPage creditPage;


    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        startPage = new StartPage();
        creditPage = startPage.goToCreditPage();
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
    @DisplayName("Should approved credit")
    public void shouldApprovedCredit() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.successNotificationWait();
        Assertions.assertEquals("APPROVED", SQLHelper.geStatusInData());
    }
    //1.2
    @Test
    @DisplayName("Should declined credit")
    public void shouldDeclinedCredit() {
        creditPage.putData(DataHelper.getDeclinedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.errorNotificationWait();
        Assertions.assertEquals("DECLINED", SQLHelper.geStatusInData());
    }
    //1.3
    @Test
    @DisplayName("Card Number is incorrect")
    public void shouldTestIncorrectCardNumber() {
        creditPage.putData(DataHelper.generateRandomCardNumber(),DataHelper.month(),
                DataHelper.generateYear("yy"),DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.errorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.4
    @Test
    @DisplayName("Card Number is null")
    public void shouldTestCardNumberNull() {
        creditPage.putData("",DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.wrongCardNumberNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.5
    @Test
    @DisplayName("Test empty form")
    public void shouldTestEmptyForm() {
        creditPage.putData("","","","","");
        creditPage.wrongCardNumberNotificationWait();
        creditPage.wrongMonthNotificationWait();
        creditPage.wrongYearNotificationWait();
        creditPage.ownerEmptyNotificationWait();
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.6
    @Test
    @DisplayName("Card Number value is empty")
    public void shouldTestCardNumberIsEmpty() {
        creditPage.putData("",DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(), DataHelper.cvcInfo());
        creditPage.wrongCardNumberNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.7
    @Test
    @DisplayName("Month value is empty")
    public void shouldTestMonthIsEmpty() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),"",DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(), DataHelper.cvcInfo());
        creditPage.wrongMonthNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.8
    @Test
    @DisplayName("Year value is empty")
    public void shouldTestYearIsEmpty() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),"",DataHelper.ownerInfo(),
                DataHelper.cvcInfo());
        creditPage.wrongYearNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.9
    @Test
    @DisplayName("Owner value is empty")
    public void shouldTestOwnerIsEmpty() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                "", DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.10
    @Test
    @DisplayName("CVC value is empty")
    public void shouldTestCVCIsEmpty() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),"");
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.11
    @Test
    @DisplayName("Month value is 13")
    public void shouldTestMonthNumber13() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),"13",DataHelper.generateYear("yy"),
                DataHelper.ownerInfoEng(),DataHelper.cvcInfo());
        creditPage.validityErrorNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.12
    @Test
    @DisplayName("Month value is not a two digit number")
    public void shouldTestMonthWithNotTwoDigitNumber() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.generateMonth(),
                DataHelper.generateYear("yy"),DataHelper.ownerInfoEng(),DataHelper.cvcInfo());
        creditPage.wrongMonthNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.13
    @Test
    @DisplayName("Year value should be past")
    public void shouldTestPastYear() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.lastYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.expiredCardNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.14
    @Test
    @DisplayName("Year value is not a two digit number")
    public void shouldTestYearWithOneDigitNumber() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.randomYear(),
                DataHelper.ownerInfo(),DataHelper.cvcInfo());
        creditPage.wrongYearNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.15
    @Test
    @DisplayName("Owner value is with dot at the end")
    public void shouldTestOwnerWithDotAtTheEnd() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfoEng() + ".",DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.16
    @Test
    @DisplayName("Owner value contains 2 letters")
    public void shouldTestOwnerWithTwoLetters() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                "A B",DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.17
    @Test
    @DisplayName("Should not pay with holder cyrillic")
    public void shouldTestNotPayWithHolderCyrillic() {
        creditPage.putData(DataHelper.getDeclinedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.getUnValidHolderCyr(),DataHelper.cvcInfo());
        creditPage.incorrectFormatOwnerNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
    //1.18
    @Test
    @DisplayName("CVC value is one digit number")
    public void shouldTestCvcAsOneDigitNumber() {
        creditPage.putData(DataHelper.getApprovedCardInfo(),DataHelper.month(),DataHelper.generateYear("yy"),
                DataHelper.ownerInfo(),DataHelper.cvcNumber());
        creditPage.wrongFormatCVVNotificationWait();
        Assertions.assertNull(SQLHelper.getStatusForCreditForm());
    }
}
