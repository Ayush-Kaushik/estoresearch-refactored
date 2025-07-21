package refactored.handler;

public class SearchQueryBuilder {
  private String ID;
  private String name;

  private String startYear;
  private String endYear;

  public SearchQueryBuilder setID(String ID) {
    this.ID = ID;
    return this;
  }

  public SearchQueryBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public SearchQueryBuilder setStartYear(String startYear) {
    this.startYear = startYear;
    return this;
  }

  public SearchQueryBuilder setEndYear(String endYear) {
    this.endYear = endYear;
    return this;
  }

  public SearchQuery build() {
    return new SearchQuery(ID, name, startYear, endYear);
  }
}