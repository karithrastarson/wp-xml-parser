/**
 * Created by kari.thrastarson on 23-09-2016.
 */
import java.util.ArrayList;

public class Post {
    private String title;
    private String author;
    private String date;
    private String content;
    private ArrayList<Comment> comments;

    /*
     * CONSTRUCTORS
     */
    public Post() {
        title = "";
        author = "";
        date = "";
        content = "";
        comments = new ArrayList<Comment>();
    }
    public Post(String t, String a, String d, String con, ArrayList<Comment> com) {
        title = t;
        author = a;
        date = d;
        content = con;
        comments = com;
    }

    /*
     *  ------SETTERS------
     */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    /*
     *  ------GETTERS------
     */
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(String commentAuthor, String commentContent, String date) {
        Comment c = new Comment(commentAuthor, commentContent, date);

        comments.add(c);
    }

    public void printComments() {
        for (Comment c : comments) {
            System.out.println("On " + c.getDate() + " " + c.getAuthor() + " wrote: \n");
            System.out.println(c.getComment());
        }
    }
}
