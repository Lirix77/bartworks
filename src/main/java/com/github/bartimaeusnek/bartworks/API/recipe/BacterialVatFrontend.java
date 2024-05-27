package com.github.bartimaeusnek.bartworks.API.recipe;

import java.util.Arrays;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.github.bartimaeusnek.bartworks.common.tileentities.multis.GT_TileEntity_BioVat;
import com.gtnewhorizons.modularui.api.math.Alignment;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.GT_NEI_DefaultHandler;
import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BacterialVatFrontend extends RecipeMapFrontend {

    public BacterialVatFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
            NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(
                uiPropertiesBuilder,
                neiPropertiesBuilder.neiSpecialInfoFormatter(new BacterialVatSpecialValueFormatter()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected List<String> handleNEIItemInputTooltip(List<String> currentTip,
            GT_NEI_DefaultHandler.FixedPositionedStack pStack) {
        if (pStack.isFluid()) {
            currentTip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("nei.biovat.input.tooltip"));
            return currentTip;
        }
        return super.handleNEIItemInputTooltip(currentTip, pStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected List<String> handleNEIItemOutputTooltip(List<String> currentTip,
            GT_NEI_DefaultHandler.FixedPositionedStack pStack) {
        if (pStack.isFluid()) {
            currentTip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("nei.biovat.output.tooltip"));
            return currentTip;
        }
        return super.handleNEIItemOutputTooltip(currentTip, pStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void drawNEIOverlayForInput(GT_NEI_DefaultHandler.FixedPositionedStack stack) {
        drawFluidOverlay(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void drawNEIOverlayForOutput(GT_NEI_DefaultHandler.FixedPositionedStack stack) {
        drawFluidOverlay(stack);
    }

    @SideOnly(Side.CLIENT)
    private void drawFluidOverlay(GT_NEI_DefaultHandler.FixedPositionedStack stack) {
        if (stack.isFluid()) {
            drawNEIOverlayText(
                    "+",
                    stack,
                    colorOverride.getTextColorOrDefault("nei_overlay_yellow", 0xFDD835),
                    0.5f,
                    true,
                    Alignment.TopRight);
            return;
        }
        super.drawNEIOverlayForOutput(stack);
    }

    private static class BacterialVatSpecialValueFormatter implements INEISpecialInfoFormatter {

        @Override
        public List<String> format(RecipeDisplayInfo recipeInfo) {
            int[] tSpecialA = GT_TileEntity_BioVat.specialValueUnpack(recipeInfo.recipe.mSpecialValue);
            String glassTier = StatCollector.translateToLocalFormatted("nei.biovat.0.name", tSpecialA[0]);
            String sievert;
            if (tSpecialA[2] == 1) {
                sievert = StatCollector.translateToLocalFormatted("nei.biovat.1.name", tSpecialA[3]);
            } else {
                sievert = StatCollector.translateToLocalFormatted("nei.biovat.2.name", tSpecialA[3]);
            }
            return Arrays.asList(glassTier, sievert);
        }
    }
}
