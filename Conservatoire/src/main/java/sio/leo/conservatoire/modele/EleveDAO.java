/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.leo.conservatoire.modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import sio.leo.conservatoire.App;

/**
 *
 * @author baras
 */

public class EleveDAO {
    private Connection cnx;
    private  Statement smt = null;
    private ResultSet rs =null;
    private Eleve unE;
    private static PreparedStatement psmt ;
    
    public EleveDAO(){ 
        //DAO dao = new DAO();
        this.cnx = DAO.getConnection();
        this.smt = DAO.getStatement();
    }
    
    @SuppressWarnings("UnusedAssignment")
    public Boolean connexionEleve(String login, String mdp) throws SQLException{
        try{
            if(!login.isEmpty() && !mdp.isEmpty()){
                if(loginExist(login)){

                    String requete = "SELECT ELEMDP FROM ELEVE WHERE ELELOGIN = ? AND ELEMDP = PASSWORD(?)"; 
                    PreparedStatement preparedStatement = cnx.prepareStatement(requete, ResultSet.TYPE_SCROLL_INSENSITIVE); 
                    preparedStatement.setString(1, login);
                    preparedStatement.setString(2, mdp);
                    System.out.println(mdp);
                    this.rs = preparedStatement.executeQuery();
                    if(rs.next()){
                        //System.out.println("aaa");
                        String pwd = rs.getString("ELEMDP");
                        //System.out.println(pwd);
                        
                        requete = "SELECT * FROM ELEVE WHERE ELEMDP ='"+pwd+"' AND ELELOGIN ='"+login+"';";
                        this.rs = smt.executeQuery(requete);
                        if(rs.next()){
                            App.setEleve(new Eleve(rs.getInt("ELENUM"),
                            rs.getInt("DISNUM"),
                            rs.getString("ELENOM"),
                            rs.getString("ELEPRENOM"),
                            rs.getInt("ELECYCLE"),
                            rs.getInt("ELEANNEECYCLE"),
                            login,
                            pwd));
                        }
                        
                        return true;
                    }
                    System.out.println("3");
                    return false;
                }
                else {
                    System.out.println("2");
                    return false; // Si id n'existe pas
                }
            } 
            else {
                System.out.println("1");
                return false; // Si id ou mdp sont vides
            }
        } 
            catch (NumberFormatException e) {
                System.out.println("4");
            return false; // Gestion spécifique si une exception de format numérique se produit
        }
    }
    
    public Boolean loginExist(String login) throws SQLException, SQLIntegrityConstraintViolationException{
        String requete = "SELECT ELELOGIN FROM ELEVE WHERE ELELOGIN= ?;";
        psmt = DAO.getConnection().prepareStatement(requete);
        psmt.setString(1, login);
        this.rs = psmt.executeQuery();
        while(rs.next()){
            String idAll  = rs.getString("ELELOGIN");
            if(idAll.equals(login)){
                return true;
            }
        }   
        return false;
    }
    
    public ArrayList<String> getPartitions() throws SQLException{
        
        ArrayList<String> options = new ArrayList<>();
        String query = "SELECT * FROM PARTITIONS ORDER BY PARNUM";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                options.add(rs.getInt("PARNUM") +" - "+ rs.getString("PARNOM") +" (de "+rs.getString("PARAUTEUR") +")");
            }
        }
        return options;
    }
     
    public ArrayList<String> getPartitionsById(int elenum) throws SQLException{
        
        ArrayList<String> options = new ArrayList<>();
        String query = "SELECT p.*, pe.NUMEROPAGECLASSEUR FROM PARTITIONELEVE pe JOIN PARTITIONS p ON pe.PARNUM = p.PARNUM AND pe.ELENUM = ? ORDER BY pe.NUMEROPAGECLASSEUR";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            preparedStatement.setInt(1, elenum);
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                options.add("Page: " + rs.getInt("NUMEROPAGECLASSEUR") + ", musique n°" + rs.getInt("PARNUM") +" - "+ rs.getString("PARNOM") +" (de "+rs.getString("PARAUTEUR") +")");
            }
        }
        return options;
    }
     
     
             
    public ArrayList<String> getPartitionsByAuteur(int elenum, String txt) throws SQLException{
        
        ArrayList<String> options = new ArrayList<>();
        String query = "SELECT p.*, pe.NUMEROPAGECLASSEUR FROM PARTITIONELEVE pe JOIN PARTITIONS p ON pe.PARNUM = p.PARNUM AND pe.ELENUM = ? AND p.PARAUTEUR LIKE '%"+txt+"%'ORDER BY pe.NUMEROPAGECLASSEUR";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            preparedStatement.setInt(1, elenum);
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                options.add("Page: " + rs.getInt("NUMEROPAGECLASSEUR") + ", musique n°" + rs.getInt("PARNUM") +" - "+ rs.getString("PARNOM") +" (de "+rs.getString("PARAUTEUR") +")");
            }
        }
        return options;
    }
    
    public ArrayList<String> getPartitionsByTitre(int elenum, String txt) throws SQLException{
        
        ArrayList<String> options = new ArrayList<>();
        String query = "SELECT p.*, pe.NUMEROPAGECLASSEUR FROM PARTITIONELEVE pe JOIN PARTITIONS p ON pe.PARNUM = p.PARNUM AND pe.ELENUM = ? AND p.PARNOM LIKE '%"+txt+"%'ORDER BY pe.NUMEROPAGECLASSEUR";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            preparedStatement.setInt(1, elenum);
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                options.add("Page: " + rs.getInt("NUMEROPAGECLASSEUR") + ", musique n°" + rs.getInt("PARNUM") +" - "+ rs.getString("PARNOM") +" (de "+rs.getString("PARAUTEUR") +")");
            }
        }
        return options; 
    }
    
    public ArrayList<String> getPartitionsByArtisteAndTitre(int elenum, String aut, String title) throws SQLException{
        
        ArrayList<String> options = new ArrayList<>();
        String query = "SELECT p.*, pe.NUMEROPAGECLASSEUR FROM PARTITIONELEVE pe JOIN PARTITIONS p ON pe.PARNUM = p.PARNUM AND pe.ELENUM = ? AND p.PARAUTEUR LIKE '%"+aut+"%' AND p.PARNOM LIKE '%"+title+"%'ORDER BY pe.NUMEROPAGECLASSEUR";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            preparedStatement.setInt(1, elenum);
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                options.add("Page: " + rs.getInt("NUMEROPAGECLASSEUR") + ", musique n°" + rs.getInt("PARNUM") +" - "+ rs.getString("PARNOM") +" (de "+rs.getString("PARAUTEUR") +")");
            }
        }
        return options;
    }
     
    public Boolean addPartitionBdd(String nom, String art)throws SQLException{
        if(!partitionExist(nom, art)){
            int id = getMaxIdPartition() +1;
            String requete = "INSERT INTO PARTITIONS VALUES("+id+", '"+nom+"', '"+art+"');";
            smt.executeUpdate(requete);
            return true;
        }
        return false;
    }
    
    public Boolean partitionExist(String nom, String art) throws SQLException{
        String requete = "SELECT PARNOM FROM PARTITIONS WHERE PARNOM = ? AND PARAUTEUR = ?;";
        psmt = DAO.getConnection().prepareStatement(requete);
        psmt.setString(1, nom);
        psmt.setString(2, art);
        this.rs = psmt.executeQuery();
        while(rs.next()){
            String nomB  = rs.getString("PARNOM");
            if(nom.equals(nomB)){
                return true;
            }
        }   
        return false;
    }
    
    public int getMaxIdPartition() throws SQLException{
        int maxId = 0;
        String query = "SELECT PARNUM FROM PARTITIONS ORDER BY PARNUM DESC LIMIT 1";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                maxId = rs.getInt("PARNUM");
            }
        }
        return maxId;
    }
    
    public Boolean setPartitionClasseur(int elenum, int choixId) throws SQLException{
        if(!classeurPartitionExist(elenum, choixId)){
            int page = getMaxPage(elenum) +1;
            String requete = "INSERT INTO PARTITIONELEVE VALUES("+elenum+", "+choixId+", "+page+");";
            smt.executeUpdate(requete);
            return true;
        }
        return false;
    }
    
    public Boolean classeurPartitionExist(int elenum, int choixId) throws SQLException{
        String requete = "SELECT ELENUM, PARNUM FROM PARTITIONELEVE WHERE ELENUM = ? AND PARNUM = ?;";
        psmt = DAO.getConnection().prepareStatement(requete);
        psmt.setInt(1, elenum);
        psmt.setInt(2, choixId);
        this.rs = psmt.executeQuery();
        while(rs.next()){
            int ele  = rs.getInt("ELENUM");
            int par  = rs.getInt("PARNUM");
            if(ele == elenum && par == choixId){
                return true;
            }
        }   
        return false;
    }
    
    public int getMaxPage(int elenum) throws SQLException{
        int maxId = 0;
        String query = "SELECT NUMEROPAGECLASSEUR FROM PARTITIONELEVE WHERE ELENUM = ? ORDER BY NUMEROPAGECLASSEUR DESC LIMIT 1";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            preparedStatement.setInt(1, elenum);
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                maxId = rs.getInt("NUMEROPAGECLASSEUR");
            }
        }
        return maxId;
    }
    
    public int getMesPartitions(int elenum) throws SQLException{
        int maxId = 0;
        String query = "SELECT COUNT(*) AS NB FROM PARTITIONELEVE WHERE ELENUM = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);) {
            preparedStatement.setInt(1, elenum);
            this.rs = preparedStatement.executeQuery();
            while (this.rs.next()) {
                // Ajouter chaque élément à la liste
                maxId = rs.getInt("NB");
            }
        }
        return maxId;
    }
    
    public Boolean deletePartitionClasseur(int elenum, int choixId) throws SQLException{
        if(classeurPartitionExist(elenum, choixId)){
            String requete = "DELETE FROM PARTITIONELEVE WHERE ELENUM ="+elenum+" AND PARNUM ="+choixId+";";
            smt.executeUpdate(requete);
            return true;
        }
        return false;
    }
    
    /*
    public ArrayList<Lardon> tousLesLardons(){ 
        
        String requete = "SELECT * FROM lardon;";
        ArrayList<Lardon> monArrayLardon = new ArrayList<>();
        try{
            //récupère le résultat de la requête (smt.executeQuery(…) dans un ResultSet
            this.rs = smt.executeQuery(requete);
            //parcours le ResultSet 

            while(rs.next()){
                
                //instancie un Lardon pour chaque occurrence du ResultSet
                String dateLardon = rs.getString("ddn");
                //System.out.println(dateLardon);
                Lardon monLardon;
                if(dateLardon != null){
                    monLardon = new Lardon(rs.getString("prenom"), rs.getString("nom") ,convertDate(dateLardon), rs.getString("cheveux"));
                }   
                else{
                    monLardon = new Lardon(rs.getString("prenom"), rs.getString("nom"), rs.getString("cheveux"));
                }
                //Ajoute l'objet Lardon dans l'ArrayList
                monArrayLardon.add(monLardon);
                
                
            }
            //Retourne l'ArrayList avec tous les Lardons de la base
            return monArrayLardon;
        }
        catch(SQLException e){
            System.out.println("requête non exécutée" + requete + "  " + e.getMessage());
        }
        return null;
    }
    
    public int compteLesLardons(){
        //retourne le résultat du select count(*)
        String requete = "SELECT COUNT(*) FROM lardon;";
        try{
            this.rs = smt.executeQuery(requete);
            rs.next();
            return rs.getInt(1);

        }
        catch(SQLException e){
            System.out.println("requête non exécutée" + requete + "  " + e.getMessage());
        }
        return 0;
    }
    
    public Lardon getUnLardon(int num) throws SQLException{
    //retourne le lardon dont le numéro est passé en paramètre
    String requete = "SELECT * FROM lardon WHERE id ="+num+";";        

        Lardon monLardon = null;
        this.rs = smt.executeQuery(requete);
        if(rs.next()){
            String dateLardon = rs.getString("ddn");
            if(dateLardon != null){
                monLardon = new Lardon(rs.getString("prenom"), rs.getString("nom"), convertDate(dateLardon), rs.getString("cheveux"));
            }   
            else{
                monLardon = new Lardon(rs.getString("prenom"), rs.getString("nom"), rs.getString("cheveux"));
            }
        }
        return monLardon;
    

    //return null;
    }
    
    public ArrayList<Lardon> getLesLardonsFamille(int num) throws SQLException{
    //retourne le lardon dont le numéro est passé en paramètre
    String requete = "SELECT lardon.* from famille join lardon on lardon.famille = famille.famille_id WHERE famille.famille_id ="+num+";";        
    
    ArrayList<Lardon> mesLardons = new ArrayList<>();
    
    this.rs = smt.executeQuery(requete);
    while(rs.next()){
        String dateLardon = rs.getString("ddn");
        if(dateLardon != null){
            
            mesLardons.add(new Lardon(rs.getString("prenom"), rs.getString("nom"), convertDate(dateLardon), rs.getString("cheveux")));
        }   
        else{
            mesLardons.add(new Lardon(rs.getString("prenom"), rs.getString("nom"), rs.getString("cheveux")));
        }

    }
    return mesLardons;


    //return null;
    }   
    
    public static String convertDate(String d){
        //transforme la date format mariadb (aaaa-mm-jj) par aaaa/mm/jj 
        if(d != null){
            String dateFormat = d.replace('-', '/');
            return dateFormat;
        }
        return null;
        // pour instancier un lardon
    }
*/

    

    
}
