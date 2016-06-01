package landlord;

import com.squareup.otto.Bus;

/**
 * Created by yunlongxu on 5/12/16.
 */
public class MyBus {
    private MyBus() {

    }
    private static Bus instance = null;

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus();
        }
        return instance;
    }
}
