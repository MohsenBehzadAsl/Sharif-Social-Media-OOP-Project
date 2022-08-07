package Controllers.PostControllers;

import Controllers.MyHomePostPageController;
import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
import DataBase.DataBase;
import View.Controller;
import component.Comment;
import component.Post;
import component.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import DataBase.UpdateSqlTable;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;

public class PostController {


    public MyHomePostPageController getMyHomePostPageController() {
        return myHomePostPageController;
    }

    public void setMyHomePostPageController(MyHomePostPageController myHomePostPageController) {
        this.myHomePostPageController = myHomePostPageController;
    }

    public MyHomePostPageController myHomePostPageController;

    private LinkedHashMap<String,Integer> helpToPlot1=new LinkedHashMap<>();
    private LinkedHashMap<String,Integer> helpToPlot2=new LinkedHashMap<>();

    public LinkedHashMap<String, Integer> getHelpToPlot1() {
        return helpToPlot1;
    }

    public void setHelpToPlot1(LinkedHashMap<String, Integer> helpToPlot1) {
        this.helpToPlot1 = helpToPlot1;
    }

    public LinkedHashMap<String, Integer> getHelpToPlot2() {
        return helpToPlot2;
    }

    public void setHelpToPlot2(LinkedHashMap<String, Integer> helpToPlot2) {
        this.helpToPlot2 = helpToPlot2;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    @FXML
    private GridPane imagePost;


    public GridPane getImagePost() {
        return imagePost;
    }

    public void setImagePost(GridPane imagePost) {
        this.imagePost = imagePost;
    }



    public CommentController commentController;

    public CommentController getCommentController() {
        return commentController;
    }

    public void setCommentController(CommentController commentController) {
        this.commentController = commentController;
    }


    @FXML
    private Circle userProfile;

    public Circle getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Circle userProfile) {
        this.userProfile = userProfile;
    }

    @FXML
    private GridPane all;

    @FXML
    private GridPane left;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label username;

    @FXML
    private Label numofLike;

    @FXML
    private Label numOfComments;

    @FXML
    private Label numOfViews;

    @FXML
    private ImageView liked;

    @FXML
    private ImageView allShowAnalyse;

    public GridPane getLeft() {
        return left;
    }

    public void setLeft(GridPane left) {
        this.left = left;
    }

    public GridPane getHelpInListener() {
        return helpInListener;
    }

    public void setHelpInListener(GridPane helpInListener) {
        this.helpInListener = helpInListener;
    }

    public GridPane getMessageGridPane() {
        return messageGridPane;
    }

    public void setMessageGridPane(GridPane messageGridPane) {
        this.messageGridPane = messageGridPane;
    }

    public TextArea getCommentTextArea() {
        return messageTextArea;
    }

    public void setCommentTextArea(TextArea messageTextArea) {
        this.messageTextArea = messageTextArea;
    }

    public GridPane getHelpShowButtonsInListener() {
        return helpShowButtonsInListener;
    }

    public void setHelpShowButtonsInListener(GridPane helpShowButtonsInListener) {
        this.helpShowButtonsInListener = helpShowButtonsInListener;
    }

    public Label getDeletOrVisit1() {
        return deletOrVisit1;
    }

    public void setDeletOrVisit1(Label deletOrVisit1) {
        this.deletOrVisit1 = deletOrVisit1;
    }

    public boolean isCommentShown() {
        return commentShown;
    }

    public void setCommentShown(boolean commentShown) {
        this.commentShown = commentShown;
    }

    @FXML
    private TextArea textArea;

    @FXML
    private GridPane helpInListener;

    @FXML
    private Label where;

    @FXML
    private VBox showLists;

    @FXML
    private ImageView imageOfPostRectangle;

    public ImageView getImageOfPostRectangle() {
        return imageOfPostRectangle;
    }

    public void setImageOfPostRectangle(ImageView imageOfPostRectangle) {
        this.imageOfPostRectangle = imageOfPostRectangle;
    }

    public VBox getMainComment() {
        return mainComment;
    }

    public void setMainComment(VBox mainComment) {
        this.mainComment = mainComment;
    }

    @FXML
    private VBox mainComment;

    @FXML
    private GridPane messageGridPane;

    @FXML
    private TextArea messageTextArea;


    @FXML
    private GridPane helpShowButtonsInListener;



    @FXML
    private ImageView BanComment;

    @FXML
    private ImageView deletePost;

    @FXML
    private Label banCommentOrFollow;

    @FXML
    private Label deletOrVisit;

    @FXML
    private GridPane analyzePost;

    @FXML
    private ImageView sender;

    @FXML
    private Label deletOrVisit1;

    private boolean commentShown=false;

    public void initializer(){
        textArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            all.setPrefHeight(textArea.getPrefHeight()*100/67);
            if (all.getPrefHeight()<=475){
                all.setPrefHeight(475);
            }
            left.getRowConstraints().get(0).setPercentHeight(80/left.getHeight()*100);
            left.getRowConstraints().get(2).setPercentHeight(80/left.getHeight()*100);
            left.getRowConstraints().get(1).setPercentHeight(100-(80/left.getHeight()*100+80/left.getHeight()*100));

            helpInListener.getRowConstraints().get(0).setPercentHeight(80/left.getHeight()*100/2);
            helpShowButtonsInListener.getRowConstraints().get(0).setPercentHeight(80/left.getHeight()*100);
        });
    }


    private int commentCounter;

    public int getCommentCounter() {
        return commentCounter;
    }

    public void setCommentCounter(int commentCounter) {
        this.commentCounter = commentCounter;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post post;


    public ImageView getBanComment() {
        return BanComment;
    }

    public void setBanComment(ImageView banComment) {
        BanComment = banComment;
    }

    public ImageView getDeletePost() {
        return deletePost;
    }

    public void setDeletePost(ImageView deletePost) {
        this.deletePost = deletePost;
    }

    public GridPane getAnalyzePost() {
        return analyzePost;
    }

    public void setAnalyzePost(GridPane analyzePost) {
        this.analyzePost = analyzePost;
    }



    public void showButtons(MouseEvent mouseEvent) {
        if (commentShown){
            all.getColumnConstraints().get(2).setPercentWidth(0);
        }

        all.getChildren().get(2).setVisible(true);
        if (all.getColumnConstraints().get(1).getPercentWidth()==0){
            if (all.getColumnConstraints().get(2).getPercentWidth()==0) {
                all.getColumnConstraints().get(1).setPercentWidth(18);
                all.getColumnConstraints().get(0).setPercentWidth(82);
            }else{
                all.getColumnConstraints().get(2).setPercentWidth(28);
                all.getColumnConstraints().get(1).setPercentWidth(18);
                all.getColumnConstraints().get(0).setPercentWidth(54);
            }
        }else{
            if (all.getColumnConstraints().get(2).getPercentWidth()==0) {
                all.getChildren().get(2).setVisible(false);
                all.getColumnConstraints().get(1).setPercentWidth(0);
                all.getColumnConstraints().get(0).setPercentWidth(100);
            }else{
                all.getChildren().get(2).setVisible(false);
                all.getColumnConstraints().get(2).setPercentWidth(32);
                all.getColumnConstraints().get(1).setPercentWidth(0);
                all.getColumnConstraints().get(0).setPercentWidth(70);
            }
        }
    }





    public void back(MouseEvent mouseEvent) throws SQLException, IOException, ClassNotFoundException {
        if (helpInListener.getRowConstraints().get(1).getPercentHeight()==0) {
            if (all.getColumnConstraints().get(1).getPercentWidth() != 0) {
                all.getColumnConstraints().get(2).setPercentWidth(0);
                all.getColumnConstraints().get(1).setPercentWidth(30);
                all.getColumnConstraints().get(0).setPercentWidth(70);
            } else {
                all.getColumnConstraints().get(2).setPercentWidth(0);
                all.getColumnConstraints().get(0).setPercentWidth(100);
            }
        }else{


            Comment comment = (Comment) post;
            this.post=comment.getCommentOfPost();

            if (post.getIsComment().equals("post")) {
                mainComment.getChildren().clear();
                this.post.setIsComment("post");
                showCommentII();
            }else{
                commentController.setComment((Comment) post);
                commentController.showCommentII();
            }
        }
    }


    public GridPane getAll() {
        return all;
    }

    public void setAll(GridPane all) {
        this.all = all;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public Label getNumofLike() {
        return numofLike;
    }

    public void setNumofLike(Label numofLike) {
        this.numofLike = numofLike;
    }

    public Label getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(Label numOfComments) {
        this.numOfComments = numOfComments;
    }

    public Label getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(Label numOfViews) {
        this.numOfViews = numOfViews;
    }

    public ImageView getLiked() {
        return liked;
    }

    public void setLiked(ImageView liked) {
        this.liked = liked;
    }

    public Label getWhere() {
        return where;
    }

    public void setWhere(Label where) {
        this.where = where;
    }

    public VBox getShowLists() {
        return showLists;
    }

    public void setShowLists(VBox showLists) {
        this.showLists = showLists;
    }



    public Label getBanCommentOrFollow() {
        return banCommentOrFollow;
    }

    public void setBanCommentOrFollow(Label banCommentOrFollow) {
        this.banCommentOrFollow = banCommentOrFollow;
    }

    public Label getDeletOrVisit() {
        return deletOrVisit;
    }

    public void setDeletOrVisit(Label deletOrVisit) {
        this.deletOrVisit = deletOrVisit;


    }

    public void visitPage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        if (deletOrVisit.getText().equals("Delete")){
            boolean flag=true;
            while (flag){
                if (post.getIsComment().equals("post")) {
                    UpdateSqlTable.deletePostFromTable(post);
                    DataBase.getPosts().remove(post);
                    flag=false;
                }else{
                    Comment comment = (Comment) post;
                    this.post=comment.getCommentOfPost();
                }
            }
            myHomePostPageController.startShowPost();
        }else{
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
            Parent parent=fxmlLoader.load();
            ShowAnotherUserPageController showAnotherUserPageController=fxmlLoader.getController();
            Controller.mainPageController.setMain(parent);
            showAnotherUserPageController.set(post.getSender());
            showAnotherUserPageController.start(post.getSender());
            post.getSender().getViewsFromPage().put(Controller.user,LocalDateTime.now());
            post.getSender().addViewsFromPageToTable(Controller.user,post.getSender(),LocalDateTime.now());
        }
    }


    public void follow(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (banCommentOrFollow.getText().endsWith("Comment")){
            if (post.getCommentAbility()) {
                //UpdateSqlTable.setCommentAbilityPostTable(post,false);
                banCommentOrFollow.setText("UnBan Comment");
                banCommentOrFollow.setFont(Font.font(13));
                post.setCommentAbility(false);
                messageTextArea.setDisable(true);
            }else{
                //UpdateSqlTable.setCommentAbilityPostTable(post,true);
                banCommentOrFollow.setText("Ban Comment");
                banCommentOrFollow.setFont(Font.font(16));
                post.setCommentAbility(true);
                messageTextArea.setDisable(false);
            }
        }else{
            if (!Controller.user.getFollowings().contains(post.getSender()) ) {
                if ( !Controller.user.equals(post.getSender())){
                    User.addFollowerAndFollowingToTable(Controller.user,post.getSender());
                    Controller.user.addFollowing(post.getSender());
                    post.getSender().addFollower(Controller.user);
                    banCommentOrFollow.setText("Unfollow");
                    //showUserRecommendation = true;
                }
            }else{
                User.removeFollowerAndFollowingFromTable(Controller.user,post.getSender());
                Controller.user.getFollowings().remove(post.getSender());
                post.getSender().getFollowers().remove(Controller.user);
                banCommentOrFollow.setText("Follow");
                //showUserRecommendation=false;
            }
        }
    }

    public void showLikedUsers(MouseEvent mouseEvent) throws IOException {

        allShowAnalyse.setVisible(false);


        helpInListener.getRowConstraints().get(0).setPercentHeight(8);
        helpInListener.getRowConstraints().get(1).setPercentHeight(0);
        helpInListener.getRowConstraints().get(2).setPercentHeight(92);
        messageTextArea.setVisible(false);sender.setVisible(false);
        helpInListener.getRowConstraints().get(3).setPercentHeight(0);



        commentShown=false;
        where.setText("Liked Users");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }


        showLists.getChildren().clear();showLists.setSpacing(20);
        List<User> users = new ArrayList(post.getLikes().keySet());

        if (users.size() == 0) {
        } else {
            for (int i = 0; i < users.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/OtherUsers.fxml"));
                Parent parent = fxmlLoader.load();
                OtherUsersController otherUsersController = fxmlLoader.getController();
                otherUsersController = fxmlLoader.getController();



                otherUsersController.getUserName().setText(users.get(i).getUserName());
                otherUsersController.getUserName().setFont(Font.font(16));


                //otherUsersController.getUserProfile().setImage(new Image(users.get(i).getPhotoNameFromImageFolder()));



                showLists.getChildren().add(parent);
            }

        }
    }

    public void showViewdUsers(MouseEvent mouseEvent) throws IOException {

        allShowAnalyse.setVisible(false);


        helpInListener.getRowConstraints().get(0).setPercentHeight(8);
        helpInListener.getRowConstraints().get(1).setPercentHeight(0);
        helpInListener.getRowConstraints().get(2).setPercentHeight(92);
        messageTextArea.setVisible(false);sender.setVisible(false);
        helpInListener.getRowConstraints().get(3).setPercentHeight(0);

        commentShown=false;
        where.setText("Viewd Users");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }


        showLists.getChildren().clear();showLists.setSpacing(20);
        List<User> users = new ArrayList(post.getViews().keySet());

        if (users.size() == 0) {
        } else {
            for (int i = 0; i < users.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/OtherUsers.fxml"));
                Parent parent = fxmlLoader.load();
                OtherUsersController otherUsersController = fxmlLoader.getController();
                otherUsersController = fxmlLoader.getController();



                otherUsersController.getUserName().setText(users.get(i).getUserName());
                otherUsersController.getUserName().setFont(Font.font(16));


                otherUsersController.getUserProfile().setImage(new Image(users.get(i).getPhotoNameFromImageFolder()));



                showLists.getChildren().add(parent);
            }

        }
    }

    public void forward(MouseEvent mouseEvent) {

    }

    public void comment(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        showCommentII();
    }



    public void like(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        post.addLikeToTable(Controller.user,post.getPostId(), LocalDateTime.now());
        post.getLikes().put(Controller.user, LocalDateTime.now());
        if (Controller.user.getLikedInfo().containsKey(post.getSender())){
            int kkk=Controller.user.getLikedInfo().get(post.getSender());
            Controller.user.getLikedInfo().put(post.getSender(),kkk+1);
        }else{
            Controller.user.getLikedInfo().put(post.getSender(),1);
        }
        liked.setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));
        numofLike.setText("Num of likes : "+String.valueOf(post.getLikes().size()));
    }


    public void startHandleTextArea() {
        textArea.applyCss();
        Node text = textArea.lookup(".text");
        textArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
            @Override
            public Double call() throws Exception {
                return text.getBoundsInLocal().getHeight();
            }
        }, text.boundsInLocalProperty()).add(20));
    }

    public void help(MouseEvent mouseEvent) {
        startHandleTextArea();
    }

    public void sendMessage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        if (!messageTextArea.getText().isEmpty()){
            Comment comment = new Comment(Controller.user, "text", messageTextArea.getText(), true, post);
            post.addComment(comment);
            messageTextArea.setText("");
            numOfComments.setText("Num of comments : "+ (++commentCounter));
        }
        showCommentII();
    }

    private void showCommentII() throws SQLException, ClassNotFoundException, IOException {

        allShowAnalyse.setVisible(false);

        messageTextArea.setVisible(true);sender.setVisible(true);


        visibleComment(true);
        if (post.getIsComment().equals("post")) {
            helpInListener.getRowConstraints().get(0).setPercentHeight(8);
            helpInListener.getRowConstraints().get(1).setPercentHeight(0);
            helpInListener.getRowConstraints().get(2).setPercentHeight(82);
            helpInListener.getRowConstraints().get(3).setPercentHeight(10);
        }else{

            helpInListener.getRowConstraints().get(0).setPercentHeight(8);
            helpInListener.getRowConstraints().get(1).setPercentHeight(34);
            helpInListener.getRowConstraints().get(2).setPercentHeight(48);
            helpInListener.getRowConstraints().get(3).setPercentHeight(10);
        }

        commentShown=false;
        where.setText("Comments");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }


        showLists.getChildren().clear();showLists.setSpacing(20);
        ArrayList<Comment> comments = new ArrayList<>();
        comments = post.getComments();
        if (comments.size() == 0) {

        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                comments.get(i).addViewToTable(Controller.user, comments.get(i).getPostId(), LocalDateTime.now());
                comments.get(i).getViews().put(Controller.user, LocalDateTime.now());

                FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/Comment.fxml"));
                Parent parent = fxmlLoader.load();
                commentController = fxmlLoader.getController();


                commentController.setPostControllerHelp(this);

                commentController.setComment(comments.get(i));
                commentController.getUsername().setText(comments.get(i).getSender().getUserName());
                commentController.getUserProfile().setFill(new ImagePattern(new Image(comments.get(i).getSender().getPhotoNameFromImageFolder())));

                commentController.getTextArea().setMinHeight(24);
                commentController.getTextArea().setWrapText(true);
                commentController.getTextArea().setText(comments.get(i).getContent());
                commentController.getTextArea().setEditable(false);


                if (comments.get(i).getLikes().containsKey(Controller.user)){
                    commentController.getLiked().setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));

                }
                showLists.getChildren().add(parent);
            }

        }
    }

    public void clicked(MouseEvent mouseEvent) {

    }

    public void visibleComment(boolean visible){
        if (visible){
//            messageTextArea.applyCss();
            Node text = messageTextArea.lookup(".text");
            messageTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
                @Override
                public Double call() throws Exception {
                    return text.getBoundsInLocal().getHeight();
                }
            }, text.boundsInLocalProperty()).add(20));
        }
        messageTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            messageGridPane.setPrefHeight(messageTextArea.getPrefHeight()*100/90);
            helpInListener.getRowConstraints().get(3).setPercentHeight(messageTextArea.getPrefHeight()/helpInListener.getHeight()*100);
            if (helpInListener.getRowConstraints().get(1).getPercentHeight()==0) {
                helpInListener.getRowConstraints().get(2).setPercentHeight(92 - messageTextArea.getPrefHeight() / helpInListener.getHeight() * 100);
            } else{
                helpInListener.getRowConstraints().get(2).setPercentHeight(58 - messageTextArea.getPrefHeight() / helpInListener.getHeight() * 100);
            }
        });

    }

    public void analyzePost(MouseEvent mouseEvent) throws IOException {

        allShowAnalyse.setVisible(true);


        helpInListener.getRowConstraints().get(0).setPercentHeight(8);
        helpInListener.getRowConstraints().get(1).setPercentHeight(0);
        helpInListener.getRowConstraints().get(2).setPercentHeight(92);
        messageTextArea.setVisible(false);sender.setVisible(false);
        helpInListener.getRowConstraints().get(3).setPercentHeight(0);

        where.setText("Analyze Post");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }

        showLists.getChildren().clear();

        for (int j=0;j<2;j++){
            System.out.println("@@@@@@@@@@@@@");



            FXMLLoader fxmlLoader0 = new FXMLLoader(PostController.class.getResource("/fxml/AnalyzePost.fxml"));
            Parent parent0 = fxmlLoader0.load();
            AnalyzePost analyzePost = fxmlLoader0.getController();
            analyzePost.getInbox().getChildren().clear();
            if (j==0){
                analyzePost.getWhere().setText("Viewd Users");


                LinkedHashMap<User, LocalDateTime> viewsFromPost = post.getViews();
                List<User> keyList = new ArrayList(viewsFromPost.keySet());
                List<LocalDateTime> valueList = new ArrayList(viewsFromPost.values());
                Collections.reverse(keyList);
                Collections.reverse(valueList);
                //LinkedHashMap<String,Integer> helpToPlot1=new LinkedHashMap<>();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                if (keyList.size() == 0) {

                } else {

                    for (int i = 0; i < keyList.size(); i++) {

                        if (helpToPlot1.containsKey(dtf.format(valueList.get(i)))){
                            Integer r=helpToPlot1.get(dtf.format(valueList.get(i)));
                            r++;
                            helpToPlot1.put(dtf.format(valueList.get(i)),r);
                        }else{
                            helpToPlot1.put(dtf.format(valueList.get(i)),1);
                        }


                        FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/OtherUsers.fxml"));
                        Parent parent = fxmlLoader.load();
                        OtherUsersController otherUsersController = fxmlLoader.getController();
                        //otherUsersController = fxmlLoader.getController();



                        otherUsersController.getUserName().setText(keyList.get(i).getUserName());
                        otherUsersController.getUserName().setFont(Font.font(16));


                        otherUsersController.getUserProfile().setImage(new Image(keyList.get(i).getPhotoNameFromImageFolder()));



                        analyzePost.getInbox().getChildren().add(parent);
                    }

                }

                analyzePost.setHelpToPlot1(helpToPlot1);

            }else{
                analyzePost.getWhere().setText("Liked Users");


                LinkedHashMap<User, LocalDateTime> likesToPost = post.getLikes();
                List<User> keyList2 = new ArrayList(likesToPost.keySet());
                List<LocalDateTime> valueList2 = new ArrayList(likesToPost.values());
                Collections.reverse(keyList2);
                Collections.reverse(valueList2);
                //LinkedHashMap<String,Integer> helpToPlot2=new LinkedHashMap<>();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                if (keyList2.size() == 0) {

                } else {
                    for (int i = 0; i < keyList2.size(); i++) {

                        if (helpToPlot2.containsKey(dtf.format(valueList2.get(i)))){
                            Integer r=helpToPlot2.get(dtf.format(valueList2.get(i)));
                            r++;
                            helpToPlot2.put(dtf.format(valueList2.get(i)),r);
                        }else{
                            helpToPlot2.put(dtf.format(valueList2.get(i)),1);
                        }

                        FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/OtherUsers.fxml"));
                        Parent parent = fxmlLoader.load();
                        OtherUsersController otherUsersController = fxmlLoader.getController();
                        //otherUsersController = fxmlLoader.getController();



                        otherUsersController.getUserName().setText(keyList2.get(i).getUserName());
                        otherUsersController.getUserName().setFont(Font.font(16));


                        otherUsersController.getUserProfile().setImage(new Image(keyList2.get(i).getPhotoNameFromImageFolder()));



                        analyzePost.getInbox().getChildren().add(parent);
                    }

                }

                analyzePost.setHelpToPlot1(helpToPlot2);

            }

            showLists.getChildren().add(parent0);
        }
        showLists.setSpacing(0);







    }

    public void showAnalyzePlot(MouseEvent mouseEvent) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Post Analysis");
        xAxis.setLabel("Date");
        yAxis.setLabel("Count");


        List<String> keyListOfHelpToPlot1 = new ArrayList(helpToPlot1.keySet());
        List<Integer> valueListOfHelpToPlot1 = new ArrayList(helpToPlot1.values());
        List<String> keyListOfHelpToPlot2 = new ArrayList(helpToPlot2.keySet());
        List<Integer> valueListOfHelpToPlot2 = new ArrayList(helpToPlot2.values());


        System.out.println("******");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("v");

        //ArrayList<XYChart.Series> allSeries=new ArrayList<>();
        for (int i=0;i<keyListOfHelpToPlot1.size();i++) {
            System.out.println("#####");
            series1.getData().add(new XYChart.Data(keyListOfHelpToPlot1.get(i),valueListOfHelpToPlot1.get(i) ));
            System.out.println(keyListOfHelpToPlot1.get(i) +" : "+valueListOfHelpToPlot1.get(i));

        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("l");
        //ArrayList<XYChart.Series> allSeries=new ArrayList<>();
        for (int i=0;i<keyListOfHelpToPlot2.size();i++) {

            series2.getData().add(new XYChart.Data(keyListOfHelpToPlot2.get(i), valueListOfHelpToPlot2.get(i)));
            System.out.println(keyListOfHelpToPlot2.get(i) +" : "+valueListOfHelpToPlot2.get(i));
        }



        Scene scene  = new Scene(bc,500,375);
        bc.getData().addAll(series1,series2);
        Stage stage2=new Stage();
        stage2.setScene(scene);
        stage2.show();

    }
}
