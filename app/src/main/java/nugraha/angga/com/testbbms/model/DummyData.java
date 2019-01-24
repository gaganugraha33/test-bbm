package nugraha.angga.com.testbbms.model;

public class DummyData {
    private String urlImage;
    private String title;
    private String desc;
    private String date;
    private float rating;

    public DummyData(String urlImage, String title, String desc, float rating, String date){
        this.urlImage = urlImage;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.date = date;
    }


    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
