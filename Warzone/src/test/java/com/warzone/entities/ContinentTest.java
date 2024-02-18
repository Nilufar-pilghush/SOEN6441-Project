package test.java.com.warzone.entities;

import main.java.com.warzone.Entities.Continent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Test class
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
        public void whenGetName_ExpectContinentName(){
            d_Continent.setName("Asia");
            Assertions.assertEquals("Asia", d_Continent.getName());
        }

        /**
         *
         */
        @Test
        public void whenSetName_ExpectContinentNameSet(){
            d_Continent.setName("Asia");
            Assertions.assertEquals("Asia", d_Continent.getName());
        }

        /**
         *
         */
        @Test
        public void whenGetControl_ExpectContinentControlValue(){
            d_Continent.setControlValue(5);
            Assertions.assertEquals(5, d_Continent.getControlValue());
        }

        /**
         *
         */
        @Test
        public void whenSetControl_ExpectContinentControlValueSet(){
            d_Continent.setControlValue(5);
            Assertions.assertEquals(5, d_Continent.getControlValue());
        }

        /**
         *
         */
        @Test
        public void whenGetOwner_ExpectContinentOwner(){
            d_Continent.setOwner("Niloufar");
            Assertions.assertEquals("Niloufar", d_Continent.getOwner());
        }

        /**
         *
         */
        @Test
        public void whenSetOwner_ExpectContinentOwnerSet(){
            d_Continent.setOwner("Niloufar");
            Assertions.assertEquals("Niloufar", d_Continent.getOwner());
        }

        /**
         *
         */
        @Test
        public void whenGetCountries_ExpectCountries(){
//        d_Continent.getCountries().put("India", new Country("India", "Asia", 1L));
//        Assertions.assertTrue(d_Continent.getCountries().containsKey("India"));
        }
    }
