package pack1;

public class Voter {
    private String name;
    private long identityNumber;
    private String password;
    private Boolean hasVoted;

    public Voter(String name, long identityNumber, String password){
        // Validate that identityNumber is 13 digits
        if (String.valueOf(identityNumber).length() > 13) {
            throw new IllegalArgumentException("Identity number must not exceed 13 digits!");
        }
        this.name = name;
        this.identityNumber = identityNumber;
        this.password = password;
        this.hasVoted = false;
    }

    public String getName(){
        return name;
    }
    public long getIdentityNumber(){
        return  identityNumber;
    }
    public String getPassword(){
        return password;
    }
    public Boolean hasVoted(){
        return hasVoted;
    }

    public void setHasVoted(Boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
