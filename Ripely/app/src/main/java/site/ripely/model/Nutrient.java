
package site.ripely.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Nutrient {

    @SerializedName("nutrient_id")
    @Expose
    private String nutrientId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * @return The nutrientId
     */
    public String getNutrientId() {
        return nutrientId;
    }

    /**
     * @param nutrientId The nutrient_id
     */
    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group The group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return The value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
