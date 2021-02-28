package plugin.huaweiGameService;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.games.ArchivesClient;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.archive.ArchiveSummary;
import com.huawei.hms.jos.games.archive.OperationResult;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import java.util.List;

import static plugin.huaweiGameService.LuaLoader.sendDispatcher;
import static plugin.huaweiGameService.Utils.archiveSummaryToJsonArray;
import static plugin.huaweiGameService.Utils.operationResultToJsonObject;

class ArchivesClientWrapper {

    @SuppressLint("StaticFieldLeak")
    private static ArchivesClient archivesClient;

    private static void initArchivesClient() {
        if (archivesClient == null) {
            archivesClient = Games.getArchiveClient(CoronaEnvironment.getCoronaActivity());
        }
    }

    static int addArchive(LuaState L) {
        return 0;
    }

    static int removeArchive(LuaState L) {
        return 0;
    }

    static int getLimitThumbnailSize(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initArchivesClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<Integer> task = archivesClient.getLimitThumbnailSize();
        task.addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                sendDispatcher(listener, false, "", Constants.getLimitThumbnailSize, Constants.archivesClient, data);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getLimitThumbnailSize, Constants.archivesClient);
                }
            }
        });
        return 0;
    }

    static int getLimitDetailsSize(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initArchivesClient();

        int listenerIndex = 2;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        Task<Integer> task = archivesClient.getLimitDetailsSize();
        task.addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                sendDispatcher(listener, false, "", Constants.getLimitDetailsSize, Constants.archivesClient, data);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getLimitDetailsSize, Constants.archivesClient);
                }
            }
        });
        return 0;
    }

    static int getShowArchiveListIntent(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initArchivesClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String title = null;
        boolean allowAddBtn, allowDeleteBtn;
        int maxArchive;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "title");
            if (L.isString(-1)) {
                title = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getShowArchiveListIntent {String, boolean, boolean, int} expected ", Constants.getShowArchiveListIntent, Constants.archivesClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "allowAddBtn");
            if (L.isBoolean(-1)) {
                allowAddBtn = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getShowArchiveListIntent {String, boolean, boolean, int} expected ", Constants.getShowArchiveListIntent, Constants.archivesClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "allowDeleteBtn");
            if (L.isBoolean(-1)) {
                allowDeleteBtn = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getShowArchiveListIntent {String, boolean, boolean, int} expected ", Constants.getShowArchiveListIntent, Constants.archivesClient);
                return 0;
            }

            L.pop(1);
            L.getField(2, "maxArchive");
            if (L.isNumber(-1)) {
                maxArchive = L.toInteger(-1);
            } else {
                sendDispatcher(listener, true, "getShowArchiveListIntent {String, boolean, boolean, int} expected ", Constants.getShowArchiveListIntent, Constants.archivesClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getShowArchiveListIntent {String, boolean, boolean, int} expected ", Constants.getShowArchiveListIntent, Constants.archivesClient);
            return 0;
        }

        final int requestCode = CoronaEnvironment.getCoronaActivity().registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
            @Override
            public void onHandleActivityResult(CoronaActivity activity, int requestCode, int resultCode, Intent data) {
                activity.unregisterActivityResultHandler(this);
                sendDispatcher(listener, false, resultCode + "", Constants.getShowArchiveListIntent, Constants.archivesClient);
            }
        });

        Task<Intent> task = archivesClient.getShowArchiveListIntent(title, allowAddBtn, allowDeleteBtn, maxArchive);
        task.addOnSuccessListener(new OnSuccessListener<Intent>() {
            @Override
            public void onSuccess(Intent intent) {
                if (intent == null) {
                    sendDispatcher(listener, true, "intent = null", Constants.getShowArchiveListIntent, Constants.archivesClient);
                } else {
                    try {
                        CoronaEnvironment.getCoronaActivity().startActivityForResult(intent, requestCode);
                    } catch (Exception e) {
                        sendDispatcher(listener, true, "Achievement Activity is Invalid", Constants.getShowArchiveListIntent, Constants.archivesClient);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    sendDispatcher(listener, true, "Return Code " + ((ApiException) e).getStatusCode(), Constants.getShowArchiveListIntent, Constants.archivesClient);
                }
            }
        });
        return 0;
    }

    static int parseSummary(LuaState L) {
        return 0;
    }

    static int getArchiveSummaryList(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initArchivesClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        boolean isRealTime;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "isRealTime");
            if (L.isBoolean(-1)) {
                isRealTime = L.toBoolean(-1);
            } else {
                sendDispatcher(listener, true, "getArchiveSummaryList {boolean} expected ", Constants.getArchiveSummaryList, Constants.archivesClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getArchiveSummaryList {boolean} expected ", Constants.getArchiveSummaryList, Constants.archivesClient);
            return 0;
        }

        Task<List<ArchiveSummary>> task = archivesClient.getArchiveSummaryList(isRealTime);
        task.addOnSuccessListener(new OnSuccessListener<List<ArchiveSummary>>() {
            @Override
            public void onSuccess(List<ArchiveSummary> data) {
                sendDispatcher(listener, false, "", Constants.getArchiveSummaryList, Constants.archivesClient, archiveSummaryToJsonArray(data));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getArchiveSummaryList, Constants.archivesClient);
                }
            }
        });

        return 0;
    }

    static int loadArchiveDetails(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initArchivesClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String archiveId;
        int diffStrategy = -1;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "archiveId");
            if (L.isString(-1)) {
                archiveId = L.toString(-1);
                L.pop(1);
            } else {
                sendDispatcher(listener, true, "loadArchiveDetails {String, int} expected ", Constants.loadArchiveDetails, Constants.archivesClient);
                return 0;
            }

            L.getField(2, "diffStrategy");
            if (L.isNumber(-1)) {
                diffStrategy = L.toInteger(-1);
            }
        } else {
            sendDispatcher(listener, true, "loadArchiveDetails {String, int} expected ", Constants.loadArchiveDetails, Constants.archivesClient);
            return 0;
        }

        Task<OperationResult> task;
        if (diffStrategy != -1) {
            task = archivesClient.loadArchiveDetails(archiveId, diffStrategy);

        } else {
            task = archivesClient.loadArchiveDetails(archiveId);
        }

        task.addOnSuccessListener(new OnSuccessListener<OperationResult>() {
            @Override
            public void onSuccess(OperationResult data) {
                sendDispatcher(listener, false, "", Constants.loadArchiveDetails, Constants.archivesClient, operationResultToJsonObject(data));
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.loadArchiveDetails, Constants.archivesClient);
                }
            }
        });
        return 0;
    }

    static int updateArchive(LuaState L) {
        return 0;
    }

    static int getThumbnail(LuaState L) {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            return 0;
        }

        initArchivesClient();

        int listenerIndex = 3;
        final int listener = CoronaLua.isListener(L, listenerIndex, Constants.eventName) ? CoronaLua.newRef(L, listenerIndex) : CoronaLua.REFNIL;

        String archiveId;

        if (L.type(2) == LuaType.TABLE || L.tableSize(2) == 0) {
            L.getField(2, "archiveId");
            if (L.isString(-1)) {
                archiveId = L.toString(-1);
            } else {
                sendDispatcher(listener, true, "getThumbnail {String} expected ", Constants.getThumbnail, Constants.archivesClient);
                return 0;
            }
        } else {
            sendDispatcher(listener, true, "getThumbnail {String} expected ", Constants.getThumbnail, Constants.archivesClient);
            return 0;
        }

        Task<Bitmap> task = archivesClient.getThumbnail(archiveId);
        task.addOnSuccessListener(new OnSuccessListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data) {
                sendDispatcher(listener, false, "", Constants.getThumbnail, Constants.archivesClient, data.toString());
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    sendDispatcher(listener, true, result, Constants.getThumbnail, Constants.archivesClient);
                }
            }
        });
        return 0;
    }
}
