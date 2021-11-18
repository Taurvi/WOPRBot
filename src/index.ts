import {AwsSecretsManagerClient} from "./clients/aws/AwsSecretsManagerClient";
import {SecretsManagerClient} from "@aws-sdk/client-secrets-manager";
import {DiscordClientProvider} from "./clients/discord/DiscordClientProvider";

export function hello(): string {
  const world = "world";
  return `Hello ${world}!`;
}

const secretsManagerClient = new SecretsManagerClient({region: "us-west-2"});
const awsSecretsManagerClient = new AwsSecretsManagerClient(secretsManagerClient);
const discordClientProvider = new DiscordClientProvider(awsSecretsManagerClient);

const discordClient = discordClientProvider.get();

