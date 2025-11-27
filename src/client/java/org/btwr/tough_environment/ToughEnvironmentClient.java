package org.btwr.tough_environment;

import net.fabricmc.api.ClientModInitializer;
import org.btwr.tough_environment.event.ModClientEvents;
import org.btwr.tough_environment.render.BlockRenderLayerMappings;

public class ToughEnvironmentClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMappings.register();
        ModClientEvents.register();

        // Modifies a block model with another one to always look different
        /**
        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.modifyModelAfterBake().register(ModelModifier.OVERRIDE_PHASE, (bakedModel, context) -> {
                Identifier id = context.resourceId();
                if (id != null && id.toString().contains("stone")) {
                    return new Strata2StoneModel(bakedModel);
                }
                return bakedModel;
            });
        });
         **/
    }

}