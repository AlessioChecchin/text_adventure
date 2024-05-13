@startuml
!theme reddress-darkblue

Class Item{
-int id
-String name
+Item(int,String)
+int getId()
+String getName()
}

Class Game{
-Graph<StoryNode, StoryNodeLink> gameGraph
-StoryNode currentNode
-StoryNode previousNode
-Stage stage
+Game()
+Graph<StoryNode, StoryNodeLink> getGameGraph()
+StoryNode getCureentNode()
+void setCurrentNode(StoryNode)
+void setStage(Stage)
+Stage getStage()
+boolean hasPreviousNode()
+void invalidatePreviousNode()
+StoryNode getPreviousNode()
}

Class Player{
-String name
-List<Item> inventory
+void setName(String)
+String getName()
+List<Item> getInventory()
+
}

Class Room{
-String name
-String description
-List<Item> items
+Room(String, String)
+String getName()
+String getDescription
+void setName(String)
+void setDescription(String)
+List<Item> getItems()
+void setItems(List<Item>)
}

Class StoryNodeLink{
-Action action
+Action getAction()
+void setAction(Action)
}

Class Action{
-String actionName
+Action(String)
+String getActionName()
}

Class Dungeon{

}

Class ApplicationContextProvider{
-ApplicationContextProvider instance
-Game game
+ApplicationContextProvider()
+ApplicationContextProvider getIstance()
+void load(String, Stage)
+void load(Game, Stage)
+Game getGame()
}

Class RoomController{
@FXML
-Label currentRoom
-Button backButton
-VBox box
-ApplicationContext context
+void initialize()
+void back()
}
@enduml
//www.plantuml.com/plantuml/png/XLJ1RXen4Btp5UwDgl07KKH3L1I9HgYHgbw7zMYnyDh8daMaKV-zdfqOcnKabyL-R_pcpOpD8X84wZix-K8xx505r3fWZ7CDOR-rFKucImingYV2xcsYviQHCZejNYWOroe77KxKDwQdYPi9_3L17DaYFUafxmHdu3cTID2_hBnAUrPU1JZixbx8XzTdht6cpikrSVjxrQQu8OQJPqerVGZeYDSN-27WqVW-dWbeKKN-9ZESSd3rQKwaLKuhPMIUgMKVK7ABsj4RhUA0XuoV3zG1WvifVmTOpBKPPsZhlKLmQWTnKrLIqXXt16iq48xuaSUQhRk-iV2AuMqyoFdQHBhZQTvp2dIiLPpByEJ4bQaoL0dmzBF6m9NqlxplHidBJcDiWZcGyMxaA7rZqk7JxovcwaCt1NeieZVgAN0TNDXnQMoccdj6IbHfz-MzuiA_Dooke7at97lMbLKjd27vgLG5gFeeSbpKMVBgj8GQlyJ7thN8UbM6my6Q1ZXwwHtXYJR17usMgtERLSP50jVmLFa5gLOytJx11Nz03nqNoT8Qwq5Nzm1QlAGuSqM84IHv_fUNaTC5RoqNjlZnv-UQRnviqUP_60vAs4DFv9tQGhENPS9-F_YJslhJrOweHdxBmpLam9g_LHsiDXXQeDDzP_y3