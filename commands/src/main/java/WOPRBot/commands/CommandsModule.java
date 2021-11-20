package WOPRBot.commands;

import WOPRBot.commands.ping.PingCommand;
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
    public List<IWOPRBotCommand> provideCommands() {
        return ImmutableList.of(new PingCommand());
    }
}
