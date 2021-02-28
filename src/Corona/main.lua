local gameService = require "plugin.huaweiGameService"
local widget = require( "widget" )
local json = require("json")

gameService.init()

local TAG = "HuaweiGameService => "

-- //////////////////////////////////////// AchievementsClient ////////////////////////////////////////
local header = display.newText( "Huawei Game Service - AchievementsClient", display.contentCenterX, 80, native.systemFont, 10 )
header:setFillColor( 255, 255, 255 )
local getShowAchievementListIntent = widget.newButton(
    {
        left = 55,
        top = 100,
        id = "getShowAchievementListIntent",
        label = "getShowAchievementListIntent",
        onPress = function(event)
            gameService.achievementsClient("getShowAchievementListIntent", function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local grow = widget.newButton(
    {
        left = 55,
        top = 135,
        id = "grow",
        label = "grow",
        onPress = function(event)
            gameService.achievementsClient("grow", {id="", numSteps=0}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local growWithResult = widget.newButton(
    {
        left = 55,
        top = 170,
        id = "growWithResult",
        label = "growWithResult",
        onPress = function(event)
            gameService.achievementsClient("growWithResult", {id="", numSteps=0}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local getAchievementList = widget.newButton(
    {
        left = 55,
        top = 205,
        id = "getAchievementList",
        label = "getAchievementList",
        onPress = function(event)
            gameService.achievementsClient("getAchievementList", {forceReload=true}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local visualize = widget.newButton(
    {
        left = 55,
        top = 240,
        id = "visualize",
        label = "visualize",
        onPress = function(event)
            gameService.achievementsClient("visualize", {id=""}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local visualizeWithResult = widget.newButton(
    {
        left = 55,
        top = 275,
        id = "visualizeWithResult",
        label = "visualizeWithResult",
        onPress = function(event)
            gameService.achievementsClient("visualizeWithResult", {id=""}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local makeSteps = widget.newButton(
    {
        left = 55,
        top = 310,
        id = "makeSteps",
        label = "makeSteps",
        onPress = function(event)
            gameService.achievementsClient("makeSteps", {id="", numSteps=0}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local makeStepsWithResult = widget.newButton(
    {
        left = 55,
        top = 345,
        id = "makeStepsWithResult",
        label = "makeStepsWithResult",
        onPress = function(event)
            gameService.achievementsClient("makeStepsWithResult", {id="", numSteps=0}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local reach = widget.newButton(
    {
        left = 55,
        top = 380,
        id = "reach",
        label = "reach",
        onPress = function(event)
            gameService.achievementsClient("reach", {id=""}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)
local reachWithResult = widget.newButton(
    {
        left = 55,
        top = 415,
        id = "reachWithResult",
        label = "reachWithResult",
        onPress = function(event)
            gameService.achievementsClient("reachWithResult", {id=""}, function(event) 
                print(TAG, json.prettify( event ))
            end)
        end,
        width = 210,
        height = 30
    }
)

-- //////////////////////////////////////// RankingsClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - RankingsClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local getTotalRankingsIntent = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "getTotalRankingsIntent",
--         label = "getTotalRankingsIntent",
--         onPress = function(event)
--             gameService.rankingsClient("getTotalRankingsIntent", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getRankingIntent = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "getRankingIntent",
--         label = "getRankingIntent",
--         onPress = function(event)
--             gameService.rankingsClient("getRankingIntent", {rankingId=""}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getCurrentPlayerRankingScore = widget.newButton(
--     {
--         left = 55,
--         top = 170,
--         id = "getCurrentPlayerRankingScore",
--         label = "getCurrentPlayerRankingScore",
--         onPress = function(event)
--             gameService.rankingsClient("getCurrentPlayerRankingScore", {rankingId="", timeDimension=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getRankingSummary = widget.newButton(
--     {
--         left = 55,
--         top = 205,
--         id = "getRankingSummary",
--         label = "getRankingSummary",
--         onPress = function(event)
--             gameService.rankingsClient("getRankingSummary", {isRealTime=true}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getMoreRankingScores = widget.newButton(
--     {
--         left = 55,
--         top = 240,
--         id = "getMoreRankingScores",
--         label = "getMoreRankingScores",
--         onPress = function(event)
--             gameService.rankingsClient("getMoreRankingScores", {rankingId="", offsetPlayerRank=0, maxResults=0, pageDirection=0, timeDimension=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getPlayerCenteredRankingScores = widget.newButton(
--     {
--         left = 55,
--         top = 275,
--         id = "getPlayerCenteredRankingScores",
--         label = "getPlayerCenteredRankingScores",
--         onPress = function(event)
--             gameService.rankingsClient("getPlayerCenteredRankingScores", {rankingId="", timeDimension=0, maxResults=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getRankingTopScores = widget.newButton(
--     {
--         left = 55,
--         top = 310,
--         id = "getRankingTopScores",
--         label = "getRankingTopScores",
--         onPress = function(event)
--             gameService.rankingsClient("getRankingTopScores", {rankingId="", timeDimension=0, maxResults=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local submitScoreWithResult = widget.newButton(
--     {
--         left = 55,
--         top = 345,
--         id = "submitScoreWithResult",
--         label = "submitScoreWithResult",
--         onPress = function(event)
--             gameService.rankingsClient("submitScoreWithResult", {rankingId="", score=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getRankingSwitchStatus = widget.newButton(
--     {
--         left = 55,
--         top = 380,
--         id = "getRankingSwitchStatus",
--         label = "getRankingSwitchStatus",
--         onPress = function(event)
--             gameService.rankingsClient("getRankingSwitchStatus", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local setRankingSwitchStatus = widget.newButton(
--     {
--         left = 55,
--         top = 415,
--         id = "setRankingSwitchStatus",
--         label = "setRankingSwitchStatus",
--         onPress = function(event)
--             gameService.rankingsClient("setRankingSwitchStatus", {status=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// ArchivesClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - ArchivesClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local getLimitThumbnailSize = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "getLimitThumbnailSize",
--         label = "getLimitThumbnailSize",
--         onPress = function(event)
--             gameService.archivesClient("getLimitThumbnailSize", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getLimitDetailsSize = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "getLimitDetailsSize",
--         label = "getLimitDetailsSize",
--         onPress = function(event)
--             gameService.archivesClient("getLimitDetailsSize", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getShowArchiveListIntent = widget.newButton(
--     {
--         left = 55,
--         top = 170,
--         id = "getShowArchiveListIntent",
--         label = "getShowArchiveListIntent",
--         onPress = function(event)
--             gameService.archivesClient("getShowArchiveListIntent", {title="", allowAddBtn=false, maxArchive=0, allowDeleteBtn=true}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getArchiveSummaryList = widget.newButton(
--     {
--         left = 55,
--         top = 205,
--         id = "getArchiveSummaryList",
--         label = "getArchiveSummaryList",
--         onPress = function(event)
--             gameService.archivesClient("getArchiveSummaryList", {isRealTime=true}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getThumbnail = widget.newButton(
--     {
--         left = 55,
--         top = 240,
--         id = "getThumbnail",
--         label = "getThumbnail",
--         onPress = function(event)
--             gameService.archivesClient("getThumbnail", {archiveId=""}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local loadArchiveDetails = widget.newButton(
--     {
--         left = 55,
--         top = 240,
--         id = "loadArchiveDetails",
--         label = "loadArchiveDetails",
--         onPress = function(event)
--             gameService.archivesClient("loadArchiveDetails", {archiveId="", diffStrategy=0}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )


-- //////////////////////////////////////// EventsClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - EventsClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local grow = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "grow",
--         label = "grow",
--         onPress = function(event)
--             gameService.eventsClient("grow",{eventId ="", growAmount=0} ,function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getEventList = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "getEventList",
--         label = "getEventList",
--         onPress = function(event)
--             gameService.eventsClient("getEventList",{forceReload =true} , function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// GamesClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - GamesClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local getAppId = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "getAppId",
--         label = "getAppId",
--         onPress = function(event)
--             gameService.gamesClient("getAppId", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local setPopupsPosition = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "setPopupsPosition",
--         label = "setPopupsPosition",
--         onPress = function(event)
--             gameService.gamesClient("setPopupsPosition", {position=0},function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local cancelGameService = widget.newButton(
--     {
--         left = 55,
--         top = 170,
--         id = "cancelGameService",
--         label = "cancelGameService",
--         onPress = function(event)
--             gameService.gamesClient("cancelGameService", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// PlayersClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - PlayersClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local getCurrentPlayer = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "getCurrentPlayer",
--         label = "getCurrentPlayer",
--         onPress = function(event)
--             gameService.playersClient("getCurrentPlayer", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getGamePlayer = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "getGamePlayer",
--         label = "getGamePlayer",
--         onPress = function(event)
--             gameService.playersClient("getGamePlayer", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getCachePlayerId = widget.newButton(
--     {
--         left = 55,
--         top = 170,
--         id = "getCachePlayerId",
--         label = "getCachePlayerId",
--         onPress = function(event)
--             gameService.playersClient("getCachePlayerId", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getPlayerExtraInfo = widget.newButton(
--     {
--         left = 55,
--         top = 205,
--         id = "getPlayerExtraInfo",
--         label = "getPlayerExtraInfo",
--         onPress = function(event)
--             gameService.playersClient("getPlayerExtraInfo", {transactionId=""}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local submitPlayerEvent = widget.newButton(
--     {
--         left = 55,
--         top = 240,
--         id = "submitPlayerEvent",
--         label = "submitPlayerEvent",
--         onPress = function(event)
--             gameService.playersClient("submitPlayerEvent", {eventId="", eventType=""}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// GameSummaryClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - GameSummaryClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local getLocalGameSummary = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "getLocalGameSummary",
--         label = "getLocalGameSummary",
--         onPress = function(event)
--             gameService.gameSummaryClient("getLocalGameSummary", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local getGameSummary = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "getGameSummary",
--         label = "getGameSummary",
--         onPress = function(event)
--             gameService.gameSummaryClient("getGameSummary", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// GamePlayerStatisticsClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - GamePlayerStatisticsClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local getGamePlayerStatistics = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "getGamePlayerStatistics",
--         label = "getGamePlayerStatistics",
--         onPress = function(event)
--             gameService.gamePlayerStatisticsClient("getGamePlayerStatistics", {isRealTime=true}, function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )

-- //////////////////////////////////////// BuoyClient ////////////////////////////////////////
-- local header = display.newText( "Huawei Game Service - BuoyClient", display.contentCenterX, 80, native.systemFont, 10 )
-- header:setFillColor( 255, 255, 255 )
-- local showFloatWindow = widget.newButton(
--     {
--         left = 55,
--         top = 100,
--         id = "showFloatWindow",
--         label = "showFloatWindow",
--         onPress = function(event)
--             gameService.buoyClient("showFloatWindow", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
-- local hideFloatWindow = widget.newButton(
--     {
--         left = 55,
--         top = 135,
--         id = "hideFloatWindow",
--         label = "hideFloatWindow",
--         onPress = function(event)
--             gameService.buoyClient("hideFloatWindow", function(event) 
--                 print(TAG, json.prettify( event ))
--             end)
--         end,
--         width = 210,
--         height = 30
--     }
-- )
