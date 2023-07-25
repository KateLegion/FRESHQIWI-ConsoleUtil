import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Parser {

    //public static String url;
    private static Document getPage(String url) throws IOException {
        //String url = "https://www.cbr.ru/currency_base/daily/";
        Document page = Jsoup.parse(new URL(url), 2000);
        return page;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Введите дату в следующем формате ДД.ММ.ГГГГ:");
        try (Scanner consoleDate = new Scanner(System.in)) {
            String date = consoleDate.nextLine();

            Document pageDate = getPage("https://www.cbr.ru/currency_base/daily/"+"?UniDbQuery.Posted=True&UniDbQuery.To="+date);
            System.out.println(pageDate);
            LocalDate today = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            LocalDate dateParse = LocalDate.parse(date, formatter);
            if (dateParse.isAfter(today)) {
                System.out.println("Введите корректную дату!");
                throw new RuntimeException();
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка!");
        }

        Document page = getPage("https://www.cbr.ru/currency_base/daily/");
        Elements buttonDate = page.select("#UniDbQuery_form > div > div > div > div");


    }

}
