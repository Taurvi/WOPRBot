package WOPRBot.clients.aws.secretsmanager;

public interface IWOPRSecretsManagerClient<T> {
    T getSecret();
}
