package WOPRBot.commands.ping;

import WOPRBot.commands.IWOPRBotCommand;
import com.google.inject.Inject;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.discordjson.json.ApplicationCommandRequest;

public class PingCommand implements IWOPRBotCommand {
    private final ApplicationCommandRequest commandRequest;
    private final ReactiveEventAdapter reactiveEventAdapter;

    @Inject
    public PingCommand() {
        this.commandRequest = ApplicationCommandRequest.builder()
                .name("ping")
                .description("Returns pong")
                .build();
        this.reactiveEventAdapter = new PingReactiveEventAdapter(commandRequest);
    }

    @Override
    public ApplicationCommandRequest getCommandRequest() {
        return this.commandRequest;
    }

    @Override
    public ReactiveEventAdapter getReactiveEventAdapter() {
        return this.reactiveEventAdapter;
    }
}
