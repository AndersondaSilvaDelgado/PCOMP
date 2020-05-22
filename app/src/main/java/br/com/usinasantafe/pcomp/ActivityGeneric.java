package br.com.usinasantafe.pcomp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.ArrayList;
import br.com.usinasantafe.pcomp.model.pst.DatabaseHelper;

/**
 * Created by anderson on 01/10/2015.
 */
public class ActivityGeneric extends OrmLiteBaseActivity<DatabaseHelper> implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public EditText editTextPadrao;

    private Location location;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 60000, FASTEST_INTERVAL = 60000; // = 5 seconds
    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getHelper();

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }

                break;
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if((EditText) findViewById(R.id.editTextPadrao) != null){
            editTextPadrao = (EditText) findViewById(R.id.editTextPadrao);
            editTextPadrao.setText("");
        }

        if((Button) findViewById(R.id.buttonNum0) != null){
            Button buttonNum0 = (Button) findViewById(R.id.buttonNum0);
            buttonNum0.setOnClickListener(new EventoBotao(0));
        }

        if((Button) findViewById(R.id.buttonNum1) != null){
            Button buttonNum1 = (Button) findViewById(R.id.buttonNum1);
            buttonNum1.setOnClickListener(new EventoBotao(1));
        }

        if((Button) findViewById(R.id.buttonNum2) != null){
            Button buttonNum2 = (Button) findViewById(R.id.buttonNum2);
            buttonNum2.setOnClickListener(new EventoBotao(2));
        }

        if((Button) findViewById(R.id.buttonNum3) != null){
            Button buttonNum3 = (Button) findViewById(R.id.buttonNum3);
            buttonNum3.setOnClickListener(new EventoBotao(3));
        }

        if((Button) findViewById(R.id.buttonNum4) != null){
            Button buttonNum4 = (Button) findViewById(R.id.buttonNum4);
            buttonNum4.setOnClickListener(new EventoBotao(4));
        }

        if((Button) findViewById(R.id.buttonNum5) != null){
            Button buttonNum5 = (Button) findViewById(R.id.buttonNum5);
            buttonNum5.setOnClickListener(new EventoBotao(5));
        }

        if((Button) findViewById(R.id.buttonNum6) != null){
            Button buttonNum6 = (Button) findViewById(R.id.buttonNum6);
            buttonNum6.setOnClickListener(new EventoBotao(6));
        }

        if((Button) findViewById(R.id.buttonNum7) != null){
            Button buttonNum7 = (Button) findViewById(R.id.buttonNum7);
            buttonNum7.setOnClickListener(new EventoBotao(7));
        }

        if((Button) findViewById(R.id.buttonNum8) != null){
            Button buttonNum8 = (Button) findViewById(R.id.buttonNum8);
            buttonNum8.setOnClickListener(new EventoBotao(8));
        }

        if((Button) findViewById(R.id.buttonNum9) != null){
            Button buttonNum9 = (Button) findViewById(R.id.buttonNum9);
            buttonNum9.setOnClickListener(new EventoBotao(9));
        }

    }


    private class EventoBotao implements View.OnClickListener{

        private int numBotao;

        public EventoBotao(int numBotao) {
            this.numBotao = numBotao;
        }

        @Override
        public void onClick(View v) {
            editTextPadrao.setText(editTextPadrao.getText() + "" + numBotao);
        }

    }

    public Double getLatitude(){
        if(location != null){
            return location.getLatitude();
        }
        else{
            return 0D;
        }
    }

    public Double getLongitude(){
        if(location != null){
            return location.getLongitude();
        }
        else {
            return 0D;
        }
    }

}
