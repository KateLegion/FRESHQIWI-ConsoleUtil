import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    //Парсинг страницы
    private static Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url), 2000);
        return page;
    }

    //Преобразование таблицы в массивы
    private static void getCurrency(Elements elements) {
        ArrayList<String> elem = new ArrayList<>();
        for (Element element:
             elements) {
            elem.add(element.text());
        }
        ArrayList<String> codeArray = new ArrayList<>();
        for (int i = 1; i < elem.size(); i+=5) {
            codeArray.add(elem.get(i));
        }
        ArrayList<String> nameArray = new ArrayList<>();
        for (int i = 3; i < elem.size(); i+=5) {
            nameArray.add(elem.get(i));
        }
        ArrayList<String> countArray = new ArrayList<>();
        for (int i = 2; i < elem.size(); i+=5) {
            countArray.add(elem.get(i));
        }
        ArrayList<String> valueArray = new ArrayList<>();
        for (int i = 4; i < elem.size(); i+=5) {
            valueArray.add(elem.get(i));
        }
        for (int i = 0; i < codeArray.size(); i++) {
            System.out.println(codeArray.get(i) + " (" + (Integer.parseInt(countArray.get(i)) > 1 ? countArray.get(i) + " " : "") +
                    nameArray.get(i) + "): " + valueArray.get(i));
        }
    }

    public static void main(String[] args) {
        System.out.println("Введите дату в следующем формате ДД.ММ.ГГГГ:");
        try (Scanner consoleDate = new Scanner(System.in)) {
            //Выбор даты для Банка
            String date = consoleDate.nextLine();
            //Работа с корректностью даты
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            LocalDate dateParse = LocalDate.parse(date, formatter);
            if (dateParse.isAfter(today)) {
                System.out.println("Введите корректную дату!");
                throw new RuntimeException();
            }
            //Парсинг страницы
            Document pageDate = getPage("https://www.cbr.ru/currency_base/daily/"+"?UniDbQuery.Posted=True&UniDbQuery.To="+date);
            Elements table = pageDate.select("div.table-wrapper");
            Elements currency = table.select("td");
            //Получение списка валют
            getCurrency(currency);

        } catch (IOException e) {
            System.out.println("Возникла ошибка!");
        }
    }

}
