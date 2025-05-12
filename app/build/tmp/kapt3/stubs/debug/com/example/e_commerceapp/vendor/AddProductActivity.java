package com.example.e_commerceapp.vendor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001bH\u0002J\"\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\u0012\u0010#\u001a\u00020\u00142\b\u0010$\u001a\u0004\u0018\u00010%H\u0014J\b\u0010&\u001a\u00020\u0019H\u0016J\b\u0010\'\u001a\u00020\u0014H\u0002J\u0010\u0010(\u001a\u00020\u00142\u0006\u0010)\u001a\u00020\u001bH\u0002J\u0012\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020\u0006H\u0002J\b\u0010-\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/example/e_commerceapp/vendor/AddProductActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "PICK_IMAGE_REQUEST", "", "imageUri", "Landroid/net/Uri;", "ivProductImage", "Landroid/widget/ImageView;", "switchInStock", "Landroid/widget/Switch;", "tilCategory", "Lcom/google/android/material/textfield/TextInputLayout;", "tilFullDesc", "tilName", "tilOriginalPrice", "tilPrice", "tilShortDesc", "tilStock", "checkVendorPaymentSetup", "", "onSuccess", "Lkotlin/Function0;", "initViews", "isValidIfsc", "", "ifscCode", "", "isValidUpiId", "upiId", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "openImagePicker", "showToast", "msg", "uriToFile", "Ljava/io/File;", "uri", "validateAndUploadProduct", "app_debug"})
public final class AddProductActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.ImageView ivProductImage;
    private com.google.android.material.textfield.TextInputLayout tilName;
    private com.google.android.material.textfield.TextInputLayout tilShortDesc;
    private com.google.android.material.textfield.TextInputLayout tilFullDesc;
    private com.google.android.material.textfield.TextInputLayout tilPrice;
    private com.google.android.material.textfield.TextInputLayout tilOriginalPrice;
    private com.google.android.material.textfield.TextInputLayout tilCategory;
    private com.google.android.material.textfield.TextInputLayout tilStock;
    private android.widget.Switch switchInStock;
    @org.jetbrains.annotations.Nullable()
    private android.net.Uri imageUri;
    private final int PICK_IMAGE_REQUEST = 1;
    
    public AddProductActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    private final void initViews() {
    }
    
    private final void openImagePicker() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void checkVendorPaymentSetup(kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    private final boolean isValidUpiId(java.lang.String upiId) {
        return false;
    }
    
    private final boolean isValidIfsc(java.lang.String ifscCode) {
        return false;
    }
    
    private final void validateAndUploadProduct() {
    }
    
    private final java.io.File uriToFile(android.net.Uri uri) {
        return null;
    }
    
    private final void showToast(java.lang.String msg) {
    }
}