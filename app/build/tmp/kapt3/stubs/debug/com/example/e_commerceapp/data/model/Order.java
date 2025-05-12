package com.example.e_commerceapp.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\bF\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00b3\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u001b\u00a2\u0006\u0002\u0010\u001cJ\t\u0010N\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00120\u000fH\u00c6\u0003J\t\u0010P\u001a\u00020\u0014H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0016H\u00c6\u0003J\t\u0010R\u001a\u00020\u0003H\u00c6\u0003J\t\u0010S\u001a\u00020\u0003H\u00c6\u0003J\t\u0010T\u001a\u00020\u0003H\u00c6\u0003J\t\u0010U\u001a\u00020\u001bH\u00c6\u0003J\t\u0010V\u001a\u00020\u0003H\u00c6\u0003J\t\u0010W\u001a\u00020\u0003H\u00c6\u0003J\t\u0010X\u001a\u00020\u0003H\u00c6\u0003J\t\u0010Y\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010Z\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010:J\t\u0010[\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\\\u001a\u00020\rH\u00c6\u0003J\u000f\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u00c6\u0003J\u00bc\u0001\u0010^\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u00c6\u0001\u00a2\u0006\u0002\u0010_J\t\u0010`\u001a\u00020\tH\u00d6\u0001J\u0013\u0010a\u001a\u00020b2\b\u0010c\u001a\u0004\u0018\u00010dH\u00d6\u0003J\t\u0010e\u001a\u00020\tH\u00d6\u0001J\t\u0010f\u001a\u00020\u0003H\u00d6\u0001J\u0019\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020\tH\u00d6\u0001R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010\u0017\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010&\"\u0004\b*\u0010(R \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010&\"\u0004\b0\u0010(R\u001a\u0010\u0018\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010&\"\u0004\b2\u0010(R\u001a\u0010\u0019\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010&\"\u0004\b4\u0010(R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010&\"\u0004\b6\u0010(R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010,\"\u0004\b8\u0010.R\u001e\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010=\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010&\"\u0004\bK\u0010(R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010&\"\u0004\bM\u0010(\u00a8\u0006l"}, d2 = {"Lcom/example/e_commerceapp/data/model/Order;", "Landroid/os/Parcelable;", "id", "", "orderId", "userId", "productId", "vendorId", "quantity", "", "date", "", "total", "", "items", "", "Lcom/example/e_commerceapp/data/model/OrderItem;", "products", "Lcom/example/e_commerceapp/data/model/Product;", "timestamp", "Lcom/google/firebase/Timestamp;", "deliveryAddress", "Lcom/example/e_commerceapp/data/model/DeliveryAddress;", "estimatedDelivery", "paymentMethod", "paymentStatus", "status", "Lcom/example/e_commerceapp/data/model/OrderStatus;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;JDLjava/util/List;Ljava/util/List;Lcom/google/firebase/Timestamp;Lcom/example/e_commerceapp/data/model/DeliveryAddress;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/e_commerceapp/data/model/OrderStatus;)V", "getDate", "()J", "setDate", "(J)V", "getDeliveryAddress", "()Lcom/example/e_commerceapp/data/model/DeliveryAddress;", "setDeliveryAddress", "(Lcom/example/e_commerceapp/data/model/DeliveryAddress;)V", "getEstimatedDelivery", "()Ljava/lang/String;", "setEstimatedDelivery", "(Ljava/lang/String;)V", "getId", "setId", "getItems", "()Ljava/util/List;", "setItems", "(Ljava/util/List;)V", "getOrderId", "setOrderId", "getPaymentMethod", "setPaymentMethod", "getPaymentStatus", "setPaymentStatus", "getProductId", "setProductId", "getProducts", "setProducts", "getQuantity", "()Ljava/lang/Integer;", "setQuantity", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getStatus", "()Lcom/example/e_commerceapp/data/model/OrderStatus;", "setStatus", "(Lcom/example/e_commerceapp/data/model/OrderStatus;)V", "getTimestamp", "()Lcom/google/firebase/Timestamp;", "setTimestamp", "(Lcom/google/firebase/Timestamp;)V", "getTotal", "()D", "setTotal", "(D)V", "getUserId", "setUserId", "getVendorId", "setVendorId", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;JDLjava/util/List;Ljava/util/List;Lcom/google/firebase/Timestamp;Lcom/example/e_commerceapp/data/model/DeliveryAddress;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/example/e_commerceapp/data/model/OrderStatus;)Lcom/example/e_commerceapp/data/model/Order;", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
@androidx.annotation.Keep()
@kotlinx.parcelize.Parcelize()
public final class Order implements android.os.Parcelable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String id;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String orderId;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String userId;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String productId;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String vendorId;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer quantity;
    private long date;
    private double total;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.e_commerceapp.data.model.OrderItem> items;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.e_commerceapp.data.model.Product> products;
    @org.jetbrains.annotations.NotNull()
    private com.google.firebase.Timestamp timestamp;
    @org.jetbrains.annotations.NotNull()
    private com.example.e_commerceapp.data.model.DeliveryAddress deliveryAddress;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String estimatedDelivery;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String paymentMethod;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String paymentStatus;
    @org.jetbrains.annotations.NotNull()
    private com.example.e_commerceapp.data.model.OrderStatus status;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.e_commerceapp.data.model.Product> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.Timestamp component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.e_commerceapp.data.model.DeliveryAddress component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.e_commerceapp.data.model.OrderStatus component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.e_commerceapp.data.model.OrderItem> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.e_commerceapp.data.model.Order copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    java.lang.String vendorId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer quantity, long date, double total, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.OrderItem> items, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.Product> products, @org.jetbrains.annotations.NotNull()
    com.google.firebase.Timestamp timestamp, @org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.DeliveryAddress deliveryAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String estimatedDelivery, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentStatus, @org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.OrderStatus status) {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    public Order(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    java.lang.String vendorId, @org.jetbrains.annotations.Nullable()
    java.lang.Integer quantity, long date, double total, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.OrderItem> items, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.Product> products, @org.jetbrains.annotations.NotNull()
    com.google.firebase.Timestamp timestamp, @org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.DeliveryAddress deliveryAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String estimatedDelivery, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentStatus, @org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.OrderStatus status) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final void setId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOrderId() {
        return null;
    }
    
    public final void setOrderId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserId() {
        return null;
    }
    
    public final void setUserId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProductId() {
        return null;
    }
    
    public final void setProductId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVendorId() {
        return null;
    }
    
    public final void setVendorId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getQuantity() {
        return null;
    }
    
    public final void setQuantity(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    public final long getDate() {
        return 0L;
    }
    
    public final void setDate(long p0) {
    }
    
    public final double getTotal() {
        return 0.0;
    }
    
    public final void setTotal(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.e_commerceapp.data.model.OrderItem> getItems() {
        return null;
    }
    
    public final void setItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.OrderItem> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.e_commerceapp.data.model.Product> getProducts() {
        return null;
    }
    
    public final void setProducts(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.e_commerceapp.data.model.Product> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.firebase.Timestamp getTimestamp() {
        return null;
    }
    
    public final void setTimestamp(@org.jetbrains.annotations.NotNull()
    com.google.firebase.Timestamp p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.e_commerceapp.data.model.DeliveryAddress getDeliveryAddress() {
        return null;
    }
    
    public final void setDeliveryAddress(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.DeliveryAddress p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEstimatedDelivery() {
        return null;
    }
    
    public final void setEstimatedDelivery(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPaymentMethod() {
        return null;
    }
    
    public final void setPaymentMethod(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPaymentStatus() {
        return null;
    }
    
    public final void setPaymentStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.e_commerceapp.data.model.OrderStatus getStatus() {
        return null;
    }
    
    public final void setStatus(@org.jetbrains.annotations.NotNull()
    com.example.e_commerceapp.data.model.OrderStatus p0) {
    }
    
    public Order() {
        super();
    }
}