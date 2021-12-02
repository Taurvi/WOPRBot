package WOPRBot.clients.aws.secretsmanager;

import WOPRBot.clients.WOPRBotClientsException;
import WOPRBot.clients.aws.secretsmanager.models.DynamoDbSecret;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Slf4j
public class DynamoDbSecretsClient extends WOPRSecretsClient<DynamoDbSecret> {
    private final String DYNAMO_DB_SECRET_ID = "DYNAMO_DB_SECRETS_PROD";

    @Inject
    public DynamoDbSecretsClient(ObjectMapper objectMapper, SecretsManagerClient secretsManagerClient) {
        super(objectMapper, secretsManagerClient);
    }

    @Override
    public DynamoDbSecret getSecret() {
        var request = GetSecretValueRequest.builder()
                .secretId(DYNAMO_DB_SECRET_ID)
                .build();

        var response = this.secretsManagerClient.getSecretValue(request);

        try {
            return this.objectMapper.readValue(response.secretString(), DynamoDbSecret.class);
        } catch (JsonProcessingException exception) {
            throw new WOPRBotClientsException("Error processing DynamoDb Secret JSON", exception);
        }
    }
}
