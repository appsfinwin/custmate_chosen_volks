package com.finwin.chosenvolks.custmate.sign_up.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.finwin.chosenvolks.custmate.BuildConfig;
import com.finwin.chosenvolks.custmate.R;
import com.finwin.chosenvolks.custmate.SupportingClass.Enc_crypter;
import com.finwin.chosenvolks.custmate.databinding.ActivitySignupMainBinding;
import com.finwin.chosenvolks.custmate.sign_up.otp.SignupOtpActivity;
import com.finwin.chosenvolks.custmate.sign_up.sign_up.action.SignupAction;

public class SignUpActivity extends AppCompatActivity {

    ImageButton iBtn_back;
    SignUpViewmodel viewmodel;
    ActivitySignupMainBinding binding;
    final Enc_crypter encr = new Enc_crypter();
    String apiKeyStored;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_signup_main);
        viewmodel=new ViewModelProvider(this).get(SignUpViewmodel.class);
        binding.setViewmodel(viewmodel);

        viewmodel.getmAction().observe(this, new Observer<SignupAction>() {
            @Override
            public void onChanged(SignupAction signupAction) {
                switch (signupAction.getAction())
                {
                    case SignupAction.ACCOUNT_HOLDER_EXIST:
//                        ConstantClass.const_accountNumber = signupAction.getAccountHolderResponse.getAccount().getData().getAccountNumber();
//                        ConstantClass.const_name = signupAction.getAccountHolderResponse.getAccount().getData().getName();
//                        ConstantClass.const_phone = signupAction.getAccountHolderResponse.getAccount().getData().getMobile();
                        //viewmodel.getApiKey();

                        viewmodel.getApiKey();
                        break;

                        case SignupAction.ACCOUNT_HOLDER_NOT_EXIST:

                            Toast.makeText(SignUpActivity.this, "Account does not exists", Toast.LENGTH_SHORT).show();
                            break;

                            case SignupAction.API_KEY_SUCCESS:
                                apiKeyStored = encr.revDecString(encr.decrypter(BuildConfig.AP_KE));
                                if (apiKeyStored.equals(signupAction.getError()))
                                {
                                    Intent intent=new Intent(SignUpActivity.this, SignupOtpActivity.class);
                                    startActivity(intent);
                                }
                                break;

                                case SignupAction.CLICK_SIGN_IN:
                                    finish();
                                    break;

                }
            }
        });


    }







}
