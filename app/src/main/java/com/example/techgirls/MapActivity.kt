package com.example.techgirls;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapFragment

public class MapActivity : AppCompatActivity() {
    private lateinit var tomtomMap:TomTomMap
    override fun onCreate(savedInstanceState:Bundle ?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        val mapOptions = MapOptions(mapKey = "@strings/API_KEY_TOMTOM");
        val mapFragment = MapFragment.newInstance(mapOptions);
        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

       /* mapFragment.getMapAsync{map->
            tomtomMap=map
            enableUserLocation()
            setUpMapListener()
        }*/

    }

    private fun setUpMapListener() {
        TODO("Not yet implemented")
    }

    private fun enableUserLocation() {
        TODO("Not yet implemented")
    }
}
