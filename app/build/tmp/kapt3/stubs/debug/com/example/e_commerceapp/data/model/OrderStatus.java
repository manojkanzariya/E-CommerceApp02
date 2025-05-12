package com.example.e_commerceapp.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \u00102\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0010B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/example/e_commerceapp/data/model/OrderStatus;", "", "displayName", "", "colorRes", "", "(Ljava/lang/String;ILjava/lang/String;I)V", "getColorRes", "()I", "getDisplayName", "()Ljava/lang/String;", "PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED", "Companion", "app_debug"})
public enum OrderStatus {
    /*public static final*/ PENDING /* = new PENDING(null, 0) */,
    /*public static final*/ PROCESSING /* = new PROCESSING(null, 0) */,
    /*public static final*/ SHIPPED /* = new SHIPPED(null, 0) */,
    /*public static final*/ DELIVERED /* = new DELIVERED(null, 0) */,
    /*public static final*/ CANCELLED /* = new CANCELLED(null, 0) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayName = null;
    private final int colorRes = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.e_commerceapp.data.model.OrderStatus.Companion Companion = null;
    
    OrderStatus(java.lang.String displayName, int colorRes) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    public final int getColorRes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.e_commerceapp.data.model.OrderStatus> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/e_commerceapp/data/model/OrderStatus$Companion;", "", "()V", "fromString", "Lcom/example/e_commerceapp/data/model/OrderStatus;", "value", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.example.e_commerceapp.data.model.OrderStatus fromString(@org.jetbrains.annotations.NotNull()
        java.lang.String value) {
            return null;
        }
    }
}