/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.leo.conservatoire.modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author baras
 */
public class DAO {
    //Pour connecter mariadb et la base lardon
    private static Connection cnx=null;
    //Pour exécuter des requêtes
    private static Statement smt =null;
   
    public static Connection getConnection(){
        String url = "jdbc:mysql://192.168.5.240:3307/Conservatoire";
        String loginBd = "admcons";
        String passwd = "pwadmcons";
        
        try{
            cnx = (Connection) DriverManager.getConnection(url, loginBd, passwd);
        }
        catch(SQLException e){
            System.out.println("Sql Execption : " + e.toString());
        }
        return cnx;
    }
    
    public static Statement getStatement() {
        try{
            smt = cnx.createStatement();
        }
        catch(SQLException e){
            System.out.println("Sql Execption : " + e.toString());
        }
        
        return smt;
    }
}
