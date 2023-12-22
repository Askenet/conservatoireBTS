/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sio.leo.conservatoire;

import java.lang.System.Logger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sio.leo.conservatoire.modele.EleveDAO;

/**
 * FXML Controller class
 *
 * @author baras
 */
public class SaisieController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ListView<String> liste;
    
    private int choixId = 0;
    
    private String resCombo ="vide";
    
    private EleveDAO eDAO = new EleveDAO();
    
        @FXML
    private Label artiste;

    @FXML
    private Label nom;

    @FXML
    private TextField txtArt;

    @FXML
    private TextField txtNom;
    
    @FXML
    private Button ajouter;
    
    @FXML
    private Label state;
    
    private final Alert alertE = new Alert(Alert.AlertType.INFORMATION, "Insertion échouée");
    private final Alert alertR = new Alert(Alert.AlertType.INFORMATION, "Insertion réussie");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initList();
            
            this.disablePartitionAdd();
        } catch (SQLException ex) {
            
        }
    } 
    
    public void showPartitionAdd(){
        artiste.visibleProperty().set(true);
        nom.visibleProperty().set(true);
        txtArt.visibleProperty().set(true);
        txtNom.visibleProperty().set(true);
        ajouter.visibleProperty().set(true);
    }
    
    public void disablePartitionAdd(){
        artiste.visibleProperty().set(false);
        nom.visibleProperty().set(false);
        txtArt.visibleProperty().set(false);
        txtNom.visibleProperty().set(false);
        ajouter.visibleProperty().set(false);
    }
    
    public void addPartition() throws SQLException{
        
        if(!txtArt.getText().isEmpty() && !txtNom.getText().isEmpty()){
            Boolean res = eDAO.addPartitionBdd(txtNom.getText(), txtArt.getText());
            
            if(res){
                initList();
                this.disablePartitionAdd();
                alertR.show();
            } 
            else{
                Alert alertEe = new Alert(Alert.AlertType.INFORMATION, "Ce titre existe déjà dans le classeur");
                alertEe.show();
            }
            
        }
        else{
            alertE.show();
        }
        
    }
    
    
    @FXML
    private void selectList() throws SQLException {
        
        String string = liste.getSelectionModel().getSelectedItem();
        if(string != null){
            if(!string.equals("vide") && !string.isEmpty()){
                choixId = 1 + liste.getSelectionModel().getSelectedIndex();
                resCombo = liste.getSelectionModel().getSelectedItem();
                System.out.println(choixId + " " + resCombo);
                Boolean res = eDAO.setPartitionClasseur(App.getEleve().getELENUM(),choixId);
                if(res == true){
                    alertR.show();
                }
                else
                    alertE.show();
                }
        }
        else
            alertE.show();
    }
    
    public void initList() throws SQLException{
        liste.getItems().clear();
        ArrayList<String> mesChoix = eDAO.getPartitions();
        for(int i=0;i<mesChoix.size();i++){
            liste.getItems().add(mesChoix.get(i));
    }}
    

    
}
