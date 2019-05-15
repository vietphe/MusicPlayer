package View;

import controller.Controller;
import interfaces.ISong;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class View extends BorderPane {
    //private controller controller;
    private Label titel = new Label("Title: ");
    private Label interpret = new Label("Interpret: ");
    private Label album = new Label("Album: ");

    private TextField titelTF = new TextField();
    private TextField interpretTF = new TextField();
    private TextField albumTF = new TextField();

    private Button load = new Button("Load");
    private Button save = new Button("Save");
    private Button play = new Button("Play");
    private Button stop = new Button("Stop");
    private Button next = new Button("Next");

    public Button getAddToPlaylist() {
        return addToPlaylist;
    }

    public Button getPlay() {
        return play;
    }

    public Button getLoad() {
        return load;
    }

    public Button getSave() {
        return save;
    }

    public Button getStop() { return stop; }

    public Button getNext() {
        return next;
    }

    public Button getCommit() {
        return commit;
    }

    public Button getAddAll() {
        return addAll;
    }

    public Button getDelete() { return delete; }

    private Button addToPlaylist = new Button("addToPlayList");
    private Button commit = new Button("commit");
    private Button addAll = new Button("Add all");
    private Button delete = new Button("Delete");

    private ChoiceBox choiceBox = new ChoiceBox();

    private HBox topHBox = new HBox();
    private HBox controllButton = new HBox();
    private HBox addRemoveBox = new HBox();

    private ListView<ISong> playlist = new ListView<>();
    private ListView<ISong> library = new ListView<>();

    public TextField getTitelTF() {
        return titelTF;
    }

    public TextField getInterpretTF() {
        return interpretTF;
    }

    public TextField getAlbumTF() {
        return albumTF;
    }

    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }

    public View(){

        titelTF.setPromptText("Title");
        interpretTF.setPromptText("Interpret");
        albumTF.setPromptText("Album");

        choiceBox.setMinWidth(250);
        choiceBox.setItems(FXCollections.observableArrayList("Binary strategy", "XML strategy", "JDBC strategy"));

        topHBox.getChildren().addAll(choiceBox,load,save);
        topHBox.setSpacing(4);

        addRemoveBox.getChildren().addAll(addToPlaylist,commit,delete);
        addRemoveBox.setPadding(new Insets(1,1,1,1));

        controllButton.setPadding(new Insets(2,2,4,2));
        controllButton.setSpacing(2);
        controllButton.getChildren().addAll(play,stop,next,commit);

        VBox rightControll = new VBox();
        rightControll.getChildren().addAll(titel,titelTF,interpret,interpretTF,album,albumTF,controllButton,addRemoveBox);

        playlist.setMinWidth(250);
        library.setMinWidth(250);
        rightControll.setMinWidth(200);

        library.setCellFactory(c -> {
            ListCell<ISong> cell = new ListCell<ISong>() {
                protected void updateItem(ISong myObject, boolean b) {
                    super.updateItem(myObject, b);
                    if (myObject != null) {
                        setText(myObject.getTitle());
                    } else {
                        setText("");
                    }
                }

            };
            return cell;

        });
        playlist.setCellFactory(c -> {
            ListCell<ISong> cell = new ListCell<ISong>() {
                protected void updateItem(ISong myObject, boolean b) {
                    super.updateItem(myObject, b);
                    if (myObject != null) {
                        setText(myObject.getTitle());
                    } else {
                        setText("");
                    }
                }

            };
            return cell;

        });


        //library.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //library.getSelectionModel().selectedItemProperty().addListener(l ->{

        //});


        this.setTop(topHBox);
        this.setLeft(library);
        this.setRight(rightControll);
        this.setCenter(playlist);
        this.setBottom(addAll);

        //Actions for buttons
        //play.setOnAction(event -> controller.playOrPause(playlist.getSelectionModel().getSelectedIndex()));
        //pause.setOnAction(event -> controller.pause());
        //next.setOnAction(event -> controller.next());
        //addAll.setOnAction(event -> controller.addAll());
        //addToPlaylist.setOnAction(event -> controller.addToPlaylist());
       // commit.setOnAction(event -> controller.commit(library));
        //save.setOnAction(event -> controller.saveSelection());
        //load.setOnAction(event -> controller.load("/Users/wenminhuang/Desktop/123"));

        //listview for mouseclick
        //library.setOnMouseClicked(event -> controller.updateLabel(library));
        //playlist.setOnMouseClicked(event -> controller.updateLabel(playlist));


    }

    public ListView<ISong> getPlayList(){
        return playlist;
    }
    public ListView<ISong> getLibrary(){
        return library;
    }

   /* public void addController(controller controller){
        this.controller = controller;
    }*/

}