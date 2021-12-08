package com.amirmohammed.hti2021androidone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amirmohammed.hti2021androidone.databinding.ActivityMainBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    TasksAdapter adapter;

    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Firebase Cloud Messaging => FCM
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String token) {
                        Log.i(TAG, "onSuccess: " + token);
                    }
                });

        getUserData();

        getUserTasks("active");

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int itemId = item.getItemId();

                if (itemId == R.id.item_tasks) {
                    getUserTasks("active");
                } else if (itemId == R.id.item_done) {
                    getUserTasks("done");
                } else if (itemId == R.id.item_archive) {
                    getUserTasks("archive");
                }
                return false;
            }
        });
    }

    private void getUserTasks(String status) {
//        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<Task> tasks = new ArrayList<>();
//
//                for (DocumentSnapshot snapshot : value.getDocuments()) {
//                    Task task = snapshot.toObject(Task.class);
//                    tasks.add(task);
//                }
//
//                adapter = new TasksAdapter(tasks, MainActivity.this, iTasks);
//                binding.rvTasks.setAdapter(adapter);
//            }
//        });

        firestore.collection("tasks")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("myTasks")
                .whereEqualTo("status", status)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot query) {
                        List<Task> tasks = new ArrayList<>();

                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            Task task = snapshot.toObject(Task.class);
                            tasks.add(task);
                        }

                        adapter = new TasksAdapter(tasks, MainActivity.this, iTasks);
                        binding.rvTasks.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> {
                    String errorMessage = e.getLocalizedMessage();
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });
    }

    ITasks iTasks = new ITasks() {
        @Override
        public void onDoneClick(Task task) {
            updateTaskOnFirestore("done", task.getId());
            adapter.taskList.remove(task);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onArchiveClick(Task task) {
            updateTaskOnFirestore("archive", task.getId());
            adapter.taskList.remove(task);
            adapter.notifyDataSetChanged();
        }
    };

    private void updateTaskOnFirestore(String status, String taskId) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);

        firestore.collection("tasks")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("myTasks")
                .document(taskId)
                .delete();
    }

    private void getUserData() {
        firestore.collection("HtiUsers")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = documentSnapshot.getString("name");
                        String phone = documentSnapshot.getString("phone");
                        String email = documentSnapshot.getString("email");

                        Log.i(TAG, "onSuccess: " + name);
                        Log.i(TAG, "onSuccess: " + phone);
                        Log.i(TAG, "onSuccess: " + email);

                        User user = documentSnapshot.toObject(User.class);
                        Log.i(TAG, "onSuccess: " + user.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = e.getLocalizedMessage();
                        Log.i(TAG, "onFailure: " + errorMessage);
                    }
                });

    }

    private void signOut() {
        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openInsertSheet(View view) {
        new InsertFragment().show(getSupportFragmentManager(), "dialog");
//        ImagePicker.with(this)
//                .crop()                    //Crop image(Optional), Check Customization for more option
//                .compress(1024)            //Final image size will be less than 1 MB(Optional)
//                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                .start();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            //Image Uri will not be null for RESULT_OK
//            Uri uri = data.getData();
//            // Use Uri object instead of File to avoid storage permissions
//            binding.imageView.setImageURI(uri);
//
//            uploadImageToFirebaseCloudStorage(uri);
//        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void uploadImageToFirebaseCloudStorage(Uri imageUri) {
        String imageName = firebaseAuth.getCurrentUser().getUid() + System.currentTimeMillis();
        storageReference.child("hti").child("2021").child(imageName).putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        getImageUrl(imageName);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = e.getLocalizedMessage();
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getImageUrl(String imageName) {
        storageReference.child("hti").child("2021").child(imageName).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i(TAG, "onSuccess: Image URL => " + uri.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = e.getLocalizedMessage();
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}