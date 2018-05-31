package app.stucre;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class Intro extends AppCompatActivity {

    private Button signInButton;
    private Button signInMicrosoft;
    //TextView statusTextView;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private Button signEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
      /*  GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
*/
        signInButton = (Button) findViewById(R.id.Signinbtn);
        signInMicrosoft = (Button) findViewById(R.id.SigninOutlook);
        signEmail = (Button) findViewById(R.id.SigninEmail);

        signInMicrosoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro.this,MicrosoftLogin.class);
                startActivity(intent);
            }
        });



        signEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intro.this,Opleiding.class);
                startActivity(intent);

            }
        });



        //signInButton.setOnClickListener(this);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /*if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
*/

    }

    private void handleSignInResult(GoogleSignInResult result) {
       /* Log.d(TAG,"handleSignInREsult:" + result.isSuccess());

        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            // statusTextView.setText("Hello:" + acct.getDisplayName());
            Intent main_to_course = new Intent(Intro.this, Courses.class);
            startActivity(main_to_course);
        }else {

        }*/
    }



    /*
    ***********************Microsoft Methode*********************
     */


}
