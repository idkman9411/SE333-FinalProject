import java.text.DateFormatSymbols;
import java.util.Locale;
public class DumpDateFormatSymbols {
    public static void main(String[] args) {
        Locale def = Locale.getDefault();
        System.out.println("Default locale: " + def + " language=" + def.getLanguage());
        DateFormatSymbols dfsDef = new DateFormatSymbols(def);
        System.out.println("Default short weekdays:");
        for (String s : dfsDef.getShortWeekdays()) System.out.println("  '" + s + "'");
        Locale de = Locale.GERMAN;
        System.out.println("German locale: " + de + " language=" + de.getLanguage());
        DateFormatSymbols dfsDe = new DateFormatSymbols(de);
        System.out.println("German short weekdays:");
        for (String s : dfsDe.getShortWeekdays()) System.out.println("  '" + s + "'");
    }
}
