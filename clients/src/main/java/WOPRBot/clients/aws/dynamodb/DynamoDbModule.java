package WOPRBot.clients.aws.dynamodb;

import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.clients.aws.secretsmanager.IWOPRSecretsManagerClient;
import WOPRBot.clients.aws.secretsmanager.models.DynamoDbSecret;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbModule extends AbstractModule {
    @Override
    public void configure() {

    }

    @Provides
    @Singleton
    public DynamoDbEnhancedClient provideDynamoDbEnhancedClient() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_WEST_2)
                .build();

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Provides
    @Singleton
    public DynamoDbTable<WOPRRecord>
    provideWOPRTable(IWOPRSecretsManagerClient<DynamoDbSecret> dynamoDbSecretClient, DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        var provider = new WOPRTableProvider(dynamoDbSecretClient, dynamoDbEnhancedClient);
        return provider.get();
    }

    @Provides
    @Singleton
    public IWOPRDynamoDbClient<WOPRRecord>
    provideWOPRRecordDdbClient(DynamoDbTable<WOPRRecord> table) {
        return new WOPRRecordDdbClient(table);
    }

}
