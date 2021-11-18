import {Client} from "discord.js";

interface IIndexController {

}

export class IndexController implements IIndexController {
    private readonly discordClient: Client;

    public constructor(discordClient: Client) {
        this.discordClient = discordClient;
    }
}
