package controller;

import interfaces.IPlaylist;
import interfaces.ISong;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class XMLStrategy implements interfaces.SerializableStrategy {

    private FileOutputStream fo = null;
    private XMLEncoder encoder = null;

    private FileInputStream fi = null;
    private XMLDecoder decoder = null;

    public XMLStrategy(){
       /* try {
            ISong s = new model.Song();
            s.setTitle("No songs saved in library and playlist!");
            if (new File("library.xml").createNewFile()) {
                try (FileOutputStream out = new FileOutputStream("library.xml");
                     XMLEncoder x = new XMLEncoder(out)) {
                    x.writeObject(s);
                }
            }
            if (new File("playlist.xml").createNewFile()) {
                try (FileOutputStream out = new FileOutputStream("playlist.xml");
                     XMLEncoder x = new XMLEncoder(out)) {
                    x.writeObject( s );
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void openWritableLibrary() throws IOException {
        try{
            fo = new FileOutputStream("library.xml");
            encoder = new XMLEncoder(fo);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void openReadableLibrary() throws IOException {
        try {
            fi = new FileInputStream("library.xml");
            decoder = new XMLDecoder(fi);
        }catch ( IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public void openWritablePlaylist() throws IOException {
        try{
            fo = new FileOutputStream("playlist.xml");
            encoder = new XMLEncoder(fo);
        }catch (IOException e){
           System.out.println("open");
           e.printStackTrace();
        }


    }

    @Override
    public void openReadablePlaylist() throws IOException {
        try{
            fi = new FileInputStream("playlist.xml");
            decoder = new XMLDecoder(fi);
        }catch (IOException e){
            System.out.println("open");
            e.printStackTrace();
        }


    }

    @Override
    public void writeSong(ISong s) throws IOException {
        encoder.writeObject(s);
        encoder.flush();
    }

    @Override
    public ISong readSong() throws IOException, ClassNotFoundException {
        try {
            ISong song = (ISong) decoder.readObject();
            return song;
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public void writeLibrary(IPlaylist p) throws IOException {
        for(ISong s : p ){
            writeSong(s);
        }
    }

    @Override
    public IPlaylist readLibrary() throws IOException, ClassNotFoundException {
        IPlaylist library = new model.Playlist();
        ISong song;

        do {
            song = readSong();
            if (song != null){
                library.addSong(song);
            }
        }while (song !=null);

        return library;
    }

    @Override
    public void writePlaylist(IPlaylist p) throws IOException {
        for (ISong s: p ){
            writeSong(s);
        }
    }

    @Override
    public IPlaylist readPlaylist() throws IOException, ClassNotFoundException {
        ISong song;
        IPlaylist playlist = new model.Playlist();
        do{
            song =readSong();
            if (song != null){
                playlist.addSong(song);
            }
        }while(song != null);

        return playlist;
    }

    @Override
    public void closeWritableLibrary() {
        if (encoder != null){
            encoder.close();
        }

        try {
            if (fo != null){
                fo.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void closeReadableLibrary() {
        try{
            fi.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        decoder.close();

    }

    @Override
    public void closeWritablePlaylist() {
         encoder.close();

        try {
            fo.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void closeReadablePlaylist() {
        try{
            fi.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        decoder.close();
    }
}
