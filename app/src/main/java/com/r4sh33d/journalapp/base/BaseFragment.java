package com.r4sh33d.journalapp.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.r4sh33d.journalapp.R;

public abstract class BaseFragment  extends Fragment implements  BaseContract.view {

    public void setToolbarTitle (String title){
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setTitle(title);
    }

    public ProgressDialog progressDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
    }

    public AlertDialog showError(CharSequence message) {
        if (getContext() == null){
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public AlertDialog showSuccessDialog(String message, DialogInterface.OnClickListener
            positiveClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("Ok", positiveClickListener);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    @Override
    public void showLoading(String message){
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void dismissLoading(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String message){
        if (getContext() != null){
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

   public void navigateToFragmentWithBackStack(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

   public void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        dismissLoading();
        progressDialog = null;
        super.onDestroyView();
    }

}
