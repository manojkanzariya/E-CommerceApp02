package com.example.e_commerceapp.vendor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\rH\u0002J\u0018\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/e_commerceapp/vendor/VendorOrdersActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/e_commerceapp/vendor/adapter/VendorOrdersAdapter;", "binding", "Lcom/example/e_commerceapp/databinding/ActivityVendorOrdersBinding;", "ordersList", "", "Lcom/example/e_commerceapp/data/model/Order;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "loadVendorOrders", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupToolbar", "updateOrderStatus", "orderId", "", "newStatus", "Lcom/example/e_commerceapp/data/model/OrderStatus;", "app_debug"})
public final class VendorOrdersActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.e_commerceapp.data.model.Order> ordersList = null;
    private com.example.e_commerceapp.vendor.adapter.VendorOrdersAdapter adapter;
    private com.example.e_commerceapp.databinding.ActivityVendorOrdersBinding binding;
    
    public VendorOrdersActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void updateOrderStatus(java.lang.String orderId, com.example.e_commerceapp.data.model.OrderStatus newStatus) {
    }
    
    private final void loadVendorOrders() {
    }
}