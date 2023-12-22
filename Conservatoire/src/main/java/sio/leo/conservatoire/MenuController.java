/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sio.leo.conservatoire;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sio.leo.conservatoire.App;

/**
 * FXML Controller class
 *
 * @author baras
 */
public class MenuController implements Initializable {

    private int etat = -1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void switchToSaisie() throws IOException {
        etat = App.getEleve().getELENUM();
        //System.out.println(etat + "" + App.getEleve().getDISNUM());
        if(etat != -1){
            App.setRoot("saisie");
        }
    }
    
    @FXML
    public void switchToRecherche() throws IOException {
        etat = App.getEleve().getELENUM();
        if(etat != -1){
            App.setRoot("recherche");
        }
    }
    
    @FXML
    public void switchToCnx() throws IOException {
        
        App.setRoot("connexion");
        
    }
    
}
