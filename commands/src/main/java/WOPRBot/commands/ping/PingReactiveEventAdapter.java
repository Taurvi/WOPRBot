package WOPRBot.commands.ping;

import com.google.inject.Inject;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class PingReactiveEventAdapter extends ReactiveEventAdapter {
    private final ApplicationCommandRequest commandRequest;

    @Inject
    public PingReactiveEventAdapter(ApplicationCommandRequest commandRequest) {
        this.commandRequest = commandRequest;
    }

    @Override
    @NotNull
    public Publisher<?> onChatInputInteraction(ChatInputInteractionEvent event) {
        var commandName = this.commandRequest.name();
        if (event.getCommandName().equals(commandName)) {
            return event.reply("pong");
        }
        return Mono.empty();
    }
}
