package landlord;

import landlord.house_info.HouseInfo;

/**
 * Created by yunlongxu on 5/12/16.
 */
public class HouseStatusEvent {
    private final int status;

    public HouseStatusEvent(int status) {

        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
