package main.java.com.warzone.Entities;

public class Order {

    private String d_PlayerName;
    private String d_TargetCountry;
    private String d_SourceCountry;
    private int d_NumberOfArmies;


    public Order(String p_PlayerName, String p_TargetCountry, int p_NumberOfArmies) {
        this.d_PlayerName = p_PlayerName;
        this.d_TargetCountry = p_TargetCountry;
        this.d_NumberOfArmies = p_NumberOfArmies;
        this.d_SourceCountry = null;
    }

    public Order(String p_PlayerName, String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        this.d_PlayerName = p_PlayerName;
        this.d_SourceCountry = p_SourceCountry;
        this.d_TargetCountry = p_TargetCountry;
        this.d_NumberOfArmies = p_NumberOfArmies;
    }


    public void execute(GameSession gameSession) {
        if (this.d_SourceCountry != null) {
            System.out.println("Executing attack order for " + this.d_PlayerName + " on " + this.d_TargetCountry + " from " + this.d_SourceCountry + " with " + this.d_NumberOfArmies + " armies");
            this.executeAttackOrder(gameSession);
        } else {
            System.out.println("Executing deploy order for " + this.d_PlayerName + " on " + this.d_TargetCountry + " with " + this.d_NumberOfArmies + " armies");
            this.executeDeployOrder(gameSession);
        }
    }

    private void executeDeployOrder(GameSession gameSession) {
        Country targetCountry = gameSession.getCountriesInSession().get(this.d_TargetCountry);
        // If not owned by player then subtract armies & update owner
        if (targetCountry.getOwner() == null || !targetCountry.getOwner().equals(this.d_PlayerName)) {
            if (this.d_NumberOfArmies > targetCountry.getNumberOfArmies()) {
                System.out.println("Successfully captured territory.");
                if (targetCountry.getOwner() != null) {
                    Player oldOwner = gameSession.getPlayers().get(targetCountry.getOwner());
                    oldOwner.removeOwnedCountry(this.d_TargetCountry);
                }
                targetCountry.set_Owner(this.d_PlayerName);
                Player newOwner = gameSession.getPlayers().get(this.d_PlayerName);
                newOwner.addOwnedCountry(targetCountry.get_Name());
                targetCountry.setNumberOfArmies(this.d_NumberOfArmies - targetCountry.getNumberOfArmies());
            } else {
                System.out.println("Failed to capture territory.");
                targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() - this.d_NumberOfArmies);
            }

        } else {
            System.out.println("Adding armies to owned territory.");
            targetCountry.addArmies(this.d_NumberOfArmies);

        }
    }

    private void executeAttackOrder(GameSession gameSession) {
        Country sourceCountry = gameSession.getCountriesInSession().get(this.d_SourceCountry);
        Country targetCountry = gameSession.getCountriesInSession().get(this.d_TargetCountry);
        // If not owned by player then subtract armies & update owner
        if (targetCountry.getOwner() == null || !targetCountry.getOwner().equals(this.d_PlayerName)) {
            if (this.d_NumberOfArmies > targetCountry.getNumberOfArmies()) {
                System.out.println("Successfully captured territory.");
                if (targetCountry.getOwner() != null) {
                    Player oldOwner = gameSession.getPlayers().get(targetCountry.getOwner());
                    oldOwner.removeOwnedCountry(this.d_TargetCountry);
                }
                targetCountry.setOwner(this.d_PlayerName);
                Player newOwner = gameSession.getPlayers().get(this.d_PlayerName);
                newOwner.addOwnedCountry(targetCountry.get_Name());
                targetCountry.setNumberOfArmies(this.d_NumberOfArmies - targetCountry.getNumberOfArmies());
            } else {
                System.out.println("Failed to capture territory.");
                targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() - this.d_NumberOfArmies);
            }

        } else {
            System.out.println("Adding armies to owned territory.");
            targetCountry.addArmies(this.d_NumberOfArmies);
        }

        // Reduce source country armies
        sourceCountry.addArmies(-this.d_NumberOfArmies);
    }


}
