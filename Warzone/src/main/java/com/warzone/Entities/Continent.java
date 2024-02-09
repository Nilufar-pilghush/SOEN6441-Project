package com.warzone.Entities;

import com.warzone.Main;

import java.util.HashMap;
import java.util.Map;

public class Continent {

      private String d_Name;
      private Map<String,Country> d_Countries;
      private  Long d_CotrolValue ;
      private  String d_Owner;

      public Continent(){
              d_Countries = new HashMap<>();
      }


      //get methods
      public String getD_Name() {
            return d_Name;
      }

      public Long getD_CotrolValue() {
            return d_CotrolValue;
      }

      public String getD_Owner() {
            return d_Owner;
      }

      public Map<String, Country> getD_Countries() {
            return d_Countries;
      }

      //needed set methods


      public void setD_Name(String d_Name) {
            this.d_Name = d_Name;
      }

      public void setD_CotrolValue(Long d_CotrolValue) {
            this.d_CotrolValue = d_CotrolValue;
      }

      public void setD_Owner(String d_Owner) {
            this.d_Owner = d_Owner;
      }
}
