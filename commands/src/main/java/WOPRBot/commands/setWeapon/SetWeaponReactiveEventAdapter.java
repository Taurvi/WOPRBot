package WOPRBot.commands.setWeapon;

import WOPRBot.clients.aws.dynamodb.IWOPRDynamoDbClient;
import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.commands.models.WeaponType;
import com.google.inject.Inject;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteraction;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.discordjson.json.ApplicationCommandRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Slf4j
public class SetWeaponReactiveEventAdapter extends ReactiveEventAdapter {
    private final IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient;
    private final ApplicationCommandRequest commandRequest;

    @Inject
    public SetWeaponReactiveEventAdapter(IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient,
                                         ApplicationCommandRequest commandRequest) {
        this.woprRecordDdbClient = woprRecordDdbClient;
        this.commandRequest = commandRequest;
    }

    @Override
    @NotNull
    public Publisher<?> onChatInputInteraction(ChatInputInteractionEvent event) {
        var commandName = this.commandRequest.name();
        if (event.getCommandName().equals(commandName)) {
            var userId = event.getInteraction().getUser().getId();
            var userDisplayName = event.getInteraction().getUser().getMention();

            var commandInteractionOptional = event.getInteraction().getCommandInteraction();

            if (commandInteractionOptional.isPresent()) {
                var response = commandInteractionOptional.get();

                return event.reply(String.format("Response received. User %s selected %s (Primary) and %s (Secondary)",
                        userDisplayName,
                        parseValueAndStoreInDb(response, userId.asLong(), "primary_weapon"),
                        parseValueAndStoreInDb(response, userId.asLong(),"secondary_weapon")));
            } else {
                return event.reply("No input was found.");
            }
        }
        return Mono.empty();
    }

    private String parseValueAndStoreInDb(ApplicationCommandInteraction interaction, long userId, String option) {
        var rawValue = interaction.getOption(option)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .orElse("Unknown");

        var value = WeaponType.fromValue(rawValue);

        this.woprRecordDdbClient.putItem(userId, option, value.getValue());

        return value.getName();
    }
}
