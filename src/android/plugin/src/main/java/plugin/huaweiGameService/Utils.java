package plugin.huaweiGameService;

import android.util.Log;

import com.huawei.hms.jos.games.RankingsClient;
import com.huawei.hms.jos.games.achievement.Achievement;
import com.huawei.hms.jos.games.archive.Archive;
import com.huawei.hms.jos.games.archive.ArchiveSummary;
import com.huawei.hms.jos.games.archive.OperationResult;
import com.huawei.hms.jos.games.event.Event;
import com.huawei.hms.jos.games.gamesummary.GameSummary;
import com.huawei.hms.jos.games.player.Player;
import com.huawei.hms.jos.games.player.PlayerExtraInfo;
import com.huawei.hms.jos.games.playerstats.GamePlayerStatistics;
import com.huawei.hms.jos.games.ranking.Ranking;
import com.huawei.hms.jos.games.ranking.RankingScore;
import com.huawei.hms.jos.games.ranking.RankingVariant;
import com.huawei.hms.jos.games.ranking.ScoreSubmissionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Utils {

    static JSONArray achivementsToJsonArray(List<Achievement> data) {
        JSONArray achivementArray = new JSONArray();
        for (Achievement achievement : data) {
            achivementArray.put(achivementToJsonObject(achievement));
        }
        return achivementArray;
    }

    private static JSONObject achivementToJsonObject(Achievement achievement) {
        JSONObject achivementObject = new JSONObject();
        try {
            achivementObject.put("Id", achievement.getId());
            achivementObject.put("ReachedSteps", achievement.getReachedSteps());
            achivementObject.put("DescInfo", achievement.getDescInfo());
            achivementObject.put("LocaleReachedSteps", achievement.getLocaleReachedSteps());
            achivementObject.put("LocaleAllSteps", achievement.getLocaleAllSteps());
            achivementObject.put("RecentUpdateTime", achievement.getRecentUpdateTime());
            achivementObject.put("DisplayName", achievement.getDisplayName());
            achivementObject.put("GamePlayer", playerToJsonObject(achievement.getGamePlayer()));
            achivementObject.put("VisualizedThumbnailUri", achievement.getVisualizedThumbnailUri());
            achivementObject.put("State", achievement.getState());
            achivementObject.put("AllSteps", achievement.getAllSteps());
            achivementObject.put("Type", achievement.getType());
            achivementObject.put("ReachedThumbnailUri", achievement.getReachedThumbnailUri());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return achivementObject;
    }

    static JSONObject playerToJsonObject(Player player) {
        JSONObject playerObject = new JSONObject();
        try {
            playerObject.put("DisplayName", player.getDisplayName());
            playerObject.put("HiResImageUri", player.getHiResImageUri());
            playerObject.put("IconImageUri", player.getIconImageUri());
            playerObject.put("Level", player.getLevel());
            playerObject.put("PlayerId", player.getPlayerId());
            playerObject.put("HiResImage", player.hasHiResImage());
            playerObject.put("IconImage", player.hasIconImage());
            playerObject.put("PlayerSign", player.getPlayerSign());
            playerObject.put("SignTs", player.getSignTs());
            playerObject.put("OpenId", player.getOpenId());
            playerObject.put("UnionId", player.getUnionId());
            playerObject.put("AccessToken", player.getAccessToken());
            playerObject.put("OpenIdSign", player.getOpenIdSign());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return playerObject;
    }

    static JSONObject playerExtraInfoToJsonObject(PlayerExtraInfo playerExtraInfo) {
        JSONObject playerExtraInfoObject = new JSONObject();
        try {
            playerExtraInfoObject.put("IsAdult", playerExtraInfo.getIsAdult());
            playerExtraInfoObject.put("PlayerId", playerExtraInfo.getPlayerId());
            playerExtraInfoObject.put("OpenId", playerExtraInfo.getOpenId());
            playerExtraInfoObject.put("PlayerDuration", playerExtraInfo.getPlayerDuration());
            playerExtraInfoObject.put("IsRealName", playerExtraInfo.getIsRealName());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return playerExtraInfoObject;
    }

    static JSONArray rankingsToJsonArray(List<Ranking> data) {
        JSONArray achivementArray = new JSONArray();
        for (Ranking ranking : data) {
            achivementArray.put(rankingToJsonObject(ranking));
        }
        return achivementArray;
    }

    private static JSONArray rankingScoreToJsonArray(List<RankingScore> data) {
        JSONArray rankingScoreArray = new JSONArray();
        for (RankingScore rankingScore : data) {
            rankingScoreArray.put(rankingScoreToJsonObject(rankingScore));
        }
        return rankingScoreArray;
    }

    static JSONObject rankingToJsonObject(Ranking ranking) {
        JSONObject rankingObject = new JSONObject();
        try {
            rankingObject.put("RankingDisplayName", ranking.getRankingDisplayName());
            rankingObject.put("RankingImageUri", ranking.getRankingImageUri());
            rankingObject.put("RankingId", ranking.getRankingId());
            rankingObject.put("RankingScoreOrder", ranking.getRankingScoreOrder());
            rankingObject.put("RankingVariants", rankingVariantsToJsonArray(ranking.getRankingVariants()));
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return rankingObject;
    }

    private static JSONArray rankingVariantsToJsonArray(ArrayList<RankingVariant> data) {
        JSONArray achivementArray = new JSONArray();
        for (RankingVariant rankingVariant : data) {
            achivementArray.put(rankingVariantToJsonObject(rankingVariant));
        }
        return achivementArray;
    }

    private static JSONObject rankingVariantToJsonObject(RankingVariant rankingVariant) {
        JSONObject rankingVariantsObject = new JSONObject();
        try {
            rankingVariantsObject.put("DisplayRanking", rankingVariant.getDisplayRanking());
            rankingVariantsObject.put("PlayerDisplayScore", rankingVariant.getPlayerDisplayScore());
            rankingVariantsObject.put("RankTotalScoreNum", rankingVariant.getRankTotalScoreNum());
            rankingVariantsObject.put("PlayerRank", rankingVariant.getPlayerRank());
            rankingVariantsObject.put("PlayerScoreTips", rankingVariant.getPlayerScoreTips());
            rankingVariantsObject.put("PlayerRawScore", rankingVariant.getPlayerRawScore());
            rankingVariantsObject.put("TimeDimension", rankingVariant.timeDimension());
            rankingVariantsObject.put("PlayerInfo", rankingVariant.hasPlayerInfo());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return rankingVariantsObject;
    }

    static JSONObject rankingsClientRankingScoresToJsonObject(RankingsClient.RankingScores rankingScores) {
        JSONObject rankingsClientRankingScoresObject = new JSONObject();
        try {
            rankingsClientRankingScoresObject.put("Ranking", rankingToJsonObject(rankingScores.getRanking()));
            rankingsClientRankingScoresObject.put("RankingScores", rankingScoreToJsonArray(rankingScores.getRankingScores()));
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return rankingsClientRankingScoresObject;
    }

    static JSONObject rankingScoreToJsonObject(RankingScore rankingScore) {
        JSONObject rankingScoreObject = new JSONObject();
        try {
            rankingScoreObject.put("DisplayRank", rankingScore.getDisplayRank());
            rankingScoreObject.put("RankingDisplayScore", rankingScore.getRankingDisplayScore());
            rankingScoreObject.put("PlayerRank", rankingScore.getPlayerRank());
            rankingScoreObject.put("PlayerRawScore", rankingScore.getPlayerRawScore());
            rankingScoreObject.put("ScoreOwnerPlayer", rankingScore.getScoreOwnerPlayer());
            rankingScoreObject.put("ScoreOwnerDisplayName", rankingScore.getScoreOwnerDisplayName());
            rankingScoreObject.put("ScoreOwnerHiIconUri", rankingScore.getScoreOwnerHiIconUri());
            rankingScoreObject.put("ScoreOwnerIconUri", rankingScore.getScoreOwnerIconUri());
            rankingScoreObject.put("ScoreTips", rankingScore.getScoreTips());
            rankingScoreObject.put("ScoreTimestamp", rankingScore.getScoreTimestamp());
            rankingScoreObject.put("TimeDimension", rankingScore.getTimeDimension());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return rankingScoreObject;
    }

    static JSONObject scoreSubmissionInfoToJsonObject(ScoreSubmissionInfo scoreSubmissionInfo) {
        JSONObject scoreSubmissionInfoObject = new JSONObject();
        try {
            scoreSubmissionInfoObject.put("RankingId", scoreSubmissionInfo.getRankingId());
            scoreSubmissionInfoObject.put("PlayerId", scoreSubmissionInfo.getPlayerId());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return scoreSubmissionInfoObject;
    }

    static JSONObject gamePlayerStatisticsToJsonObject(GamePlayerStatistics gamePlayerStatistics) {
        JSONObject gamePlayerStatisticsObject = new JSONObject();
        try {
            gamePlayerStatisticsObject.put("AverageOnLineMinutes", gamePlayerStatistics.getAverageOnLineMinutes());
            gamePlayerStatisticsObject.put("DaysFromLastGame", gamePlayerStatistics.getDaysFromLastGame());
            gamePlayerStatisticsObject.put("PaymentTimes", gamePlayerStatistics.getPaymentTimes());
            gamePlayerStatisticsObject.put("OnlineTimes", gamePlayerStatistics.getOnlineTimes());
            gamePlayerStatisticsObject.put("TotalPurchasesAmountRange", gamePlayerStatistics.getTotalPurchasesAmountRange());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return gamePlayerStatisticsObject;
    }

    static JSONObject operationResultToJsonObject(OperationResult operationResult) {
        JSONObject operationResultObject = new JSONObject();
        try {
            operationResultObject.put("Difference", operationResult.getDifference());
            operationResultObject.put("Archive", operationResult.getArchive());
            operationResultObject.put("Difference", operationResult.isDifference());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return operationResultObject;
    }

    static JSONObject archiveToJsonObject(Archive archive) {
        JSONObject archiveObject = new JSONObject();
        try {
            archiveObject.put("Summary", archiveSummaryToJsonObject(archive.getSummary()));
            archiveObject.put("Details", archive.getDetails());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return archiveObject;
    }

    static JSONArray archiveSummaryToJsonArray(List<ArchiveSummary> archiveSummaries) {
        JSONArray achivementArray = new JSONArray();
        for (ArchiveSummary event : archiveSummaries) {
            achivementArray.put(archiveSummaryToJsonObject(event));
        }
        return achivementArray;
    }

    private static JSONObject archiveSummaryToJsonObject(ArchiveSummary archiveSummary) {
        JSONObject archiveSummaryObject = new JSONObject();
        try {
            archiveSummaryObject.put("ThumbnailRatio", archiveSummary.getThumbnailRatio());
            archiveSummaryObject.put("DescInfo", archiveSummary.getDescInfo());
            archiveSummaryObject.put("GameSummary", gameSummaryToJsonObject(archiveSummary.getGameSummary()));
            archiveSummaryObject.put("RecentUpdateTime", archiveSummary.getRecentUpdateTime());
            archiveSummaryObject.put("GamePlayer", playerToJsonObject(archiveSummary.getGamePlayer()));
            archiveSummaryObject.put("ActiveTime", archiveSummary.getActiveTime());
            archiveSummaryObject.put("CurrentProgress", archiveSummary.getCurrentProgress());
            archiveSummaryObject.put("Id", archiveSummary.getId());
            archiveSummaryObject.put("FileName", archiveSummary.getFileName());
            archiveSummaryObject.put("Thumbnail", archiveSummary.hasThumbnail());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return archiveSummaryObject;
    }

    static JSONObject gameSummaryToJsonObject(GameSummary gameSummary) {
        JSONObject gameSummaryObject = new JSONObject();
        try {
            gameSummaryObject.put("AchievementCount", gameSummary.getAchievementCount());
            gameSummaryObject.put("AppId", gameSummary.getAppId());
            gameSummaryObject.put("DescInfo", gameSummary.getDescInfo());
            gameSummaryObject.put("GameName", gameSummary.getGameName());
            gameSummaryObject.put("GameHdImgUri", gameSummary.getGameHdImgUri());
            gameSummaryObject.put("GameIconUri", gameSummary.getGameIconUri());
            gameSummaryObject.put("RankingCount", gameSummary.getRankingCount());
            gameSummaryObject.put("FirstKind", gameSummary.getFirstKind());
            gameSummaryObject.put("SecondKind", gameSummary.getSecondKind());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return gameSummaryObject;
    }

    static JSONArray evensToJsonArray(List<Event> data) {
        JSONArray achivementArray = new JSONArray();
        for (Event event : data) {
            achivementArray.put(evenToJsonObject(event));
        }
        return achivementArray;
    }

    private static JSONObject evenToJsonObject(Event event) {
        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("Description", event.getDescription());
            eventObject.put("EventId", event.getEventId());
            eventObject.put("LocaleValue", event.getLocaleValue());
            eventObject.put("ThumbnailUri", event.getThumbnailUri());
            eventObject.put("Name", event.getName());
            eventObject.put("GamePlayer", playerToJsonObject(event.getGamePlayer()));
            eventObject.put("Value", event.getValue());
            eventObject.put("Visible", event.isVisible());
        } catch (JSONException e) {
            Log.i(Constants.eventName, Objects.requireNonNull(e.getMessage()));
        }
        return eventObject;
    }

}
