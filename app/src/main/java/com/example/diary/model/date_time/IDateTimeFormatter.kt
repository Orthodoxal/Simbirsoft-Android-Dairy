package com.example.diary.model.date_time

/**
 * Date and time formatter interface
 */
interface IDateTimeFormatter {

    /**
     * @param [startTimes]
     * Get default times when user create new business
     * Example:
     * Current time: 22.11.22 19:20
     * Default time: start - 22.11.22 20:00; end - 22.11.22 21:00
     * @return Pair<Start<Date, Time>, Finish<Date, Time>>
     */
    fun getDefaultTimes(startTimes: Long): Pair<Pair<String, String>, Pair<String, String>>

    /**
     * @param [millis]
     * @param [default] used for getting local device time
     * Get date
     * @return [String]
     */
    fun getDate(millis: Long, default: Boolean = false): String

    /**
     * @param [millis]
     * @param [default] used for getting local device time
     * Get time
     * @return [String]
     */
    fun getTime(millis: Long, default: Boolean = false): String

    /**
     * @param [date]
     * @param [time]
     * Get timestamp
     * @return [Long]
     */
    fun getTimes(date: String, time: String): Long

    /**
     * @param [hour]
     * Get hour in millis
     * @return [Long]
     */
    fun getHour(hour: String): Long

}