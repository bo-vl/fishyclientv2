package utils.misc;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class NBTUtil {
    public static NBTTagCompound getTagCompound(ItemStack item, String path) {
        if (item != null && item.hasTagCompound()) {
            NBTTagCompound currentCompound = item.getTagCompound();
            if (currentCompound == null) return null;

            if (path != null) {
                String[] pathSegments = path.split("/");
                for (String segment : pathSegments) {
                    if (currentCompound.hasKey(segment)) {
                        currentCompound = currentCompound.getCompoundTag(segment);
                    } else {
                        return null;
                    }
                }
            }

            return currentCompound;
        }

        return null;
    }

    public static String getStringFromNBT(ItemStack item, String path) {
        NBTTagCompound compound = getTagCompound(item, path);
        if (compound != null) {
            return compound.getString(path);
        }
        return null;
    }

    public static boolean isCustomSkull(ItemStack item, String textureBase64) {
        if (item != null && item.getItem() == Items.skull && item.hasTagCompound()) {
            NBTTagCompound skullOwner = getTagCompound(item, "SkullOwner");
            if (skullOwner != null && skullOwner.hasKey("Properties")) {
                NBTTagList textures = skullOwner.getCompoundTag("Properties").getTagList("textures", 10);
                if (textures.tagCount() > 0) {
                    String texture = textures.getCompoundTagAt(0).getString("Value");
                    return texture.equals(textureBase64);
                }
            }
        }
        return false;
    }

    public static boolean hasSkyblockID(ItemStack item, String targetId) {
        NBTTagCompound extraAttributes = getTagCompound(item, "ExtraAttributes");
        if (extraAttributes != null) {
            String id = extraAttributes.getString("id");
            return id != null && id.equals(targetId);
        }
        return false;
    }

    public static String getSkullTexture(ItemStack item) {
        if (item != null && item.getItem() == Items.skull && item.hasTagCompound()) {
            NBTTagCompound skullOwner = getTagCompound(item, "SkullOwner");
            if (skullOwner != null && skullOwner.hasKey("Properties")) {
                NBTTagList textures = skullOwner.getCompoundTag("Properties").getTagList("textures", 10);
                if (textures.tagCount() > 0) {
                    return textures.getCompoundTagAt(0).getString("Value");
                }
            }
        }
        return null;
    }
}
