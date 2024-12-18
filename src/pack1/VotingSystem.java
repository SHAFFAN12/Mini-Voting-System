package pack1;
import java.util.*;

public class VotingSystem {
    private List<Candidate> candidates; // List of all candidates
    private Map<Long, Voter> registeredVoters; // Map of registered voters

    // Constructor: Initialize candidates and voter registry
    public VotingSystem(List<String> candidateNames) {
        candidates = new ArrayList<>();
        for (String name : candidateNames) {
            candidates.add(new Candidate(name));
        }
        registeredVoters = new HashMap<>();
    }

    // Register a new voter
    public String registerVoter(String name, long identityNumber, String password) {
        // Validate inputs
        if (name == null || name.trim().isEmpty()) {
            return "Invalid name! Name cannot be empty.";
        }
        if (String.valueOf(identityNumber).length() != 13) {
            return "Invalid identity number! Must be exactly 13 digits.";
        }
        if (password == null || password.length() < 6) {
            return "Weak password! Must be at least 6 characters long.";
        }
        if (registeredVoters.containsKey(identityNumber)) {
            return "Voter already registered!";
        }

        // Register voter
        registeredVoters.put(identityNumber, new Voter(name, identityNumber, password));
        return "Registration successful!";
    }

    // Login existing voter
    public Voter login(long identityNumber, String password) {
        Voter voter = registeredVoters.get(identityNumber);
        if (voter != null && voter.getPassword().equals(password)) {
            return voter;
        }
        return null; // Return null if login fails
    }

    // Vote for a candidate
    public String vote(Voter voter, String candidateName) {
        if (voter.hasVoted()) {
            return "You have already voted!";
        }

        for (Candidate candidate : candidates) {
            if (candidate.getName().equalsIgnoreCase(candidateName)) {
                candidate.addVotes();
                voter.setHasVoted(true);
                return "Vote cast successfully for " + candidateName;
            }
        }
        return "Invalid candidate!";
    }

    // Get list of all candidates
    public List<Candidate> getCandidates() {
        return candidates;
    }

    // Display voting results
    public void displayResults() {
        System.out.println("\nVoting Results:");
        for (Candidate candidate : candidates) {
            System.out.println(candidate.getName() + ": " + candidate.getVotes() + " votes");
        }
    }
}
