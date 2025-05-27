package pojo;

public class User {
    private String create_time;
    private String last_login;
    private int level;
    private String select_wordbook;

//    public User(String userName, String passWord, String e_Mail, String create_time, String last_login, int level, String select_wordbook ) {
//        this.create_time = create_time;
//        this.last_login = last_login;
//        this.level = level;
//        this.select_wordbook = select_wordbook;
//        this.e_Mail = e_Mail;
//        this.passWord = passWord;
//        this.userName = userName;
//    }

    public User() {

    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSelect_wordbook() {
        return select_wordbook;
    }

    public void setSelect_wordbook(String select_wordbook) {
        this.select_wordbook = select_wordbook;
    }

    private String e_Mail;
    private String passWord;
    private String userName;
    private String emailPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public User(String e_Mail, String passWord, String userName, String emailPassword) {
        this.e_Mail = e_Mail;
        this.passWord = passWord;
        this.userName = userName;
        this.emailPassword = emailPassword;
    }

    public User(String e_Mail, String passWord, String userName) {
        this.e_Mail = e_Mail;
        this.passWord = passWord;
        this.userName = userName;
    }



    public User(String e_Mail, String passWord){
        this.e_Mail = e_Mail;
        this.passWord = passWord;
    }

    public User(String e_Mail) {
        this.e_Mail = e_Mail;
    }


    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getE_Mail() {
        return e_Mail;
    }

    public void setE_Mail(String e_Mail) {
        this.e_Mail = e_Mail;
    }


}
