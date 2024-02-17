package main.java.com.warzone.Entities;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a continent in the Warzone game.
 *
 * This class encapsulates the properties and behavior of a continent in the Warzone game,
 * including its name, control value, owner, and the countries it contains.
 * Continents play a crucial role in determining gameplay strategy and control.
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */

public class Continent {

      /**
       * The name of the continent.
       */
      private String d_Name;

      /**
       * A map containing the countries within the continent.
       */
      private Map<String,Country> d_Countries;

      /**
       * The control value of the continent.
       */
      private  int d_ControlValue ;

      /**
       * The owner of the continent.
       */
      private  String d_Owner;

      public Continent(){
              d_Countries = new HashMap<>();
      }


      /**
       * Retrieves the name of the continent.
       *
       * @return The name of the continent.
       */
      public String getName() {
            return d_Name;
      }

      /**
       * Retrieves the control value of the continent.
       *
       * @return The control value of the continent.
       */
      public int getControlValue() {
            return d_ControlValue;
      }

      /**
       * Retrieves the owner of the continent.
       *
       * @return The owner of the continent.
       */
      public String getOwner() {
            return d_Owner;
      }

      /**
       * Get all the countries of the continent.
       *
       * @return Map of countries in the continent
       */
      public Map<String, Country> getCountries() {
            return d_Countries;
      }

      /**
       * Sets the name of the continent.
       *
       * @param d_Name The name to set for the continent.
       */
      public void setName(String d_Name) {
            this.d_Name = d_Name;
      }

      /**
       * Sets the control value of the continent.
       *
       * @param d_ControlValue The control value to set for the continent.
       */
      public void setControlValue(int d_ControlValue) {
            this.d_ControlValue = d_ControlValue;
      }

      /**
       * Sets the owner of the continent.
       *
       * @param d_Owner The owner to set for the continent.
       */
      public void setOwner(String d_Owner) {
            this.d_Owner = d_Owner;
      }




}
