package pack1;

import java.util.*;

public class VotingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> candidateNames = Arrays.asList("Alice", "Bob", "Charlie");
        VotingSystem votingSystem = new VotingSystem(candidateNames);

        while (true) {
            System.out.println("\n=== Voting System Menu ===");
            System.out.println("1. Register as a Voter");
            System.out.println("2. Login to Vote");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Voter registration
                    System.out.print("Enter your Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter your Identity Number: ");
                    String identityStr = scanner.nextLine(); // Read input as String for validation

                    long identityNumber;
                    try {
                        identityNumber = Long.parseLong(identityStr); // Convert to long
                        if (identityStr.length() != 13) {
                            System.out.println("Identity number must be exactly 13 digits!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid identity number! Please enter a numeric value.");
                        break;
                    }

                    System.out.print("Set your Password: ");
                    String password = scanner.nextLine();

                    String registrationResult = votingSystem.registerVoter(name, identityNumber, password);
                    System.out.println(registrationResult);
                    break;

                case 2:
                    // Voter login
                    System.out.print("Enter your Identity Number: ");
                    String idStr = scanner.nextLine(); // Read input as String for validation
                    long id;

                    try {
                        id = Long.parseLong(idStr); // Convert to long
                        if (idStr.length() != 13) {
                            System.out.println("Identity number must be exactly 13 digits!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid identity number! Please enter a numeric value.");
                        break;
                    }

                    System.out.print("Enter your Password: ");
                    String pass = scanner.nextLine();

                    Voter voter = votingSystem.login(id, pass);
                    if (voter == null) {
                        System.out.println("Invalid login credentials!");
                        break;
                    }

                    if (voter.hasVoted()) {
                        System.out.println("You have already voted!");
                        break;
                    }

                    // Display candidates
                    System.out.println("Candidates:");
                    for (String candidate : candidateNames) {
                        System.out.println(candidate);
                    }

                    // Cast vote
                    System.out.print("Enter the candidate's name you want to vote for: ");
                    String candidateName = scanner.nextLine();
                    String voteResult = votingSystem.vote(voter, candidateName);
                    System.out.println(voteResult);
                    break;

                case 3:
                    // Exit and display results
                    System.out.println("Thank you for using the Voting System!");
                    votingSystem.displayResults();
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        }
    }
}


