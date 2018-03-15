package app.stucre;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class Vak {


    private String id;
    private String course;
    private String credit;

    public Vak() {
    }

    public Vak(String id, String course, String credit) {
        this.id = id;
        this.course = course;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
