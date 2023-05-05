module com.mercadolibre.integradora2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.mercadolibre.integradora2 to javafx.fxml;
    exports com.mercadolibre.integradora2;
    exports com.mercadolibre.integradora2.controller;
    opens com.mercadolibre.integradora2.controller to javafx.fxml;
    opens com.mercadolibre.integradora2.model to com.google.gson;
}