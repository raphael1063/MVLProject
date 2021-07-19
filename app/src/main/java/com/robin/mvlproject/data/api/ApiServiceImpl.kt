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
                        requestBody.a.locationInfo
                    ),
                    Label(
                        requestBody.b.aqi,
                        requestBody.b.latitude,
                        requestBody.b.longitude,
                        requestBody.b.locationInfo
                    ), 77777
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
                            37.504157809047776,
                            127.06022311002017,
                            "Gangnam District Apgujeong-dong"
                        ),
                        Label(
                            34,
                            37.49932776515914,
                            127.11275015026331,
                            "Daechi-dong Daechi 2(i)-dong"
                        ),
                        12000
                    ),
                    Book(
                        1,
                        Label(
                            53,
                            37.49822441045347,
                            127.00013559311628,
                            "Songpa District Songpa 2(i)-dong"
                        ),
                        Label(
                            27,
                            37.533846974675484,
                            126.98528017848732,
                            "Banpo-dong Banpo 4(sa)-dong"
                        ),
                        15000
                    ),
                    Book(
                        2,
                        Label(
                            70,
                            37.5235049075288,
                            126.92385725677013,
                            "Yongsan District Yongsan 2(i)-ga-dong"
                        ),
                        Label(
                            38,
                            37.50693084024564,
                            126.93487409502268,
                            "Yeongdeungpo District Singil 1(il)-dong"
                        ),
                        8000
                    ),
                    Book(
                        3,
                        Label(
                            42,
                            37.48566448894246,
                            126.88917964696884,
                            "Gwanak District Sinwon-dong"
                        ),
                        Label(
                            37,
                            37.48580628999009,
                            126.88793241977693,
                            "Guro District Guro 3(sam)-dong"
                        ),
                        13200
                    )
                )
            )
        }
    }
}