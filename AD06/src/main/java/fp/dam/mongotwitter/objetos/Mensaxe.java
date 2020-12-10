/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.objetos;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author manuel
 */
public class Mensaxe {

    private String text;
    private Usuario user;
    private Date date;
    private ArrayList<String> hashtags = new ArrayList<>();

    public Mensaxe(String text, Usuario user, Date date) {
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

}
