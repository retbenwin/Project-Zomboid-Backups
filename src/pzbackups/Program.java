/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pzbackups;

/**
 *
 * @author retbenwin
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Running Aplication...");    
        java.awt.EventQueue.invokeLater(() -> {
            FrmMain frm = new FrmMain();
            frm.setLocationRelativeTo(null);
            frm.setVisible(true);
        });
    }
    
}
