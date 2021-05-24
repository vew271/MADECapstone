package com.example.madecapstone.core.di

import androidx.room.Room
import com.example.madecapstone.core.data.MovieRepository
import com.example.madecapstone.core.data.source.local.LocalDataSource
import com.example.madecapstone.core.data.source.local.room.FilmDB
import com.example.madecapstone.core.data.source.remote.RemoteDataSource
import com.example.madecapstone.core.data.source.remote.network.RetrofitAPI
import com.example.madecapstone.core.domain.repository.CoreRepository
import com.example.madecapstone.core.utils.AppExecutor
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FilmDB>().filmDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("codext".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FilmDB::class.java, "movie.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/discover/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(RetrofitAPI::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutor() }
    single<CoreRepository> { MovieRepository(get(), get(), get()) }
}