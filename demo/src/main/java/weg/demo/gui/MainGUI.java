package weg.demo.gui;

import javax.swing.*;

public class MainGUI {
    
    public static void main(String[] args) {
        // Configurar look and feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar look and feel: " + e.getMessage());
        }
        
        // Executar a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
} 