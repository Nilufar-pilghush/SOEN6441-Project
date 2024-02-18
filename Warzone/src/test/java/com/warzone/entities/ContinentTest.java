package test.java.com.warzone.entities;

import main.java.warzone.entities.Continent;
import main.java.warzone.entities.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Niloufar Pilgush
 * @author Nasrin Maarefi
 * @author Jerome Kithinji
 * @author Ali sayed Salehi
 * @author Fatemeh Chaji
 * @version 1.0.0
 */
    public class ContinentTest {

        /**
         *
         */
        private Continent d_Continent;

        /**
         *
         */
        public ContinentTest(){
            d_Continent = new Continent();
        }

        /**
         *
         */
        @Test
        public void whenGetName_ExpectContinentNameTest(){
            d_Continent.setName("Asia");
            Assertions.assertEquals("Asia", d_Continent.getName());
        }

        /**
         *
         */
        @Test
        public void whenSetName_ExpectContinentNameSetTest(){
            d_Continent.setName("Asia");
            Assertions.assertEquals("Asia", d_Continent.getName());
        }

        /**
         *
         */
        @Test
        public void whenGetControl_ExpectContinentControlValueTest(){
            d_Continent.setControlValue(5);
            Assertions.assertEquals(5, d_Continent.getControlValue());
        }

        /**
         *
         */
        @Test
        public void whenSetControl_ExpectContinentControlValueSetTest(){
            d_Continent.setControlValue(5);
            Assertions.assertEquals(5, d_Continent.getControlValue());
        }

        /**
         *
         */
        @Test
        public void whenGetOwner_ExpectContinentOwnerTest(){
            d_Continent.setOwner("Niloufar");
            Assertions.assertEquals("Niloufar", d_Continent.getOwner());
        }

        /**
         *
         */
        @Test
        public void whenSetOwner_ExpectContinentOwnerSetTest(){
            d_Continent.setOwner("Niloufar");
            Assertions.assertEquals("Niloufar", d_Continent.getOwner());
        }

        /**
         *
         */
        @Test
        public void whenGetCountries_ExpectCountriesTest(){
        d_Continent.getCountries().put("India", new Country(12, "Asia", "Yes"));
        Assertions.assertTrue(d_Continent.getCountries().containsKey("India"));
        }
    }

