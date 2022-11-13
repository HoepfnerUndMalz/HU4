package se1.hu4.calculator;


public class UnknownCalculator {

    final static double MIN = -1000;
    final static double MAX = 1000;

    boolean on;

    public UnknownCalculator() {
        on = false;
    }

    /*
     * Switch calculator on
     * @throws IllegalStateException
    public void switchOn() throws IllegalStateException {
        on= true;
        System.out.println("*Calculator on*");
    }
    */

    public void switchOn() {
        on = true;
        System.out.println("*Taschenrechner angeschalten*");
    }

    /*
     * Switch calculator off
     * @throws IllegalStateException

    public void switchOff() throws IllegalStateException {
        on= false;
        System.out.println("*Calculator off*");
    }
    */
    public void switchOff() {
        on = false;
        System.out.println("*Taschenrechner ausgeschalten*");
    }

    /*
     * Query calculator state
     * @return true if calculator is on, else false
     */
    public boolean isOn() {
        return on;
    }

    /*
     * Perform mathematical operation on two arguments and return result
     * @param x First argument, real number as string, valid range: -1000 <= x <= 1000
     * @param y Second argument, real number as string, valid range: -1000 <= x <= 1000
     * @param op Operator, valid operators: '+','-','*','/'
     * @return x op y
     * @throws IllegalStateException In case of this method being used in an illegal calculator state
     * @throws IllegalArgumentException In case of illegal arguments x, y
     * @throws ArithmeticException In case of an arithmetic exception, i.e. division by zero
     */
    public double calc(String x, String y, char op) throws IllegalArgumentException, IllegalStateException, ArithmeticException
    {


        if (!on)
            throw new IllegalStateException("Taschenrechner ist ausgeschaltet.");

        //Check, ob gueltige Eingabelparameter (x, y, Operator)
        double a, b;

        try {
            a = Double.valueOf(x); // Quelle: https://www.digitalocean.com/community/tutorials/java-convert-string-to-double
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Keine gueltige Eingabe fuer X.");
        }
        try {
            b = Double.valueOf(y); // Quelle: https://www.digitalocean.com/community/tutorials/java-convert-string-to-double
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Keine gueltige Eingabe fuer Y.");
        }

        if (op != '+' && op != '-' && op != '*' && op != '/')
            throw new IllegalArgumentException("Keine gueltige Eingabe fuer den Operator.");

        //Check, ob eingegeben Zahlen im vorgegebenen Wertebereich:
        if (a < MIN || a > MAX)
            throw new IllegalArgumentException("Eingabe fuer Parameter X liegt ausserhalb des erlaubten Wertebereichs.");
        if (b < MIN || b > MAX)
            throw new IllegalArgumentException("Eingabe fuer Parameter Y liegt ausserhalb des erlaubten Wertebereichs.");


        switch (op) {
            case '+':
                System.out.print(a + " + " + b + " = ");
                return Math.round((a+b)*1000)/1000.0; // Quelle: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
            case '-':
                System.out.print(a + " - " + b + " = ");
                return Math.round((a-b)*1000)/1000.0; // Quelle: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
            case '*':
                System.out.print(a + " * " + b + " = ");
                return Math.round((a*b)*1000)/1000.0; // Quelle: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
            case '/': //Division durch 0 abfangen
                if (b == 0)
                    throw new ArithmeticException("Division durch 0 nicht erlaubt.");
                System.out.print(a + " / " + b + " = ");
                return Math.round((a/b)*1000)/1000.0; // Quelle: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
        }
        return 0;
    }

    public static void main(String[] args)
    {
        UnknownCalculator c = new UnknownCalculator();
        try {
            c.switchOn();
            // Beispielrechnung
            System.out.println( c.calc("3.0", "1000.0", '/') );
            c.switchOff();
        }
        catch (IllegalStateException ise)
        { System.out.println("Calculator State Error: " + ise.getMessage()); }
        catch (IllegalArgumentException iae)
        { System.out.println("Calculator Argument Error: " + iae.getMessage()); }
        catch (ArithmeticException ae)
        { System.out.println("Calculator Arithmetic Error: " + ae.getMessage()); }
    }
}