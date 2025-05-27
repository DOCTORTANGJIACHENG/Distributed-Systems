package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import pojo.wordBook;
import util.DruidUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class wordBookDao {

    private int id;
    private String name;
    private String description;


    //查询所有词书
    public List<wordBook> findAllWordBook() throws SQLException {
        String sql = "select id, name, description from word_book";
        var conn = DruidUtils.getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        List<wordBook> wordBooks = new ArrayList<>();
        while (rs.next()) {
            wordBook wordbook = new wordBook();
            wordbook.setId(rs.getInt("id"));
            wordbook.setName(rs.getString("name"));
            wordbook.setDescription(rs.getString("description"));
            wordBooks.add(wordbook);
        }
        for (wordBook wordbook : wordBooks) {
            System.out.println(wordbook);
        }
        return wordBooks;

    }
}
