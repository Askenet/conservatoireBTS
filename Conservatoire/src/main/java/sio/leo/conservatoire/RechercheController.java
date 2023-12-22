/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sio.leo.conservatoire;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RechercheController implements Initializable {

    private EleveDAO eDAO = new EleveDAO();
    
    @FXML
    private Label nom;
    
    @FXML
    private ListView<String> liste;
    
    private int choixId = 0;
    
    private String resCombo ="vide";
    
    @FXML
    private TextField auteur;

    @FXML
    private Button chercher;
    
     @FXML
    private Button cherchertitre;

    @FXML
    private TextField titre;
    
    
    @FXML
    private Button duo;
    
    @FXML
    private Button deletebtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            duo.visibleProperty().set(false);
            initHeader();
        } catch (SQLException ex) {
            Logger.getLogger(RechercheController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            initListe();     
        } catch (SQLException ex) {
            
        }
    }    
    
    public void rechercher() throws SQLException{
        if(!auteur.getText().isEmpty() && titre.getText().isEmpty())
        {
            duo.visibleProperty().set(false);
            cherchertitre.visibleProperty().set(false);
            ArrayList<String> mesChoix = eDAO.getPartitionsByAuteur(App.getEleve().getELENUM(),auteur.getText());
            liste.getItems().clear();
            for(int i=0;i<mesChoix.size();i++)
            {
                liste.getItems().add(mesChoix.get(i));
            }
        }
        else if(auteur.getText().isEmpty() && !titre.getText().isEmpty())
        {
            duo.visibleProperty().set(false);
            chercher.visibleProperty().set(false);
            ArrayList<String> mesChoix = eDAO.getPartitionsByTitre(App.getEleve().getELENUM(),titre.getText());
            liste.getItems().clear();
            for(int i=0;i<mesChoix.size();i++)
            {
                liste.getItems().add(mesChoix.get(i));
            }
        }
        else if(auteur.getText().isEmpty() && titre.getText().isEmpty()){
            cherchertitre.visibleProperty().set(true);
            chercher.visibleProperty().set(true);
            duo.visibleProperty().set(false);
            initListe();
        }
        else if(!auteur.getText().isEmpty() && !titre.getText().isEmpty()){
            cherchertitre.visibleProperty().set(false);
            chercher.visibleProperty().set(false);
            
            duo.visibleProperty().set(true);
            ArrayList<String> mesChoix = eDAO.getPartitionsByArtisteAndTitre(App.getEleve().getELENUM(),auteur.getText(),titre.getText());
            liste.getItems().clear();
            for(int i=0;i<mesChoix.size();i++)
            {
                liste.getItems().add(mesChoix.get(i));
            }
        }
    }
    
    public void supprimerTitre() throws SQLException{
        String string = liste.getSelectionModel().getSelectedItem();
        if(!string.equals("vide") && !string.isEmpty() && string != null){
            String[] parts = string.split("°", 2);
            String part2 = parts[1]; 
            String[] parts2 = part2.split(" -", 2);
            String idTitre = parts2[0];
            System.out.println(idTitre);
            
            Boolean res = eDAO.deletePartitionClasseur(App.getEleve().getELENUM(), Integer.parseInt(idTitre));
            
            if(res){
                
                initListe();
                initHeader();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Suppression réussie de : " + string + " du classeur");
                alert.show();
                
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Suppression échouée");
                alert.show();
            }
        }
    }
    
    public void alertRecherche(){
        if(auteur.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il existe "+liste.getItems().size()+" chansons");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il existe "+liste.getItems().size()+" chansons, par l'artiste: "+auteur.getText());
            alert.show();
        }
    }
    
    public void alertTitre(){
        if(auteur.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il existe "+liste.getItems().size()+" chansons");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il existe "+liste.getItems().size()+" chansons, avec en titre: "+titre.getText());
            alert.show();
        }
    }
    
    public void alertDuo(){ 
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il existe "+liste.getItems().size()+" chansons, par l'artiste: "+auteur.getText()+", avec en titre: "+titre.getText());
        alert.show();  
    }
    
    public void initListe() throws SQLException{
        liste.getItems().clear();
        ArrayList<String> mesChoix = eDAO.getPartitionsById(App.getEleve().getELENUM());
        for(int i=0;i<mesChoix.size();i++){
            liste.getItems().add(mesChoix.get(i));
        }
    }
    
    public void initHeader() throws SQLException{
        nom.setText("Voici les partitions de : " + App.getEleve().getELEPRENOM() + ", il en posséde : " + eDAO.getMesPartitions(App.getEleve().getELENUM()));
    }
}
