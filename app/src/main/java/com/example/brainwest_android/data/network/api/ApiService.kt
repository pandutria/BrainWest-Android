package com.example.brainwest_android.data.network.api

import com.example.brainwest_android.data.model.Community
import com.example.brainwest_android.data.model.CommunityHistory
import com.example.brainwest_android.data.model.CommunityHistoryMessage
import com.example.brainwest_android.data.model.CommunityMember
import com.example.brainwest_android.data.model.ConsultationHistoryMessage
import com.example.brainwest_android.data.model.Doctor
import com.example.brainwest_android.data.model.Donation
import com.example.brainwest_android.data.model.DonationTransaction
import com.example.brainwest_android.data.model.Education
import com.example.brainwest_android.data.model.Event
import com.example.brainwest_android.data.model.EventTransaction
import com.example.brainwest_android.data.model.MidtransDonationTransaction
import com.example.brainwest_android.data.model.MidtransEventTransaction
import com.example.brainwest_android.data.model.MidtransProductTransactionHeader
import com.example.brainwest_android.data.model.Product
import com.example.brainwest_android.data.model.ProductTransactionDetail
import com.example.brainwest_android.data.model.ProductTransactionHeader
import com.example.brainwest_android.data.model.RehabilitationVideo
import com.example.brainwest_android.data.model.User
import com.example.brainwest_android.data.network.request.CommunityMemberRequest
import com.example.brainwest_android.data.network.request.CommunityMessageRequest
import com.example.brainwest_android.data.network.request.ConsultationRequest
import com.example.brainwest_android.data.network.request.DonationTransactionRequest
import com.example.brainwest_android.data.network.request.EventTransactionRequest
import com.example.brainwest_android.data.network.request.LoginRequest
import com.example.brainwest_android.data.network.request.ProductTransactionDetailRequest
import com.example.brainwest_android.data.network.request.RegisterRequest
import com.example.brainwest_android.data.network.request.ProductTransactionHeaderRequest
import com.example.brainwest_android.data.network.request.VideoByRehabRequest
import com.example.brainwest_android.data.network.response.BaseResponse
import com.example.brainwest_android.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<BaseResponse<User>>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("me")
    suspend fun me(@Header("Authorization") token: String): Response<BaseResponse<User>>

    @GET("education")
    suspend fun getAllEducation(@Header("Authorization") token: String)
    : Response<BaseResponse<List<Education>>>

    @GET("education/{id}")
    suspend fun getEducationById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse<Education>>

    @GET("events")
    suspend fun getAllEvent(@Header("Authorization") token: String)
    : Response<BaseResponse<List<Event>>>

    @GET("events/{id}")
    suspend fun getEventById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse<Event>>

    @POST("event/transaction")
    suspend fun postEventTransaction(
        @Header("Authorization") token: String,
        @Body request: EventTransactionRequest
    ): Response<BaseResponse<MidtransEventTransaction>>

    @GET("me/event/transaction")
    suspend fun getMyEventTransaction(@Header("Authorization") token: String)
    : Response<BaseResponse<List<EventTransaction>>>

    @GET("donates")
    suspend fun getAllDonation(@Header("Authorization") token: String)
    : Response<BaseResponse<List<Donation>>>

    @GET("donates/{id}")
    suspend fun getDonationById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse<Donation>>

    @POST("donate/transaction")
    suspend fun postDonateTransaction(
        @Header("Authorization") token: String, @Body request: DonationTransactionRequest)
    : Response<BaseResponse<MidtransDonationTransaction>>

    @GET("me/donate/transaction")
    suspend fun getHistoryDonation(@Header("Authorization") token: String)
    : Response<BaseResponse<List<DonationTransaction>>>

    @GET("doctor")
    suspend fun getDoctor(): Response<BaseResponse<List<Doctor>>>

    @GET("doctor/{id}")
    suspend fun getDoctorById(@Path("id") id: Int): Response<BaseResponse<Doctor>>

    @GET("consultation/history")
    suspend fun getHistoryConsultation(@Header("Authorization") token: String)
            : Response<BaseResponse<List<ConsultationHistoryMessage>>>

    @POST("consultation")
    suspend fun sendMessageConsultation(
        @Header("Authorization") token: String,
        @Body request: ConsultationRequest,
        @Header("Accept") accept: String = "application/json",
    ): Response<BaseResponse<List<ConsultationHistoryMessage>>>

    @GET("rehabilitation/video/by-rehab/{id}")
    suspend fun getRehabById(@Path("id") id: Int): Response<BaseResponse<List<RehabilitationVideo>>>

    @POST("rehabilitation/video/by-rehab")
    suspend fun getVideoByRehab(@Body request: VideoByRehabRequest)
    : Response<BaseResponse<List<RehabilitationVideo>>>

    @GET("rehabilitation/video/{id}")
    suspend fun getVideoRehabById(@Path("id") id: Int): Response<BaseResponse<RehabilitationVideo>>

    @GET("community")
    suspend fun getAllCommunity(): Response<BaseResponse<List<Community>>>

    @GET("community/{id}")
    suspend fun getCommunity(@Path("id") id: Int): Response<BaseResponse<Community>>

    @GET("community/history")
    suspend fun getHistoryCommunity(@Header("Authorization") token: String)
    : Response<BaseResponse<List<CommunityHistory>>>

    @POST("community/member")
    suspend fun postMemberCommunity(
        @Header("Authorization") token: String,
        @Body request: CommunityMemberRequest
    ): Response<BaseResponse<CommunityMember>>

    @POST("community/message")
    suspend fun sendMessageCommunity(
        @Header("Authorization") token: String,
        @Body request: CommunityMessageRequest,
    ): Response<BaseResponse<CommunityHistoryMessage>>

    @GET("community/message/history")
    suspend fun historyCommunityMessage(@Header("Authorization") token: String)
    : Response<BaseResponse<List<CommunityHistoryMessage>>>

    @GET("product")
    suspend fun getProduct(): Response<BaseResponse<List<Product>>>

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<BaseResponse<Product>>

    @POST("product/transaction")
    suspend fun postProductHeader(
        @Header("Authorization") token: String,
        @Body request: ProductTransactionHeaderRequest,
    ): Response<BaseResponse<MidtransProductTransactionHeader>>

    @POST("product/transaction/detail")
    suspend fun postProductDetail(
        @Body request: ProductTransactionDetailRequest,
    ): Response<BaseResponse<ProductTransactionHeader>>

    @GET("product/transaction/by-user")
    suspend fun getProductHeaderByUser(@Header("Authorization") token: String)
    : Response<BaseResponse<List<ProductTransactionHeader>>>

    @GET("product/transaction/detail")
    suspend fun getProducrDetail(): Response<BaseResponse<List<ProductTransactionDetail>>>

    @DELETE("product/transaction{id}")
    suspend fun deleteProductHeader(@Path("id") id: Int): Response<BaseResponse<ProductTransactionHeader>>
}