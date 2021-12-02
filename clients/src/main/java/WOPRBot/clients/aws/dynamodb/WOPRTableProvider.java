package WOPRBot.clients.aws.dynamodb;

import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.clients.aws.secretsmanager.IWOPRSecretsManagerClient;
import WOPRBot.clients.aws.secretsmanager.models.DynamoDbSecret;
import com.google.inject.Inject;
import com.google.inject.Provider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class WOPRTableProvider implements Provider<DynamoDbTable<WOPRRecord>> {
    private final IWOPRSecretsManagerClient<DynamoDbSecret> dynamoDbSecretClient;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Inject
    public WOPRTableProvider(IWOPRSecretsManagerClient<DynamoDbSecret> dynamoDbSecretClient,
                             DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbSecretClient = dynamoDbSecretClient;
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    @Override
    public DynamoDbTable<WOPRRecord> get() {
        var tablename = dynamoDbSecretClient.getSecret().getTableName();
        return this.dynamoDbEnhancedClient.table(tablename, TableSchema.fromBean(WOPRRecord.class));
    }
}
