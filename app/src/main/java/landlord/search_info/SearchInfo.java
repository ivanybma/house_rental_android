package landlord.search_info;

/**

 * Created by yunlongxu on 4/19/16.
 */
public class SearchInfo {
    private String information;
    private int iconId;

    public SearchInfo(String information, int iconId) {
        this.information = information;
        this.iconId = iconId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
