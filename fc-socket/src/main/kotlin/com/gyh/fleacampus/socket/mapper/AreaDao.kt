package com.gyh.fleacampus.socket.mapper

import com.gyh.fleacampus.socket.entity.Area
import org.springframework.data.repository.reactive.ReactiveSortingRepository

/**
 * Created by GYH on 2021/7/5
 */
interface AreaDao : ReactiveSortingRepository<Area, Int>