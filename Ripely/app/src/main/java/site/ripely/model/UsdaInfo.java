
package site.ripely.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsdaInfo {

    @SerializedName("report")
    @Expose
    private Report report;

    /**
     * 
     * @return
     *     The report
     */
    public Report getReport() {
        return report;
    }

    /**
     * 
     * @param report
     *     The report
     */
    public void setReport(Report report) {
        this.report = report;
    }

}
