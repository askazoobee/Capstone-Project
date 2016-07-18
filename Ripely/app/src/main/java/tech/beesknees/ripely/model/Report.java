
package tech.beesknees.ripely.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Report {

    @SerializedName("sr")
    @Expose
    private String sr;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("food")
    @Expose
    private Food food;

    /**
     * @return The sr
     */
    public String getSr() {
        return sr;
    }

    /**
     * @param sr The sr
     */
    public void setSr(String sr) {
        this.sr = sr;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The food
     */
    public Food getFood() {
        return food;
    }

    /**
     * @param food The food
     */
    public void setFood(Food food) {
        this.food = food;
    }

}
