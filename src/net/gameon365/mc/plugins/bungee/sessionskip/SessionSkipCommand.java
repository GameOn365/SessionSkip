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
        this.plugin = plugin;
    }
    
    protected void sendMessage( Object p, String m )
    {
        if( p instanceof ProxiedPlayer )
        {
            ProxiedPlayer pl = (ProxiedPlayer) p;
            pl.sendMessage( m );
        }
        else
        {
            this.plugin.getLogger().info( m );
        }
    }
    
    public void execute( CommandSender sender, String[] args )
    {
        String name;
        ProxiedPlayer p;
        
        try
        {
            p = (ProxiedPlayer) sender;
            name = p.getDisplayName();
        }
        catch( ClassCastException e )
        {
            name = "*CONSOLE*";
            p = null;
        }
        
        try{
            switch( args[0] )
            {
                case "reload":
                    this.plugin.onDisable();
                    this.plugin.reloadConfig();
                    this.plugin.onEnable();
                    this.sendMessage( p, "[SessionSkip] Plugin reloaded." );
                    this.plugin.getLogger().log( Level.INFO, "[SessionSkip] Plugin reloaded by {0}.", name );
                    break;
                case "disable":
                    this.plugin.enabled = false;
                    this.sendMessage( p, "[SessionSkip] Plugin disabled.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart." );
                    this.plugin.getLogger().log( Level.INFO, "[SessionSkip] Plugin disabled by {0}.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart.", name );
                    break;
                case "enable":
                    this.plugin.enabled = true;
                    this.sendMessage( p, "[SessionSkip] Plugin enabled.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart." );
                    this.plugin.getLogger().log( Level.INFO, "[SessionSkip] Plugin enabled by {0}.  WARNING: The plugin will use the value in the config file to determine whether it is enabled or disabled after the next BungeeCord restart.", name );
                    break;
                default:
                    this.sendMessage( p, "[SessionSkip] Whatever you just tried to do, well, uh, that command doesn't exist." );
                    break;
            }
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
                this.sendMessage( p, "[SessionSkip] Commands:" );
                this.sendMessage( p, "[SessionSkip] /sessionskip reload - Reload the plugin and configuration file." );
                this.sendMessage( p, "[SessionSkip] /sessionskip enable - Enable skipping authentication for rules defined in the configuration." );
                this.sendMessage( p, "[SessionSkip] /sessionskip disable - Disables skipping authentication." );
        }
    }
}
