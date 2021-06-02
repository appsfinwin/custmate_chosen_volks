package com.finwin.chosenvolks.custmate.home.reacharge;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.finwin.chosenvolks.custmate.R;
import com.finwin.chosenvolks.custmate.SupportingClass.ConstantClass;
import com.finwin.chosenvolks.custmate.SupportingClass.Enc_Utils;
import com.finwin.chosenvolks.custmate.SupportingClass.Enc_crypter;
import com.finwin.chosenvolks.custmate.databinding.FrgRechargeBinding;
import com.finwin.chosenvolks.custmate.home.reacharge.action.RechargeAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import online.devliving.passcodeview.PasscodeView;

public class RechargeFragment extends Fragment {

    final Enc_crypter encr = new Enc_crypter();
    Spinner spnrOperator, spnrCircle;
    EditText EDTmob_or_id, EDTamount;
    RequestQueue requestQueue;
    private LinearLayout linear1, linear2;
    ImageButton iBtn_back;
    Button BtnProceed;
    TextView textView_pre, textView_post, TVRecrgStatus;
    SweetAlertDialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    static String operType;
    private ProgressDialog pDialog;
    RechargeViewmodel viewmodel;
    FrgRechargeBinding binding;
    Dialog mpinDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.frg_recharge, container, false);
        sharedPreferences= getActivity().getSharedPreferences("com.finwin.chosenvolks.custmate",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        viewmodel = new ViewModelProvider(getActivity()).get(RechargeViewmodel.class);
        requestQueue = Volley.newRequestQueue(getContext());
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);


        EDTmob_or_id = binding.edtMobOrId;
        EDTamount = binding.edtAmount;

        linear1 = binding.linear1;
        linear2 = binding.linear2;
        textView_pre = binding.txtPre;
        textView_post = binding.txtPost;
        BtnProceed = binding.btnRcrgProcd;

        binding.setViewmodel(viewmodel);

        viewmodel.getCircle();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<String> lisType = Arrays.asList(ConstantClass.masterTypArrayID);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String rslt = bundle.getString(ConstantClass.mstrType, "MOB");
            switch (rslt) {
                case "MOB": //Prepaid
                    operType = lisType.get(0);
                    viewmodel.operType.set(operType);
                    binding.txtRecrgType.setText(R.string.mob_recharge);
                    binding.txtRecrgStatus.setVisibility(View.GONE);
                    binding.linrPrePost.setVisibility(View.VISIBLE);
                    viewmodel.getOperatorList(operType);
                    break;

                case "DATA":    //Data Recharge
                    operType = lisType.get(2);
                    viewmodel.operType.set(operType);
                    binding.txtRecrgType.setText(R.string.data_recharge);
                    binding.txtRecrgStatus.setVisibility(View.GONE);
                    binding.linrPrePost.setVisibility(View.VISIBLE);
                    viewmodel.getOperatorList(operType);
                    break;

                case "DTH": //DTH Recharge
                    operType = lisType.get(4);
                    viewmodel.operType.set(operType);
                    binding.txtRecrgType.setText(R.string.recharge);
                    binding.txtRecrgStatus.setVisibility(View.VISIBLE);
                    binding.txtRecrgStatus.setText(ConstantClass.masterTypArray[4]);
                    binding.linrPrePost.setVisibility(View.GONE);
                    viewmodel.getOperatorList(operType);
                    break;

                case "LAND":    //Landline
                    operType = lisType.get(3);
                    viewmodel.operType.set(operType);
                    binding.txtRecrgType.setText(R.string.recharge);
                    binding.txtRecrgStatus.setVisibility(View.VISIBLE);
                    binding.txtRecrgStatus.setText(R.string.lndbill_pymnt);
                    binding.linrPrePost.setVisibility(View.GONE);
                    viewmodel.getOperatorList(operType);
                    break;

                case "LAND_BROAD":  //Broadband
                    operType = lisType.get(3);
                    viewmodel.operType.set(operType);
                    binding.txtRecrgType.setText(R.string.recharge);
                    binding.txtRecrgStatus.setVisibility(View.VISIBLE);
                    binding.txtRecrgStatus.setText(R.string.Broadbill_pymnt);
                    binding.linrPrePost.setVisibility(View.GONE);
                    viewmodel.getOperatorList(operType);
                    break;
            }
        }

        viewmodel.getmAction().observe(getViewLifecycleOwner(), new Observer<RechargeAction>() {
            @Override
            public void onChanged(RechargeAction rechargeAction) {
                switch (rechargeAction.getAction()) {
                    case RechargeAction.GET_CIRCLE_SUCCESS:
                        viewmodel.setCircleData(rechargeAction.getCircleResponse.getData());
                        break;
                    case RechargeAction.GET_OPERATOR_SUCCESS:
                        viewmodel.setOperatorData(rechargeAction.getOperatorResponse.getData());
                        break;

                    case RechargeAction.CLICK_BACK:
                        getFragmentManager().popBackStack();
                        break;

                    case RechargeAction.VALIDATE_MPIN_SUCCESS:
                        if (rechargeAction.validateMpinResponse.getValue()) {
                            mpinDialog.dismiss();
                            viewmodel.recharge();
                        } else {
                            Toast.makeText(getActivity(), "Invalid MPIN", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case RechargeAction.VALIDATE_MPIN_ERROR:
                        mpinDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Invalid!")
                                .setContentText("Invalid MPIN")
                                .show();
                        break;

                    case RechargeAction.RECHARGE_SUCCESS:

                        Intent intent = new Intent(getActivity(), RechargeFragmentReceipt.class);
                        intent.putExtra("account_number", viewmodel.mobileOrId.get());
                        intent.putExtra("Rech_Amount", viewmodel.amount.get());
                        startActivity(intent);
                        break;

                    case RechargeAction.RECHARGE_ERROR:
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error!")
                                .setContentText(rechargeAction.getError())
                                .show();
                        break;
                    case RechargeAction.CLICK_PROCEED:

                        verifyMpin();
                        break;
                }
            }
        });

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operType = lisType.get(0);
                viewmodel.operType.set(operType);
                linear1.setBackgroundResource(R.drawable.top_leftcorner);
                textView_pre.setTextColor(Color.parseColor("#ffffff"));

                linear2.setBackgroundResource(R.drawable.top_rightcorner_white);
                textView_post.setTextColor(Color.parseColor("#837a7a"));
            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operType = lisType.get(1);
                viewmodel.operType.set(operType);
                linear2.setBackgroundResource(R.drawable.top_rightcorner);
                textView_post.setTextColor(Color.parseColor("#ffffff"));

                linear1.setBackgroundResource(R.drawable.top_leftcorner_white);
                textView_pre.setTextColor(Color.parseColor("#837a7a"));
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        viewmodel.getmAction().setValue(new RechargeAction(RechargeAction.DEFAULT));
    }


    public void verifyMpin() {
        mpinDialog = new Dialog(getActivity());
        mpinDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mpinDialog.setCancelable(true);
        mpinDialog.setTitle("Enter mPIN");
        mpinDialog.setContentView(R.layout.mpin_verification);

        PasscodeView passcodeView = mpinDialog.findViewById(R.id.passcode_view);
        if (passcodeView.requestFocus()) {
            mpinDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        passcodeView.setPasscodeEntryListener(new PasscodeView.PasscodeEntryListener() {
            @Override
            public void onPasscodeEntered(String passcode) {
                mpinDialog.dismiss();
                validateMPIN(passcode);
            }
        });
        mpinDialog.show();
    }

    public void validateMPIN(String mpin) {
        Map<String, String> params = new HashMap<>();
        Map<String, String> items = new HashMap<>();
        items.put("userid",
                sharedPreferences.getString(ConstantClass.CUST_ID,""));
        items.put("MPIN", mpin);

        params.put("data", encr.conRevString(Enc_Utils.enValues(items)));
        Log.e("mpin post", encr.conRevString(Enc_Utils.enValues(items)));

        viewmodel.validateMpin(params);
    }


}