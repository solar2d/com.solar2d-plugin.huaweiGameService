package plugin.huaweiGameService;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.playerstats.GamePlayerStatistics;
import com.huawei.hms.jos.games.playerstats.GamePlayerStatisticsClient;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.gamePlayerStatisticsToJsonObject;

class GamePlayerStatisticsClientWrapper {
    private static GamePlayerStatisticsClient gamePlayerStatisticsClient;

    private static void initGamePlayerStatisticsClient() {
        if (gamePlayerStatisticsClient == null) {
            gamePlayerStatisticsClient = Games.getGamePlayerStatsClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getGamePlayerStatistics(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initGamePlayerStatisticsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        boolean isRealTime;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "isRealTime");
            if (L.isBoolean(-1)) {
                isRealTime = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getGamePlayerStatistics {boolean} expected ", Constants.getGamePlayerStatistics, Constants.gamePlayerStatisticsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getGamePlayerStatistics {boolean} expected ", Constants.getGamePlayerStatistics, Constants.gamePlayerStatisticsClient);
            return 0;
        }

        Task<GamePlayerStatistics> task = gamePlayerStatisticsClient.getGamePlayerStatistics(isRealTime);
        task.addOnSuccessListener(new OnSuccessListener<GamePlayerStatistics>() {
            @Override
            public void onSuccess(GamePlayerStatistics gamePlayerStatistics) {
                sendDispatcher(listener, false, "", Constants.getGamePlayerStatistics, Constants.gamePlayerStatisticsClient, gamePlayerStatisticsToJsonObject(gamePlayerStatistics));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getGamePlayerStatistics, Constants.gamePlayerStatisticsClient);
                }
            }
        });

        return 0;
    }
}
