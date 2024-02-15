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
}
