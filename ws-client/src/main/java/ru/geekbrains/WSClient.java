package ru.geekbrains;

import ru.geekbrains.repos.*;

import java.net.MalformedURLException;
import java.net.URL;

public class WSClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/base-jsf-app/CategoryService/CategoryRepositoryImpl?wsdl");
        CategoryService categoryService = new CategoryService(url);
        CategoryRepositoryWS categoryRepository = categoryService.getCategoryRepositoryImplPort();
        categoryRepository.findAll().forEach(c-> System.out.println(c.getName()));
    }
}
