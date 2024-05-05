module com.adventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;


    opens com.adventure to javafx.fxml;
    exports com.adventure;
}