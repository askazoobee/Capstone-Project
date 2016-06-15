
package tech.beesknees.ripely.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WikiInfo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("extract")
    @Expose
    private String extract;

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The extract
     */
    public String getExtract() {
        return extract;
    }

    /**
     * @param extract The extract
     */
    public void setExtract(String extract) {
        this.extract = extract;
    }

}
