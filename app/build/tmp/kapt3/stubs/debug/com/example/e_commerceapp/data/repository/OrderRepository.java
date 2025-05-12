package com.example.e_commerceapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0018\u0010\u0005\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0004\u0012\u00020\u00040\u0006J|\u0010\t\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u000f\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00040\u00122\u0016\u0010\u0017\u001a\u0012\u0012\b\u0012\u00060\u0018j\u0002`\u0019\u0012\u0004\u0012\u00020\u00040\u0006\u00a8\u0006\u001a"}, d2 = {"Lcom/example/e_commerceapp/data/repository/OrderRepository;", "", "()V", "fetchOrders", "", "onResult", "Lkotlin/Function1;", "", "Lcom/example/e_commerceapp/data/model/Order;", "placeOrder", "cartItems", "Lcom/example/e_commerceapp/data/model/Product;", "totalAmount", "", "paymentMethod", "", "vendorId", "onSuccess", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "orderId", "confirmationDetails", "onFailure", "Ljava/lang/Exception;", "Lkotlin/Exception;", "app_debug"})
public final class OrderRepository {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.e_commerceapp.data.repository.OrderRepository INSTANCE = null;
    
    private OrderRepository() {
        super();
    }
    
    public final void placeOrder(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.Product> cartItems, double totalAmount, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.NotNull()
    java.lang.String vendorId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super com.example.e_commerceapp.data.model.Order, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> onFailure) {
    }
    
    public final void fetchOrders(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.example.e_commerceapp.data.model.Order>, kotlin.Unit> onResult) {
    }
}