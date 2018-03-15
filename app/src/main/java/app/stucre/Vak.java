package app.stucre;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class Vak {


    private int id;
    private String course;
    private String credit;

    public Vak(int id,String course, String credit) {
        this.id = id;
        this.course = course;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
