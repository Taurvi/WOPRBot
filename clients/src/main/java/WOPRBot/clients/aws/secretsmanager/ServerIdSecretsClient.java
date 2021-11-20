package WOPRBot.clients.aws.secretsmanager;

import WOPRBot.clients.WOPRBotClientsException;
import WOPRBot.clients.aws.secretsmanager.models.ServerIdSecret;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import javax.inject.Inject;

public class ServerIdSecretsClient extends WOPRSecretsClient<ServerIdSecret> {
    private final String SERVER_ID_SECRET_ID = "SERVER_ID_SECRET";

    @Inject
    public ServerIdSecretsClient(ObjectMapper objectMapper, SecretsManagerClient secretsManagerClient) {
        super(objectMapper, secretsManagerClient);
    }

    @Override
    public ServerIdSecret getSecret() {
        var request = GetSecretValueRequest.builder()
                .secretId(SERVER_ID_SECRET_ID)
                .build();

        var response = this.secretsManagerClient.getSecretValue(request);
        try {
            return this.objectMapper.readValue(response.secretString(), ServerIdSecret.class);
        } catch (JsonProcessingException exception) {
            throw new WOPRBotClientsException("Error processing Server ID Secret JSON", exception);
        }
    }
}
