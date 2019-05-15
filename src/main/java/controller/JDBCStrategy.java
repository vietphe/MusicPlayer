package controller;

import interfaces.IPlaylist;
import interfaces.ISong;
import model.Playlist;
import model.Song;


import java.io.IOException;
import java.sql.*;


public class JDBCStrategy implements interfaces.SerializableStrategy {
    private Connection connection= null;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private String db = "jdbc:sqlite:music.db";
    private String table;

    JDBCStrategy(){
        try{
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private void emptyTable(String name){
        try{
            connection = DriverManager.getConnection(db);
            pstmt = connection.prepareStatement("DELETE FROM "+name+" ;");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chooseTable(String tableName){
        table = tableName;
    }

    @Override
    public void openWritableLibrary() throws IOException {
        try{
            connection = DriverManager.getConnection(db);
            PreparedStatement createLibrary = connection.prepareStatement("CREATE TABLE IF NOT EXISTS library(id Integer NOT NULL ,path TEXT NOT NULL ,interpret TEXT,album TEXT,title TEXT);");
            createLibrary.executeUpdate();
            emptyTable("library");
            //pstmt = connection.prepareStatement("INSERT INTO library(id,path,interpret,album,title)VALUES (?,?,?,?,?);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openReadableLibrary() throws IOException {
        try{
            connection =DriverManager.getConnection(db);
            PreparedStatement createLibrary = connection.prepareStatement("CREATE  TABLE IF NOT EXISTS library(id INTEGER NOT NULL ,path TEXT NOT NULL , interpret TEXT,album TEXT,title TEXT);");
            createLibrary.executeUpdate();


            //pstmt = connection.prepareStatement("SELECT * FROM library;");
            //rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openWritablePlaylist() throws IOException {
        try{
            connection = DriverManager.getConnection(db);
            PreparedStatement createLibrary = connection.prepareStatement("CREATE TABLE IF NOT EXISTS playlist(id Integer NOT NULL ,path TEXT NOT NULL ,interpret TEXT,album TEXT,title TEXT);");
            createLibrary.executeUpdate();
            emptyTable("playlist");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void openReadablePlaylist() throws IOException {
        try{
            connection =DriverManager.getConnection(db);
            PreparedStatement createLibrary = connection.prepareStatement("CREATE  TABLE IF NOT EXISTS playlist(id INTEGER ,path TEXT, interpret TEXT,album TEXT,title TEXT);");
            createLibrary.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void writeSong(ISong s) throws IOException {
        try {
            pstmt = connection.prepareStatement("INSERT INTO "+table+"( id,path,album,interpret,title)VALUES (?,?,?,?,?);");
            pstmt.setLong(1, ( s.getId()));
            pstmt.setString(2, s.getPath());
            pstmt.setString(3, s.getInterpret());
            pstmt.setString(4, s.getAlbum());
            pstmt.setString(5, s.getTitle());

            pstmt.execute();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ISong readSong() throws IOException, ClassNotFoundException {
        ISong song = new Song();
        try {

                song.setId(rs.getLong("id"));
                song.setAlbum(rs.getString("interpret"));
                song.setPath(rs.getString("path"));
                song.setTitle(rs.getString("title"));
                song.setInterpret(rs.getString("interpret"));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return song;
    }

    @Override
    public void writeLibrary(IPlaylist p) throws IOException {
        try{
            chooseTable("library");
            for(ISong s: p){
                System.out.println("Song:"+s.getTitle());
                writeSong(s);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public IPlaylist readLibrary() throws IOException, ClassNotFoundException {
        IPlaylist library = new Playlist();
        try{
            pstmt = connection.prepareStatement("SELECT * FROM library;");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                library.addSong(readSong());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public void writePlaylist(IPlaylist p) throws IOException {
        try{
            chooseTable("playlist");
            for(ISong s: p){
                System.out.println("Song:"+s.getTitle());
                writeSong(s);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        /*try{
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("DROP table if EXISTS teilSongs");
            statement.executeUpdate("create table teilSongs(id INTEGER not null ,path TEXT ,interpret TEXT, album TEXT,title TEXT)");
            Iterator<interfaces.ISong> itr = p.iterator();
            while(itr.hasNext()){
                pp = connection.prepareStatement("INSERT INTO teilSongs(id,path,interpret,album,title)values(?,?,?,?,?)");
                writeSong(itr.next());
            }

        }catch (SQLException e){
            e.printStackTrace();
        }*/
    }

    @Override
    public IPlaylist readPlaylist() throws IOException, ClassNotFoundException {
        IPlaylist library = new Playlist();
        try{
            pstmt = connection.prepareStatement("SELECT * FROM playlist;");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                library.addSong(readSong());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return library;
    }

    @Override
    public void closeWritableLibrary() {
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void closeReadableLibrary() {
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void closeWritablePlaylist() {
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void closeReadablePlaylist() {
        try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
