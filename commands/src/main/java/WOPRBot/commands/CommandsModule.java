package WOPRBot.commands;

import WOPRBot.clients.aws.dynamodb.IWOPRDynamoDbClient;
import WOPRBot.clients.aws.dynamodb.models.WOPRRecord;
import WOPRBot.commands.getWeaponByUser.GetWeaponByUserCommand;
import WOPRBot.commands.ping.PingCommand;
import WOPRBot.commands.setWeapon.SetWeaponCommand;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.util.List;

public class CommandsModule extends AbstractModule {
    @Override
    public void configure() {

    }

    @Named("IWOPRBotCommands")
    @Provides
    @Singleton
    public List<IWOPRBotCommand> provideCommands(IWOPRDynamoDbClient<WOPRRecord> woprRecordDdbClient) {
        return ImmutableList.of(
                new PingCommand(),
                new SetWeaponCommand(woprRecordDdbClient),
                new GetWeaponByUserCommand(woprRecordDdbClient));
    }
}
