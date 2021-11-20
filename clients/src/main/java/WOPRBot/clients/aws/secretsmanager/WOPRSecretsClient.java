package WOPRBot.clients.aws.secretsmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public abstract class WOPRSecretsClient<T> implements IWOPRSecretsManagerClient<T> {
    protected final ObjectMapper objectMapper;
    protected final SecretsManagerClient secretsManagerClient;

    public WOPRSecretsClient(ObjectMapper objectMapper, SecretsManagerClient secretsManagerClient) {
        this.objectMapper = objectMapper;
        this.secretsManagerClient = secretsManagerClient;
    }
}
