package org.tough_environment.config;

public class TESettings
{
        public boolean hcMaterialDurability = true;
        public boolean hcMaterialSpeed = true;
        public boolean hcHandBreakingSpeed = true;



        public boolean isHardcoreMaterialDurabilityEnabled() {
                return hcMaterialDurability;
        }
        public boolean isHardcoreMaterialSpeedEnabled() {
                return hcMaterialSpeed;
        }

        public boolean isHardcoreHandBreakingSpeedEnabled() {
                return hcHandBreakingSpeed;
        }

}
