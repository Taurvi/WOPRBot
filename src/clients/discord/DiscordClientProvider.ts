import {Client, Intents} from "discord.js";
import {IAwsSecretsManagerClient} from "../aws/AwsSecretsManagerClient";

interface IDiscordClient {
    get: () => Client;
}

export class DiscordClientProvider implements IDiscordClient {
    private readonly awsSecretsManagerClient: IAwsSecretsManagerClient;

    public constructor(awsSecretsManagerClient: IAwsSecretsManagerClient) {
        this.awsSecretsManagerClient = awsSecretsManagerClient;
    }

    public get(): Client {
        const client = new Client({intents: [Intents.FLAGS.GUILDS]});
        client.once('ready', async (client: Client) => this.authenticate(client));
        return client;
    }

    private async authenticate(client: Client): Promise<void> {
        console.log("discordClient initialized.");
        const discordToken = await this.awsSecretsManagerClient.get("WOPRBot-Discord-Token");
        await client.login(discordToken);
    }
}

