package com.mymusicmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    
    @Override
    public void init() {
        // Check for the resource pack before playing music
        if (isResourcePackLoaded()) {
            playLoadingMusic();
        } else {
            System.out.println("[LoadingMusicMod] No resource pack detected or missing files.");
        }
    }

    public void playLoadingMusic() {
        Minecraft mc = Minecraft.getMinecraft();

        // Define the exact locations in the resource pack
        ResourceLocation loadingMusic = new ResourceLocation("minecraft", "menu/LoadMusic1.ogg");
        ResourceLocation menuMusic = new ResourceLocation("minecraft", "sounds/music/menu/menu1.ogg");

        // Attempt to play music only if the resource exists
        if (doesResourceExist(loadingMusic)) {
            playMusic(loadingMusic);
        }

        mc.addScheduledTask(() -> {
            if (mc.currentScreen != null && doesResourceExist(menuMusic)) {
                playMusic(menuMusic);
            }
        });
    }

    private void playMusic(ResourceLocation sound) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getSoundHandler().playSound(PositionedSoundRecord.create(sound, 1.0F));
    }

    private boolean doesResourceExist(ResourceLocation resource) {
        try {
            Minecraft.getMinecraft().getResourceManager().getResource(resource);
            return true;  // The resource exists
        } catch (IOException e) {
            return false; // Resource not found
        }
    }

    private boolean isResourcePackLoaded() {
        // Check if at least one resource pack is active
        return !Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries().isEmpty();
    }
}
