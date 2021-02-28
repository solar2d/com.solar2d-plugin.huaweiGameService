package plugin.huaweiGameService;

import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.PlayersClient;
import com.huawei.hms.jos.games.player.Player;
import com.huawei.hms.jos.games.player.PlayerExtraInfo;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.playerExtraInfoToJsonObject;
import static plugin.huaweiGameService.Utils.playerToJsonObject;

class PlayersClientWrapper {
    private static PlayersClient playersClient;

    private static void initPlayersClient() {
        if (playersClient == null) {
            playersClient = Games.getPlayersClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getCurrentPlayer(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initPlayersClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<Player> task = playersClient.getCurrentPlayer();
        task.addOnSuccessListener(new OnSuccessListener<Player>() {
            @Override
            public void onSuccess(Player data) {
                sendDispatcher(listener, false, "", Constants.getCurrentPlayer, Constants.playersClient, playerToJsonObject(data));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getCurrentPlayer, Constants.playersClient);
                }
            }
        });
        return 0;
    }

    static int getGamePlayer(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initPlayersClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<Player> task = playersClient.getGamePlayer();
        task.addOnSuccessListener(new OnSuccessListener<Player>() {
            @Override
            public void onSuccess(Player data) {
                sendDispatcher(listener, false, "", Constants.getGamePlayer, Constants.playersClient, playerToJsonObject(data));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getGamePlayer, Constants.playersClient);
                }
            }
        });

        return 0;
    }

    static int getCachePlayerId(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initPlayersClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<String> task = playersClient.getCachePlayerId();
        task.addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String data) {
                sendDispatcher(listener, false, data, Constants.getCachePlayerId, Constants.playersClient);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getCachePlayerId, Constants.playersClient);
                }
            }
        });
        return 0;
    }

    static int getPlayerExtraInfo(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initPlayersClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String transactionId = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "transactionId");
            if (L.isString(-1)) {
                transactionId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getPlayerExtraInfo {String} expected ", Constants.getPlayerExtraInfo, Constants.playersClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getPlayerExtraInfo {String} expected ", Constants.getPlayerExtraInfo, Constants.playersClient);
            return 0;
        }

        Task<PlayerExtraInfo> task = playersClient.getPlayerExtraInfo(transactionId);
        task.addOnSuccessListener(new OnSuccessListener<PlayerExtraInfo>() {
            @Override
            public void onSuccess(PlayerExtraInfo data) {
                sendDispatcher(listener, false, "", Constants.getPlayerExtraInfo, Constants.playersClient, playerExtraInfoToJsonObject(data));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getPlayerExtraInfo, Constants.playersClient);
                }
            }
        });
        return 0;
    }

    static int submitPlayerEvent(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initPlayersClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String playerId = null;
        String eventId = null;
        String eventType = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "playerId");
            if (L.isString(-1)) {
                playerId = L.toString(-1);
                L.pop(1);
            }

            L.getField(2, "eventId");
            if (L.isString(-1)) {
                eventId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "submitPlayerEvent {String, String, String} expected ", Constants.submitPlayerEvent, Constants.playersClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "eventType");
            if (L.isString(-1)) {
                eventType = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "submitPlayerEvent {String, String, String} expected ", Constants.submitPlayerEvent, Constants.playersClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "submitPlayerEvent {String, String, String} expected ", Constants.submitPlayerEvent, Constants.playersClient);
            return 0;
        }

        Task<String> task;
        if (playerId != null) {
            task = playersClient.submitPlayerEvent(playerId, eventId, eventType);
        } else {
            task = playersClient.submitPlayerEvent(eventId, eventType);
        }

        task.addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String data) {
                sendDispatcher(listener, false, "", Constants.submitPlayerEvent, Constants.playersClient);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.submitPlayerEvent, Constants.playersClient);
                }
            }
        });
        return 0;
    }

    static int savePlayerInfo(LuaState L) {
        return 0;
    }

    static int setGameTrialProcess(LuaState L) {
        return 0;
    }
}
