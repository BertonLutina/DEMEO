package app.stucre;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class Vak {


    private String id;
    private String course;
    private String credit;
    private boolean checked;
    private String creditPunten;






    public Vak() {
    }

    public Vak(String id, String course, String credit, String creditPunten) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.creditPunten = creditPunten;
    }

    public Vak(String id, String course, String credit, boolean checked, String creditPunten) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.checked = checked;
        this.creditPunten = creditPunten;
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


    public String getCreditPunten() {
        return creditPunten;
    }

    public void setCreditPunten(String creditPunten) {
        this.creditPunten = creditPunten;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked() {
        checked = !checked ;
    }


}
