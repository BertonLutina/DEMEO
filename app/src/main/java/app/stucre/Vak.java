package app.stucre;

import java.io.Serializable;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class Vak implements Serializable{


    private String id;
    private String course;
    private String credit;
    private boolean checked;
    private String creditPunten;
    private boolean geslaagd = false;
    private int chance;
    private final static int chanceMax = 4;
    private int score;
    private int fase;
    private boolean isEnabled;






    public Vak() {
    }

    public Vak(String id, String course, String credit, String creditPunten, int fase, boolean geslaagd) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.creditPunten = creditPunten;
        this.fase = fase;
        this.geslaagd = geslaagd;
    }

    public Vak(String id, String course, String credit, boolean checked, String creditPunten,int fase, boolean geslaagd) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.checked = checked;
        this.creditPunten = creditPunten;
    }

    public Vak(String id, String course, String credit, boolean checked, String creditPunten,int fase, boolean geslaagd, int score, int chance) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.checked = checked;
        this.creditPunten = creditPunten;
        this.score = score;
        this.chance = chance;
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

    public int getFase() {
        return fase;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isGeslaagd() {
        return geslaagd;
    }

    public void setGeslaagd(boolean geslaagd) {
        this.geslaagd = geslaagd;
    }

    public int getScore() {
        return score;
    }

    public Integer setScore(int score) {
        if((score >= 0) && (score <= 20)){
        this.score = score;
        return this.score;
        }
        return null;
    }

    public int getChance() {
        return chance;
    }

    public Integer setChance(int chance) {

        if(chance > chanceMax && chance <0)
            return null;

        this.chance = chance;
        return this.chance;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

}
