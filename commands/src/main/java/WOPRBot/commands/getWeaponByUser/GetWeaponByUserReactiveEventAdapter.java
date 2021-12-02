package WOPRBot.commands.getWeaponByUser;

import WOPRBot.clients.aws.dynamodb.IWOPRDynamoDbClient;
import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.commands.models.DataType;
import WOPRBot.commands.models.WeaponType;
import com.google.inject.Inject;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteraction;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.User;
import discord4j.discordjson.json.ApplicationCommandRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Slf4j
public class GetWeaponByUserReactiveEventAdapter extends ReactiveEventAdapter {
    private final IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient;
    private final ApplicationCommandRequest commandRequest;

    @Inject
    public GetWeaponByUserReactiveEventAdapter(IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient,
                                               ApplicationCommandRequest commandRequest) {
        this.woprRecordDdbClient = woprRecordDdbClient;
        this.commandRequest = commandRequest;
    }

    @Override
    @NotNull
    public Publisher<?> onChatInputInteraction(ChatInputInteractionEvent event) {
        var commandName = this.commandRequest.name();

        if (event.getCommandName().equals(commandName)) {
            var commandInteractionOptional = event.getInteraction()
                    .getCommandInteraction();

            if (commandInteractionOptional.isPresent()) {
                var user = parseUser(commandInteractionOptional.get());
                String rawPrimaryWeapon = this.getFromDatabase(
                        user.getId().asLong(), DataType.PRIMARY_WEAPON.getValue());
                String rawSecondaryWeapon = this.getFromDatabase(
                        user.getId().asLong(), DataType.SECONDARY_WEAPON.getValue());

                return event.reply(String.format("<@%d> uses %s (primary) and %s (secondary).", user.getId().asLong(),
                        WeaponType.fromValue(rawPrimaryWeapon).getName(),
                        WeaponType.fromValue(rawSecondaryWeapon).getName()));
            } else {
                return event.reply("No input was found");
            }
        }

        return Mono.empty();
    }

    private User parseUser(ApplicationCommandInteraction interaction) {
        var rawValue = interaction.getOption("target_user")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asUser).orElse(Mono.empty());

        return rawValue.block();
    }

    private String getFromDatabase(long userId, String type) {
        var record = this.woprRecordDdbClient.getItem(userId, type);
        return record.getValue();
    }
}
