import com.google.gson.Gson
import org.javalite.http.Get
import org.javalite.http.Http

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class GetTemperatureForTomorrow {

    String[] phrase() {
        ["temperatura jutro", "temperature tomorrow"]
    }

    String act(String cmd) {
        String apiKey = "081cfe3b3ff076da70e365a1ab1abb37"
        String city = "7531275"

        if (apiKey == null || city == null)
            return "Error"

        Get response = Http.get("http://api.openweathermap.org/data/2.5/forecast?id="+city+"&appid="+apiKey+"&units=metric")

        Gson gson = new Gson()
        Map json = (Map) gson.fromJson(response.text(), Object.class)
        List entries = (List) json.get("list")

        Map foundEntry = null
        for (int i = 0; i < entries.size(); i++) {
            Map entry = (Map) entries.get(i)
            Double dt = (Double) entry.get("dt")
            Instant date = Instant.ofEpochSecond(dt.longValue())
            LocalDateTime localDate = LocalDateTime.ofInstant(date, ZoneId.of(ZoneOffset.UTC.getId()))
            if (localDate.getHour() == 12) {
                LocalDateTime dateTruncated = localDate.truncatedTo(ChronoUnit.DAYS)
                LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
                if (now.plus(1, ChronoUnit.DAYS).equals(dateTruncated)) {
                    foundEntry = entry
                    break
                }
            }
        }

        if (foundEntry == null)
            return "Error"

        Map main = (Map) foundEntry.get("main")
        Double temp = (Double) main.get("temp")
        long temp0 = Math.round(temp)

        if (cmd.equals(phrase()[0]))
            return "Temperatura jutro wyniesie "+temp0+" stopni Celsjusza"
        else
            return "Temperature for tomorrow is "+temp0+" degrees Celsius"
    }
}