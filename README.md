# Huawei Game Service Solar2d Plugin

This plugin was created based on Huawei Game Service. Please [check](https://developer.huawei.com/consumer/en/hms/huawei-game/) for detailed information about Huawei Game Service. 

In order to use the Huawei Game Service, you must first create an account from developer.huawei.com. And after logging in with your account, and then you must create a project in the huawei console in order to use HMS kits.

## Project Setup

To use the plugin please add following to `build.settings`

```lua
{
    plugins = {
        ["plugin.huaweiGameService"] = {
            publisherId = "com.solar2d",
        },
    },
}
```

After you need to define the plugin in main.lua.

```lua
local gameService = require "plugin.huaweiGameService"

gameService.init()
```


## References
HMS Game Service  [here](https://developer.huawei.com/consumer/en/hms/huawei-game/)  
HMS Game Service Result Codes [here](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/guide-error-0000001050994619-V5)

## License
MIT
