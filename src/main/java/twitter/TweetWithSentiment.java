package twitter;

public class TweetWithSentiment {

    private final int sentiment;
    private String line;
    private String cssClass;

    public TweetWithSentiment(String line, String cssClass, int sentiment) {
        super();
        this.line = line;
        this.cssClass = cssClass;
        this.sentiment = sentiment;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getSentiment() { return sentiment; }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    public String toString() {
        return "TweetWithSentiment [line=" + line + ", cssClass=" + cssClass + "]";
    }
}
