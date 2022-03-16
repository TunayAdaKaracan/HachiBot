event = require("EventLib")

function start()
    event.registerEvent(event.types.ReadyEvent, onReady)
    event.registerEvent(event.types.MessageEvent, onMessage)
end

function onReady()
    print("Started")
end

function onMessage(ctx)
    event.unregisterEvent(event.types.MessageEvent, onMessage)
    print("by")
end