package net.gameon365.mc.plugins.bungee.sessionskip;

import java.util.Collection;
import java.util.logging.Level;
//import java.util.HashSet;

import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;

import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.event.EventHandler;

public class SessionSkip extends ConfigurablePlugin implements Listener {
    protected Collection listeners;
    protected Collection hostnames;
    protected Collection remoteips;
    
    @Override
    public void onEnable()
    {
        this.listeners = this.getConfig().getList( "listeners" );
        this.hostnames = this.getConfig().getList( "hostnames" );
        this.remoteips = this.getConfig().getList( "remoteips" );
        
        this.getProxy().getPluginManager().registerListener( this, this );
    }
    
    @Override
    public void onDisable()
    {
        this.listeners = null;
        this.hostnames = null;
        this.remoteips = null;
    }
    
    @EventHandler
    public void onPlayerHandshakeEvent( PlayerHandshakeEvent e )
    {
        InitialHandler handler = (InitialHandler) e.getConnection();
        
        this.getProxy().getLogger().log( Level.INFO, "Connection via listener: {0}", handler.getVirtualHost().toString() );
        this.getProxy().getLogger().log( Level.INFO, "Connection via hostname: {0}", handler.getVirtualHost().getHostString() );
        this.getProxy().getLogger().log( Level.INFO, "Connection from remote IP: {0}", handler.getAddress().getAddress().getHostAddress() );
        
        
        // handler.getVirtualHost().getAddress().toString() + Integer.toString( handler.getVirtualHost().getPort() )
        if( this.listeners.contains( handler.getVirtualHost().toString() ) )
        {
            handler.setOnlineMode( false );
            this.getProxy().getLogger().log( Level.INFO, "Skipping session server authentication for player {0} ({1}) since listener matched {2}", new Object[]{ handler.getName(), handler.getAddress().toString(), handler.getVirtualHost().toString() } );
            return;
        }
        
        if( this.hostnames.contains( handler.getVirtualHost().getHostString() ) )
        {
            handler.setOnlineMode( false );
            this.getProxy().getLogger().log( Level.INFO, "Skipping session server authentication for player {0} ({1}) since hostname matched {2}", new Object[]{ handler.getName(), handler.getAddress().toString(), handler.getVirtualHost().getHostString() } );
            return;
        }
        
        if( this.remoteips.contains( handler.getAddress().getAddress().getHostAddress() ) )
        {
            handler.setOnlineMode( false );
            this.getProxy().getLogger().log( Level.INFO, "Skipping session server authentication for player {0} ({1}) since remote IP matched {2}", new Object[]{ handler.getName(), handler.getAddress().toString(), handler.getAddress().getAddress().getHostAddress() } );
            return;
        }
        
        this.getProxy().getLogger().log( Level.INFO, "Authenticating player {0} ({1}) since no skip rules matched.", new Object[]{ handler.getName(), handler.getAddress().toString() } );
    }
}
