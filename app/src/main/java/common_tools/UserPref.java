package common_tools;

import android.content.Context;

import landlord.landlord_info.Landlord;
import tenant.tenant_info.Tenant;

/**
 * Created by cheyikung on 4/25/16.
 */
public class UserPref {

    public static void setLanlordUser(Landlord landlord, Context ctx){
        UserPrefHelper userPrefHelper = UserPrefHelper.getUserPrefHelper(ctx, 0);
        userPrefHelper.putObject("landlord_user", landlord);
        userPrefHelper.commit();
    }

    public static Landlord getLanlordUser(Context ctx){
        UserPrefHelper userPrefHelper = UserPrefHelper.getUserPrefHelper(ctx, 0);
        Landlord landlord = userPrefHelper.getObject("landlord_user", Landlord.class);
        return landlord;
    }

    public static void setTenantUser(Tenant tenant, Context ctx){
        UserPrefHelper userPrefHelper = UserPrefHelper.getUserPrefHelper(ctx, 0);
        userPrefHelper.putObject("tenant_user", tenant);
        userPrefHelper.commit();
    }

    public static Tenant getTenantUser(Context ctx){
        UserPrefHelper userPrefHelper = UserPrefHelper.getUserPrefHelper(ctx, 0);
        Tenant tenant = userPrefHelper.getObject("tenant_user", Tenant.class);
        return tenant;
    }

    public static void clearUser(Context ctx){
        UserPrefHelper userPrefHelper = UserPrefHelper.getUserPrefHelper(ctx, 0);
        userPrefHelper.clearObject();
        userPrefHelper.commit();
    }
}
