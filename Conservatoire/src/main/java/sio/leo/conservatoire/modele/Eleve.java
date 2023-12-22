/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sio.leo.conservatoire.modele;

/**
 *
 * @author baras
 */
public class Eleve {
    private int ELENUM;
    private int DISNUM;
    private String ELENOM;
    private String ELEPRENOM;
    private int ELECYCLE;
    private int ELEANNEECYCLE;
    private String ELELOGIN;
    private String ELEMDP;
    
    public Eleve(){
        this.ELENUM = -1;
    }

    public Eleve(int ELENUM, int DISNUM, String ELENOM, String ELEPRENOM, int ELECYCLE, int ELEANNEECYCLE, String ELELOGIN, String ELEMDP) {
        this.ELENUM = ELENUM;
        this.DISNUM = DISNUM;
        this.ELENOM = ELENOM;
        this.ELEPRENOM = ELEPRENOM;
        this.ELECYCLE = ELECYCLE;
        this.ELEANNEECYCLE = ELEANNEECYCLE;
        this.ELELOGIN = ELELOGIN;
        this.ELEMDP = ELEMDP;
    }

    public int getELENUM() {
        return ELENUM;
    }

    public void setELENUM(int ELENUM) {
        this.ELENUM = ELENUM;
    }

    public int getDISNUM() {
        return DISNUM;
    }

    public void setDISNUM(int DISNUM) {
        this.DISNUM = DISNUM;
    }

    public String getELENOM() {
        return ELENOM;
    }

    public void setELENOM(String ELENOM) {
        this.ELENOM = ELENOM;
    }

    public String getELEPRENOM() {
        return ELEPRENOM;
    }

    public void setELEPRENOM(String ELEPRENOM) {
        this.ELEPRENOM = ELEPRENOM;
    }

    public int getELECYCLE() {
        return ELECYCLE;
    }

    public void setELECYCLE(int ELECYCLE) {
        this.ELECYCLE = ELECYCLE;
    }

    public int getELEANNEECYCLE() {
        return ELEANNEECYCLE;
    }

    public void setELEANNEECYCLE(int ELEANNEECYCLE) {
        this.ELEANNEECYCLE = ELEANNEECYCLE;
    }

    public String getELELOGIN() {
        return ELELOGIN;
    }

    public void setELELOGIN(String ELELOGIN) {
        this.ELELOGIN = ELELOGIN;
    }

    public String getELEMDP() {
        return ELEMDP;
    }

    public void setELEMDP(String ELEMDP) {
        this.ELEMDP = ELEMDP;
    }
    
    
}
