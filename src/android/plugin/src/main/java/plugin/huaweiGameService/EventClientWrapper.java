package plugin.huaweiGameService;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.EventsClient;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.event.Event;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import java.util.List;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.evensToJsonArray;

class EventClientWrapper {
    private static EventsClient eventsClient;

    private static void initEventsClient() {
        if (eventsClient == null) {
            eventsClient = Games.getEventsClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int grow(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initEventsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String eventId = null;
        int growAmount;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "eventId");
            if (L.isString(-1)) {
                eventId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "grow {String, Integer} expected ", Constants.grow, Constants.eventName);
                return 0;
            }

            L.pop(1);
            L.getField(2, "growAmount");
            if (L.isNumber(-1)) {
                growAmount = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "grow {String, Integer} expected ", Constants.grow, Constants.eventName);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "grow {String, Integer} expected ", Constants.grow, Constants.eventName);
            return 0;
        }

        try {
            eventsClient.grow(eventId, growAmount);
            sendDispatcher(listener, false, "", Constants.grow, Constants.eventName);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage(), Constants.grow, Constants.eventName);
        }

        return 0;
    }

    static int getEventList(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initEventsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        boolean forceReload;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "forceReload");
            if (L.isBoolean(-1)) {
                forceReload = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getEventList {boolean} expected ", Constants.getEventList, Constants.eventName);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getEventList {boolean} expected ", Constants.getEventList, Constants.eventName);
            return 0;
        }

        Task<List<Event>> task = eventsClient.getEventList(forceReload);
        task.addOnSuccessListener(new OnSuccessListener<List<Event>>() {
            @Override
            public void onSuccess(List<Event> data) {
                sendDispatcher(listener, true, "", Constants.growWithResult, Constants.achievementsClient, evensToJsonArray(data));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    int rtnCode = ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, rtnCode + "", Constants.growWithResult, Constants.achievementsClient);
                }
            }
        });

        return 0;
    }
}
