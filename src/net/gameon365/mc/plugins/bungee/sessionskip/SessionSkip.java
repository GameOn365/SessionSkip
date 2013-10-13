package net.gameon365.mc.plugins.bungee.globaljoins;

import java.util.Collection;
import java.util.HashSet;

import net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class GlobalJoins extends ConfigurablePlugin implements Listener {
    protected String loginString;
    protected String logoutString;
    protected Collection<String> playersConnected;
    
    @Override
    public void onEnable()
    {
        this.loginString = this.getConfig().getString( "strings.login", "&e%s joined the network." );
        this.logoutString = this.getConfig().getString( "strings.logout", "&e%s left the network." );
        
        this.playersConnected = new HashSet<>();
        
        this.getProxy().getPluginManager().registerListener( this, this );
    }
    
    @Override
    public void onDisable()
    {
        for( String p : this.playersConnected )
        {
            this.doLogout( p );
        }
        
        this.loginString = null;
        this.logoutString = null;
        this.playersConnected = null;
    }
    
    @EventHandler
    public void onServerConnectedEvent( ServerConnectedEvent e )
    {
        this.doLogin( e.getPlayer().getName() );
    }
    
    @EventHandler
    public void onServerKickEvent( ServerKickEvent e )
    {
        this.doLogout( e.getPlayer().getName() );
    }
    
    @EventHandler
    public void onPlayerDisconnectEvent( PlayerDisconnectEvent e )
    {
        this.doLogout( e.getPlayer().getName() );
    }
    
    public void doLogin( String p )
    {
        if( ! this.playersConnected.contains( p ) )
        {
            this.getProxy().broadcast( ChatColor.translateAlternateColorCodes( '&', String.format( this.loginString, p ) ) );
            this.playersConnected.add( p );
        }
    }
    
    public void doLogout( String p )
    {
        if( this.playersConnected.contains( p ) )
        {
            this.getProxy().broadcast( ChatColor.translateAlternateColorCodes( '&', String.format( this.logoutString, p ) ) );
            this.playersConnected.remove( p );
        }
    }
}
