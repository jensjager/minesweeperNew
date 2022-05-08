module com.example.minesweepernew {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minesweepernew to javafx.fxml;
    exports com.example.minesweepernew;
}