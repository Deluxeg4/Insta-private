package instadevelopment.kiriyaga.api.core.feature;

import instadevelopment.kiriyaga.api.model.feature.Feature;

import static instadevelopment.kiriyaga.api.InstaApi.*;


public class FeatureService {

    private final FeatureStorage storage = new FeatureStorage();

    public void init() {
        FeatureRegistry.registerAnnotatedFeatures(storage);
        LOGGER.info("Registered " + storage.size() + " Features.");
    }

    public FeatureStorage getStorage() {
        return storage;
    }

    public void registerFeature(Feature Feature) {
        storage.add(Feature);
    }

    public void unregisterFeature(Feature Feature) {
        storage.remove(Feature);
    }
}
