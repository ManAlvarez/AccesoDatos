/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.bd;

import com.mongodb.DBObject;
import fp.dam.mongotwitter.menus.MenuInicio;
import fp.dam.mongotwitter.objetos.Mensaxe;
import fp.dam.mongotwitter.objetos.Usuario;
import fp.dam.mongotwitter.utilidades.Utiles;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author manuel
 */
public class Controlador {

    private static final BaseDeDatos bd = new BaseDeDatos();
    private static final Utiles utiles = new Utiles();
    private static Usuario usuario;
    private static Usuario usuarioLogueado;
    private static Mensaxe mensaxe;

    /**
     * Método para configurar la base de datos.
     */
    public void confInical() {
        bd.conexion();
        bd.baseDeDatos();
        bd.crearColeccion();
    }

    /**
     * Método para registrar un usuario.
     */
    public void rexistrarUsuario() {
        String nome = utiles.insertarDatosString("Inserta o nome completo do usuario: ");
        String username = utiles.insertarDatosString("Inserta o username: ");
        String password = utiles.insertarDatosString("Inserta o contrasinal para o usuario: ");
        usuario = new Usuario(nome, username, password);
        if (bd.consultaNoExisteUsuario(usuario)) {
            bd.insertarUsuario(usuario);
        } else {
            System.out.println("El usuario xa existe.");
            String decision = utiles.tomaDecision("Deseas intetalo de novo? (SI/NO): ");
            if (decision.equalsIgnoreCase("SI")) {
                rexistrarUsuario();
            }
        }
        utiles.pulsarEnter();
    }

    /**
     * Método para comprobar el logueo de un usuario.
     */
    public void loguearUsuario() {
        String username = utiles.insertarDatosString("Inserta o username: ");
        String password = utiles.insertarDatosString("Inserta o contrasinal: ");
        usuario = new Usuario(username, password);
        if (bd.consultarPass(usuario)) {
            usuarioLogueado = bd.ObtenerUsuario(usuario);
            MenuInicio.menu(usuarioLogueado);
        } else {
            System.out.println("Usuario o contrasinal incorrecto.");
        }
        utiles.pulsarEnter();
    }

    /**
     * Método para escribir un mensaje y listar los hashtags.
     *
     * @param usuario
     */
    public void escribirMensaxe(Usuario usuario) {
        Date fecha = new Date();
        String text = utiles.insertarDatosString("Inserta el mensaxe: ");
        String hashtagTemp = "";
        ArrayList<String> hashtags = new ArrayList<>();
        boolean engadirHashtag = false;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '#') {
                engadirHashtag = true;
            }
            if (engadirHashtag == true) {
                if (i == text.length()) {
                    hashtags.add(hashtagTemp);
                    hashtagTemp = "";
                    engadirHashtag = false;
                }
                if (text.charAt(i) == ' ') {
                    hashtags.add(hashtagTemp);
                    hashtagTemp = "";
                    engadirHashtag = false;
                } else if (text.charAt(i) != '#') {
                    hashtagTemp += text.charAt(i);
                }
            }
        }
        if (!hashtagTemp.isEmpty()) {
            hashtags.add(hashtagTemp);
            hashtagTemp = "";
        }
        mensaxe = new Mensaxe(text, usuario, fecha);
        mensaxe.setHashtags(hashtags);
        bd.insertarMensaxe(mensaxe);
        utiles.pulsarEnter();
    }

    /**
     * Método para ver todos los documentos de la colección mensaxes paginados
     * de 5 en 5.
     */
    public void verTodasAsMensaxes() {
        int contador = 0;
        ArrayList<DBObject> listaMensaxes = bd.seleccionarMensaxes();
        if (!listaMensaxes.isEmpty()) {
            for (Object mensaxeAux : listaMensaxes) {
                System.out.println(mensaxeAux);
                contador++;
                if (contador == 5) {
                    contador = 0;
                    utiles.pulsarEnter("Pulsa enter para pasar a seguinte páxina.");
                }
            }
        } else {
            System.out.println("No hay mensaxes para mostrar.");
        }
        utiles.pulsarEnter();
    }

    /**
     * Método para buscar los usuarios que contengan una cadena de texto.
     */
    public void buscarUsuario() {
        String cadena = utiles.insertarDatosString("Inserta el username del usuario que deseas buscar: ");
        ArrayList<DBObject> usuarios = bd.seleccionarUsuarios(cadena);
        usuarios.forEach((usuarioAux) -> {
            System.out.println(usuarioAux);
        });
        utiles.pulsarEnter();
    }

    /**
     * Método para seguir a un usuario.
     *
     * @param usuario
     */
    public void seguirUsuario(Usuario usuario) {
        String username = utiles.insertarDatosString("Inserta el usuario que deseas seguir: ");
        if (!bd.consultaNoExisteUsuario(username)) {
            bd.actualizarUsuariosSigo(usuario, username);
            System.out.println("Has empezado a seguir a " + username);
        } else {
            System.out.println("El usuario no existe.");
        }
        utiles.pulsarEnter();
    }

    /**
     * Método para ver los mensajes de los usuarios que sigo.
     *
     * @param usuario
     */
    public void verMensaxesDeUsuariosQueSigo(Usuario usuario) {
        int contador = 0;
        ArrayList<String> seguidos = bd.seleccionarSeguidores(usuario);
        if (seguidos != null) {
            for (String seguido : seguidos) {
                ArrayList<DBObject> listaMensaxes = bd.seleccionarMensaxes(seguido);
                if (!listaMensaxes.isEmpty()) {
                    System.out.println("Mensaxes de: " + seguido);
                    for (Object mensaxeAux : listaMensaxes) {
                        System.out.println(mensaxeAux);
                        contador++;
                        if (contador == 5) {
                            contador = 0;
                            utiles.pulsarEnter("Pulsa enter para pasar a seguinte páxina.");
                        }
                    }
                } else {
                    System.out.println("No hay mensaxes para mostrar del usuario: " + seguido);
                }
            }
        } else {
            System.out.println("No estas seguiendo a ningún usuario.");
        }
        utiles.pulsarEnter();
    }

    /**
     * Método para buscar los mensajes que contengan el hashtag introducido.
     */
    public void buscarHashtag() {
        int contador = 0;
        String hashtag = utiles.insertarDatosString("Inserta el hashtag que deseas buscar: ");
        hashtag = hashtag.replace("#", "");
        ArrayList<DBObject> listaMensaxes = bd.seleccionarHashtags(hashtag);
        if (!listaMensaxes.isEmpty()) {
            for (Object mensaxeAux : listaMensaxes) {
                System.out.println(mensaxeAux);
                contador++;
                if (contador == 5) {
                    contador = 0;
                    utiles.pulsarEnter("Pulsa enter para pasar a seguinte páxina.");
                }
            }
        } else {
            System.out.println("No hay mensaxes para mostrar.");
        }
        utiles.pulsarEnter();
    }

}
