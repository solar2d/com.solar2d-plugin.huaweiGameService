package plugin.huaweiGameService;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.buoy.BuoyClient;
import com.naef.jnlua.LuaState;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;

class BuoyClientWrapper {
    private static BuoyClient buoyClient;

    private static void initBuoyClient() {
        if (buoyClient == null) {
            buoyClient = Games.getBuoyClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int showFloatWindow(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initBuoyClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        try {
            buoyClient.showFloatWindow();
            sendDispatcher(listener, false, "", Constants.showFloatWindow, Constants.achievementsClient);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage(), Constants.showFloatWindow, Constants.achievementsClient);
        }

        return 0;
    }

    static int hideFloatWindow(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initBuoyClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        try {
            buoyClient.hideFloatWindow();
            sendDispatcher(listener, false, "", Constants.hideFloatWindow, Constants.achievementsClient);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage(), Constants.hideFloatWindow, Constants.achievementsClient);
        }

        return 0;
    }

}
