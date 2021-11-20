package WOPRBot.clients.discord;

import WOPRBot.clients.aws.secretsmanager.IWOPRSecretsManagerClient;
import WOPRBot.clients.aws.secretsmanager.models.DiscordSecret;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

public class DiscordModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Provides
    @Singleton
    public DiscordClient
    provideDiscordClient(IWOPRSecretsManagerClient<DiscordSecret> discordSecretsManagerClient) {
        var discordClientProvider = new DiscordClientProvider(discordSecretsManagerClient);
        return discordClientProvider.get();
    }

    @Provides
    @Singleton
    public GatewayDiscordClient
    provideGatewayDiscordClient(DiscordClient discordClient) {
        return discordClient.login().block();
    }
}
