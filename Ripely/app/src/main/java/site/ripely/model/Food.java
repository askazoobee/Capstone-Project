
package site.ripely.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("ndbno")
    @Expose
    private String ndbno;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nutrients")
    @Expose
    private List<Nutrient> nutrients = new ArrayList<Nutrient>();

    /**
     * 
     * @return
     *     The ndbno
     */
    public String getNdbno() {
        return ndbno;
    }

    /**
     * 
     * @param ndbno
     *     The ndbno
     */
    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The nutrients
     */
    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    /**
     * 
     * @param nutrients
     *     The nutrients
     */
    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

}
