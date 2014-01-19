package models;

public class ContactImpl implements Contact {
  private int id;
  private String name;
  private String notes;

  public ContactImpl(int id, String name) {
    this.id = id;
    this.name = name;
    notes = "";
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getNotes() {
    return notes;
  }

  @Override
  public void addNotes(String note) {
    String newline = System.lineSeparator();
    note += newline + newline;
    if (notes == null) {
      notes = note;
    } else {
      notes += note;
    }
  }
}
