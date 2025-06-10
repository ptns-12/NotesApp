package ptns.ntu.notes_mobileapp;

import java.sql.Timestamp;

//du lieu ghi chu
public class Note {
    String title;
    String content;
    Timestamp timestamp;

    //rong
    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
