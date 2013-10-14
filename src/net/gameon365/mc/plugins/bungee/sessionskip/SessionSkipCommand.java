package net.gameon365.mc.plugins.bungee.sessionskip;

import java.util.logging.Level;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SessionSkipCommand extends Command
{
    protected SessionSkip plugin;
    
    public SessionSkipCommand( SessionSkip plugin )
    {
        super( "sessionskip", "sessionskip.admin" );
    }
    
    public void execute( CommandSender sender, String[] args )
    {
        ProxiedPlayer p = (ProxiedPlayer) sender;
        switch( args[0] )
        {
            case "reload":
                this.plugin.onDisable();
                this.plugin.onEnable();
                p.sendMessage( "[SessionSkip] Plugin reloaded." );
                this.plugin.getLogger().log( Level.INFO, "[SessionSkip] Plugin reloaded by {0}.", p.getDisplayName());
                break;
            case "disable":
                this.plugin.enabled = false;
                p.sendMessage( "[SessionSkip] Plugin disabled.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart." );
                this.plugin.getLogger().log( Level.INFO, "[SessionSkip] Plugin disabled by {0}.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart.", p.getDisplayName());
                break;
            case "enable":
                this.plugin.enabled = true;
                p.sendMessage( "[SessionSkip] Plugin enabled.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart." );
                this.plugin.getLogger().log( Level.INFO, "[SessionSkip] Plugin enabled by {0}.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart.", p.getDisplayName());
                break;
            default:
                p.sendMessages( "[SessionSkip] Commands:",
                                "[SessionSkip] /sessionskip reload - Reload the plugin and configuration file.",
                                "[SessionSkip] /sessionskip enable - Enable skipping authentication for rules defined in the configuration.",
                                "[SessionSkip] /sessionskip disable - Disables skipping authentication." );
                break;
        }
    }
}
