package pack1;
import java.util.*;

public class VotingSystem {
    private List<Candidate> candidates;
    private Map<Long, Voter> registeredVoters;


    public VotingSystem(List<String> candidateNames){
        candidates = new ArrayList<>();
        for(String name: candidateNames){
            candidates.add(new Candidate(name));
        }
        registeredVoters = new HashMap<>();
    }

    // Register a new User
    public String registerVoter(String name, long identityNumber, String password){
        if(registeredVoters.containsKey(identityNumber)){
            return "Voter already registered!!";
        }
        registeredVoters.put(identityNumber, new Voter(name, identityNumber, password));
        return "Registered Successfull!!";
    }

    //login existing User

    public Voter login(String identityNumber, String password){
        Voter voter = registeredVoters.get(identityNumber);
        if(voter != null && voter.getPassword().equals(password)){
            return voter;
        }
        return null;
    }

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



