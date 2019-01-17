package hw1.demirtas.tarik.com.instacomm;

public class Comment {
    int id;
    String text;
    int premium;
    int type; // Types: 1 = Shocked, 2 = Cute, 3 = Funny, 4 = Scared, 5 = Supportive


    public Comment(int id, String text, int premium, int type) {
        this.id = id;
        this.text = text;
        this.premium = premium;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
