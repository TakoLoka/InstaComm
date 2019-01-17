package hw1.demirtas.tarik.com.instacomm;

import android.database.Cursor;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class Commons {
    public static String currentComment = "This guy should be more careful";
    public static Cursor cursor;
    public static int curentItemIndex=0;
    public static ArrayList<hw1.demirtas.tarik.com.instacomm.Comment> comments = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static User curUser;
    public static int curType;


    public static ArrayList<hw1.demirtas.tarik.com.instacomm.Comment> getComments() {
        return comments;
    }

    public static int getID(){
        return comments.size()+1;
    }

    public static void setData(ArrayList<hw1.demirtas.tarik.com.instacomm.Comment> data) {
        Commons.comments = data;
    }

    public static String getCurrentComment() {
        return currentComment;
    }

    public static void setCurrentComment(String currentComment) {
        Commons.currentComment = currentComment;
    }

    public static User getCurUser() {
        return curUser;
    }
}
