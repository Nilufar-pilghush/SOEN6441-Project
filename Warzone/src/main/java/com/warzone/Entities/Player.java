package main.java.com.warzone.Entities;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {

    private String d_Name;
    private Set<String> d_OwnedCountries;
    private int d_NumberOfArmies;
    private List<Order> d_OrderList;


    Player(String p_Name) {
        this.d_Name = p_Name;
        d_OwnedCountries = new HashSet<>();
        d_OrderList = new java.util.ArrayList<>();
    }

    public String getName() {

        return d_Name;
    }

    public void setName(String p_Name) {

        this.d_Name = p_Name;
    }

    public Set<String> getOwnedCountries() {

        return d_OwnedCountries;
    }

    public void addOwnedCountry(String p_OwnedCountry) {

        this.d_OwnedCountries.add(p_OwnedCountry);
    }

    public void removeOwnedCountry(String p_OwnedCountry) {

        this.d_OwnedCountries.remove(p_OwnedCountry);
    }

    public List<Order> getOrderList() {

        return d_OrderList;
    }

    public void addDeployOrder(String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new Order(this.d_Name, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    public void addAttackOrder(String p_SourceCountry, String p_TargetCountry, int p_NumberOfArmies) {
        Order l_Order = new Order(this.d_Name, p_SourceCountry, p_TargetCountry, p_NumberOfArmies);
        this.d_OrderList.add(l_Order);
    }

    public void removeOrder(Order p_Order) {

        this.d_OrderList.remove(p_Order);
    }
    public Order nextOrder() {
        if (this.d_OrderList.isEmpty()) {
            return null;
        }
        return this.d_OrderList.remove(0);
    }

    public int getNumberOfArmies() {

        return d_NumberOfArmies;
    }

    public void setNumberOfArmies(int p_NumberOfArmies) {

        this.d_NumberOfArmies = p_NumberOfArmies;
    }

    public void addArmies(int p_NumberOfArmies) {

        this.d_NumberOfArmies += p_NumberOfArmies;
    }

    public boolean ownsCountry(String p_CountryName) {

        return this.d_OwnedCountries.contains(p_CountryName);
    }



}
