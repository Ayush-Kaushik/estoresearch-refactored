package estore.e2e;

/**
 * ApplicationInteraction This class is mainly to store record of interaction
 * for example:
 * 
 * sequence = 1
 * input = "search"
 * output = "search results"
 */
public class ApplicationInteraction {

    private int sequence;
    private String input;
    private String output;

    public ApplicationInteraction(int sequence, String input, String output) {
        this.input = input;
        this.output = output;
        this.sequence = sequence;
    }

    public int getSequence() {
        return this.sequence;
    }

    public String getInput() {
        return this.input;
    }

    public String getOutput() {
        return this.output;
    }
}
