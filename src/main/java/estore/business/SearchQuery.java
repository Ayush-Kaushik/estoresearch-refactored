package estore.business;

public class SearchQuery {
    private final String id;
    private final String name;
    private final String year;

    public SearchQuery(String id, String name, String year) {
        this.id = id == null ? "" : id;
        this.name = name == null ? "" : name;
        this.year = year == null ? "" : year;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }
}
