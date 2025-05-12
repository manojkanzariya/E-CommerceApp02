package com.example.e_commerceapp.vendor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\b\u0010\u0010\u001a\u00020\rH\u0002J\u0012\u0010\u0011\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/e_commerceapp/vendor/InventoryActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/e_commerceapp/vendor/adapter/InventoryAdapter;", "binding", "Lcom/example/e_commerceapp/databinding/ActivityInventoryBinding;", "productList", "", "Lcom/example/e_commerceapp/data/model/Product;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "deleteProduct", "", "product", "editProduct", "fetchProducts", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupToolbar", "app_debug"})
public final class InventoryActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.e_commerceapp.data.model.Product> productList = null;
    private com.example.e_commerceapp.vendor.adapter.InventoryAdapter adapter;
    private com.example.e_commerceapp.databinding.ActivityInventoryBinding binding;
    
    public InventoryActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void fetchProducts() {
    }
    
    private final void deleteProduct(com.example.e_commerceapp.data.model.Product product) {
    }
    
    private final void editProduct(com.example.e_commerceapp.data.model.Product product) {
    }
}