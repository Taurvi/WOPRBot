package WOPRBot.commands;

import discord4j.core.event.ReactiveEventAdapter;
import discord4j.discordjson.json.ApplicationCommandRequest;

public interface IWOPRBotCommand {
    ApplicationCommandRequest getCommandRequest();
    ReactiveEventAdapter getReactiveEventAdapter();
}
