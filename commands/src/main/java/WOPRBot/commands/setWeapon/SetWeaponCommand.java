package WOPRBot.commands.setWeapon;

import WOPRBot.clients.aws.dynamodb.IWOPRDynamoDbClient;
import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.commands.IWOPRBotCommand;
import WOPRBot.commands.models.DataType;
import WOPRBot.commands.models.WeaponType;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import discord4j.core.event.ReactiveEventAdapter;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

import java.util.List;

public class SetWeaponCommand implements IWOPRBotCommand {
    private final ApplicationCommandRequest commandRequest;
    private final ReactiveEventAdapter reactiveEventAdapter;

    @Inject
    public SetWeaponCommand(IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient) {
        var primaryWeaponSelection =
                this.getWeaponOptions(DataType.PRIMARY_WEAPON, "Select your primary weapon");
        var secondaryWeaponSelection =
                this.getWeaponOptions(DataType.SECONDARY_WEAPON, "Select your secondary weapon");

        this.commandRequest =  ApplicationCommandRequest.builder()
                .name("set_weapon")
                .type(1)
                .description("Sets the user's primary and secondary weapons")
                .addOption(primaryWeaponSelection)
                .addOption(secondaryWeaponSelection)
                .build();

        this.reactiveEventAdapter = new SetWeaponReactiveEventAdapter(woprRecordDdbClient, this.commandRequest);
    }

    @Override
    public ApplicationCommandRequest getCommandRequest() {
        return commandRequest;
    }

    @Override
    public ReactiveEventAdapter getReactiveEventAdapter() {
        return reactiveEventAdapter;
    }

    private ApplicationCommandOptionData getWeaponOptions(DataType type, String description) {
        var roleChoices = this.getRoleChoices();
        return ApplicationCommandOptionData.builder()
                .name(type.getValue())
                .description(description)
                .type(3)
                .choices(roleChoices)
                .required(true)
                .build();
    }

    private List<ApplicationCommandOptionChoiceData> getRoleChoices() {
        var weaponsList = ImmutableList.of(
                WeaponType.SWORD,
                WeaponType.RAPIER,
                WeaponType.HATCHET,
                WeaponType.SPEAR,
                WeaponType.GREAT_AXE,
                WeaponType.WAR_HAMMER,
                WeaponType.BOW,
                WeaponType.MUSKET,
                WeaponType.FIRE_STAFF,
                WeaponType.LIFE_STAFF,
                WeaponType.ICE_GAUNTLET,
                WeaponType.VOID_GAUNTLET);

        return weaponsList.stream().map(this::createWeapon).toList();
    }

    private ApplicationCommandOptionChoiceData createWeapon(WeaponType weaponType) {
        return ApplicationCommandOptionChoiceData.builder()
                .name(weaponType.getName())
                .value(weaponType.getValue())
                .build();
    }
}
