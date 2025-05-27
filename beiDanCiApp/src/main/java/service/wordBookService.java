package service;

import dao.wordBookDao;
import pojo.wordBook;

import java.sql.SQLException;
import java.util.List;

public class wordBookService {

    wordBook wordBook;

    public void getWordBooks(List<wordBook> wordBooks) throws SQLException {

        wordBookDao wordBookDao = new wordBookDao();
        List<wordBook> allWordBook = wordBookDao.findAllWordBook();
        wordBooks.addAll(allWordBook);

    }
}
