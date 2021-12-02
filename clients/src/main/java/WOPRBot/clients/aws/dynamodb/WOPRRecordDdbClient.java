package WOPRBot.clients.aws.dynamodb;

import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Slf4j
public class WOPRRecordDdbClient implements IWOPRDynamoDbClient<WOPRRecord> {
    private final DynamoDbTable<WOPRRecord> table;

    @Inject
    public WOPRRecordDdbClient(DynamoDbTable<WOPRRecord> table) {
        this.table = table;
    }


    @Override
    public WOPRRecord getItem(long userId, String type) {
        log.info(String.format("Retrieving: %d %s", userId, type));
        var key = Key.builder()
                .partitionValue(userId)
                .sortValue(type)
                .build();

        return this.table.getItem(record -> record.key(key));
    }

    @Override
    public void putItem(long userId, String type, String value) {
        log.info(String.format("Storing: %d, %s, %s", userId, type, value));
        var item = WOPRRecord.builder()
                .userId(userId)
                .type(type)
                .value(value)
                .build();

        this.table.putItem(item);
    }
}
