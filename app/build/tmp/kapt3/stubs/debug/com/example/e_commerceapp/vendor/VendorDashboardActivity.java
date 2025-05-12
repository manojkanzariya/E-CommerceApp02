package com.example.e_commerceapp.vendor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u000fH\u0016J\u0012\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/e_commerceapp/vendor/VendorDashboardActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "tvOrdersCount", "Landroid/widget/TextView;", "tvRevenue", "loadDashboardData", "", "loadVendorHeaderData", "navView", "Lcom/google/android/material/navigation/NavigationView;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class VendorDashboardActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private android.widget.TextView tvOrdersCount;
    private android.widget.TextView tvRevenue;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.firebase.firestore.FirebaseFirestore db;
    private com.google.firebase.storage.FirebaseStorage storage;
    
    public VendorDashboardActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadVendorHeaderData(com.google.android.material.navigation.NavigationView navView) {
    }
    
    private final void loadDashboardData() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
}