package com.example.m5_lesson8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.m5_lesson8.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject lateinit var localNotificationManager: LocalNotificationManager
    private  val points = ArrayList<LatLng>()
    private var  poling : Polygon? = null
    private var polin : Polyline? = null
    private  val options = PolylineOptions()
    private  val polygon = PolygonOptions()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setListeners()
    }
    private fun setListeners() {
        binding.polylineBtn.setOnClickListener {
            drawHolyLine()
        }
        binding.polygonBtn.setOnClickListener {
            drawPolygon()
        }
        binding.notific.setOnClickListener{
            localNotificationManager.createNotification()
        }
        binding.clearBtn.setOnClickListener {
            points.clear()
            poling?.remove()
            polin?.remove()
            mMap.clear()
        }}
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener {  latlg->    mMap.addMarker(MarkerOptions().position(latlg))
            val updateFactory = CameraUpdateFactory.newLatLngZoom(latlg,10f)
            mMap.moveCamera(updateFactory)
            points.add(latlg)}
    }

    private fun drawHolyLine(){
        if (polin == null){
        options.addAll(points)
            polin?.width = 20f
        options.color(
            ContextCompat.getColor(this, R.color.purple_500))
            polin = mMap.addPolyline(options)
        }
    }

    private fun drawPolygon(){
        if (poling == null){
            polygon.addAll(points)
            poling = mMap.addPolygon(polygon)
            poling?.fillColor = ContextCompat.getColor(this,R.color.red)
            poling?.strokeColor = ContextCompat.getColor(this,R.color.red)
        }
    }
}