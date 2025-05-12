package com.example.e_commerceapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JT\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\u0016\u0010\u0010\u001a\u0012\u0012\b\u0012\u00060\u0012j\u0002`\u0013\u0012\u0004\u0012\u00020\u00060\u0011J0\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0018\u0010\u0017\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u0018\u0012\u0004\u0012\u00020\u00060\u0011J \u0010\u001a\u001a\u00020\u00062\u0018\u0010\u0017\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u0018\u0012\u0004\u0012\u00020\u00060\u0011J8\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0018\u0010\u0017\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u0018\u0012\u0004\u0012\u00020\u00060\u0011J\u0018\u0010\u001f\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\b2\u0006\u0010 \u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/example/e_commerceapp/data/repository/ProductRepository;", "", "()V", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "addProductReview", "", "userId", "", "userName", "productId", "rating", "", "comment", "onSuccess", "Lkotlin/Function0;", "onFailure", "Lkotlin/Function1;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "getProductReviews", "limit", "", "onResult", "", "Lcom/example/e_commerceapp/data/model/Review;", "getProducts", "Lcom/example/e_commerceapp/data/model/Product;", "getRelatedProducts", "category", "excludeProductId", "updateProductRating", "newRating", "app_debug"})
public final class ProductRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    
    public ProductRepository() {
        super();
    }
    
    public final void getProducts(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.example.e_commerceapp.data.model.Product>, kotlin.Unit> onResult) {
    }
    
    public final void getRelatedProducts(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String excludeProductId, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.example.e_commerceapp.data.model.Product>, kotlin.Unit> onResult) {
    }
    
    public final void getProductReviews(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.example.e_commerceapp.data.model.Review>, kotlin.Unit> onResult) {
    }
    
    public final void addProductReview(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.NotNull()
    java.lang.String productId, float rating, @org.jetbrains.annotations.NotNull()
    java.lang.String comment, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> onFailure) {
    }
    
    private final void updateProductRating(java.lang.String productId, float newRating) {
    }
}