package com.example.e_commerceapp.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u0010\u001a\u00020\fH\u0002J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\nH\u0002J\b\u0010\u0014\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/e_commerceapp/ui/auth/ForgotPasswordActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authManager", "Lcom/example/e_commerceapp/data/repository/FirebaseAuthManager;", "binding", "Lcom/example/e_commerceapp/databinding/ActivityForgotPasswordBinding;", "isValidEmail", "", "email", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "sendResetEmail", "setupClickListeners", "showErrorMessage", "message", "showSuccessMessage", "validateAndSubmit", "app_debug"})
public final class ForgotPasswordActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.e_commerceapp.databinding.ActivityForgotPasswordBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final com.example.e_commerceapp.data.repository.FirebaseAuthManager authManager = null;
    
    public ForgotPasswordActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void validateAndSubmit() {
    }
    
    private final void sendResetEmail(java.lang.String email) {
    }
    
    private final boolean isValidEmail(java.lang.String email) {
        return false;
    }
    
    private final void showSuccessMessage(java.lang.String message) {
    }
    
    private final void showErrorMessage(java.lang.String message) {
    }
}