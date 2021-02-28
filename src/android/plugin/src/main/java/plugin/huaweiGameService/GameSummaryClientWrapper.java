package plugin.huaweiGameService;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.GameSummaryClient;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.gamesummary.GameSummary;
import com.naef.jnlua.LuaState;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.gameSummaryToJsonObject;

class GameSummaryClientWrapper {
    private static GameSummaryClient gameSummaryClient;

    private static void initGameSummaryClient() {
        if (gameSummaryClient == null) {
            gameSummaryClient = Games.getGameSummaryClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getLocalGameSummary(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initGameSummaryClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<GameSummary> task = gameSummaryClient.getLocalGameSummary();
        task.addOnSuccessListener(new OnSuccessListener<GameSummary>() {
            @Override
            public void onSuccess(GameSummary data) {
                sendDispatcher(listener, false, "", Constants.getLocalGameSummary, Constants.gameSummaryClient, gameSummaryToJsonObject(data));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getLocalGameSummary, Constants.gameSummaryClient);
                }
            }
        });

        return 0;
    }

    static int getGameSummary(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initGameSummaryClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<GameSummary> task = gameSummaryClient.getGameSummary();
        task.addOnSuccessListener(new OnSuccessListener<GameSummary>() {
            @Override
            public void onSuccess(GameSummary data) {
                sendDispatcher(listener, false, "", Constants.getGameSummary, Constants.gameSummaryClient, gameSummaryToJsonObject(data));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getGameSummary, Constants.gameSummaryClient);
                }
            }
        });

        return 0;
    }
}
