module com.mercadolibre.integradora2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.mercadolibre.integradora2 to javafx.fxml;
    exports com.mercadolibre.integradora2;
    exports com.mercadolibre.integradora2.controller;
    exports com.mercadolibre.integradora2.model;
    opens com.mercadolibre.integradora2.model to com.google.gson, javafx.fxml;
    opens com.mercadolibre.integradora2.controller to com.google.gson, javafx.fxml;
}