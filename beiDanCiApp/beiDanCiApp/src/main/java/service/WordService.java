package service;

import dao.UserDao;
import dao.WordDao;
import pojo.Word;
import pojo.wordBook;
import util.DruidUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordService {

    public WordService() {
    }

    public List<Word> getWordsByWordbook(wordBook wordbook) throws SQLException {
        List<Word> words = new ArrayList<>();
        WordDao wordDao = new WordDao();

        if(wordbook!=null) {
            ResultSet rs = wordDao.queryWordsByWordbook(wordbook);
            while (rs.next()) {
                words.add(new Word(rs.getString("word"), rs.getString("phonetic"), rs.getString("meaning"), rs.getString("example")));
            }
            return words;
        }
        else return null;


    }

    public ArrayList<Word> initWordbyWordbook() throws SQLException {
        ArrayList<Word> words = new ArrayList<>();
        WordDao wordDao = new WordDao();
        UserDao userDao = new UserDao();
        ResultSet resultSet = userDao.queryUserInfo();
        ResultSet rs = null;
        wordBook WordBook = null;
        while (resultSet.next()) {
            WordBook = new wordBook(resultSet.getString("name"));
        }
        if (WordBook != null) {
            rs = wordDao.queryWordsByWordbook(WordBook);
        }

        if (rs != null) {
            while (rs.next()) {
                words.add(new Word(rs.getString("word"), rs.getString("phonetic"), rs.getString("meaning"), rs.getString("example")));
            }
        }
        return words;
    }
}
