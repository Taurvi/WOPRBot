import {GetSecretValueCommand, SecretsManagerClient} from "@aws-sdk/client-secrets-manager";

export interface IAwsSecretsManagerClient {
    get: (secretId: string) => Promise<string>
}

export class AwsSecretsManagerClient implements IAwsSecretsManagerClient {
    private readonly secretsManagerClient: SecretsManagerClient;

    public constructor(secretsManagerClient: SecretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    public async get(secretId: string): Promise<string> {
        const command = new GetSecretValueCommand({SecretId: secretId});
        const response = await this.secretsManagerClient.send(command);

        if (response?.SecretString == null) {
            throw new Error(`AWS secret ${secretId} not found`)
        }

        return response.SecretString;
    }
}