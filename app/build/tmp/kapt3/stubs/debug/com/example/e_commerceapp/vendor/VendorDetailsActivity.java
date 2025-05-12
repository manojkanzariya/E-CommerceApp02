package com.example.e_commerceapp.vendor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\b\u0010\u0015\u001a\u00020\u0013H\u0002J\"\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0012\u0010\u001b\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0013H\u0002JJ\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00020\f2\b\u0010&\u001a\u0004\u0018\u00010\f2\u0006\u0010\'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020\u0013H\u0002J\u0012\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020\u0010H\u0002J8\u0010-\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/example/e_commerceapp/vendor/VendorDetailsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "PICK_IMAGE_REQUEST", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/example/e_commerceapp/databinding/ActivityVendorDetailsBinding;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "email", "", "isNewRegistration", "", "selectedImageUri", "Landroid/net/Uri;", "uid", "completeRegistration", "", "loadExistingVendorData", "navigateToDashboard", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openImagePicker", "registerVendor", "storeName", "storeDescription", "gstNumber", "address", "city", "pincode", "imageUrl", "progressDialog", "Landroid/app/ProgressDialog;", "setupListeners", "uriToFile", "Ljava/io/File;", "uri", "validateInputs", "app_debug"})
public final class VendorDetailsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.e_commerceapp.databinding.ActivityVendorDetailsBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.firebase.firestore.FirebaseFirestore db;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri selectedImageUri;
    private final int PICK_IMAGE_REQUEST = 1;
    private java.lang.String uid;
    private java.lang.String email;
    private boolean isNewRegistration = false;
    
    public VendorDetailsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadExistingVendorData() {
    }
    
    private final void setupListeners() {
    }
    
    private final void openImagePicker() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void completeRegistration() {
    }
    
    private final void registerVendor(java.lang.String storeName, java.lang.String storeDescription, java.lang.String gstNumber, java.lang.String address, java.lang.String city, java.lang.String pincode, java.lang.String imageUrl, android.app.ProgressDialog progressDialog) {
    }
    
    private final java.io.File uriToFile(android.net.Uri uri) {
        return null;
    }
    
    private final void navigateToDashboard() {
    }
    
    private final boolean validateInputs(java.lang.String storeName, java.lang.String storeDescription, java.lang.String gstNumber, java.lang.String address, java.lang.String city, java.lang.String pincode) {
        return false;
    }
}