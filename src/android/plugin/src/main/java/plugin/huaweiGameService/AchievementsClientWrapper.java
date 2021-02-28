package plugin.huaweiGameService;

import android.content.Intent;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.AchievementsClient;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.achievement.Achievement;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import java.util.List;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.achivementsToJsonArray;


class AchievementsClientWrapper {

    private static AchievementsClient achievementsClient;

    private static void initAchievementsClient() {
        if (achievementsClient == null) {
            achievementsClient = Games.getAchievementsClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int getShowAchievementListIntent(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;


        final int requestCode = activity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
            @Override
            public void onHandleActivityResult(CoronaActivity activity, int requestCode, int resultCode, Intent data) {
                activity.unregisterActivityResultHandler(this);
                sendDispatcher(listener, false, "", Constants.getShowAchievementListIntent, Constants.achievementsClient);
            }
        });

        Task<Intent> task = achievementsClient.getShowAchievementListIntent();
        task.addOnSuccessListener(new OnSuccessListener<Intent>() {
            @Override
            public void onSuccess(Intent intent) {
                if (intent == null) {
                    sendDispatcher(listener, true, "intent is null", Constants.getShowAchievementListIntent, Constants.achievementsClient);
                } else {
                    try {
                        activity.startActivityForResult(intent, requestCode);
                    } catch (Exception e) {
                        sendDispatcher(listener, true, "Achievement Activity is Invalid", Constants.getShowAchievementListIntent, Constants.achievementsClient);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    int rtnCode = ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, "Return Code " + rtnCode, Constants.getShowAchievementListIntent, Constants.achievementsClient);
                }
            }
        });
        return 0;
    }

    static int grow(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;
        int numSteps;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "grow {String, Integer} expected ", Constants.grow, Constants.achievementsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "numSteps");
            if (L.isString(-1)) {
                numSteps = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "grow {String, Integer} expected ", Constants.grow, Constants.achievementsClient);
                return 0;
            }

        } else {
            sendDispatcher(listener, true, "grow {String, Integer} expected ", Constants.grow, Constants.achievementsClient);
            return 0;
        }

        try {
            achievementsClient.grow(id, numSteps);
            sendDispatcher(listener, false, "", Constants.grow, Constants.achievementsClient);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage(), Constants.grow, Constants.achievementsClient);
        }

        return 0;
    }

    static int growWithResult(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;
        int numSteps;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "growWithResult {String, Integer} expected ", Constants.growWithResult, Constants.achievementsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "numSteps");

            if (L.isString(-1)) {
                numSteps = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "growWithResult {String, Integer} expected ", Constants.growWithResult, Constants.achievementsClient);
                return 0;
            }

        } else {
            sendDispatcher(listener, true, "growWithResult {String, Integer} expected ", Constants.growWithResult, Constants.achievementsClient);
            return 0;
        }

        Task<Boolean> task = achievementsClient.growWithResult(id, numSteps);
        task.addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean isSuccess) {
                sendDispatcher(listener, false, "", Constants.growWithResult, Constants.achievementsClient, isSuccess);
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

    static int getAchievementList(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        boolean forceReload;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "forceReload");
            if (L.isBoolean(-1)) {
                forceReload = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getAchievementList {boolean} expected ", Constants.getAchievementList, Constants.achievementsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getAchievementList {boolean} expected ", Constants.getAchievementList, Constants.achievementsClient);
            return 0;
        }

        Task<List<Achievement>> task = achievementsClient.getAchievementList(forceReload);
        task.addOnSuccessListener(new OnSuccessListener<List<Achievement>>() {
            @Override
            public void onSuccess(List<Achievement> data) {
                if (data == null) {
                    sendDispatcher(listener, true, "Achievement list is null", Constants.getAchievementList, Constants.achievementsClient);
                    return;
                }

                sendDispatcher(listener, false, "", Constants.getAchievementList, Constants.achievementsClient, achivementsToJsonArray(data));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getAchievementList, Constants.achievementsClient);
                }
            }
        });

        return 0;
    }

    static int visualize(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "visualize {String} expected ", Constants.visualize, Constants.achievementsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "visualize {String} expected ", Constants.visualize, Constants.achievementsClient);
            return 0;
        }

        try {
            achievementsClient.visualize(id);
            sendDispatcher(listener, false, "", Constants.visualize, Constants.achievementsClient);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage(), Constants.visualize, Constants.achievementsClient);
        }

        return 0;
    }

    static int visualizeWithResult(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "visualizeWithResult {String} expected ", Constants.visualizeWithResult, Constants.achievementsClient);
                return 0;
            }

        } else {
            sendDispatcher(listener, true, "visualizeWithResult {String} expected ", Constants.visualizeWithResult, Constants.achievementsClient);
            return 0;
        }

        Task<Void> task = achievementsClient.visualizeWithResult(id);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                sendDispatcher(listener, false, "", Constants.visualizeWithResult, Constants.achievementsClient);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    int rtnCode = ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, rtnCode + "", Constants.visualizeWithResult, Constants.achievementsClient);
                }
            }
        });

        return 0;
    }

    static int makeSteps(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;
        int numSteps;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
                L.pop(1);
            } else {
                sendDispatcher(listener, true, "makeSteps {String, Intenger} expected ", Constants.makeSteps, Constants.achievementsClient);
                return 0;
            }
            L.getField(2, "numSteps");
            if (L.isNumber(-1)) {
                numSteps = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "makeSteps {String, Intenger} expected ", Constants.makeSteps, Constants.achievementsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "makeSteps {String, Intenger} expected ", Constants.makeSteps, Constants.achievementsClient);
            return 0;
        }

        try {
            achievementsClient.makeSteps(id, numSteps);
            sendDispatcher(listener, false, "", Constants.makeSteps, Constants.achievementsClient);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage() + " " + ((ApiException) e).getStatusCode(), Constants.makeSteps, Constants.achievementsClient);
        }

        return 0;
    }

    static int makeStepsWithResult(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;
        int numSteps;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "makeStepsWithResult {String, Intenger} expected ", Constants.makeStepsWithResult, Constants.achievementsClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "numSteps");

            if (L.isString(-1)) {
                numSteps = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "makeStepsWithResult {String, Intenger} expected ", Constants.makeStepsWithResult, Constants.achievementsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "makeStepsWithResult {String, Intenger} expected ", Constants.makeStepsWithResult, Constants.achievementsClient);
            return 0;
        }

        Task<Boolean> task = achievementsClient.makeStepsWithResult(id, numSteps);
        task.addOnSuccessListener(new OnSuccessListener<Boolean>() {
            @Override
            public void onSuccess(Boolean isSucess) {
                sendDispatcher(listener, false, "", Constants.makeStepsWithResult, Constants.achievementsClient, isSucess);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                int rtnCode = ((ApiException) e).getStatusCode();
                sendDispatcher(listener, true, rtnCode + "", Constants.makeStepsWithResult, Constants.achievementsClient);
            }
        });


        return 0;
    }

    static int reach(LuaState L) {

        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "reach {String} expected ", Constants.reach, Constants.achievementsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "reach {String} expected ", Constants.reach, Constants.achievementsClient);
            return 0;
        }

        try {
            achievementsClient.reach(id);
            sendDispatcher(listener, false, "", Constants.reach, Constants.achievementsClient);
        } catch (Exception e) {
            sendDispatcher(listener, true, e.getMessage(), Constants.reach, Constants.achievementsClient);
        }

        return 0;
    }

    static int reachWithResult(LuaState L) {
        final CoronaActivity activity = CoronaEnvironment.getCoronaActivity();
        if (activity == null) {
            return 0;
        }

        initAchievementsClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String id = null;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "id");
            if (L.isString(-1)) {
                id = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "reachWithResult {String} expected ", Constants.reachWithResult, Constants.achievementsClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "reachWithResult {String} expected ", Constants.reachWithResult, Constants.achievementsClient);
            return 0;
        }

        Task<Void> task = achievementsClient.reachWithResult(id);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void v) {
                sendDispatcher(listener, false, "", Constants.reachWithResult, Constants.achievementsClient);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    int rtnCode = ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, rtnCode + "", Constants.reachWithResult, Constants.achievementsClient);
                }
            }
        });

        return 0;
    }

}
