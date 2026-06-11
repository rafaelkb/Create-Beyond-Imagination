package net.darkygaia.beyondimagination.registry;

import net.darkygaia.beyondimagination.BeyondImagination;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, BeyondImagination.MODID);

    public static final Supplier<SoundEvent> REACTOR_AMBIENT = registerSoundEvent("reactor_ambient");
    public static final Supplier<SoundEvent> TURBINE_SPIN = registerSoundEvent("turbine_spin");
    public static final Supplier<SoundEvent> CENTRIFUGE_WHIR = registerSoundEvent("centrifuge_whir");
    public static final Supplier<SoundEvent> IRRADIATION_HUM = registerSoundEvent("irradiation_hum");
    public static final Supplier<SoundEvent> CONDENSER_STEAM = registerSoundEvent("condenser_steam");
    public static final Supplier<SoundEvent> CONTROLLER_CLICK = registerSoundEvent("controller_click");
    public static final Supplier<SoundEvent> MELTDOWN_ALARM = registerSoundEvent("meltdown_alarm");
    public static final Supplier<SoundEvent> GEIGER_CLICK = registerSoundEvent("geiger_click");
    public static final Supplier<SoundEvent> SIREN_SMALL = registerSoundEvent("siren_small");
    public static final Supplier<SoundEvent> SIREN_MEDIUM = registerSoundEvent("siren_medium");
    public static final Supplier<SoundEvent> SIREN_LARGE = registerSoundEvent("siren_large");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BeyondImagination.MODID, name)));
    }

    public static void register() {
        // Will be called by ModRegistry to load the class
    }
}
