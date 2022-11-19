package zadanie12;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Unit test for App.
 */
public class AppTest 
{
    @Test
    public void testSumToPayInt() {
      Element e1 = new Element(2, "Sok", 3);
      Element e2 = new Element(1, "Czekolda", 5);
      Element e3 = new Element(4, "Mleko", 2);
  
      assertEquals(19.00, Invoice.countSumToPayA(new ArrayList<>(
          Arrays.asList(e1, e2, e3)
      )), 0);
    }
  
    @Test
    public void testSumToPayDouble() {
      Element e1 = new Element(2, "Sok", 3.5);
      Element e2 = new Element(1, "Czekolda", 5.69);
      Element e3 = new Element(4, "Mleko", 2.24);
  
      assertEquals(21.65, Invoice.countSumToPayA(new ArrayList<>(
          Arrays.asList(e1, e2, e3)
      )), 0);
    }
    
    @Test
    public void testSumToPayIntDouble() {
      Element e1 = new Element(2, "Sok", 3.99);
      Element e2 = new Element(1, "Czekolda", 5);
      Element e3 = new Element(4, "Mleko", 2.14);
  
      assertEquals(21.54, Invoice.countSumToPayA(new ArrayList<>(
          Arrays.asList(e1, e2, e3)
      )), 0);
    }
}
