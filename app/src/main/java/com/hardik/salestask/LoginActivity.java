package com.hardik.salestask;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hardik.salestask.core.BaseActivity;
import com.hardik.salestask.models.User;
import com.hardik.salestask.network.NetworkController;
import com.hardik.salestask.network.NetworkControllerRetro;
import com.hardik.salestask.network.interfaces.LoginService;
import com.hardik.salestask.network.interfaces.OnCompleteListener;
import com.hardik.salestask.network.model.WeData;
import com.hardik.salestask.network.service.RequestManager;
import com.hardik.salestask.utils.ActivityUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener {


    private static final String TAG = LoginActivity.class.getSimpleName();
    //================================
    //VIEWS
    //================================
    @Email(sequence = 2)
    @BindView(R.id.edt_email)
    MaterialEditText edtEmail;

    @NotEmpty(sequence = 3)
    @BindView(R.id.edt_password)
    MaterialEditText edtPassword;

    //================================
    //OBJECTS
    //================================

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public void onLoginClicked(View view) {
        validator.validate();
    }

    public void onLogin() {

        User user = new User();
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtPassword.getText().toString());

        showProgressDialog("Logging In");

        LoginService loginApi = NetworkControllerRetro.getInstance().getRetrofit().create(LoginService.class);
        Call<User> call = loginApi.login(edtEmail.getText().toString(), edtPassword.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    Log.d(TAG, "EMAil = " + response.raw().body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(LoginActivity.this, "Call success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);

                finish();

                dimissProgressDialog();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(LoginActivity.this, "Call FAILED", Toast.LENGTH_SHORT).show();
                dimissProgressDialog();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onValidationSucceeded() {
        onLogin();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_SHORT).show();
            }
            break;
        }

    }
}
