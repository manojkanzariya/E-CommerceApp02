package com.example.e_commerceapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\bH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J.\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000bJ\u000e\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\bR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/e_commerceapp/data/repository/UserRepository;", "", "()V", "savedAddress", "Lcom/example/e_commerceapp/data/model/Address;", "sharedPreferences", "Landroid/content/SharedPreferences;", "user", "Lcom/example/e_commerceapp/data/model/User;", "fetchUserFromFirebase", "uid", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAddress", "getUser", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasAddress", "", "init", "", "context", "Landroid/content/Context;", "loadSavedAddress", "loadUser", "saveAddress", "name", "phone", "address", "city", "pincode", "setUser", "newUser", "app_debug"})
public final class UserRepository {
    @org.jetbrains.annotations.Nullable()
    private static com.example.e_commerceapp.data.model.User user;
    @org.jetbrains.annotations.Nullable()
    private static android.content.SharedPreferences sharedPreferences;
    @org.jetbrains.annotations.Nullable()
    private static com.example.e_commerceapp.data.model.Address savedAddress;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.e_commerceapp.data.repository.UserRepository INSTANCE = null;
    
    private UserRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchUserFromFirebase(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.e_commerceapp.data.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.e_commerceapp.data.model.User> $completion) {
        return null;
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void saveAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String city, @org.jetbrains.annotations.NotNull()
    java.lang.String pincode) {
    }
    
    public final boolean hasAddress() {
        return false;
    }
    
    private final void loadSavedAddress() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.e_commerceapp.data.model.Address getAddress() {
        return null;
    }
    
    private final void loadUser() {
    }
    
    public final void setUser(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.User newUser) {
    }
}