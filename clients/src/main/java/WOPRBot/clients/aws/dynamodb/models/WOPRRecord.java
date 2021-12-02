package WOPRBot.clients.aws.dynamodb.models;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class WOPRRecord {
    @Getter(onMethod = @__({@DynamoDbPartitionKey}))
    private long userId;
    @Getter(onMethod = @__({@DynamoDbSortKey}))
    private String type;
    private String value;
}
