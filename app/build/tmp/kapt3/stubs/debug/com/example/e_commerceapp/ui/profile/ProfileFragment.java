package com.example.e_commerceapp.ui.profile;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0019H\u0002J\b\u0010\u001f\u001a\u00020\u0019H\u0002J\b\u0010 \u001a\u00020\u0019H\u0002J\b\u0010!\u001a\u00020\u0019H\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u0019H\u0002J\b\u0010&\u001a\u00020\u0019H\u0002J\b\u0010\'\u001a\u00020\u0019H\u0002J$\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u00100\u001a\u00020\u0019H\u0016J\b\u00101\u001a\u00020\u0019H\u0016J\u001a\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020)2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u00104\u001a\u00020\u0019H\u0002J\u0010\u00105\u001a\u00020\u00192\u0006\u00106\u001a\u000207H\u0002J\b\u00108\u001a\u00020\u0019H\u0002J\u0010\u00109\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010:\u001a\u00020\u0019H\u0002J\u0016\u0010;\u001a\u00020\u00192\f\u0010<\u001a\b\u0012\u0004\u0012\u00020$0=H\u0002J\u0010\u0010>\u001a\u00020\u00192\u0006\u0010?\u001a\u00020@H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006A"}, d2 = {"Lcom/example/e_commerceapp/ui/profile/ProfileFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/example/e_commerceapp/databinding/FragmentProfileBinding;", "binding", "getBinding", "()Lcom/example/e_commerceapp/databinding/FragmentProfileBinding;", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "hasVendorRole", "", "navigationView", "Lcom/google/android/material/navigation/NavigationView;", "orderHistoryAdapter", "Lcom/example/e_commerceapp/ui/adapter/OrderHistoryAdapter;", "viewModel", "Lcom/example/e_commerceapp/ui/profile/ProfileViewModel;", "getViewModel", "()Lcom/example/e_commerceapp/ui/profile/ProfileViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "checkUserRoles", "", "confirmLogout", "handleVendorNavigation", "navOptionsWithBackStack", "Landroidx/navigation/NavOptions;", "navigateToAddress", "navigateToChangePassword", "navigateToEditProfile", "navigateToForgotPassword", "navigateToOrderDetails", "order", "Lcom/example/e_commerceapp/data/model/Order;", "navigateToVendorRegistration", "navigateToWishlist", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onResume", "onViewCreated", "view", "performLogout", "setTheme", "themeMode", "", "setupClickListeners", "setupDrawer", "setupRecyclerView", "updateOrderHistoryUI", "orders", "", "updateUserUI", "user", "Lcom/example/e_commerceapp/data/model/User;", "app_debug"})
public final class ProfileFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.example.e_commerceapp.databinding.FragmentProfileBinding _binding;
    private com.example.e_commerceapp.ui.adapter.OrderHistoryAdapter orderHistoryAdapter;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private com.google.android.material.navigation.NavigationView navigationView;
    private com.google.firebase.firestore.FirebaseFirestore firestore;
    private boolean hasVendorRole = false;
    
    public ProfileFragment() {
        super();
    }
    
    private final com.example.e_commerceapp.databinding.FragmentProfileBinding getBinding() {
        return null;
    }
    
    private final com.example.e_commerceapp.ui.profile.ProfileViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void checkUserRoles() {
    }
    
    private final void setupDrawer(boolean hasVendorRole) {
    }
    
    private final void handleVendorNavigation(boolean hasVendorRole) {
    }
    
    private final void navigateToVendorRegistration() {
    }
    
    private final void setTheme(int themeMode) {
    }
    
    private final void navigateToWishlist() {
    }
    
    private final void navigateToChangePassword() {
    }
    
    private final void navigateToForgotPassword() {
    }
    
    private final void navigateToAddress() {
    }
    
    private final androidx.navigation.NavOptions navOptionsWithBackStack() {
        return null;
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateUserUI(com.example.e_commerceapp.data.model.User user) {
    }
    
    private final void updateOrderHistoryUI(java.util.List<com.example.e_commerceapp.data.model.Order> orders) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void navigateToOrderDetails(com.example.e_commerceapp.data.model.Order order) {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void navigateToEditProfile() {
    }
    
    private final void confirmLogout() {
    }
    
    private final void performLogout() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}