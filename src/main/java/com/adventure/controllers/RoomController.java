package com.adventure.controllers;

import com.adventure.ApplicationContextProvider;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.nodes.Room;
import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jgrapht.Graph;

public class RoomController
{
    /*@FXML
    private Label currentRoom;

    @FXML
    private Button backButton;

    @FXML
    private VBox box;

    private ApplicationContext context;

    @FXML
    public void initialize()
    {
        // Obtaining useful nodes and models.
        this.context = ApplicationContextProvider.getInstance();
        Room room = (Room) context.getGame().getCurrentNode();
        Graph<StoryNode, StoryNodeLink> graph = this.context.getGame().getGameGraph();

        // Printing current room.
        currentRoom.setText("YOU'RE IN  " + room.getName());

        // Dynamically generating button.
        for (StoryNodeLink edge: graph.outgoingEdgesOf(room))
        {
            StoryNode targetNode = graph.getEdgeTarget(edge);
            Button btnChangeRoom = new Button();
            btnChangeRoom.setText(">>" + edge.getAction().getActionName());
            btnChangeRoom.getStyleClass().add("menu-button");

            // Lambda function is capturing targetNode.
            btnChangeRoom.setOnAction((ActionEvent event) -> {
                this.context.getGame().setCurrentNode(targetNode);
            });

            this.box.getChildren().add(btnChangeRoom);
        }

        // NOTE: in this case we are only checking if there was a previous node rendered.
        // We don't check for the graph structure and if there are back edged.
        // For this reason hasPreviousNode may not be sufficient to establish if a back command
        // could be invoked.
        if(!this.context.getGame().hasPreviousNode())
        {
            this.backButton.setDisable(true);
        }

    }

    @FXML
    public void back()
    {
        // Can't be fired if the button is disabled.
        this.context.getGame().setCurrentNode(this.context.getGame().getPreviousNode());
    }*/
}
