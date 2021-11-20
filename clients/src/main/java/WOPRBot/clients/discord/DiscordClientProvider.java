package WOPRBot.clients.discord;

import WOPRBot.clients.aws.secretsmanager.IWOPRSecretsManagerClient;
import WOPRBot.clients.aws.secretsmanager.models.DiscordSecret;
import com.google.inject.Inject;
import com.google.inject.Provider;
import discord4j.core.DiscordClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiscordClientProvider implements Provider<DiscordClient> {
    private final IWOPRSecretsManagerClient<DiscordSecret> discordSecretsManagerClient;

    @Inject
    public DiscordClientProvider(IWOPRSecretsManagerClient<DiscordSecret> discordSecretsManagerClient) {
        this.discordSecretsManagerClient = discordSecretsManagerClient;
    }

    @Override
    public DiscordClient get() {
        var secret = discordSecretsManagerClient.getSecret();
        return DiscordClient.create(secret.getToken());
    }
}
