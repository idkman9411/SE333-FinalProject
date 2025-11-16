import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestParseDate2 {
    public static void main(String[] args) {
        String s = "Mi, 09 Apr 2008 23:55:38 GMT";
        String pattern = "EEE, dd MMM yyyy HH:mm:ss zzz";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.GERMAN);
        ParsePosition pos = new ParsePosition(0);
        Date d = sdf.parse(s, pos);
        System.out.println("parse with default dfs -> date=" + d + " pos=" + pos.getIndex());

        // modified dfs
        SimpleDateFormat sdf2 = new SimpleDateFormat(pattern, Locale.GERMAN);
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
        String[] sw = dfs.getShortWeekdays();
        for (int i=0;i<sw.length;i++) if (sw[i]!=null && sw[i].endsWith(".")) sw[i]=sw[i].substring(0, sw[i].length()-1);
        dfs.setShortWeekdays(sw);
        sdf2.setDateFormatSymbols(dfs);
        ParsePosition pos2 = new ParsePosition(0);
        Date d2 = sdf2.parse(s, pos2);
        System.out.println("parse with modified dfs -> date=" + d2 + " pos=" + pos2.getIndex());

        // try remove weekday and parse
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz", Locale.GERMAN);
        ParsePosition pos3 = new ParsePosition(0);
        String alt = s.replaceFirst("^[\\p{L}]{1,4}[\\.,]*\\s*", "");
        Date d3 = sdf3.parse(alt, pos3);
        System.out.println("altStr='" + alt + "' -> date=" + d3 + " pos=" + pos3.getIndex());
    }
}
