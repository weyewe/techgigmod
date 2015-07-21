package weyewe.com.techgig;

/**
 * Created by willy on 21/07/15.
 */
public class FeedItem {
    private String title;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public FeedItem( String url , String title){
        this.title = title;
        this.thumbnail = url;
    }
}
