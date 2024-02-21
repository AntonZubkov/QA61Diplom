package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    //private static final Faker fakerRu = new Faker(new Locale("ru"));
    private static final Faker fakerEn = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getApprovedCardInfo() {
        return ("4444 44444 4444 4441");
    }

    public static String getDeclinedCardInfo() {
        return ("4444 4444 4444 4442");
    }


    public static String generateRandomCardNumber(){
        return (fakerEn.numerify("################"));
    }
    public static String month() {
        Random random = new Random();
        int monthValue = random.nextInt(12) + 1;
        String formattedMonth = String.format("%02d", monthValue);
        return formattedMonth;
    }
    public static String generateMonth() {
        Random random = new Random();
        int monthNumber = random.nextInt(9) + 1;
        return String.valueOf(monthNumber);
    }

    public static String lastMonth(String pattern){
        LocalDate lastMonth= LocalDate.now().minusMonths(2);
        return lastMonth.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String thisYear(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String lastYear(String pattern) {
        LocalDate lastYear = LocalDate.now().minusYears(1);
        return lastYear.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String generateYear(String pattern) {
        Year currentYear = Year.now();
        Year futureYear = currentYear.plusYears(5);
        return futureYear.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String futureMoreThanFiveYears(String pattern) {
        Year currentYear = Year.now();
        Year futureYear = currentYear.plusYears(10);
        return futureYear.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String randomYear() {
        return fakerEn.numerify("#");
    }

    public static String ownerInfo() {
        String firstname = fakerEn.name().firstName();
        String lastname = fakerEn.name().lastName();
        return firstname + " " + lastname;
    }
    public static String ownerInfoEng() {
        String firstname = fakerEn.name().firstName();
        String lastname = fakerEn.name().lastName();
        return firstname + " " + lastname;
    }
    public static String generateOwnerInfo() {
        return  fakerEn.numerify("### ##");

    }
    public static String getUnValidHolderCyr() {
        final Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String cvcInfo() {
        return fakerEn.numerify("###");
    }
    public static String cvcNumber(){
        return fakerEn.numerify("#");
    }
    public static String cvcDoubleNumber(){
        return fakerEn.numerify("##");
    }
}
