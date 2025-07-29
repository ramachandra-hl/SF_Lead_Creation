package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class Utilities {
    private static final Logger log = LogManager.getLogger(Utilities.class);
    private static final Random random = new Random();


    public static String formatCurrentDate(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(new Date());
    }


    public static String getTomorrowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return formatter.format(date);
    }

    public static String getDateTwoMonthsInTheFuture() {
        LocalDate today = LocalDate.now();
        LocalDate twoMonthsLater = today.plusMonths(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return twoMonthsLater.format(formatter);
    }


    public static String getTodayDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }


    public static String generateRandomString(int length) {
        if (length <= 0) return "";

        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomAscii;
            if (ThreadLocalRandom.current().nextBoolean()) {
                randomAscii = ThreadLocalRandom.current().nextInt(65, 91);
            } else {
                randomAscii = ThreadLocalRandom.current().nextInt(97, 123);
            }
            stringBuilder.append((char) randomAscii);
        }

        return stringBuilder.toString();
    }


    public static String convertDateToStringFormat() {
        Date today = new Date();

        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yy");

        int day = Integer.parseInt(dayFormat.format(today));
        int month = Integer.parseInt(monthFormat.format(today));
        int year = Integer.parseInt(yearFormat.format(today));

        // Mapping for months
        String[] months = {
                "", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        // Mapping for day numbers
        String[] days = {
                "", "first", "second", "third", "fourth", "fifth", "sixth", "seventh",
                "eighth", "ninth", "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth",
                "fifteenth", "sixteenth", "seventeenth", "eighteenth", "nineteenth", "twentieth",
                "twentyfirst", "twentysecond", "twentythird", "twentyfourth", "twentyfifth",
                "twentysixth", "twentyseventh", "twentyeighth", "twentyninth", "thirtieth",
                "thirtyfirst"
        };

        // Convert year to words
        Map<Character, String> numberWords = new HashMap<>();
        numberWords.put('0', "zero");
        numberWords.put('1', "one");
        numberWords.put('2', "two");
        numberWords.put('3', "three");
        numberWords.put('4', "four");
        numberWords.put('5', "five");
        numberWords.put('6', "six");
        numberWords.put('7', "seven");
        numberWords.put('8', "eight");
        numberWords.put('9', "nine");

        StringBuilder yearInWords = new StringBuilder();
        for (char digit : String.valueOf(year).toCharArray()) {
            yearInWords.append(numberWords.get(digit));
        }
        return days[day] + months[month] + yearInWords;
    }

    public static String convert(int number) {
        String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        if (number == 0) {
            return "Zero";
        }
        String result = "";

        if (number >= 1000) {
            result += ones[number / 1000] + "Thousand";
            number %= 1000;
        }
        if (number >= 100) {
            result += ones[number / 100] + "Hundred";
            number %= 100;
        }
        if (number >= 20) {
            result += tens[number / 10];
            number %= 10;
        }
        if (number > 0) {
            result += ones[number];
        }
        return result.trim();
    }

    public static void csvToProperties(String csvFilePath, String propertiesFile) throws IOException {
        Properties properties = new Properties();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
             FileWriter writer = new FileWriter(propertiesFile)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] keyValue = line.split(",", 2);

                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    properties.setProperty(key, value);
                } else {
                    log.error("Invalid line in CSV: {}", line);
                }
            }
            properties.store(writer, "Generated from CSV");
            log.info("Properties file created successfully: {}", propertiesFile);
        }
    }
}


