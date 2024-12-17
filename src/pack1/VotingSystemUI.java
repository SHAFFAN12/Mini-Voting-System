package pack1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.List;

public class VotingSystemUI {
    private VotingSystem votingSystem; // Backend integration
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
        frame.setSize(500, 400);

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
        JPanel panel = new JPanel(new GridLayout(6, 1));
        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();
        JLabel lblID = new JLabel("Identity Number:");
        JTextField txtID = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField();
        JButton btnRegister = new JButton("Register");

        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);

        btnRegister.addActionListener(e -> {
            String name = txtName.getText();

            // Parse the ID from String to long and handle potential errors
            long id;
            try {
                id = Long.parseLong(txtID.getText());
                // Optionally, validate ID length (if needed)
                if (txtID.getText().length() > 13) {
                    lblMessage.setText("Identity number must not exceed 13 digits!");
                    return;
                }
            } catch (NumberFormatException ex) {
                lblMessage.setText("Invalid identity number! Please enter a valid number.");
                return;
            }

            String password = new String(txtPassword.getPassword());
            String result = votingSystem.registerVoter(name, id, password);
            lblMessage.setText(result);
        });


        JButton btnGoToLogin = new JButton("Go to Login");
        btnGoToLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblID);
        panel.add(txtID);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnRegister);
        panel.add(lblMessage);
        panel.add(btnGoToLogin);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JLabel lblID = new JLabel("Identity Number:");
        JTextField txtID = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);

        btnLogin.addActionListener(e -> {
            String id = txtID.getText();
            String password = new String(txtPassword.getPassword());
            loggedInVoter = votingSystem.login(id, password);
            if (loggedInVoter == null) {
                lblMessage.setText("Invalid login credentials!");
            } else {
                lblMessage.setText("Login Successful!");
                cardLayout.show(mainPanel, "Voting");
            }
        });

        JButton btnBackToRegister = new JButton("Back to Registration");
        btnBackToRegister.addActionListener(e -> cardLayout.show(mainPanel, "Registration"));

        panel.add(lblID);
        panel.add(txtID);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(lblMessage);
        panel.add(btnBackToRegister);

        return panel;
    }

    private JPanel createVotingPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel lblInstruction = new JLabel("Select a candidate to vote for:");
        panel.add(lblInstruction);

        ButtonGroup group = new ButtonGroup();
        JPanel candidatesPanel = new JPanel(new GridLayout(votingSystem.getCandidates().size(), 1));

        for (Candidate candidate : votingSystem.getCandidates()) {
            JRadioButton btnCandidate = new JRadioButton(candidate.getName());
            group.add(btnCandidate);
            candidatesPanel.add(btnCandidate);
        }

        JButton btnVote = new JButton("Vote");
        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);

        btnVote.addActionListener(e -> {
            if (loggedInVoter.hasVoted()) {
                lblMessage.setText("You have already voted!");
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
            } else {
                lblMessage.setText("Please select a candidate!");
            }
        });

        JButton btnShowResults = new JButton("Show Results");
        btnShowResults.addActionListener(e -> cardLayout.show(mainPanel, "Results"));

        panel.add(candidatesPanel);
        panel.add(btnVote);
        panel.add(lblMessage);
        panel.add(btnShowResults);

        return panel;
    }


    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea txtResults = new JTextArea();
        txtResults.setEditable(false);

        JButton btnViewResults = new JButton("View Results");
        btnViewResults.addActionListener(e -> {
            StringBuilder results = new StringBuilder("Voting Results:\n");
            for (Candidate candidate : votingSystem.getCandidates()) {
                results.append(candidate.getName())
                        .append(": ")
                        .append(candidate.getVotes())
                        .append(" votes\n");
            }
            txtResults.setText(results.toString());
        });

        JButton btnBackToLogin = new JButton("Back to Login");
        btnBackToLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        panel.add(new JScrollPane(txtResults), BorderLayout.CENTER);
        panel.add(btnViewResults, BorderLayout.NORTH);
        panel.add(btnBackToLogin, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VotingSystemUI::new);
    }
}
