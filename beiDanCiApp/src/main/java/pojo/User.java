package pojo;

public class User {
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
