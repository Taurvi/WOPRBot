package WOPRBot.commands.getWeaponByUser;

import WOPRBot.clients.aws.dynamodb.IWOPRDynamoDbClient;
import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.commands.IWOPRBotCommand;
import com.google.inject.Inject;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

public class GetWeaponByUserCommand implements IWOPRBotCommand {
    private final ApplicationCommandRequest commandRequest;
    private final ReactiveEventAdapter reactiveEventAdapter;

    @Inject
    public GetWeaponByUserCommand(IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient) {
        this.commandRequest = ApplicationCommandRequest.builder()
                .name("get_weapon_by_user")
                .type(1)
                .addOption(getUserOption())
                .description("Gets the weapons selected by a user")
                .build();

        this.reactiveEventAdapter = new GetWeaponByUserReactiveEventAdapter(woprRecordDdbClient,
                this.commandRequest);
    }

    @Override
    public ApplicationCommandRequest getCommandRequest() {
        return this.commandRequest;
    }

    @Override
    public ReactiveEventAdapter getReactiveEventAdapter() {
        return this.reactiveEventAdapter;
    }

    private ApplicationCommandOptionData getUserOption() {
        return ApplicationCommandOptionData.builder()
                .name("target_user")
                .description("User you want weapon info on.")
                .type(6)
                .required(true)
                .build();
    }
}
