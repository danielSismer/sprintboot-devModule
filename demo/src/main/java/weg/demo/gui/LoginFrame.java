package weg.demo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cadastroButton;
    private JLabel statusLabel;
    
    private static final String BASE_URL = "http://localhost:8081";
    
    public LoginFrame() {
        setTitle("Sistema de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        layoutComponents();
        setupEventHandlers();
    }
    
    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        cadastroButton = new JButton("Cadastro");
        statusLabel = new JLabel("Digite suas credenciais");
        statusLabel.setForeground(Color.BLUE);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titleLabel = new JLabel("Sistema de Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Usuário:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(cadastroButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
        
        // Status label
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCadastro();
            }
        });
        
        // Enter key para login
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Por favor, preencha todos os campos");
            statusLabel.setForeground(Color.RED);
            return;
        }
        
        // Chamada para o endpoint /login via POST
        new Thread(() -> {
            try {
                String response = callEndpoint("/login", username, password);
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Login: " + response);
                    statusLabel.setForeground(Color.GREEN);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Erro ao conectar com o servidor: " + ex.getMessage());
                    statusLabel.setForeground(Color.RED);
                });
            }
        }).start();
    }
    
    private void handleCadastro() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Por favor, preencha todos os campos");
            statusLabel.setForeground(Color.RED);
            return;
        }
        
        // Chamada para o endpoint /cadastro via POST
        new Thread(() -> {
            try {
                String response = callEndpoint("/cadastro", username, password);
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Cadastro: " + response);
                    statusLabel.setForeground(Color.GREEN);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Erro ao conectar com o servidor: " + ex.getMessage());
                    statusLabel.setForeground(Color.RED);
                });
            }
        }).start();
    }
    
    private String callEndpoint(String endpoint, String username, String password) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        // Preparar dados para envio
        String postData = "username=" + URLEncoder.encode(username, StandardCharsets.UTF_8.toString()) +
                         "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
        
        // Enviar dados
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
            writer.write(postData);
            writer.flush();
        }
        
        int responseCode = connection.getResponseCode();
        
        BufferedReader reader;
        if (responseCode >= 200 && responseCode < 300) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        return response.toString();
    }
    
    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
} 