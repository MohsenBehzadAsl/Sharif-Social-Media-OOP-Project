package Controllers.GroupControllers;

import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
import DataBase.DataBase;
import View.Controller;
import component.Group;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import DataBase.UpdateSqlTable;

import java.io.IOException;
import java.sql.SQLException;

public class MemberIconController {

    public Parent backParent;
    public User member;
    public Group group;
    GroupInfoController groupInfoController;

    @FXML
    private GridPane total;
    @FXML
    private Button ban;
    @FXML
    private Label id;
    @FXML
    private Circle image;
    @FXML
    private Label name;
    @FXML
    private Button type;

    public void setFirst() {
        setBan();
        setType();
        id.setText("@"+member.getId());
        name.setText(member.getUserName());
        image.setFill(new ImagePattern(new Image(member.getPhotoNameFromImageFolder())));
        setContextMenu(groupInfoController);
    }

    public void setContextMenu(GroupInfoController GroupInfoController){
        ContextMenu menu = new ContextMenu();
        if (Controller.user!=member) {
            if (Controller.user == group.getOwner()) {
                MenuItem remove = new MenuItem("Remove"); //true
                remove.setOnAction(e -> {
                    try {
                        group.removeMember(member);
                        GroupInfoController.updateMembers();
                        GroupInfoController.updateFollowers();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                MenuItem ban=null;
                if (group.getLinkedMembers().get(member)) {
                    ban = new MenuItem("UnBan");
                    ban.setOnAction(e -> {
                        group.getLinkedMembers().put(member, false);
                        try {
                            GroupInfoController.updateMembers();
                            GroupInfoController.updateFollowers();
                            UpdateSqlTable.setBanMemberToTable(group,member,false);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                }else {
                    ban = new MenuItem("Ban");
                    ban.setOnAction(e -> {
                        group.getLinkedMembers().put(member, true);
                        try {
                            UpdateSqlTable.setBanMemberToTable(group,member,true);
                            GroupInfoController.updateMembers();
                            GroupInfoController.updateFollowers();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
                MenuItem type=null;
                if (group.getAdmins().contains(member)){ //true
                    type = new MenuItem("Set Normal");
                    type.setOnAction(e -> {
                        try {
                            group.removeAdmin(member);
                            GroupInfoController.updateMembers();
                            GroupInfoController.updateFollowers();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                }else {
                    type = new MenuItem("Set Admin");
                    type.setOnAction(e -> {
                        try {
                            group.addAdmin(member);
                            GroupInfoController.updateMembers();
                            GroupInfoController.updateFollowers();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
                menu.getItems().add(remove);
                menu.getItems().add(ban);
                menu.getItems().add(type);

            }else if (group.getAdmins().contains(Controller.user)){
                if (!group.getAdmins().contains(member)) {
                    MenuItem remove = new MenuItem("Remove");
                    remove.setOnAction(e -> {
                        try {
                            group.removeMember(member);
                            GroupInfoController.updateMembers();
                            GroupInfoController.updateFollowers();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    MenuItem ban = null;
                    if (group.getLinkedMembers().get(member)) {
                        ban = new MenuItem("UnBan");
                        ban.setOnAction(e -> {
                            group.getLinkedMembers().put(member, false);
                            try {
                                UpdateSqlTable.setBanMemberToTable(group,member,false);
                                GroupInfoController.updateMembers();
                                GroupInfoController.updateFollowers();

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            } catch (ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        });
                    } else {
                        ban = new MenuItem("Ban");
                        ban.setOnAction(e -> {
                            group.getLinkedMembers().put(member, true);
                            try {
                                UpdateSqlTable.setBanMemberToTable(group,member,true);
                                GroupInfoController.updateMembers();
                                GroupInfoController.updateFollowers();

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            } catch (ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                    menu.getItems().add(remove);
                    menu.getItems().add(ban);
                }
            }

            total.setOnContextMenuRequested(e -> {
                menu.show(type.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });

        }
    }

    private void setBan() {
        if (!group.getLinkedMembers().get(member)) {
            String style = "";
            style += "-fx-background-color: Green;";
            style += "-fx-background-radius: 50;";
            ban.setStyle(style);
            ban.setText("UnBan");
        }else {
            String style = "";
            style += "-fx-background-color: Red;";
            style += "-fx-background-radius: 50;";
            ban.setStyle(style);
            ban.setText("Ban");
        }
    }
    private void setType() {
        if (group.getOwner()==member) {
            String style = "";
            style += "-fx-background-color: #f35b23;";
            style += "-fx-background-radius: 50;";
            type.setStyle(style);
            type.setText("Owner");
        }else if (group.getAdmins().contains(member)){
            String style = "";
            style += "-fx-background-color: Orange;";
            style += "-fx-background-radius: 50;";
            type.setStyle(style);
            type.setText("Admin");
        }else {
            String style = "";
            style += "-fx-background-color: #e0e086;";
            style += "-fx-background-radius: 50;";
            type.setStyle(style);
            type.setText("Normal");
        }
    }


    @FXML
    void visitPage(MouseEvent event) throws IOException {
        if(!event.getButton().equals(MouseButton.SECONDARY)) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
            Parent parent = fxmlLoader.load();
            ShowAnotherUserPageController showAnotherUserPageController = fxmlLoader.getController();
            Controller.main.getChildren().clear();
            Controller.main.getRowConstraints().removeAll();
            Controller.main.getColumnConstraints().removeAll();
            Controller.main.add(parent, 0, 0);
            showAnotherUserPageController.nowParent = parent;
            showAnotherUserPageController.backParent = backParent;
            showAnotherUserPageController.set(member);
        }
    }

}
