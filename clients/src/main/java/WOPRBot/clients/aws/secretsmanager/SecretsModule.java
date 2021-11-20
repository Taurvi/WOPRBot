package WOPRBot.clients.aws.secretsmanager;

import WOPRBot.clients.aws.secretsmanager.models.DiscordSecret;
import WOPRBot.clients.aws.secretsmanager.models.ServerIdSecret;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public class SecretsModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Provides
    @Singleton
    public SecretsManagerClient provideSecretsManagerClient() {
        return SecretsManagerClient.builder().region(Region.US_WEST_2).build();
    }

    @Provides
    @Singleton
    public IWOPRSecretsManagerClient<DiscordSecret>
    provideDiscordSecretsManager(ObjectMapper objectMapper, SecretsManagerClient secretsManagerClient) {
        return new DiscordSecretsClient(objectMapper, secretsManagerClient);
    }

    @Provides
    @Singleton
    public IWOPRSecretsManagerClient<ServerIdSecret>
    provideServerIdSecretsManager(ObjectMapper objectMapper, SecretsManagerClient secretsManagerClient) {
        return new ServerIdSecretsClient(objectMapper, secretsManagerClient);
    }
}
