package refactored.handler;

public class SearchQuery {
  private String ID;
  private String name;
  private String startYear;
  private String endYear;

  public SearchQuery(String ID, String name, String startYear, String endYear) {
    this.ID = ID;
    this.name = name;
    this.startYear = startYear;
    this.endYear = endYear;
  }
}