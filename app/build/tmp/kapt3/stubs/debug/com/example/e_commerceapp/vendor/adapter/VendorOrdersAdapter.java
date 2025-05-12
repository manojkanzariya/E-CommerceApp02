package com.example.e_commerceapp.vendor.adapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001eB-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0007\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u000e\u001a\u00020\n2\n\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\rH\u0016J\u001c\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\rH\u0016J2\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0007H\u0002J \u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/e_commerceapp/vendor/adapter/VendorOrdersAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/e_commerceapp/vendor/adapter/VendorOrdersAdapter$OrderViewHolder;", "orders", "", "Lcom/example/e_commerceapp/data/model/Order;", "onStatusUpdate", "Lkotlin/Function2;", "", "Lcom/example/e_commerceapp/data/model/OrderStatus;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function2;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "showStatusUpdateDialog", "context", "Landroid/content/Context;", "order", "updateOrderStatus", "orderId", "newStatus", "statusTextView", "Landroid/widget/TextView;", "OrderViewHolder", "app_debug"})
public final class VendorOrdersAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.e_commerceapp.vendor.adapter.VendorOrdersAdapter.OrderViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.e_commerceapp.data.model.Order> orders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<java.lang.String, com.example.e_commerceapp.data.model.OrderStatus, kotlin.Unit> onStatusUpdate = null;
    
    public VendorOrdersAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.Order> orders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super com.example.e_commerceapp.data.model.OrderStatus, kotlin.Unit> onStatusUpdate) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.example.e_commerceapp.vendor.adapter.VendorOrdersAdapter.OrderViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.vendor.adapter.VendorOrdersAdapter.OrderViewHolder holder, int position) {
    }
    
    private final void updateOrderStatus(java.lang.String orderId, com.example.e_commerceapp.data.model.OrderStatus newStatus, android.widget.TextView statusTextView) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    private final void showStatusUpdateDialog(android.content.Context context, com.example.e_commerceapp.data.model.Order order, kotlin.jvm.functions.Function2<? super java.lang.String, ? super com.example.e_commerceapp.data.model.OrderStatus, kotlin.Unit> onStatusUpdate) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0015\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\u0017\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0011\u0010\u0019\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u0011\u0010\u001b\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0010R\u0011\u0010\u001d\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0010R\u0011\u0010\u001f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0010\u00a8\u0006!"}, d2 = {"Lcom/example/e_commerceapp/vendor/adapter/VendorOrdersAdapter$OrderViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/example/e_commerceapp/databinding/ItemVendorOrderBinding;", "(Lcom/example/e_commerceapp/vendor/adapter/VendorOrdersAdapter;Lcom/example/e_commerceapp/databinding/ItemVendorOrderBinding;)V", "btnUpdateStatus", "Landroid/widget/Button;", "getBtnUpdateStatus", "()Landroid/widget/Button;", "ivProductImage", "Landroid/widget/ImageView;", "getIvProductImage", "()Landroid/widget/ImageView;", "tvCustomerName", "Landroid/widget/TextView;", "getTvCustomerName", "()Landroid/widget/TextView;", "tvCustomerPhone", "getTvCustomerPhone", "tvOrderDate", "getTvOrderDate", "tvOrderId", "getTvOrderId", "tvOrderTotal", "getTvOrderTotal", "tvProductName", "getTvProductName", "tvProductPrice", "getTvProductPrice", "tvQuantity", "getTvQuantity", "tvStatus", "getTvStatus", "app_debug"})
    public final class OrderViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.example.e_commerceapp.databinding.ItemVendorOrderBinding binding = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvOrderId = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvOrderDate = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView ivProductImage = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvProductName = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvProductPrice = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvQuantity = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvCustomerName = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvCustomerPhone = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvStatus = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvOrderTotal = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.Button btnUpdateStatus = null;
        
        public OrderViewHolder(@org.jetbrains.annotations.NotNull()
        com.example.e_commerceapp.databinding.ItemVendorOrderBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvOrderId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvOrderDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getIvProductImage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvProductName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvProductPrice() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvQuantity() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvCustomerName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvCustomerPhone() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvStatus() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvOrderTotal() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.Button getBtnUpdateStatus() {
            return null;
        }
    }
}