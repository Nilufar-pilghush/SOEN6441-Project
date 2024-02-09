package main.java.com.warzone.Entities;

import com.warzone.Entities.Order;

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
}
