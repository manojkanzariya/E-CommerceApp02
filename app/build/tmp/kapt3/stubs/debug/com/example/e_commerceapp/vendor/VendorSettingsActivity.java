package com.example.e_commerceapp.vendor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0002J\u0012\u0010\u0015\u001a\u00020\u000e2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u000eH\u0014J\b\u0010\u0019\u001a\u00020\u000eH\u0002J\b\u0010\u001a\u001a\u00020\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u000eH\u0002J\b\u0010\u001c\u001a\u00020\u000eH\u0002J\b\u0010\u001d\u001a\u00020\u000eH\u0002J\b\u0010\u001e\u001a\u00020\u000eH\u0002J\b\u0010\u001f\u001a\u00020\u000eH\u0002J\b\u0010 \u001a\u00020\u000eH\u0002J\b\u0010!\u001a\u00020\u000eH\u0002J\b\u0010\"\u001a\u00020\u000eH\u0002J\b\u0010#\u001a\u00020\u000eH\u0002J\b\u0010$\u001a\u00020\u000eH\u0002J\u0010\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u0013H\u0002J\b\u0010\'\u001a\u00020\u000eH\u0002J\b\u0010(\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/example/e_commerceapp/vendor/VendorSettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/example/e_commerceapp/databinding/ActivityVendorSettingsBinding;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "hasCustomerRole", "", "prefs", "Lcom/example/e_commerceapp/utils/PreferenceHelper;", "checkUserRoles", "", "confirmAccountDeletion", "confirmLogout", "deleteAccount", "password", "", "loadUserData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "openChangePassword", "openEmailPreferences", "openPaymentMethods", "openPaymentMethods1", "openProfileEditor", "openShippingSettings", "openStoreEditor", "performLogout", "setupClickListeners", "setupToolbar", "showEmailPreferenceDialog", "showFinalConfirmation", "showToast", "message", "switchToCustomerMode", "updateCurrentRoleAndNavigate", "app_debug"})
public final class VendorSettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.e_commerceapp.databinding.ActivityVendorSettingsBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.example.e_commerceapp.utils.PreferenceHelper prefs;
    private com.google.firebase.firestore.FirebaseFirestore db;
    private boolean hasCustomerRole = false;
    
    public VendorSettingsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void checkUserRoles() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void loadUserData() {
    }
    
    private final void switchToCustomerMode() {
    }
    
    private final void updateCurrentRoleAndNavigate() {
    }
    
    private final void openProfileEditor() {
    }
    
    private final void openChangePassword() {
    }
    
    private final void openEmailPreferences() {
    }
    
    private final void openStoreEditor() {
    }
    
    private final void openShippingSettings() {
    }
    
    private final void openPaymentMethods1() {
    }
    
    private final void openPaymentMethods() {
    }
    
    private final void showEmailPreferenceDialog() {
    }
    
    private final void confirmLogout() {
    }
    
    private final void performLogout() {
    }
    
    private final void confirmAccountDeletion() {
    }
    
    private final void showFinalConfirmation() {
    }
    
    private final void deleteAccount(java.lang.String password) {
    }
    
    private final void showToast(java.lang.String message) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
}