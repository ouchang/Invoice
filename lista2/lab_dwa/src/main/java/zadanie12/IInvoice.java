package zadanie12;

interface IInvoice {
  public void saveData(Invoice invoice);

  public void readDataCustomer(Invoice invoice);

  public void readDataSeller(Invoice invoice);

  public int getInvoiceID();
}
