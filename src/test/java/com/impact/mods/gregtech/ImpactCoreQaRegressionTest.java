package com.impact.mods.gregtech;

import java.net.URLClassLoader;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.junit.jupiter.api.Test;

public class ImpactCoreQaRegressionTest {
    @Test
    void repairedControllersBuildWithValidHatches() throws Throwable {
        try (LaunchClassLoader loader = new LaunchClassLoader(
                ((URLClassLoader) getClass().getClassLoader()).getURLs())) {
            loader.addClassLoaderExclusion("org.junit.");
            loader.registerTransformer("cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer");
            try {
                loader.loadClass("com.impact.mods.gregtech.ImpactCoreQaRegressionChecks").getMethod("check")
                        .invoke(null);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
