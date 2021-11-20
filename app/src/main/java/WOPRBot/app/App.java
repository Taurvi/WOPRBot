package WOPRBot.app;

import WOPRBot.clients.aws.secretsmanager.IWOPRSecretsManagerClient;
import WOPRBot.clients.aws.secretsmanager.SecretsModule;

import WOPRBot.clients.aws.secretsmanager.models.ServerIdSecret;
import WOPRBot.clients.discord.DiscordModule;
import WOPRBot.commands.CommandsModule;
import WOPRBot.commands.IWOPRBotCommand;
import com.google.inject.Guice;
import com.google.inject.Key;
import com.google.inject.name.Names;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class App {
    public static void main(String[] args) {
        var injector = Guice.createInjector(new CoreModule(), new SecretsModule(), new DiscordModule(), new CommandsModule());
        var commandsList = injector.getInstance(new Key<List<IWOPRBotCommand>>(Names.named("IWOPRBotCommands")){});
        var serverIdSecretsClient = injector.getInstance(new Key<IWOPRSecretsManagerClient<ServerIdSecret>>(){});
        var serverIdSecret = serverIdSecretsClient.getSecret();

        var discordClient = injector.getInstance(DiscordClient.class);

        discordClient.withGateway(gatewayClient -> {
            gatewayClient.getEventDispatcher().on(ReadyEvent.class)
                    .subscribe(readyEvent -> log.info(String.format("Logged in as %s", readyEvent.getSelf().getUsername())));

            var restDiscordClient = gatewayClient.getRestClient();
            commandsList.forEach((command) -> {
                var applicationId = restDiscordClient.getApplicationId().block();


                restDiscordClient.getApplicationService()
                        .createGuildApplicationCommand(applicationId, Snowflake.asLong(serverIdSecret.getServerId()), command.getCommandRequest())
                        .doOnError(error -> log.warn("Unable to create guild command", error))
                        .onErrorResume(error -> Mono.empty())
                        .block();

                gatewayClient.on(command.getReactiveEventAdapter()).subscribe();
            });
            return gatewayClient.onDisconnect();
        }).block();

    }
}
