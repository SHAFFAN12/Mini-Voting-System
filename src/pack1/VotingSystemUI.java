package pack1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.List;

public class VotingSystemUI {
    private VotingSystem votingSystem;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Voter loggedInVoter;

    public VotingSystemUI() {
        // Initialize the Voting System with Candidates
        List<String> candidates = List.of("Alice", "Bob", "Charlie");
        votingSystem = new VotingSystem(candidates);

        // Initialize Frame
        frame = new JFrame("Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        // Card Layout for Switching Panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add Panels to Main Panel
        mainPanel.add(createRegistrationPanel(), "Registration");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createVotingPanel(), "Voting");
        mainPanel.add(createResultsPanel(), "Results");

        // Add Main Panel to Frame
        frame.add(mainPanel);
        frame.setVisible(true);

        // Show Registration Panel Initially
        cardLayout.show(mainPanel, "Registration");
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        JLabel lblTitle = new JLabel("Voter Registration", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridwidth = 2; // Span title across two columns
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1; // Reset to single column for fields

        // Name Label and Field
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblName, gbc);

        JTextField txtName = new JTextField(15); // Smaller size
        txtName.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtName, gbc);

        // Identity Number Label and Field
        JLabel lblID = new JLabel("Identity Number:");
        lblID.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblID, gbc);

        JTextField txtID = new JTextField(15); // Smaller size
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtID, gbc);

        // Password Label and Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(15); // Smaller size
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtPassword, gbc);

        // Register Button
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setBackground(new Color(0, 123, 255));
        btnRegister.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span button across two columns
        panel.add(btnRegister, gbc);

        // Message Label
        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span message across two columns
        panel.add(lblMessage, gbc);

        // Go to Login Button
        JButton btnGoToLogin = new JButton("Go to Login");
        btnGoToLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnGoToLogin.setBackground(new Color(0, 123, 255));
        btnGoToLogin.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Span button across two columns
        panel.add(btnGoToLogin, gbc);

        // Button Actions
        btnRegister.addActionListener(e -> {
            String name = txtName.getText();
            long id;
            try {
                id = Long.parseLong(txtID.getText());
                if (txtID.getText().length() != 13) {
                    lblMessage.setText("Identity number must be exactly 13 digits!");
                    lblMessage.setForeground(Color.RED);
                    return;
                }
            } catch (NumberFormatException ex) {
                lblMessage.setText("Invalid identity number! Please enter a valid number.");
                lblMessage.setForeground(Color.RED);
                return;
            }

            String password = new String(txtPassword.getPassword());
            String result = votingSystem.registerVoter(name, id, password);

            lblMessage.setText(result);
            lblMessage.setForeground(result.startsWith("Voter registered") ? Color.GREEN : Color.BLACK);

            // Clear form fields after successful registration
            if (result.startsWith("Voter registered")) {
                txtName.setText("");
                txtID.setText("");
                txtPassword.setText("");
            }
        });

        btnGoToLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Voter Login", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        panel.add(lblTitle, gbc);

        // Identity Number Field
        JLabel lblID = new JLabel("Identity Number:");
        lblID.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset to one column
        panel.add(lblID, gbc);

        JTextField txtID = new JTextField(15);
        txtID.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtID, gbc);

        // Password Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtPassword, gbc);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(0, 123, 255));
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        panel.add(btnLogin, gbc);

        // Message Label
        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(lblMessage, gbc);

        // Back to Registration Button
        JButton btnBackToRegister = new JButton("Back to Registration");
        btnBackToRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnBackToRegister.setBackground(new Color(0, 123, 255));
        btnBackToRegister.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(btnBackToRegister, gbc);

        // Button Actions
        btnLogin.addActionListener(e -> {
            String idText = txtID.getText();
            String password = new String(txtPassword.getPassword());

            // Validate ID length (13 digits) and numeric
            if (idText.length() != 13 || !idText.matches("\\d+")) {
                lblMessage.setText("Invalid identity number! Must be 13 digits.");
                lblMessage.setForeground(Color.RED);
                return;
            }

            // Convert ID to long
            long id = 0;
            try {
                id = Long.parseLong(idText); // Convert string to long
            } catch (NumberFormatException ex) {
                lblMessage.setText("Invalid identity number format.");
                lblMessage.setForeground(Color.RED);
                return;
            }

            // Login Logic
            loggedInVoter = votingSystem.login(id, password);
            if (loggedInVoter == null) {
                lblMessage.setText("Invalid login credentials!");
                lblMessage.setForeground(Color.RED);
            } else {
                lblMessage.setText("Login Successful!");
                lblMessage.setForeground(Color.GREEN);

                // Navigate to Voting Panel
                cardLayout.show(mainPanel, "Voting");
            }
        });

        btnBackToRegister.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));

        return panel;
    }

    private JPanel createVotingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel lblTitle = new JLabel("Cast Your Vote", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup group = new ButtonGroup();
        JPanel candidatesPanel = new JPanel(new GridLayout(votingSystem.getCandidates().size(), 1));
        candidatesPanel.setBorder(BorderFactory.createTitledBorder("Candidates"));

        for (Candidate candidate : votingSystem.getCandidates()) {
            JRadioButton btnCandidate = new JRadioButton(candidate.getName());
            btnCandidate.setFont(new Font("Arial", Font.PLAIN, 16));
            group.add(btnCandidate);
            candidatesPanel.add(btnCandidate);
        }

        JButton btnVote = createButton("Vote");
        JLabel lblMessage = createMessageLabel();

        btnVote.addActionListener(e -> {
            if (loggedInVoter.hasVoted()) {
                lblMessage.setText("You have already voted!");
                lblMessage.setForeground(Color.RED);
                return;
            }

            String selectedCandidate = null;
            Enumeration<AbstractButton> buttons = group.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selectedCandidate = button.getText();
                    break;
                }
            }

            if (selectedCandidate != null) {
                String voteResult = votingSystem.vote(loggedInVoter, selectedCandidate);
                lblMessage.setText(voteResult);
                lblMessage.setForeground(voteResult.startsWith("Vote cast") ? Color.GREEN : Color.RED);
            } else {
                lblMessage.setText("Please select a candidate!");
                lblMessage.setForeground(Color.RED);
            }
        });

        JButton btnShowResults = createButton("Show Results");
        btnShowResults.addActionListener(e -> cardLayout.show(mainPanel, "Results"));

        panel.add(lblTitle);
        panel.add(candidatesPanel);
        panel.add(btnVote);
        panel.add(lblMessage);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnShowResults);

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea txtResults = new JTextArea();
        txtResults.setEditable(false);
        txtResults.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton btnViewResults = createButton("View Results");
        btnViewResults.addActionListener(e -> {
            StringBuilder results = new StringBuilder("Voting Results:\n\n");
            for (Candidate candidate : votingSystem.getCandidates()) {
                results.append(candidate.getName())
                        .append(": ")
                        .append(candidate.getVotes())
                        .append(" votes\n");
            }
            txtResults.setText(results.toString());
        });

        JButton btnBackToLogin = createButton("Back to Login");
        btnBackToLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        panel.add(new JScrollPane(txtResults), BorderLayout.CENTER);
        panel.add(btnViewResults, BorderLayout.NORTH);
        panel.add(btnBackToLogin, BorderLayout.SOUTH);

        return panel;
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setToolTipText(placeholder);
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setToolTipText(placeholder);
        return passwordField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }

    private JLabel createMessageLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingSystemUI::new);
    }
}
