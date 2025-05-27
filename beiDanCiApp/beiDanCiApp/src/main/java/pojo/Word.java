package pojo;

// Word.java
public class Word {
    private final String word;     // 英文单词
    private final String phonetic; // 音标
    private final String meaning;  // 中文释义
    private final String example;





    public Word(String word, String phonetic, String meaning, String example) {
        this.word = word;
        this.phonetic = phonetic;
        this.meaning = meaning;
        this.example = example;
    }


    // Getters
    public String getWord() { return word; }
    public String getPhonetic() { return phonetic; }
    public String getMeaning() { return meaning; }
    public String getExample() {
        return example;
    }



}