package dao;

import pojo.wordBook;
import util.DruidUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WordDao {
    public WordDao() {
    }
    public ResultSet queryWordsByWordbook(wordBook wordbook) throws SQLException {
        String sql = "select word, phonetic, meaning, example from word inner join word_book wb on word.book_id = wb.id where name = ?";
        var conn = DruidUtils.getConnection();
        var pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,wordbook.getName());
        ResultSet rs = pstmt.executeQuery();
        if (rs!=null){
            return rs;
        }
        else {
            System.out.println("查询该词书下的单词失败！");
            return null;
        }

    }
}
