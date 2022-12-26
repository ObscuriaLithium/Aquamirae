
package com.obscuria.aquamirae.registry;

import com.obscuria.obscureapi.registry.ObscureAPIEnchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.List;

public class AquamiraeCreativeTab {

	public static List<ItemStack> poisonedChakra() {
		final List<ItemStack> list = new ArrayList<>();
		final ItemStack stack1 = AquamiraeItems.POISONED_CHAKRA.get().getDefaultInstance();
		final ItemStack stack2 = AquamiraeItems.POISONED_CHAKRA.get().getDefaultInstance();
		stack1.enchant(ObscureAPIEnchantments.DISTANCE.get(), ObscureAPIEnchantments.DISTANCE.get().getMaxLevel());
		stack1.enchant(ObscureAPIEnchantments.FAST_SPIN.get(), ObscureAPIEnchantments.FAST_SPIN.get().getMaxLevel());
		stack2.enchant(ObscureAPIEnchantments.MIRROR.get(), ObscureAPIEnchantments.MIRROR.get().getMaxLevel());
		list.add(stack1); list.add(stack2);
		return list;
	}

	public static List<ItemStack> mazeRose() {
		final List<ItemStack> list = new ArrayList<>();
		final ItemStack stack1 = AquamiraeItems.MAZE_ROSE.get().getDefaultInstance();
		final ItemStack stack2 = AquamiraeItems.MAZE_ROSE.get().getDefaultInstance();
		stack1.enchant(ObscureAPIEnchantments.DISTANCE.get(), ObscureAPIEnchantments.DISTANCE.get().getMaxLevel());
		stack1.enchant(ObscureAPIEnchantments.FAST_SPIN.get(), ObscureAPIEnchantments.FAST_SPIN.get().getMaxLevel());
		stack2.enchant(ObscureAPIEnchantments.MIRROR.get(), ObscureAPIEnchantments.MIRROR.get().getMaxLevel());
		list.add(stack1); list.add(stack2);
		return list;
	}

	public static List<ItemStack> logBooks() {
		final List<ItemStack> list = new ArrayList<>();
		final ItemStack book1 = Items.WRITTEN_BOOK.getDefaultInstance();
		final ItemStack book2 = Items.WRITTEN_BOOK.getDefaultInstance();
		final ItemStack book3 = Items.WRITTEN_BOOK.getDefaultInstance();
		final ListNBT pages1 = new ListNBT();
		final ListNBT pages2 = new ListNBT();
		final ListNBT pages3 = new ListNBT();

		pages1.add(StringNBT.valueOf("{\"text\":\"Entry #12\n\nLast afternoon, Captain Cornelia went to the seabed in search of any additional resources. There are rumors among the crew that the real subject of her expedition is something else.\"}"));
		pages1.add(StringNBT.valueOf("{\"text\":\"More than a day has passed since the captain's dive. She was wearing the only diving suit we had, so we couldn't help her in any way. Worst of all, she had the master key with her. As our hopes of rescue dwindle, the threat of rebellion grows.\"}"));
		book1.getOrCreateTag().putString("title", "Pillagers Ship Logbook");
		book1.getOrCreateTag().putString("author", "Unknown");
		book1.getOrCreateTag().putInt("generation", 3);
		book1.getOrCreateTag().putBoolean("resolved", true);
		book1.getOrCreateTag().put("pages", pages1);

		pages2.add(StringNBT.valueOf("{\"text\":\"Entry #34\n\nThese beasts from the depths prowl for someone to devour... One of those critters has already eaten our midshipman... Swallowed him whole along with his crossbow.\"}"));
		pages2.add(StringNBT.valueOf("{\"text\":\"Entry #35\n\nWe are waiting in vain for rescue... No one will come to help us, we have to survive on our own. Today the construction of the fort from parts of the ship was completed. In any case, she could no longer plow the seas ever again.\"}"));
		pages2.add(StringNBT.valueOf("{\"extra\":[{\"text\":\"Entry #36\n\nWe are no longer able to heat the fort, resources are running out. \"},{\"color\":\"dark_green\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"extra\":[{\"color\":\"green\",\"text\":\"Underground shelter\"},{\"text\":\"\n  There are more structures\n  underneath the ice... Looks\n  like I have to go down to\n  these terrible fish...\"}],\"text\":\"\"}},\"text\":\"Part of the crew began to dig a shelter underground\"},{\"text\":\". It is the only way we have a chance not to freeze to death.\"}],\"text\":\"\"}"));
		pages2.add(StringNBT.valueOf("{\"text\":\"Entry #37\n\nWe hid our supplies and valuable cargo underground. But Poseidon cursed us, and during the storm the lieutenant washed away into the sea... Along with master keys.\"}"));
		pages2.add(StringNBT.valueOf("{\"extra\":[{\"text\":\"The hope remains that we could possibly find \"},{\"color\":\"dark_green\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"extra\":[{\"color\":\"green\",\"text\":\"Cargo keys\"},{\"text\":\"\n  Without the keys, I will not\n  be able to receive the\n  most valuable cargo. I need\n  to find other ships nearby.\"}],\"text\":\"\"}},\"text\":\"similar keys on other ships of our fleet\"},{\"text\":\", which have suffered the same fate as us.\"}],\"text\":\"\"}"));
		book2.getOrCreateTag().putString("title", "Pillagers Outpost Logbook");
		book2.getOrCreateTag().putString("author", "Unknown");
		book2.getOrCreateTag().putInt("generation", 3);
		book2.getOrCreateTag().putBoolean("resolved", true);
		book2.getOrCreateTag().put("pages", pages2);

		pages3.add(StringNBT.valueOf("{\"text\":\"Entry #41\n\nIn these cursed lands there you can't find shelter even under the ice and the thickness of the earth... Some creatures that look like eels crawled out of the depths of the underworld, depriving us of the opportunity \"}"));
		pages3.add(StringNBT.valueOf("{\"text\":\"to return back to the surface.\n\nOnly the mistress of the moon knows how long shall we last... At all events, our hopes of seeing sunlight had all but vanished. This place will become our grave, our eternal tomb. For us and that damned rune...\"}"));
		pages3.add(StringNBT.valueOf("{\"text\":\"Last Entry\n\nIt whispers to me, whispers inside my head the secrets of ice and snow... Ice... Now we are chained in ice forever...\n\nI suppose a similar fate awaits the crew that set out in search of the fire rune... I wonder if they made it\"}"));
		pages3.add(StringNBT.valueOf("{\"text\":\"to the Great Dark Forest valley. Though it doesn't really matter now...\"}"));
		book3.getOrCreateTag().putString("title", "Pillagers Shelter Logbook");
		book3.getOrCreateTag().putString("author", "Unknown");
		book3.getOrCreateTag().putInt("generation", 3);
		book3.getOrCreateTag().putBoolean("resolved", true);
		book3.getOrCreateTag().put("pages", pages3);

		list.add(book1); list.add(book2); list.add(book3);
		return list;
	}
}
