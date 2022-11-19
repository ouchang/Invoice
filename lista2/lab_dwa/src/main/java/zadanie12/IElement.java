package zadanie12;

import java.util.ArrayList;

interface IElement {
  public void saveData(Element element, int invoiceID);

  public ArrayList<Element> readData(int invoiceID);
}
