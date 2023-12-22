/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sio.leo.conservatoire;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sio.leo.conservatoire.modele.Eleve;
import sio.leo.conservatoire.modele.EleveDAO;

/**
 * FXML Controller class
 *
 * @author baras
 */
public class ConnexionController implements Initializable {

    
    @FXML
    private Button btnDeco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(App.getEleve().getELENUM() == -1){
            btnDeco.visibleProperty().set(false);
        }
        else
            btnDeco.visibleProperty().set(true);
    }    
    
    
    @FXML
    private Label connexion;

    @FXML
    private Label llogin;

    @FXML
    private TextField login;

    @FXML
    private Label lpassword;

    @FXML
    private TextField password;
    
    @FXML
    private Label status;

    
    private EleveDAO eDAO = new EleveDAO();
    
    public void connexionalabase() throws SQLException{
        String myLogin = login.getText();
        String myPassword = password.getText();
        Boolean res = eDAO.connexionEleve(myLogin, myPassword);
        System.out.println(res);
        if(res){
            status.setText("Connexion réussie");
            btnDeco.visibleProperty().set(true);
        }
        else{
            status.setText("Echec de la connexion");
        }
    }
    
    public void deco(){
        status.setText("DéConnexion réussie");
        App.setEleve(new Eleve());
        btnDeco.visibleProperty().set(false);
    }
    
}
