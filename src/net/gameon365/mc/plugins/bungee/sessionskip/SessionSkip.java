package net.gameon365.mc.plugins.bungee.sessionskip;

import java.util.Collection;
import java.util.logging.Level;

import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;

import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;

public class SessionSkip extends ConfigurablePlugin implements Listener
{
    protected boolean debug;
    protected boolean enabled;
    protected Collection listeners;
    protected Collection hostnames;
    protected Collection remoteips;
    
    @Override
    public void onEnable()
    {
        this.debug = this.getConfig().getBoolean( "debug", true );
        this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Debug output set to {0}.", this.debug);
        
        this.enabled = this.getConfig().getBoolean( "enabled", true );
        this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Plugin state is set to {0}.", this.enabled);
        
        this.listeners = this.getConfig().getList( "listeners" );
        this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Loaded {0} listener rules.", this.listeners.size());
        
        this.hostnames = this.getConfig().getList( "hostnames" );
        this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Loaded {0} hostname rules.", this.hostnames.size());
        
        this.remoteips = this.getConfig().getList( "remoteips" );
        this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Loaded {0} remote IP rules.", this.remoteips.size());
        
        this.getProxy().getPluginManager().registerListener( this, this );
        
        this.getProxy().getPluginManager().registerCommand( this, new SessionSkipCommand( this ) );
    }
    
    @Override
    public void onDisable()
    {
        this.debug = true;
        this.enabled = false;
        this.listeners = null;
        this.hostnames = null;
        this.remoteips = null;
        
        this.getProxy().getPluginManager().unregisterListeners( this );
        
        this.getProxy().getPluginManager().unregisterCommands( this );
    }
    
    @EventHandler
    public void onPlayerHandshakeEvent( PlayerHandshakeEvent e )
    {
        InitialHandler handler = (InitialHandler) e.getConnection();
        
        if( !this.enabled )
        {
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Authenticating player {0} ({1}) since SessionSkip is not enabled in the config.", new Object[]{ handler.getName(), handler.getAddress().toString() } );
        }
        
        if( this.debug )
        {
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Connection via listener: {0}", handler.getVirtualHost().toString() );
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Connection via hostname: {0}", handler.getVirtualHost().getHostString() );
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Connection from remote IP: {0}", handler.getAddress().getAddress().getHostAddress() );
        }
        
        if( this.listeners.contains( handler.getVirtualHost().toString() ) )
        {
            handler.setOnlineMode( false );
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Skipping session server authentication for player {0} ({1}) since listener matched {2}", new Object[]{ handler.getName(), handler.getAddress().toString(), handler.getVirtualHost().toString() } );
            return;
        }
        
        if( this.hostnames.contains( handler.getVirtualHost().getHostString() ) )
        {
            handler.setOnlineMode( false );
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Skipping session server authentication for player {0} ({1}) since hostname matched {2}", new Object[]{ handler.getName(), handler.getAddress().toString(), handler.getVirtualHost().getHostString() } );
            return;
        }
        
        if( this.remoteips.contains( handler.getAddress().getAddress().getHostAddress() ) )
        {
            handler.setOnlineMode( false );
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Skipping session server authentication for player {0} ({1}) since remote IP matched {2}", new Object[]{ handler.getName(), handler.getAddress().toString(), handler.getAddress().getAddress().getHostAddress() } );
            return;
        }
        
        if( this.debug )
        {
            this.getProxy().getLogger().log( Level.INFO, "[SessionSkip] Authenticating player {0} ({1}) since no skip rules matched.", new Object[]{ handler.getName(), handler.getAddress().toString() } );
        }
    }
}
