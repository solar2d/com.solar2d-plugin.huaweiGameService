package plugin.huaweiGameService;

class Constants {

    static final String eventName = "Huawei Game Service";

    // Provider
    static final String achievementsClient = "achievementsClient";
    static final String archivesClient = "archivesClient";
    static final String eventsClient = "eventsClient";
    static final String gamesClient = "gamesClient";
    static final String gameSummaryClient = "gameSummaryClient";
    static final String playersClient = "playersClient";
    static final String rankingsClient = "rankingsClient";
    static final String gamePlayerStatisticsClient = "gamePlayerStatisticsClient";
    static final String buoyClient = "buoyClient";

    // AchievementsClient
    static final String getShowAchievementListIntent = "getShowAchievementListIntent";
    static final String grow = "grow";
    static final String growWithResult = "growWithResult";
    static final String getAchievementList = "getAchievementList";
    static final String visualize = "visualize";
    static final String visualizeWithResult = "visualizeWithResult";
    static final String makeSteps = "makeSteps";
    static final String makeStepsWithResult = "makeStepsWithResult";
    static final String reach = "reach";
    static final String reachWithResult = "reachWithResult";

    // RankingsClient
    static final String getTotalRankingsIntent = "getTotalRankingsIntent";
    static final String getRankingIntent = "getRankingIntent";
    static final String getCurrentPlayerRankingScore = "getCurrentPlayerRankingScore";
    static final String getRankingSummary = "getRankingSummary";
    static final String getMoreRankingScores = "getMoreRankingScores";
    static final String getPlayerCenteredRankingScores = "getPlayerCenteredRankingScores";
    static final String getRankingTopScores = "getRankingTopScores";
    static final String submitRankingScore = "submitRankingScore";
    static final String submitScoreWithResult = "submitScoreWithResult";
    static final String getRankingSwitchStatus = "getRankingSwitchStatus";
    static final String setRankingSwitchStatus = "setRankingSwitchStatus";

    //ArchivesClient
    static final String addArchive = "addArchive";
    static final String removeArchive = "removeArchive";
    static final String getLimitThumbnailSize = "getLimitThumbnailSize";
    static final String getLimitDetailsSize = "getLimitDetailsSize";
    static final String getShowArchiveListIntent = "getShowArchiveListIntent";
    static final String parseSummary = "parseSummary";
    static final String getArchiveSummaryList = "getArchiveSummaryList";
    static final String loadArchiveDetails = "loadArchiveDetails";
    static final String updateArchive = "updateArchive";
    static final String getThumbnail = "getThumbnail";

    //EventsClient
    static final String getEventList = "getEventList";
    static final String getEventListByIds = "getEventListByIds";

    //GamesClient
    static final String getAppId = "getAppId";
    static final String setPopupsPosition = "setPopupsPosition";
    static final String cancelGameService = "cancelGameService";

    //PlayersClient
    static final String getCurrentPlayer = "getCurrentPlayer";
    static final String getGamePlayer = "getGamePlayer";
    static final String getCachePlayerId = "getCachePlayerId";
    static final String getPlayerExtraInfo = "getPlayerExtraInfo";
    static final String submitPlayerEvent = "submitPlayerEvent";
    static final String savePlayerInfo = "savePlayerInfo";
    static final String setGameTrialProcess = "setGameTrialProcess";

    //GameSummaryClient
    static final String getLocalGameSummary = "getLocalGameSummary";
    static final String getGameSummary = "getGameSummary";

    //GamePlayerStatisticsClient
    static final String getGamePlayerStatistics = "getGamePlayerStatistics";

    //BuoyClient
    static final String showFloatWindow = "showFloatWindow";
    static final String hideFloatWindow = "hideFloatWindow";

}
