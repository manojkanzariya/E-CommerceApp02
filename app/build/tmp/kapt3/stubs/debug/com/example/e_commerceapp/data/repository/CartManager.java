package com.example.e_commerceapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0006\u0010\u0012\u001a\u00020\u000eJ\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0015J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\u001b\u001a\u00020\u000eH\u0002J\u000e\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\bJ\b\u0010\u001e\u001a\u00020\u000eH\u0002J\u0016\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010 \u001a\u00020!R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/example/e_commerceapp/data/repository/CartManager;", "", "()V", "CART_KEY", "", "CART_PREFS", "cartItems", "", "Lcom/example/e_commerceapp/data/model/CartItem;", "gson", "Lcom/google/gson/Gson;", "sharedPreferences", "Landroid/content/SharedPreferences;", "addToCart", "", "product", "Lcom/example/e_commerceapp/data/model/Product;", "(Lcom/example/e_commerceapp/data/model/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearCart", "fetchMissingVendorId", "getCartItems", "", "getCartSubtotal", "", "init", "context", "Landroid/content/Context;", "loadCart", "removeFromCart", "cartItem", "saveCart", "updateQuantity", "newQuantity", "", "app_debug"})
public final class CartManager {
    private static android.content.SharedPreferences sharedPreferences;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CART_PREFS = "cart_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CART_KEY = "cart_items";
    @org.jetbrains.annotations.NotNull()
    private static final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.example.e_commerceapp.data.model.CartItem> cartItems = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.e_commerceapp.data.repository.CartManager INSTANCE = null;
    
    private CartManager() {
        super();
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToCart(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void removeFromCart(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.CartItem cartItem) {
    }
    
    public final void updateQuantity(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.CartItem cartItem, int newQuantity) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.e_commerceapp.data.model.CartItem> getCartItems() {
        return null;
    }
    
    public final double getCartSubtotal() {
        return 0.0;
    }
    
    public final void clearCart() {
    }
    
    private final void saveCart() {
    }
    
    private final void loadCart() {
    }
    
    private final void fetchMissingVendorId(com.example.e_commerceapp.data.model.Product product) {
    }
}