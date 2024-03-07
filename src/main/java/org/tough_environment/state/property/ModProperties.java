package org.tough_environment.state.property;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {

    public static final IntProperty BREAK_LEVEL = IntProperty.of("break_level", 0, 9);

    public static final BooleanProperty HAS_MORTAR = BooleanProperty.of("has_mortar");


}
