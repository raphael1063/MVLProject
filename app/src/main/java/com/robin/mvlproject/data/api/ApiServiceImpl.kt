package com.robin.mvlproject.data.api

import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Book
import com.robin.mvlproject.data.entities.Label
import io.reactivex.Single

class ApiServiceImpl : ApiService {
    override fun getBooks(requestBody: BooksRequest): Single<Book> {
        return Single.create { data ->
            data.onSuccess(
                Book(
                    0,
                    Label(
                        requestBody.a.aqi,
                        requestBody.a.latitude,
                        requestBody.a.longitude,
                        requestBody.a.locationInfo,
                        requestBody.a.name
                    ),
                    Label(
                        requestBody.b.aqi,
                        requestBody.b.latitude,
                        requestBody.b.longitude,
                        requestBody.b.locationInfo,
                        requestBody.b.name
                    ), 72500
                )
            )
        }
    }

    override fun getHistory(year: String, month: String): Single<List<Book>> {
        return Single.create { data ->
            data.onSuccess(
                listOf(
                    Book(
                        0,
                        Label(
                            34,
                            37.512,
                            127.053,
                            "Samseong-dong Samseong 1(il)-dong",
                            null
                        ),
                        Label(
                            34,
                            37.484,
                            127.03,
                            "Seocho-dong Seocho 2(i)-dong",
                            null
                        ),
                        12000
                    ),
                    Book(
                        1,
                        Label(
                            42,
                            37.553,
                            126.999,
                            "Jung District Jangchung-dong",
                            "장충동"
                        ),
                        Label(
                            55,
                            37.559,
                            126.972,
                            "Seoul Jung District",
                            null
                        ),
                        15000
                    ),
                    Book(
                        2,
                        Label(
                            46,
                            37.476,
                            126.931,
                            "Gwanak District Sinwon-dong",
                            "신원동"
                        ),
                        Label(
                            46,
                            37.503,
                            126.945,
                            "Dongjak District Sangdo 2(i)-dong",
                            "상도2동"
                        ),
                        8000
                    ),
                    Book(
                        3,
                        Label(
                            38,
                            37.569,
                            126.825,
                            "District Gayang 1(il)-dong",
                            "가양1동"
                        ),
                        Label(
                            53,
                            37.53,
                            126.837,
                            "Gangseo District Hwagok 1(il)-dong",
                            "화곡동"
                        ),
                        13200
                    )
                )
            )
        }
    }
}