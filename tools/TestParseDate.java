import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestParseDate {
    public static void main(String[] args) throws Exception {
        String s = "Mi, 09 Apr 2008 23:55:38 GMT";
        String pattern = "EEE, dd MMM yyyy HH:mm:ss zzz";
        System.out.println("Input: '" + s + "' pattern='" + pattern + "'\n");

        // Case 1: explicit German locale
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.GERMAN);
            System.out.println("Default dfs for German: ");
            for (String wd: new DateFormatSymbols(Locale.GERMAN).getShortWeekdays()) System.out.println(" '"+wd+"'");
            Date d = sdf.parse(s);
            System.out.println("Parsed with German locale: " + d);
        } catch (ParseException pe) {
            System.out.println("Parse failed with German locale: " + pe.getMessage());
        }

        // Case 2: German locale but strip dots from shortWeekdays and set dfs
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat(pattern, Locale.GERMAN);
            DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
            String[] sw = dfs.getShortWeekdays();
            for (int i=0;i<sw.length;i++) if (sw[i]!=null && sw[i].endsWith(".")) sw[i]=sw[i].substring(0, sw[i].length()-1);
            dfs.setShortWeekdays(sw);
            sdf2.setDateFormatSymbols(dfs);
            System.out.println("Modified dfs for German: ");
            for (String wd: dfs.getShortWeekdays()) System.out.println(" '"+wd+"'");
            Date d2 = sdf2.parse(s);
            System.out.println("Parsed with modified dfs: " + d2);
        } catch (ParseException pe) {
            System.out.println("Parse failed with modified dfs: " + pe.getMessage());
        }

        // Case 3: default locale set to German, parser created with default SimpleDateFormat()
        try {
            Locale orig = Locale.getDefault();
            Locale.setDefault(Locale.GERMAN);
            SimpleDateFormat sdf3 = new SimpleDateFormat(pattern);
            Date d3 = sdf3.parse(s);
            System.out.println("Parsed with default German locale: " + d3);
            Locale.setDefault(orig);
        } catch (ParseException pe) {
            System.out.println("Parse failed with JVM default German: " + pe.getMessage());
        }
    }
}
