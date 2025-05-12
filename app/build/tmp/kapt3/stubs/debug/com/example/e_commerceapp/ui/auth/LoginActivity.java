package com.example.e_commerceapp.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0010H\u0002J\"\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\u0012\u0010\u0018\u001a\u00020\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0018\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0010H\u0002J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0010H\u0002J\b\u0010 \u001a\u00020\u000eH\u0002J\u0010\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u0010H\u0002J\u0010\u0010#\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010\'\u001a\u00020\u0010H\u0002J\b\u0010(\u001a\u00020\u000eH\u0002J\u001e\u0010)\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u00102\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000e0+H\u0002J\b\u0010,\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/example/e_commerceapp/ui/auth/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "RC_SIGN_IN", "", "authManager", "Lcom/example/e_commerceapp/data/repository/FirebaseAuthManager;", "binding", "Lcom/example/e_commerceapp/databinding/ActivityLoginBinding;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "checkUserRolesAndNavigate", "", "userId", "", "firebaseAuthWithGoogle", "idToken", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "performLogin", "email", "password", "setSessionAndNavigate", "role", "setupClickListeners", "showError", "message", "showLoading", "show", "", "showRoleChooser", "currentRole", "signInWithGoogle", "updateCurrentRole", "onComplete", "Lkotlin/Function0;", "validateAndLogin", "app_debug"})
@kotlin.Suppress(names = {"DEPRECATION"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.e_commerceapp.databinding.ActivityLoginBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final com.example.e_commerceapp.data.repository.FirebaseAuthManager authManager = null;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient;
    private final int RC_SIGN_IN = 9001;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void signInWithGoogle() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void firebaseAuthWithGoogle(java.lang.String idToken) {
    }
    
    private final void validateAndLogin() {
    }
    
    private final void performLogin(java.lang.String email, java.lang.String password) {
    }
    
    private final void checkUserRolesAndNavigate(java.lang.String userId) {
    }
    
    private final void showRoleChooser(java.lang.String currentRole) {
    }
    
    private final void updateCurrentRole(java.lang.String role, kotlin.jvm.functions.Function0<kotlin.Unit> onComplete) {
    }
    
    private final void setSessionAndNavigate(java.lang.String role) {
    }
    
    private final void showLoading(boolean show) {
    }
    
    private final void showError(java.lang.String message) {
    }
}