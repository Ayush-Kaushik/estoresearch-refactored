package refactored.model;

public class SearchQuery {
  private String id;
  private String name;
  private String startYear;
  private String endYear;

  public SearchQuery(String ID, String name, String startYear, String endYear) {
    this.id = ID;
    this.name = name;
    this.startYear = startYear;
    this.endYear = endYear;
  }

  public String getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getStartYear() {
    return startYear;
  }

  public String getEndYear() {
    return endYear;
  }
}