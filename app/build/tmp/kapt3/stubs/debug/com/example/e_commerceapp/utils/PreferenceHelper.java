package com.example.e_commerceapp.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u001c\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006J\r\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006J\u0006\u0010\u0016\u001a\u00020\u0010J\r\u0010\u0017\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014J\u0006\u0010\u0018\u001a\u00020\u0010J\b\u0010\u0019\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001b\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0006J\u0016\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0010J\u000e\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u0013J\u0016\u0010\"\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0010J\u000e\u0010#\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u0006J\u000e\u0010%\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0010J\u000e\u0010&\u001a\u00020\u000e2\u0006\u0010\'\u001a\u00020\u0013J\u000e\u0010(\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0010J\u000e\u0010)\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020\u0006J\u000e\u0010+\u001a\u00020\u000e2\u0006\u0010,\u001a\u00020\u0006J\u000e\u0010-\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/example/e_commerceapp/utils/PreferenceHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "PREF_PHONE_NUMBER", "", "masterKey", "Landroidx/security/crypto/MasterKey;", "preferences", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "sharedPreferences", "clearPreferences", "", "getEmailPreference", "", "key", "getFreeShippingThreshold", "", "()Ljava/lang/Double;", "getPhoneNumber", "getShippingDeliveryOption", "getShippingFlatRate", "getShippingPickupOption", "getStoreContact", "getStoreDescription", "getStoreName", "isPaymentMethodEnabled", "method", "setEmailPreference", "enabled", "setFreeShippingThreshold", "threshold", "setPaymentMethodEnabled", "setPhoneNumber", "phone", "setShippingDeliveryOption", "setShippingFlatRate", "rate", "setShippingPickupOption", "setStoreContact", "contact", "setStoreDescription", "desc", "setStoreName", "name", "app_debug"})
public final class PreferenceHelper {
    private final android.content.SharedPreferences preferences = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String PREF_PHONE_NUMBER = "phone_number";
    @org.jetbrains.annotations.NotNull()
    private final androidx.security.crypto.MasterKey masterKey = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    
    public PreferenceHelper(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void setStoreName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStoreName() {
        return null;
    }
    
    public final void setEmailPreference(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean enabled) {
    }
    
    public final boolean getEmailPreference(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return false;
    }
    
    public final void setPaymentMethodEnabled(@org.jetbrains.annotations.NotNull()
    java.lang.String method, boolean enabled) {
    }
    
    public final boolean isPaymentMethodEnabled(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
        return false;
    }
    
    public final void clearPreferences() {
    }
    
    public final void setPhoneNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhoneNumber() {
        return null;
    }
    
    public final void setStoreDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String desc) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStoreDescription() {
        return null;
    }
    
    public final void setStoreContact(@org.jetbrains.annotations.NotNull()
    java.lang.String contact) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStoreContact() {
        return null;
    }
    
    public final void setShippingDeliveryOption(boolean enabled) {
    }
    
    public final boolean getShippingDeliveryOption() {
        return false;
    }
    
    public final void setShippingPickupOption(boolean enabled) {
    }
    
    public final boolean getShippingPickupOption() {
        return false;
    }
    
    public final void setShippingFlatRate(double rate) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getShippingFlatRate() {
        return null;
    }
    
    public final void setFreeShippingThreshold(double threshold) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getFreeShippingThreshold() {
        return null;
    }
}