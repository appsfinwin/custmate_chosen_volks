package com.finwin.chosenvolks.custmate.home.transfer.search_beneficiary;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.finwin.chosenvolks.custmate.R;
import com.finwin.chosenvolks.custmate.SupportingClass.Enc_crypter;
import com.finwin.chosenvolks.custmate.databinding.BeneficiarySrchActvtyBinding;
import com.finwin.chosenvolks.custmate.home.transfer.search_beneficiary.adapter.BeneficiaryAdapter;
import com.finwin.chosenvolks.custmate.home.transfer.search_beneficiary.adapter.SearchBeneficiaryRowAction;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.View.VISIBLE;

public class SearchBeneficiary extends AppCompatActivity {

    ImageButton search_cancel, btn_search;
    TextView tvwrng_msg;
    EditText Edt_acc_no;
    ImageButton ibtn_benf_refresh;
    BeneficiaryAdapter benAdapter;
    private ProgressDialog dialog;
    static Enc_crypter encr = new Enc_crypter();
    BeneficiarySrchActvtyBinding binding;
    SearchBeneficiaryViewmodel viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.beneficiary_srch_actvty);
        viewmodel=new ViewModelProvider(this).get(SearchBeneficiaryViewmodel.class);
        binding.setViewmodel(viewmodel);


        dialog = new ProgressDialog(SearchBeneficiary.this,R.style.AppCompatAlertDialogStyle);

        tvwrng_msg = findViewById(R.id.tv_wrng_msg);

        setRecyclerView(binding.rvBenList);

        search_cancel = findViewById(R.id.ibtn_benf_back);
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ibtn_benf_refresh = findViewById(R.id.ibtn_benf_refresh);
        ibtn_benf_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBeneficiary();
            }
        });

        getBeneficiary();

        viewmodel.getmAction().observe(this, new Observer<SearchBeneficairyAction>() {
            @Override
            public void onChanged(SearchBeneficairyAction searchBeneficairyAction) {

                dialog.dismiss();
                switch (searchBeneficairyAction.getAction())
                {
                    case SearchBeneficairyAction.BENEFICIARY_SUCCESS:
                        benAdapter.setBeneficiaryDataList(searchBeneficairyAction.getBeneficiaryListResponse().getData());
                        benAdapter.notifyDataSetChanged();
                        break;

                        case SearchBeneficairyAction.API_ERROR:
                            binding.rvBenList.setVisibility(View.INVISIBLE);
                                    tvwrng_msg.setVisibility(VISIBLE);
                                    tvwrng_msg.setText("Not found!");
                           
                                new SweetAlertDialog(SearchBeneficiary.this, SweetAlertDialog.ERROR_TYPE)
                                        .setContentText(searchBeneficairyAction.getError())
                                        .setTitleText("Error!")
                                        .show();
                                break;
                }
            }
        });

    }

    private void setRecyclerView(RecyclerView rvBenList) {
        benAdapter=new BeneficiaryAdapter();
        rvBenList.setLayoutManager(new LinearLayoutManager(this));
        rvBenList.setHasFixedSize(true);
        rvBenList.setAdapter(benAdapter);

        setObservable(benAdapter);

    }

    private void setObservable(BeneficiaryAdapter benAdapter) {
        benAdapter.getmAction().observe(this, new Observer<SearchBeneficiaryRowAction>() {
            @Override
            public void onChanged(SearchBeneficiaryRowAction searchBeneficiaryRowAction) {
                if (searchBeneficiaryRowAction.getAction()==SearchBeneficiaryRowAction.CLICK_BENEFICIARY)
                {

                    Intent resultIntent = new Intent();
                                            resultIntent.putExtra("result_ben_name", searchBeneficiaryRowAction.getBeneficiaryData().getReceiverName());
                                            resultIntent.putExtra("result_ben_account_no", searchBeneficiaryRowAction.getBeneficiaryData().getReceiverAccountno());
                                            setResult(RESULT_OK, resultIntent);
                                            finish();

                }
            }
        });
    }


    @SuppressLint("ResourceType")
    private void getBeneficiary() {
        dialog.setMessage("Loading, Please wait...");
        dialog.show();
        viewmodel.getBeneficiary("oa");
    }


}
