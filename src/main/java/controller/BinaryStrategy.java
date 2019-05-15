package controller;

import interfaces.SerializableStrategy;
import interfaces.IPlaylist;
import interfaces.ISong;
import model.Playlist;
import java.io.*;


public class BinaryStrategy implements SerializableStrategy {

    private FileOutputStream fo = null;
    private ObjectOutputStream oo = null;

    private FileInputStream fi = null;
    private ObjectInputStream oi = null;

    BinaryStrategy(){
        /*try{
            ISong s = new model.Song();
            s.setTitle("No Song saved in library and in playlist!");
            if (new File("library.ser").createNewFile()){
                try(FileOutputStream out = new FileOutputStream("library.ser");
                    ObjectOutputStream ob = new ObjectOutputStream(out)){
                    ob.writeObject(s);
                }
            }
            if (new File("playlist.ser").createNewFile()){
                try(FileOutputStream out = new FileOutputStream("playlist.ser");
                    ObjectOutputStream ob = new ObjectOutputStream(out)){
                    ob.writeObject(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void openWritableLibrary() throws IOException {
            fo = new FileOutputStream("library.ser");
            oo = new ObjectOutputStream( fo );
    }

    @Override
    public void openReadableLibrary() throws IOException {

            fi = new FileInputStream("library.ser");
            oi = new ObjectInputStream( fi );

    }

    @Override
    public void openWritablePlaylist() throws IOException {

            fo = new FileOutputStream("playlist.ser");
            oo = new ObjectOutputStream( fo );

    }

    @Override
    public void openReadablePlaylist() throws IOException {

            fi = new FileInputStream("playlist.ser");
            oi = new ObjectInputStream( fi );

    }

    @Override
    public void writeSong(ISong s) throws IOException {
        oo.writeObject ( s );
        oo.flush();
    }

    @Override
    public ISong readSong() throws IOException, ClassNotFoundException {

        try{
            ISong song;
            song = (ISong) oi.readObject();
            return song;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void writeLibrary(IPlaylist p) throws IOException {
        for (ISong s:p){
            writeSong(s);
        }
    }

    @Override
    public IPlaylist readLibrary() throws IOException, ClassNotFoundException {
       IPlaylist library = new model.Playlist();
       ISong song ;
       while(fi.available()>0){
          song = readSong();
          library.addSong(song);
       }
        return library;
    }

    @Override
    public void writePlaylist(IPlaylist p) throws IOException {
        for (ISong s : p){
            writeSong(s);
        }
    }

    @Override
    public IPlaylist readPlaylist() throws IOException, ClassNotFoundException {
        ISong song ;
        IPlaylist playlist = new Playlist();

        while(fi.available()>0){
            song = readSong();
            playlist.addSong ( song );
        }
        return playlist;
    }

    @Override
    public void closeWritableLibrary() {
        if (oo != null){
            try{
                oo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fo != null){
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void closeReadableLibrary() {
        if (oi != null){
            try {
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fi != null){
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void closeWritablePlaylist() {
        if(oo != null){
            try {
                oo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fo != null){
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void closeReadablePlaylist() {
        if (oi != null){
            try{
                oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fi != null){
            try {
                fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
