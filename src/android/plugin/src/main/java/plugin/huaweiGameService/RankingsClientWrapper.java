package plugin.huaweiGameService;

import android.content.Intent;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.RankingsClient;
import com.huawei.hms.jos.games.ranking.Ranking;
import com.huawei.hms.jos.games.ranking.RankingScore;
import com.huawei.hms.jos.games.ranking.ScoreSubmissionInfo;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import java.util.List;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.rankingScoreToJsonObject;
import static plugin.huaweiGameService.Utils.rankingToJsonObject;
import static plugin.huaweiGameService.Utils.rankingsClientRankingScoresToJsonObject;
import static plugin.huaweiGameService.Utils.rankingsToJsonArray;
import static plugin.huaweiGameService.Utils.scoreSubmissionInfoToJsonObject;

class RankingsClientWrapper {
    private static RankingsClient rankingsClient;

    private static void initRankingsClient() {
        if (rankingsClient == null) {
            rankingsClient = Games.getRankingsClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getTotalRankingsIntent(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        final int requestCode = activity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
            @Override
            public void onHandleActivityResult(CoronaActivity activity, int requestCode, int resultCode, Intent data) {
                activity.unregisterActivityResultHandler(this);
                sendDispatcher(listener, false, "", Constants.getTotalRankingsIntent, Constants.rankingsClient);
            }
        });

        Task<Intent> intentTask = rankingsClient.getTotalRankingsIntent();
        intentTask.addOnSuccessListener(new OnSuccessListener<Intent>() {
            @Override
            public void onSuccess(Intent intent) {
                try {
                    activity.startActivityForResult(intent, requestCode);
                } catch (Exception e) {
                    sendDispatcher(listener, true, "Ranking Activity is Invalid", Constants.getTotalRankingsIntent, Constants.rankingsClient);
                }
            }
        });
        intentTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getTotalRankingsIntent, Constants.rankingsClient);
                }
            }
        });

        return 0;
    }

    static int getRankingIntent(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String rankingId = null;
        int timeDimension = 0;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "rankingId");
            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getRankingIntent {String} expected ", Constants.getRankingIntent, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "numSteps");

            if (L.isNumber(-1)) {
                timeDimension = L.toInteger(-1);
            }
        } else {
            sendDispatcher(listener, true, "getRankingIntent {String} expected ", Constants.getRankingIntent, Constants.rankingsClient);
            return 0;
        }

        final int requestCode = activity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
            @Override
            public void onHandleActivityResult(CoronaActivity activity, int requestCode, int resultCode, Intent data) {
                activity.unregisterActivityResultHandler(this);
                sendDispatcher(listener, false, "", Constants.getRankingIntent, Constants.rankingsClient);
            }
        });

        Task<Intent> intentTask;
        if (timeDimension != 0) {
            intentTask = rankingsClient.getRankingIntent(rankingId, timeDimension);

        } else {
            intentTask = rankingsClient.getRankingIntent(rankingId);
        }
        intentTask.addOnSuccessListener(new OnSuccessListener<Intent>() {
            @Override
            public void onSuccess(Intent intent) {
                try {
                    activity.startActivityForResult(intent, requestCode);
                } catch (Exception e) {
                    sendDispatcher(listener, true, "Ranking Activity is Invalid", Constants.getRankingIntent, Constants.rankingsClient);
                }
            }
        });
        intentTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getRankingIntent, Constants.rankingsClient);
                }
            }
        });

        return 0;
    }

    static int getCurrentPlayerRankingScore(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;


        String rankingId = null;
        int timeDimension = 0;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "rankingId");
            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getCurrentPlayerRankingScore {String, int} expected ", Constants.getCurrentPlayerRankingScore, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "timeDimension");

            if (L.isNumber(-1)) {
                timeDimension = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getCurrentPlayerRankingScore {String, int} expected ", Constants.getCurrentPlayerRankingScore, Constants.rankingsClient);
                return 0;
            }

        } else {
            sendDispatcher(listener, true, "getCurrentPlayerRankingScore {String, int} expected ", Constants.getCurrentPlayerRankingScore, Constants.rankingsClient);
            return 0;
        }


        Task<RankingScore> task = rankingsClient.getCurrentPlayerRankingScore(rankingId, timeDimension);
        task.addOnSuccessListener(new OnSuccessListener<RankingScore>() {
            @Override
            public void onSuccess(RankingScore rankingScore) {
                sendDispatcher(listener, false, "", Constants.getCurrentPlayerRankingScore, Constants.rankingsClient, rankingScoreToJsonObject(rankingScore));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getCurrentPlayerRankingScore, Constants.rankingsClient);
                }
            }
        });

        return 0;
    }

    static int getRankingSummary(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String rankingId = null;
        Boolean isRealTime = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "isRealTime");
            if (L.isBoolean(-1)) {
                isRealTime = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getRankingSummary {Boolean} expected ", Constants.getRankingSummary, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "rankingId");

            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            }

        } else {
            sendDispatcher(listener, true, "getRankingSummary {Boolean} expected ", Constants.getRankingSummary, Constants.rankingsClient);
            return 0;
        }


        if (rankingId == null) {
            Task<List<Ranking>> intentTask = rankingsClient.getRankingSummary(isRealTime);
            intentTask.addOnSuccessListener(new OnSuccessListener<List<Ranking>>() {
                @Override
                public void onSuccess(List<Ranking> data) {
                    sendDispatcher(listener, false, "", Constants.getRankingSummary, Constants.rankingsClient, rankingsToJsonArray(data));
                }
            });
            intentTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ApiException) {
                        sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getRankingSummary, Constants.rankingsClient);
                    }
                }
            });
        } else {
            Task<Ranking> intentTask = rankingsClient.getRankingSummary(rankingId, isRealTime);
            intentTask.addOnSuccessListener(new OnSuccessListener<Ranking>() {
                @Override
                public void onSuccess(Ranking data) {
                    sendDispatcher(listener, false, "", Constants.getRankingSummary, Constants.rankingsClient, rankingToJsonObject(data));
                }
            });
            intentTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ApiException) {
                        sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getRankingSummary, Constants.rankingsClient);
                    }
                }
            });
        }


        return 0;
    }

    static int getMoreRankingScores(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String rankingId = null;
        long offsetPlayerRank;
        int maxResults;
        int pageDirection;
        int timeDimension;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "rankingId");
            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getMoreRankingScores {String, long, int, int, int} expected ", Constants.getMoreRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "offsetPlayerRank");
            if (L.isNumber(-1)) {
                offsetPlayerRank = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getMoreRankingScores {String, long, int, int, int} expected ", Constants.getMoreRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "maxResults");
            if (L.isNumber(-1)) {
                maxResults = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getMoreRankingScores {String, long, int, int, int} expected ", Constants.getMoreRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "pageDirection");
            if (L.isNumber(-1)) {
                pageDirection = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getMoreRankingScores {String, long, int, int, int} expected ", Constants.getMoreRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "timeDimension");
            if (L.isNumber(-1)) {
                timeDimension = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getMoreRankingScores {String, long, int, int, int} expected ", Constants.getMoreRankingScores, Constants.rankingsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getMoreRankingScores {String, long, int, int, int} expected ", Constants.getMoreRankingScores, Constants.rankingsClient);
            return 0;
        }

        Task<RankingsClient.RankingScores> task = rankingsClient.getMoreRankingScores(rankingId, offsetPlayerRank, maxResults, pageDirection, timeDimension);
        task.addOnSuccessListener(new OnSuccessListener<RankingsClient.RankingScores>() {
            @Override
            public void onSuccess(RankingsClient.RankingScores data) {
                sendDispatcher(listener, false, "", Constants.getMoreRankingScores, Constants.rankingsClient, rankingsClientRankingScoresToJsonObject(data));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getMoreRankingScores, Constants.rankingsClient);
                }
            }
        });
        return 0;
    }

    static int getPlayerCenteredRankingScores(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String rankingId = null;
        int timeDimension;
        int maxResults;
        long offsetPlayerRank = 0;
        int pageDirection = 0;
        boolean isRealTime = false;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "rankingId");
            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getPlayerCenteredRankingScores {String, int, int} expected ", Constants.getPlayerCenteredRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "maxResults");
            if (L.isNumber(-1)) {
                maxResults = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getPlayerCenteredRankingScores {String, int, int} expected ", Constants.getPlayerCenteredRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "timeDimension");
            if (L.isNumber(-1)) {
                timeDimension = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getPlayerCenteredRankingScores {String, int, int} expected ", Constants.getPlayerCenteredRankingScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "offsetPlayerRank");
            if (L.isNumber(-1)) {
                offsetPlayerRank = L.toInteger(-1);
                L.pop(1);
            }

            L.getField(2, "pageDirection");
            if (L.isNumber(-1)) {
                pageDirection = L.toInteger(-1);
                L.pop(1);
            }

            L.getField(2, "isRealTime");
            if (L.isBoolean(-1)) {
                isRealTime = L.toBoolean(-1);
            }

        } else {
            sendDispatcher(listener, true, "getPlayerCenteredRankingScores {String, int, int} expected ", Constants.getPlayerCenteredRankingScores, Constants.rankingsClient);
            return 0;
        }

        if (offsetPlayerRank != 0 && pageDirection != 0) {
            Task<RankingsClient.RankingScores> task = rankingsClient.getPlayerCenteredRankingScores(rankingId, timeDimension, maxResults, offsetPlayerRank, pageDirection);
            task.addOnSuccessListener(new OnSuccessListener<RankingsClient.RankingScores>() {
                @Override
                public void onSuccess(RankingsClient.RankingScores data) {
                    sendDispatcher(listener, false, "", Constants.getPlayerCenteredRankingScores, Constants.rankingsClient, rankingsClientRankingScoresToJsonObject(data));
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ApiException) {
                        sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getPlayerCenteredRankingScores, Constants.rankingsClient);
                    }
                }
            });
        } else {
            Task<RankingsClient.RankingScores> task = rankingsClient.getPlayerCenteredRankingScores(rankingId, timeDimension, maxResults, isRealTime);
            task.addOnSuccessListener(new OnSuccessListener<RankingsClient.RankingScores>() {
                @Override
                public void onSuccess(RankingsClient.RankingScores data) {
                    sendDispatcher(listener, false, "", Constants.getPlayerCenteredRankingScores, Constants.rankingsClient, rankingsClientRankingScoresToJsonObject(data));
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ApiException) {
                        sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getPlayerCenteredRankingScores, Constants.rankingsClient);
                    }
                }
            });
        }

        return 0;
    }

    static int getRankingTopScores(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String rankingId = null;
        int timeDimension;
        int maxResults;
        long offsetPlayerRank = 0;
        int pageDirection = 0;
        boolean isRealTime = false;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "rankingId");
            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getRankingTopScores {String, int, int} expected ", Constants.getRankingTopScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "maxResults");
            if (L.isNumber(-1)) {
                maxResults = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getRankingTopScores {String, int, int} expected ", Constants.getRankingTopScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "timeDimension");
            if (L.isNumber(-1)) {
                timeDimension = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getRankingTopScores {String, int, int} expected ", Constants.getRankingTopScores, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "offsetPlayerRank");
            if (L.isNumber(-1)) {
                offsetPlayerRank = L.toInteger(-1);
                L.pop(1);
            }

            L.getField(2, "pageDirection");
            if (L.isNumber(-1)) {
                pageDirection = L.toInteger(-1);
                L.pop(1);
            }

            L.getField(2, "isRealTime");
            if (L.isBoolean(-1)) {
                isRealTime = L.toBoolean(-1);
            }

        } else {
            sendDispatcher(listener, true, "getRankingTopScores {String, int, int} expected ", Constants.getRankingTopScores, Constants.rankingsClient);
            return 0;
        }

        if (offsetPlayerRank != 0 && pageDirection != 0) {
            Task<RankingsClient.RankingScores> task = rankingsClient.getRankingTopScores(rankingId, timeDimension, maxResults, offsetPlayerRank, pageDirection);
            task.addOnSuccessListener(new OnSuccessListener<RankingsClient.RankingScores>() {
                @Override
                public void onSuccess(RankingsClient.RankingScores data) {
                    sendDispatcher(listener, false, "", Constants.getRankingTopScores, Constants.rankingsClient, rankingsClientRankingScoresToJsonObject(data));
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ApiException) {
                        sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getRankingTopScores, Constants.rankingsClient);
                    }
                }
            });
        } else {
            Task<RankingsClient.RankingScores> task = rankingsClient.getRankingTopScores(rankingId, timeDimension, maxResults, isRealTime);
            task.addOnSuccessListener(new OnSuccessListener<RankingsClient.RankingScores>() {
                @Override
                public void onSuccess(RankingsClient.RankingScores data) {
                    sendDispatcher(listener, false, "", Constants.getRankingTopScores, Constants.rankingsClient, rankingsClientRankingScoresToJsonObject(data));
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ApiException) {
                        sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getRankingTopScores, Constants.rankingsClient);
                    }
                }
            });
        }
        return 0;
    }

    static int submitScoreWithResult(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String rankingId = null;
        long score;
        String scoreTips = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "rankingId");
            if (L.isString(-1)) {
                rankingId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "submitScoreWithResult {String, long} expected ", Constants.submitScoreWithResult, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "score");
            if (L.isNumber(-1)) {
                score = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "submitScoreWithResult {String, long} expected ", Constants.submitScoreWithResult, Constants.rankingsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "scoreTips");
            if (L.isString(-1)) {
                scoreTips = L.toString(-1);
            }
        } else {
            sendDispatcher(listener, true, "submitScoreWithResult {String, long} expected ", Constants.submitScoreWithResult, Constants.rankingsClient);
            return 0;
        }
        Task<ScoreSubmissionInfo> task;
        if (scoreTips != null) {
            task = rankingsClient.submitScoreWithResult(rankingId, score, scoreTips);
        } else {
            task = rankingsClient.submitScoreWithResult(rankingId, score);
        }
        task.addOnSuccessListener(new OnSuccessListener<ScoreSubmissionInfo>() {
            @Override
            public void onSuccess(ScoreSubmissionInfo data) {
                sendDispatcher(listener, false, "", Constants.submitScoreWithResult, Constants.rankingsClient, scoreSubmissionInfoToJsonObject(data));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:"
                            + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, ((ApiException) e).getStatusCode() + "", Constants.submitScoreWithResult, Constants.rankingsClient);
                }
            }
        });
        return 0;
    }

    static int getRankingSwitchStatus(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<Integer> task = rankingsClient.getRankingSwitchStatus();
        task.addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                sendDispatcher(listener, false, "", Constants.getRankingSwitchStatus, Constants.rankingsClient, data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:"
                            + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getRankingSwitchStatus, Constants.rankingsClient);
                }
            }
        });
        return 0;
    }

    static int setRankingSwitchStatus(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initRankingsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        int status;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "status");
            if (L.isNumber(-1)) {
                status = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "setRankingSwitchStatus {int} expected ", Constants.setRankingSwitchStatus, Constants.rankingsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "setRankingSwitchStatus {int} expected ", Constants.setRankingSwitchStatus, Constants.rankingsClient);
            return 0;
        }

        Task<Integer> task = rankingsClient.setRankingSwitchStatus(status);
        task.addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                sendDispatcher(listener, false, "", Constants.setRankingSwitchStatus, Constants.rankingsClient, data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:"
                            + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.setRankingSwitchStatus, Constants.rankingsClient);
                }
            }
        });
        return 0;
    }

}
