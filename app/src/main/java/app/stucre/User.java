package app.stucre;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class User {

    private String userId;
    private String Login;
    private String passwoord;
    private String naam;
    private String Email;
    private List<Vak> vakList;
    private ImageView profile;
    private Boolean [] geslaagd;

    public User() {
    }

    public User(String userId, String naam, String email, List<Vak> vakList, ImageView profile, Boolean[] geslaagd) {
        this.userId = userId;
        this.naam = naam;
        Email = email;
        this.vakList = vakList;
        this.profile = profile;
        this.geslaagd = geslaagd;
    }

    public User(String userId, String login, String passwoord, String naam, String email, List<Vak> vakList, ImageView profile, Boolean[] geslaagd) {
        this.userId = userId;
        Login = login;
        this.passwoord = passwoord;
        this.naam = naam;
        Email = email;
        this.vakList = vakList;
        this.profile = profile;
        this.geslaagd = geslaagd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPasswoord() {
        return passwoord;
    }

    public void setPasswoord(String passwoord) {
        this.passwoord = passwoord;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public List<Vak> getVakList() {
        return vakList;
    }

    public void setVakList(List<Vak> vakList) {
        this.vakList = vakList;
    }

    public ImageView getProfile() {
        return profile;
    }

    public void setProfile(ImageView profile) {
        this.profile = profile;
    }

    public Boolean[] getGeslaagd() {
        return geslaagd;
    }

    public void setGeslaagd(Boolean[] geslaagd) {
        this.geslaagd = geslaagd;
    }


}
