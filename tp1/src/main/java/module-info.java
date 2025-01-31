module br.com.tp1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.tp1 to javafx.fxml;
    exports br.com.tp1;
}
