package WOPRBot.clients.aws.dynamodb;

public interface IWOPRDynamoDbClient<T> {
    T getItem(long userId, String type);

    void putItem(long userId, String type, String value);
}
