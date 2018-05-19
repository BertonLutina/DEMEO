package app.stucre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private String [] voltijdigheden;
    private String voltijdigheid;
    private boolean clickable;






    public Vak() {
    }



    public Vak(String id, String course, String credit, String creditPunten, int fase, boolean geslaagd, boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.creditPunten = creditPunten;
        this.fase = fase;
        this.geslaagd = geslaagd;
        this.clickable = clickable;
        this.voltijdigheden = new  String[]{};
    }
    public Vak(String id, String course, String credit, String creditPunten, int fase, int score ,boolean geslaagd, boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.creditPunten = creditPunten;
        this.fase = fase;
        this.geslaagd = geslaagd;
        this.clickable = clickable;
        this.score = score;
        this.voltijdigheden = new  String[]{};
    }

    public Vak(String id, String course, String credit, String creditPunten, int fase, boolean geslaagd,String [] voltijdigheden, boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.creditPunten = creditPunten;
        this.fase = fase;
        this.geslaagd = geslaagd;
        this.voltijdigheden = voltijdigheden;
        this.clickable = clickable;
    }

    public Vak(String id, String course, String credit, String creditPunten, int fase, int score,boolean geslaagd,String [] voltijdigheden, boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.creditPunten = creditPunten;
        this.fase = fase;
        this.geslaagd = geslaagd;
        this.voltijdigheden = voltijdigheden;
        this.clickable = clickable;
        this.score = score;
    }

    public Vak(String id, String course, String credit, boolean checked, String creditPunten,int fase, boolean geslaagd,String [] voltijdigheden, boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.checked = checked;
        this.creditPunten = creditPunten;
        this.voltijdigheden = voltijdigheden;
        this.clickable = clickable;
    }

    public Vak(String id, String course, String credit, boolean checked, String creditPunten, boolean geslaagd, int chance, int score, int fase, boolean isEnabled, String [] voltijdigheden,boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.checked = checked;
        this.creditPunten = creditPunten;
        this.geslaagd = geslaagd;
        this.chance = chance;
        this.score = score;
        this.fase = fase;
        this.isEnabled = isEnabled;
        this.voltijdigheden = voltijdigheden;
        this.clickable = clickable;
    }

    public Vak(String id, String course, String credit, boolean checked, String creditPunten, int fase, boolean geslaagd, int score, int chance, String [] voltijdigheden, boolean clickable) {
        this.id = id;
        this.course = course;
        this.credit = credit;
        this.checked = checked;
        this.creditPunten = creditPunten;
        this.score = score;
        this.chance = chance;
        this.voltijdigheden = voltijdigheden;
        this.clickable = clickable;
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

    public static int getChanceMax() {
        return chanceMax;
    }

    public String[] getVoltijdigheden() {
        return voltijdigheden;
    }

    public void setVoltijdigheden(String[] voltijdigheden) {
        this.voltijdigheden = voltijdigheden;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
