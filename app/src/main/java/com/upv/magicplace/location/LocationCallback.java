package com.upv.magicplace.location;

import android.location.Location;

/**
 * Created by Kelvin on 30/04/2017.
 */

public interface LocationCallback {

    void onLocationChanged(Location location);
}
