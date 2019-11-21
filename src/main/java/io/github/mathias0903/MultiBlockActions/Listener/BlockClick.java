package io.github.mathias0903.MultiBlockActions.Listener;

import io.github.mathias0903.MultiBlockActions.LockedMultiBlock;
import io.github.mathias0903.MultiBlockActions.Main;
import io.github.mathias0903.MultiBlockActions.Manager.MultiBlockManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BlockClick implements Listener {
    private MultiBlockManager multiBlockManager = Main.getInstance().getMultiBlockManager();

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getHand().equals(EquipmentSlot.HAND)) {
                Block clickedBlock = e.getClickedBlock();
                for(LockedMultiBlock lockedMultiBlock: multiBlockManager.getLockedMultiBlocks()) {
                    if(lockedMultiBlock.isKeyBlock(clickedBlock)) {
                        if(lockedMultiBlock.isLockedMultiBlock(clickedBlock)) {
                            lockedMultiBlock.doAction(e.getPlayer());
                            return;
                        }
                    }
                }
            }
        }
    }
}
