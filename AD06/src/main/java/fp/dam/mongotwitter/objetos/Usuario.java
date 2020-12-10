/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.objetos;

import java.util.ArrayList;

/**
 *
 * @author manuel
 */
public class Usuario {

    private String nome;
    private String username;
    private String password;
    private ArrayList<String> follows = new ArrayList<>();

    public Usuario(String nome, String username, String password) {
        this.nome = nome;
        this.username = username;
        this.password = password;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFollows() {
        return follows;
    }

    public void setFollows(ArrayList<String> follows) {
        this.follows = follows;
    }

}
