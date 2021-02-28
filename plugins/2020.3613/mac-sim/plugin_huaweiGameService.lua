local Library = require "CoronaLibrary"

local lib = Library:new{ name='plugin.huaweiGameService', publisherId='com.solar2d' }

local placeholder = function()
	print( "WARNING: The '" .. lib.name .. "' library is not available on this platform." )
end


lib.init = placeholder
lib.achievementsClient = placeholder
lib.rankingsClient = placeholder
lib.archivesClient = placeholder
lib.eventsClient = placeholder
lib.gamesClient = placeholder
lib.playersClient = placeholder
lib.gameSummaryClient = placeholder
lib.gamePlayerStatisticsClient = placeholder
lib.buoyClient = placeholder

-- Return an instance
return lib