package controller;



import interfaces.IPlaylist;
import interfaces.ISong;
import interfaces.SerializableStrategy;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Model;
import model.Song;
import View.View;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class Controller {

    private Model model;
    private View view;

    private ListView<interfaces.ISong> listView;
    private MediaPlayer mediaPlayer;

    private SerializableStrategy saveStrat;
    private SerializableStrategy loadStrat;


    public void link(View view, Model model) throws RemoteException{

        this.view = view;
        this.model= model;
        importFiles();
        listView = view.getPlayList();
        view.getLibrary().setItems(model.getLibrary());
        view.getPlayList().setItems(model.getPlaylist());

       /* if(model.getLibrary().getList().size()!=0){
            view.getLibrary().getItems().addAll(model.getPlaylist().getList());
        }else System.out.println("No songs found in the playlist database");
*/

        view.getAddToPlaylist().setOnAction(event -> addToPlaylist());
        view.getDelete().setOnAction(event ->delete());
        view.getPlay().setOnAction(event -> playOrPause());
        view.getStop().setOnAction(event -> stop());
        view.getNext().setOnAction(event -> next());
        view.getCommit().setOnAction(event -> commit(view.getLibrary()));
        view.getAddAll().setOnAction(event -> addAll());
        view.getLoad().setOnAction(event -> load());
        view.getSave().setOnAction(event -> save());
        view.getPlayList().setOnMouseClicked(event -> updateLabel(view.getPlayList()));
        view.getLibrary().setOnMouseClicked(event -> updateLabel(view.getLibrary()));



    }




    private void importFiles() {
        IDGenerator idGenerator = new IDGenerator();
        try{
            File file = new File( "C:\\Users\\ENVY\\Desktop\\Musik" );
            File[] fs = file.listFiles();

            for (File f:fs){
                String pathf = f.getAbsolutePath();
                if(pathf.endsWith(".mp3")){
                    Song song = new Song( IDGenerator.getNextID(),f.getAbsolutePath(),null,null,f.getName());
                    model.getLibrary().add(song);
                }
            }
            System.out.println(model.getLibrary().size()+" songs imported");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void play(interfaces.ISong song) {
        Media media = new Media(new File(song.getPath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                if ((model.getPlaylist().size() - 1) >= (view.getPlayList().getSelectionModel().getSelectedIndex() + 1)) {

                    play(model.getPlaylist().get(view.getPlayList().getSelectionModel().getSelectedIndex() + 1));
                    //view.getPlayList().getSelectionModel().selectIndices(view.getLvPlaylist().getSelectionModel().getSelectedIndex() + 1);
                    updateLabel(view.getPlayList());

                } else System.out.println("End of Playlist");
                return;
            }
        });
    }


    public void playOrPause() {

        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            }else{
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                }

            }

        }
        if ((mediaPlayer == null || mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) && model.getLibrary().size() != 0) {
            if (!listView.getSelectionModel().isEmpty()) {
                try {
                        play(model.getPlaylist().get(view.getPlayList().getSelectionModel().getSelectedIndex()));
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }
            }


    }



    public void next() {
        if(model.getPlaylist().size() != 0 && mediaPlayer !=null){
            mediaPlayer.stop();
            play(model.getPlaylist().get((view.getPlayList().getSelectionModel().getSelectedIndex()+1)));
            view.getPlayList().getSelectionModel().selectIndices((view.getPlayList().getSelectionModel().getSelectedIndex()+1)%model.getPlaylist().size());
            updateLabel(view.getPlayList());
        }
    }

    public void stop() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    public void addAll() {
        int a = model.getPlaylist().size();
        for(int i = 0 ; i<view.getLibrary().getItems().size();i++){
            model.getPlaylist().add(a + i, model.getLibrary().get(i));
            //view.getPlayList().getItems().addAll(model.getLibrary().get(i));
        }
    }

    public void addToPlaylist() {
        ObservableList<ISong>  addlist = view.getLibrary().getSelectionModel().getSelectedItems();
        ObservableList<ISong>  exlist  = view.getPlayList().getItems();
        int a = model.getPlaylist().size();
        int i = 0 ;


        for(ISong m :addlist){
            int counter = 0;
            for (ISong n:exlist) {
                if ((m.getPath().equals(n.getPath())))
                    counter = 1;
            }

                if (counter != 1) {
                  //  view.getPlayList().getItems().add(m);
                    model.getPlaylist().add(i + a, m);
                    i = i + 1;
                }
        }
    }


    public void delete() {
        ObservableList<ISong> deletelist = view.getPlayList().getSelectionModel().getSelectedItems();
        for (ISong s: deletelist){
            view.getPlayList().getItems().removeAll(s);

        }

    }

    public void updateLabel(ListView<interfaces.ISong> listView) {
        if(!listView.getSelectionModel().isEmpty()){
            view.getTitelTF().setText(listView.getSelectionModel().getSelectedItem().getTitle());
            view.getAlbumTF().setText(listView.getSelectionModel().getSelectedItem().getAlbum());
            view.getInterpretTF().setText(listView.getSelectionModel().getSelectedItem().getInterpret());
        }else
            System.out.println("please select");
    }


    public void commit(ListView<interfaces.ISong> listView) {
        if(model.getLibrary().size() != 0 && !listView.getSelectionModel().isEmpty()){
            try{
                model.getLibrary().get(listView.getSelectionModel().getSelectedIndex()).setTitle(view.getTitelTF().getText());
                model.getLibrary().get(listView.getSelectionModel().getSelectedIndex()).setAlbum(view.getAlbumTF().getText());
                model.getLibrary().get(listView.getSelectionModel().getSelectedIndex()).setInterpret(view.getInterpretTF().getText());
                view.getLibrary().refresh();
                view.getPlayList().refresh();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }


    private void save() {
        if (!view.getChoiceBox().getSelectionModel().isEmpty()) {

            if (view.getChoiceBox().getSelectionModel().getSelectedIndex() == 0) {//BinaryStrategy
                saveStrat = new BinaryStrategy();
            }

            if (view.getChoiceBox().getSelectionModel().getSelectedIndex() == 1) {
                saveStrat = new XMLStrategy();
            }

            if (view.getChoiceBox().getSelectionModel().getSelectedIndex() == 2) {
                saveStrat = new JDBCStrategy();
            }

       /* if (view.getChoiceBox().getSelectionModel().getSelectedIndex()==3){
            saveStrat = new OpenJPAStrategy();
        }*/

            try {
                System.out.println( "Saving" );
                saveStrat.openWritableLibrary();
                saveStrat.writeLibrary( model.getLibrary());
                saveStrat.closeWritableLibrary();
                saveStrat.openWritablePlaylist();
                saveStrat.writePlaylist( model.getPlaylist() );
               // System.out.println ( model.getPlaylist () );
                saveStrat.closeWritablePlaylist();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println( "Saved" );

        }
    }



    public void load() /*throws ClassNotFoundException */{
        if (!view.getChoiceBox().getSelectionModel().isEmpty()) {
            if (view.getChoiceBox().getSelectionModel().getSelectedIndex() == 0) {
                loadStrat = new BinaryStrategy();
            }
            if (view.getChoiceBox().getSelectionModel().getSelectedIndex() == 1) {
                loadStrat = new XMLStrategy();
            }
            if (view.getChoiceBox().getSelectionModel().getSelectedIndex() == 2) {
                loadStrat = new JDBCStrategy();
            }

            try{
                System.out.println("loading");

                loadStrat.openReadableLibrary();
                IPlaylist library = loadStrat.readLibrary();

                model.getLibrary().clear();
                view.getLibrary().getItems().clear();

                for (ISong song : library) {
                    model.getLibrary().add(song);
                    System.out.println(song);
                    //view.getLibrary().getItems().add(song);
                }



                loadStrat.openReadablePlaylist();
                IPlaylist playlist = loadStrat.readPlaylist();

                model.getPlaylist().clear();
                view.getPlayList().getItems().clear();

                for (ISong song : playlist) {
                    model.getPlaylist().add(model.getLibrary().findSongByID(song.getId()));
                    System.out.println(song.getTitle());
                    System.out.println(song.getId());
                    view.getPlayList().refresh();
                    //view.getPlayList().getItems().add(model.getLibrary().findSongByID(song.getId()));
                    }
                System.out.println( model.getPlaylist() );

                System.out.println("loaded");
                } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                loadStrat.closeReadablePlaylist();
                loadStrat.closeReadableLibrary();
            }


        }


    }
}



