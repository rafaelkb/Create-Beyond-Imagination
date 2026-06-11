package net.darkygaia.beyondimagination.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.darkygaia.beyondimagination.BeyondImagination;
import net.darkygaia.beyondimagination.radiation.RadiationManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = BeyondImagination.MODID)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("radiation")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("get")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            float rad = RadiationManager.getRadiation(player);
                            context.getSource().sendSuccess(() -> Component.literal("§a[CBI]§f Nível de radiação atual: §e" + rad + " mRad"), false);
                            return 1;
                        }))
                .then(Commands.literal("set")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    RadiationManager.setRadiation(player, amount);
                                    context.getSource().sendSuccess(() -> Component.literal("§a[CBI]§f Nível de radiação definido para: §e" + amount + " mRad"), false);
                                    return 1;
                                })))
        );
    }
}
