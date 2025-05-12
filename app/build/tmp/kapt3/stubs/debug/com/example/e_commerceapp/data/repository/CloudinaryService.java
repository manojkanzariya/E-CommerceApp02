package com.example.e_commerceapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\'\u00a8\u0006\t"}, d2 = {"Lcom/example/e_commerceapp/data/repository/CloudinaryService;", "", "uploadImage", "Lretrofit2/Call;", "Lcom/example/e_commerceapp/data/model/CloudinaryResponse;", "uploadPreset", "Lokhttp3/RequestBody;", "file", "Lokhttp3/MultipartBody$Part;", "app_debug"})
public abstract interface CloudinaryService {
    
    @retrofit2.http.Multipart()
    @retrofit2.http.POST(value = "image/upload")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.example.e_commerceapp.data.model.CloudinaryResponse> uploadImage(@retrofit2.http.Part(value = "upload_preset")
    @org.jetbrains.annotations.NotNull()
    okhttp3.RequestBody uploadPreset, @retrofit2.http.Part()
    @org.jetbrains.annotations.NotNull()
    okhttp3.MultipartBody.Part file);
}