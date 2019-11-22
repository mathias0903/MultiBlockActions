package io.github.mathias0903.MultiBlockActions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {
    public boolean onCommand(CommandSender sender, String[] args);
}
