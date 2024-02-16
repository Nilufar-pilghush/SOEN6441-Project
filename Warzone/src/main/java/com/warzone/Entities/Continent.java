package main.java.com.warzone.Entities;



import java.util.HashMap;
import java.util.Map;

public class Continent {

      private String d_Name;
      private Map<String,Country> d_Countries;
      private  Long d_ControlValue ;
      private  String d_Owner;

      public Continent(){
              d_Countries = new HashMap<>();
      }


      //get methods
      public String get_Name() {
            return d_Name;
      }

      public int get_ControlValue() {
            return d_ControlValue;
      }

      public String get_Owner() {
            return d_Owner;
      }

      public Map<String, Country> getD_Countries() {
            return d_Countries;
      }

      //needed set methods


      public void set_Name(String d_Name) {
            this.d_Name = d_Name;
      }

      public void set_ControlValue(Long d_ControlValue) {
            this.d_ControlValue = d_ControlValue;
      }

      public void set_Owner(String d_Owner) {
            this.d_Owner = d_Owner;
      }
}
