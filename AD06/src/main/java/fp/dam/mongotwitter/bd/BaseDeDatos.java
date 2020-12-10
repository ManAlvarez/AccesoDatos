/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.bd;

import com.mongodb.*;
import com.mongodb.client.model.*;
import fp.dam.mongotwitter.json.LeerJsonConfig;
import fp.dam.mongotwitter.objetos.ConfigMongoDB;
import fp.dam.mongotwitter.objetos.Mensaxe;
import fp.dam.mongotwitter.objetos.Usuario;
import java.util.ArrayList;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;

/**
 *
 * @author manuel
 */
public class BaseDeDatos {

    private static final String COLECCION_USUARIO = "usuario";
    private static final String COLECCION_MENSAXE = "mensaxe";
    private static final ConfigMongoDB configMongoDB = LeerJsonConfig.leerJson();
    private static MongoClient mongoClient;
    private static DB database;
    private static DBCollection coleccionUsuario;
    private static DBCollection coleccionMensaxe;

    /**
     * Método para hacer la conexión a la base de datos de MongoDB.
     */
    public void conexion() {
        //Conexión con MongoDB
        String host = configMongoDB.getAddress();
        String port = configMongoDB.getPort();
        mongoClient = new MongoClient(new MongoClientURI("mongodb://" + host + ":" + port));
    }

    /**
     * Método para obtener la base de datos.
     */
    public void baseDeDatos() {
        //Collemos a base de datos que queremos. Hai que creala fora
        String dbName = configMongoDB.getDbname();
        database = mongoClient.getDB(dbName);
    }

    /**
     * Método para crear colecciones en la base de datos.
     */
    public void crearColeccion() {
        //Coleccion alumno. Hai que creala fora
        coleccionUsuario = database.getCollection(COLECCION_USUARIO);
        coleccionMensaxe = database.getCollection(COLECCION_MENSAXE);
    }

    /**
     * Método para consultar si existe un usuario pasado como parámetro en los
     * documentos de la colección usuario de la bd.
     *
     * @param usuario
     * @return
     */
    public boolean consultaNoExisteUsuario(Usuario usuario) {
        DBObject query = new BasicDBObject("username", usuario.getUsername());
        DBObject cursor = coleccionUsuario.findOne(query);
        if (cursor == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para consultar si existe un usuario pasado como parámetro en los
     * documentos de la colección usuario de la bd.
     *
     * @param username
     * @return
     */
    public boolean consultaNoExisteUsuario(String username) {
        DBObject query = new BasicDBObject("username", username);
        DBObject cursor = coleccionUsuario.findOne(query);
        return cursor == null;
    }

    /**
     * Método para insertar un documento en la colección usuario.
     *
     * @param usuario
     */
    public void insertarUsuario(Usuario usuario) {
        DBObject novoUsuario = new BasicDBObject()
                .append("nome", usuario.getNome())
                .append("username", usuario.getUsername())
                .append("password", usuario.getPassword());
        coleccionUsuario.insert(novoUsuario);
        System.out.println("Inserción realizada con éxito");
    }

    /**
     * Método para consultar el password del usuario pasado como parámetro.
     *
     * @param usuario
     * @return
     */
    public boolean consultarPass(Usuario usuario) {
        DBObject query = new BasicDBObject("username", usuario.getUsername());
        try (DBCursor cursor = coleccionUsuario.find(query)) {
            while (cursor.hasNext()) {
                String passwordBD = cursor.next().get("password").toString();
                if (usuario.getPassword().equals(passwordBD)) {
                    cursor.close();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método para obtener el documento del usuario indicado en el parámetro.
     *
     * @param usuario
     * @return
     */
    public Usuario ObtenerUsuario(Usuario usuario) {
        Usuario usuarioLogueado = null;
        DBObject query = new BasicDBObject("username", usuario.getUsername());
        try (DBCursor cursor = coleccionUsuario.find(query)) {
            while (cursor.hasNext()) {
                String nome = cursor.next().get("nome").toString();
                usuarioLogueado = new Usuario(nome, usuario.getUsername(), usuario.getPassword());
            }
        }
        return usuarioLogueado;
    }

    /**
     * Método para insertar un documento en la coleccion mensaxe de la bd.
     *
     * @param mensaxe
     */
    public void insertarMensaxe(Mensaxe mensaxe) {
        DBObject novaMensaxe = new BasicDBObject()
                .append("text", mensaxe.getText())
                .append("user", new BasicDBObject()
                        .append("nome", mensaxe.getUser().getNome())
                        .append("username", mensaxe.getUser().getUsername()))
                .append("date", mensaxe.getDate())
                .append("hashtags", mensaxe.getHashtags());
        coleccionMensaxe.insert(novaMensaxe);
        System.out.println("Inserción realizada con éxito");
    }

    /**
     * Método para listar todos los documentos de la colección mensaxe de la bd.
     *
     * @return
     */
    public ArrayList<DBObject> seleccionarMensaxes() {
        ArrayList<DBObject> listaMensaxes = new ArrayList<>();
        try (DBCursor cursor = coleccionMensaxe.find()) {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                listaMensaxes.add(object);
            }
        }
        return listaMensaxes;
    }

    /**
     * Método para listar los documentos de la colección usuario que contengan
     * la cadena de texto pasada como parámetro.
     *
     * @param cadena
     * @return
     */
    public ArrayList<DBObject> seleccionarUsuarios(String cadena) {
        ArrayList<DBObject> listaUsuarios = new ArrayList<>();
        Bson filter = Filters.or(Filters.regex("username", cadena), Filters.regex("nome", cadena));
        DBObject query = new BasicDBObject(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        try (DBCursor cursor = coleccionUsuario.find(query)) {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                listaUsuarios.add(object);
            }
        }
        return listaUsuarios;
    }

    /**
     * Método para actualizar un documento de la colección usuario para añadirle
     * a los usuarios que sigue.
     *
     * @param usuario
     * @param usuarioASeguir
     */
    public void actualizarUsuariosSigo(Usuario usuario, String usuarioASeguir) {
        ArrayList<String> follows = new ArrayList<>();
        Bson filter = Filters.eq("username", usuario.getUsername());
        DBObject query = new BasicDBObject(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        try (DBCursor cursor = coleccionUsuario.find(query)) {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                if (!(object.get("follows") == null)) {
                    follows = (ArrayList<String>) object.get("follows");
                }
            }
        }
        follows.add(usuarioASeguir);
        Bson filterUp = Filters.eq("username", usuario.getUsername());
        DBObject queryUp = new BasicDBObject(filterUp.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        Bson updateAux = Updates.set("follows", follows);
        DBObject update = new BasicDBObject(updateAux.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        coleccionUsuario.update(queryUp, update);
    }

    /**
     * Método para listar los usuarios que sigue un usuario pasado como
     * parámetro.
     *
     * @param usuario
     * @return
     */
    public ArrayList<String> seleccionarSeguidores(Usuario usuario) {
        ArrayList<String> listaUsuariosSeguidos = new ArrayList<>();
        Bson filter = Filters.eq("username", usuario.getUsername());
        DBObject query = new BasicDBObject(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        try (DBCursor cursor = coleccionUsuario.find(query)) {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                listaUsuariosSeguidos = (ArrayList<String>) object.get("follows");
            }
        }
        return listaUsuariosSeguidos;
    }

    /**
     * Método para listar todos los documentos de la colección mensaxes que que
     * pertenezca al username que le pasamos como parámetro.
     *
     * @param username
     * @return
     */
    public ArrayList<DBObject> seleccionarMensaxes(String username) {
        ArrayList<DBObject> listaMensaxes = new ArrayList<>();
        Bson filter = Filters.eq("user.username", username);
        DBObject query = new BasicDBObject(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        try (DBCursor cursor = coleccionMensaxe.find(query)) {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                listaMensaxes.add(object);
            }
        }
        return listaMensaxes;
    }

    /**
     * Método para listar todos los mensajes que contengan el hashtag pasado
     * como parámetro.
     *
     * @param hashtag
     * @return
     */
    public ArrayList<DBObject> seleccionarHashtags(String hashtag) {
        ArrayList<DBObject> listaMensaxes = new ArrayList<>();
        Bson filter = Filters.in("hashtags", hashtag);
        DBObject query = new BasicDBObject(filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()));
        try (DBCursor cursor = coleccionMensaxe.find(query)) {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                listaMensaxes.add(object);
            }
        }
        return listaMensaxes;
    }
}
