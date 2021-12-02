package WOPRBot.clients.aws.secretsmanager;

import WOPRBot.clients.WOPRBotClientsException;
import WOPRBot.clients.aws.secretsmanager.models.DiscordSecret;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import javax.inject.Inject;

@Slf4j
public class DiscordSecretsClient extends WOPRSecretsClient<DiscordSecret> {
    private final String DISCORD_SECRET_ID = "DISCORD_SECRETS_PROD";

    @Inject
    public DiscordSecretsClient(ObjectMapper objectMapper, SecretsManagerClient secretsManagerClient) {
        super(objectMapper, secretsManagerClient);
    }

    @Override
    public DiscordSecret getSecret() {
        var request = GetSecretValueRequest.builder()
                .secretId(DISCORD_SECRET_ID)
                .build();

        var response = this.secretsManagerClient.getSecretValue(request);
        try {
            return this.objectMapper.readValue(response.secretString(), DiscordSecret.class);
        } catch (JsonProcessingException exception) {
            throw new WOPRBotClientsException("Error processing Discord Secret JSON", exception);
        }
    }
}
