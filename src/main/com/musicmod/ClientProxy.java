package com.musicmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    
    @Override
    public void init() {
        // Play the loading music when the mod initializes
        playLoadingMusic();
    }

    public void playLoadingMusic() {
        Minecraft mc = Minecraft.getMinecraft();

        // Define the exact locations in the resource pack
        ResourceLocation loadingMusic = new ResourceLocation("minecraft", "menu/LoadMusic1.ogg");
        ResourceLocation menuMusic = new ResourceLocation("minecraft", "sounds/music/menu/menu1.ogg");

        // Play loading music (if the player is still in the loading screen)
        playMusic(loadingMusic);

        // Schedule menu music to play when the main menu is reached
        mc.addScheduledTask(() -> {
            if (mc.currentScreen != null) {
                playMusic(menuMusic);
            }
        });
    }

    private void playMusic(ResourceLocation sound) {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.getResourceManager().getResourceDomains().contains("minecraft")) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(sound, 1.0F));
        }
    }
}