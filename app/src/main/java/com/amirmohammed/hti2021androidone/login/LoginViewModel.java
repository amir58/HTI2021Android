package com.amirmohammed.hti2021androidone.login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.amirmohammed.hti2021androidone.maps.MapHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private final MutableLiveData<String> loginSuccessMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> loginFailureMutableLiveData = new MutableLiveData<>();

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginSuccessMutableLiveData.postValue(task.getResult().getUser().getUid());
                        } else {
                            loginFailureMutableLiveData.setValue(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    public LiveData<String> isLoginSuccess(){
        return loginSuccessMutableLiveData;
    }

    public LiveData<String> isLoginFailure(){
        return loginFailureMutableLiveData;
    }

}
