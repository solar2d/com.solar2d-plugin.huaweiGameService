//
//  LuaLoader.java
//  TemplateApp
//
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.huaweiGameService;

import android.content.Context;
import android.util.Log;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.config.LazyInputStream;
import com.huawei.hms.jos.JosApps;
import com.huawei.hms.jos.JosAppsClient;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


@SuppressWarnings("WeakerAccess")
public class LuaLoader implements JavaFunction, CoronaRuntimeListener {

    private int fListener;
    public static String TAG = "HuaweiGameSerice";

    private static final String EVENT_NAME = Constants.eventName;

    public static CoronaRuntimeTaskDispatcher fDispatcher = null;

    public LuaLoader() {
        fListener = CoronaLua.REFNIL;
        CoronaEnvironment.addRuntimeListener(this);
    }

    @Override
    public int invoke(LuaState L) {
        NamedJavaFunction[] luaFunctions = new NamedJavaFunction[]{
                new init(),
                new achievementsClient(),
                new rankingsClient(),
                new archivesClient(),
                new eventsClient(),
                new gamesClient(),
                new playersClient(),
                new gameSummaryClient(),
                new gamePlayerStatisticsClient(),
                new buoyClient()
        };
        String libName = L.toString(1);
        L.register(libName, luaFunctions);

        return 1;
    }

    private class init implements NamedJavaFunction {

        @Override
        public String getName() {
            return "init";
        }

        @Override
        public int invoke(LuaState L) {
            int listenerIndex = 1;

            if (CoronaLua.isListener(L, listenerIndex, EVENT_NAME)) {
                fListener = CoronaLua.newRef(L, listenerIndex);
            }

            CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
            AGConnectServicesConfig config = AGConnectServicesConfig.fromContext(activity);
            config.overlayWith(new LazyInputStream(activity) {
                public InputStream get(Context context) {
                    try {
                        Log.i(TAG, "agconnect-services.json ");
                        return context.getAssets().open("agconnect-services.json");
                    } catch (IOException e) {
                        Log.i(TAG, "agconnect-services.json reading Exception " + e);
                        return null;
                    }
                }
            });

            JosAppsClient appsClient = JosApps.getJosAppsClient(activity);
            appsClient.init();

            fDispatcher = new CoronaRuntimeTaskDispatcher(L);

            return 0;
        }
    }

    public static class achievementsClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.achievementsClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.getShowAchievementListIntent:
                    return AchievementsClientWrapper.getShowAchievementListIntent(L);
                case Constants.grow:
                    return AchievementsClientWrapper.grow(L);
                case Constants.growWithResult:
                    return AchievementsClientWrapper.growWithResult(L);
                case Constants.getAchievementList:
                    return AchievementsClientWrapper.getAchievementList(L);
                case Constants.visualize:
                    return AchievementsClientWrapper.visualize(L);
                case Constants.visualizeWithResult:
                    return AchievementsClientWrapper.visualizeWithResult(L);
                case Constants.makeSteps:
                    return AchievementsClientWrapper.makeSteps(L);
                case Constants.makeStepsWithResult:
                    return AchievementsClientWrapper.makeStepsWithResult(L);
                case Constants.reach:
                    return AchievementsClientWrapper.reach(L);
                case Constants.reachWithResult:
                    return AchievementsClientWrapper.reachWithResult(L);
            }
            return 0;
        }
    }

    public static class rankingsClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.rankingsClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.getTotalRankingsIntent:
                    return RankingsClientWrapper.getTotalRankingsIntent(L);
                case Constants.getRankingIntent:
                    return RankingsClientWrapper.getRankingIntent(L);
                case Constants.getCurrentPlayerRankingScore:
                    return RankingsClientWrapper.getCurrentPlayerRankingScore(L);
                case Constants.getRankingSummary:
                    return RankingsClientWrapper.getRankingSummary(L);
                case Constants.getMoreRankingScores:
                    return RankingsClientWrapper.getMoreRankingScores(L);
                case Constants.getPlayerCenteredRankingScores:
                    return RankingsClientWrapper.getPlayerCenteredRankingScores(L);
                case Constants.getRankingTopScores:
                    return RankingsClientWrapper.getRankingTopScores(L);
                case Constants.submitScoreWithResult:
                    return RankingsClientWrapper.submitScoreWithResult(L);
                case Constants.getRankingSwitchStatus:
                    return RankingsClientWrapper.getRankingSwitchStatus(L);
                case Constants.setRankingSwitchStatus:
                    return RankingsClientWrapper.setRankingSwitchStatus(L);
            }
            return 0;
        }
    }

    public static class archivesClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.archivesClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
//                case Constants.addArchive:
//                    return ArchivesClientWrapper.addArchive(L);
//                case Constants.removeArchive:
//                    return ArchivesClientWrapper.removeArchive(L);
                case Constants.getLimitThumbnailSize:
                    return ArchivesClientWrapper.getLimitThumbnailSize(L);
                case Constants.getLimitDetailsSize:
                    return ArchivesClientWrapper.getLimitDetailsSize(L);
                case Constants.getShowArchiveListIntent:
                    return ArchivesClientWrapper.getShowArchiveListIntent(L);
//                case Constants.parseSummary:
//                    return ArchivesClientWrapper.parseSummary(L);
                case Constants.getArchiveSummaryList:
                    return ArchivesClientWrapper.getArchiveSummaryList(L);
                case Constants.loadArchiveDetails:
                    return ArchivesClientWrapper.loadArchiveDetails(L);
//                case Constants.updateArchive:
//                    return ArchivesClientWrapper.updateArchive(L);
                case Constants.getThumbnail:
                    return ArchivesClientWrapper.getThumbnail(L);
            }

            return 0;
        }
    }

    public static class eventsClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.eventsClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.grow:
                    return EventClientWrapper.grow(L);
                case Constants.getEventList:
                    return EventClientWrapper.getEventList(L);
//                case Constants.getEventListByIds:
//                    return EventClientWrapper.getEventListByIds(L);
            }

            return 0;
        }
    }

    public static class gamesClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.gamesClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.getAppId:
                    return GamesClientWrapper.getAppId(L);
                case Constants.setPopupsPosition:
                    return GamesClientWrapper.setPopupsPosition(L);
                case Constants.cancelGameService:
                    return GamesClientWrapper.cancelGameService(L);
            }

            return 0;
        }
    }

    public static class playersClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.playersClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.getCurrentPlayer:
                    return PlayersClientWrapper.getCurrentPlayer(L);
                case Constants.getGamePlayer:
                    return PlayersClientWrapper.getGamePlayer(L);
                case Constants.getCachePlayerId:
                    return PlayersClientWrapper.getCachePlayerId(L);
                case Constants.getPlayerExtraInfo:
                    return PlayersClientWrapper.getPlayerExtraInfo(L);
                case Constants.submitPlayerEvent:
                    return PlayersClientWrapper.submitPlayerEvent(L);
//                case Constants.savePlayerInfo:
////                    return PlayersClientWrapper.savePlayerInfo(L);
////                case Constants.setGameTrialProcess:
////                    return PlayersClientWrapper.setGameTrialProcess(L);
            }

            return 0;
        }
    }

    public static class gameSummaryClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.gameSummaryClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.getLocalGameSummary:
                    return GameSummaryClientWrapper.getLocalGameSummary(L);
                case Constants.getGameSummary:
                    return GameSummaryClientWrapper.getGameSummary(L);
            }

            return 0;
        }
    }

    public static class gamePlayerStatisticsClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.gamePlayerStatisticsClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            if (L.toString(1).equals(Constants.getGamePlayerStatistics)){
                return GamePlayerStatisticsClientWrapper.getGamePlayerStatistics(L);
            }

            return 0;
        }
    }

    public static class buoyClient implements NamedJavaFunction {
        @Override
        public String getName() {
            return Constants.buoyClient;
        }

        @Override
        public int invoke(LuaState L) {
            if (L.type(1) != LuaType.STRING) {
                return 0;
            }

            switch (L.toString(1)) {
                case Constants.showFloatWindow:
                    return BuoyClientWrapper.showFloatWindow(L);
                case Constants.hideFloatWindow:
                    return BuoyClientWrapper.hideFloatWindow(L);
            }

            return 0;
        }
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        CoronaLua.dispatchEvent(L, listener, 0);

                    } catch (Exception ex) {
                        Log.i(TAG, "Corona Error:", ex);

                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider, final JSONArray jsonArray) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        L.pushString(jsonArray.toString());
                        L.setField(-2, "data");

                        CoronaLua.dispatchEvent(L, listener, 0);
                    } catch (Exception ex) {
                        Log.i(TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider, final JSONObject jsonObject) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        L.pushString(jsonObject.toString());
                        L.setField(-2, "data");

                        CoronaLua.dispatchEvent(L, listener, 0);
                    } catch (Exception ex) {
                        Log.i(TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider, final String data) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        L.pushString(data);
                        L.setField(-2, "data");

                        CoronaLua.dispatchEvent(L, listener, 0);

                    } catch (Exception ex) {
                        Log.i(TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider, final int data) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        L.pushInteger(data);
                        L.setField(-2, "data");

                        CoronaLua.dispatchEvent(L, listener, 0);

                    } catch (Exception ex) {
                        Log.i(TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    public static void sendDispatcher(final int listener, final boolean isError, final String message, final String type, final String provider, final boolean data) {
        fDispatcher.send(new CoronaRuntimeTask() {
            @Override
            public void executeUsing(CoronaRuntime coronaRuntime) {
                if (listener != CoronaLua.REFNIL) {
                    LuaState L = coronaRuntime.getLuaState();
                    try {
                        CoronaLua.newEvent(L, Constants.eventName);

                        L.pushString(message);
                        L.setField(-2, "message");

                        L.pushBoolean(isError);
                        L.setField(-2, "isError");

                        L.pushString(type);
                        L.setField(-2, "type");

                        L.pushString(provider);
                        L.setField(-2, "provider");

                        L.pushBoolean(data);
                        L.setField(-2, "data");

                        CoronaLua.dispatchEvent(L, listener, 0);

                    } catch (Exception ex) {
                        Log.i(TAG, "Corona Error:", ex);
                    } finally {
                        CoronaLua.deleteRef(L, listener);
                    }
                }
            }
        });
    }

    @Override
    public void onLoaded(CoronaRuntime runtime) {
    }

    @Override
    public void onStarted(CoronaRuntime runtime) {
    }

    @Override
    public void onSuspended(CoronaRuntime runtime) {
    }

    @Override
    public void onResumed(CoronaRuntime runtime) {
    }

    @Override
    public void onExiting(CoronaRuntime runtime) {
        CoronaLua.deleteRef(runtime.getLuaState(), fListener);
        fListener = CoronaLua.REFNIL;
    }

}
