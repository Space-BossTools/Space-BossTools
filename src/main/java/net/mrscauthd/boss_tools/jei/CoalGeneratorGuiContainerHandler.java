package net.mrscauthd.boss_tools.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gui.GeneratorGUIGuiWindow;
import net.mrscauthd.boss_tools.jei.JeiPlugin.GeneratorJeiCategory;

public class CoalGeneratorGuiContainerHandler implements IGuiContainerHandler<GeneratorGUIGuiWindow> {

	public CoalGeneratorGuiContainerHandler() {

	}

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(GeneratorGUIGuiWindow containerScreen, double mouseX, double mouseY) {
		return Collections.singleton(new IGuiClickableArea() {
			@Override
			public Rectangle2d getArea() {
				return new Rectangle2d(78, 52, 13, 13);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
				recipesGui.showCategories(Arrays.asList(GeneratorJeiCategory.Uid));
			}

			@Override
			public List<ITextComponent> getTooltipStrings() {
				List<ITextComponent> list = new ArrayList<>();
				list.add(new StringTextComponent("Burn Time: " + containerScreen.getTileEntity().getPowerSystem().getStored()));
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

}