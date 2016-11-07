
package com.rayzr522.funpaintball.config;

public interface ISerializable {

    /**
     * Called when the variable is loaded from serialized data. Good for setting
     * values based on loaded data.
     */
    public void onDeserialize();

    /**
     * Called right before serialization. Good for setting values that need to
     * be serialized.
     */
    public void onPreSerialize();

}
