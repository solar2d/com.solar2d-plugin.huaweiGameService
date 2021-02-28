package plugin.huaweiGameService;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.GamesClient;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;

class GamesClientWrapper {
    private static GamesClient gamesClient;

    private static void initGamesClient() {
        if (gamesClient == null) {
            gamesClient = Games.getGamesClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getAppId(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initGamesClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<String> task = gamesClient.getAppId();
        task.addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String data) {
                sendDispatcher(listener, false, "", Constants.getAppId, Constants.gamesClient, data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getAppId, Constants.gamesClient);
                }
            }
        });

        return 0;
    }

    static int setPopupsPosition(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initGamesClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        int position;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "position");
            if (L.isNumber(-1)) {
                position = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "setPopupsPosition {int} expected ", Constants.setPopupsPosition, Constants.gamesClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "setPopupsPosition {int} expected ", Constants.setPopupsPosition, Constants.gamesClient);
            return 0;
        }

        Task<Void> task = gamesClient.setPopupsPosition(position);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                sendDispatcher(listener, false, "", Constants.setPopupsPosition, Constants.gamesClient);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.setPopupsPosition, Constants.gamesClient);
                }
            }
        });

        return 0;
    }

    static int cancelGameService(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initGamesClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<Boolean> task = gamesClient.cancelGameService();
        task.addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                sendDispatcher(listener, false, "", Constants.cancelGameService, Constants.gamesClient, data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.cancelGameService, Constants.gamesClient);
                }
            }
        });

        return 0;
    }
}
