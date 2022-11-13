package se1.hu4.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
// Für JUnit5 Assertions, Annotationen etc.

public class UnknownCalculatorTest {

    // Das Testobjekt dieser Testklasse:
    UnknownCalculator uc;


    // static: Wird einmalig ausgeführt, vor allen Tests auch jene mit @BeforeEach markierten.
    @BeforeAll
    static void setup(){
        System.out.println(">>@BeforeAll");
    }

    // Wird vor jedem einzelnen Test ausgeführt
    // Zweck: Ein "frisches" Testobjekt für Test vorbereiten: Instantieren, initialisieren, Ressourcen vorbereiten etc.
    @BeforeEach
    void setupThis(){
        uc= new UnknownCalculator();
        uc.switchOn();
    }

    // Wird nach jedem einzelnen Test ausgeführt
    // Zweck: Das Testobjekt herunterfahren: Ressourcen freigeben etc.
    @AfterEach
    void shutdownThis(){
        uc.switchOff();
    }

    @AfterAll
    static void shutdown(){
        System.out.println(">>@AfterAll");
    }

    //Test zu Anforderung: Der Rechner muss vor der Verwendung mit switchOn() eingeschaltet werden.
    @Test
    void calcConstructedButNotOn(){ // In diesem Test neues Testobjekt Taschenrechner ohne ihn einzuschalten, um zu testen, ob nach Erstellung des Objekts TR tatsaechlich noch nicht an.
        uc = new UnknownCalculator();
        assertFalse(uc.isOn(), "Taschenrechner sollte noch nicht eingeschaltet sein.");
    }

    // Test zu Anforderung: isOn() prueft, ob der Rechner eingeschaltet werden konnte.
    @Test
    void switchedOn(){ // Testobjekt Taschenrechner in beforeeach erzeugt und angeschalten.
        assertTrue(uc.isOn(), "Taschenrechner sollte eingeschaltet sein.");
    }

    // Tests ungueltige Eingabeparameter:
    @Test
    void ungueltigeEingabeX() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("String", "3.0",'+')
                ,"Keine gueltige Eingabe fuer x (Exception).");
    }

    @Test
    void ungueltigeEingabeY() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("3.0", "String",'+')
                ,"Keine gueltige Eingabe fuer y (Exception).");
    }

    @Test
    void ungueltigeEingabeOperator() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("3.0", "2.0",'x')
                ,"Keine gueltige Eingabe fuer den Operator (Exception).");
    }


    // Tests zu Anforderung: calc(String x, String y, char op) berechnet für die beiden Parameter x und y das Ergebnis nach der in op festgelegter Grundrechenart (‚+‘,‘-‘,‘*‘,‘/‘).

    @Test
    void addition() {
        assertEquals(4.1, uc.calc("2.0", "2.1", '+'), "2.0 + 2.1 sollte 4.1 ergeben.");
    }

    @Test
    void subtraction() {
        assertEquals(1.3, uc.calc("3.5", "2.2", '-'), "3.5 - 2.2 sollte 1.3 ergeben.");
    }

    @Test
    void multiplication() {
        assertEquals(6.0, uc.calc("3.0", "2.0", '*'), "3.0 * 2.0 sollte 6 ergeben.");
    }

    @Test
    void division() {
        assertEquals( 3.0 , uc.calc("9.0","3.0", '/'), "9.0 / 3.0 sollte 3.0 ergeben.");
    }


    //Test zu Anforderung: Gueltiger Wertebereich von x und y: von -1000 bis 1000

    @Test
    void xTooSmall() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("-1001.0", "5.0",'+')
                ,"X-Wert ist zu klein fuer den erlaubten Wertebereich.");
    }

    @Test
    void xTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("1001.0", "10.0",'+')
                ,"X-Wert ist zu gross fuer den erlaubten Wertebereich.");
    }

    @Test
    void yTooSmall() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("5.0", "-1001.0",'+')
                ,"Y-Wert ist zu klein fuer den erlaubten Wertebereich.");
    }

    @Test
    void yTooBig() {
        assertThrows(IllegalArgumentException.class,
                () -> uc.calc("5.0", "1001.0",'+')
                ,"Y-Wert ist zu gross fuer den erlaubten Wertebereich.");
    }

    //Test zu Anforderung: Zahlentyp-Double > bereits ueber die anderen Tests abgedeckt.

    //Test zu Anforderung: switchOff() schaltet das Geraet wieder aus.

    @Test
    void switchedOff() {
        uc.switchOff();
        assertFalse(uc.isOn(), "Taschenrechner sollte ausgeschaltet sein.");
    }

    //Test aus- und einschalten in Kombi
    @Test
    void switchedOffOn() {
        uc.switchOff();
        assertFalse(uc.isOn(), "Taschenrechner sollte ausgeschaltet sein.");
        uc.switchOn();
        assertTrue(uc.isOn(), "Taschenrechner sollte eingeschaltet sein.");
    }

    //Test weitere Exceptions:
    @Test
    void readyForCalculation1() {
        uc.switchOff();
        assertThrows(IllegalStateException.class,
                () -> uc.calc("7.0", "1.0",'+')
                ,"Taschenrechner wurde schon ausgeschalten.");
    }

    @Test
    void readyForCalculation2() {
        uc = new UnknownCalculator();
        assertThrows(IllegalStateException.class,
                () -> uc.calc("7.0", "1.0",'+')
                ,"Taschenrechner ist erst bereit, wenn eingeschaltet.");
    }

    @Test
    void divisionByZero() {
        assertThrows(ArithmeticException.class,
                () -> uc.calc("3.0", "0",'/')
                ,"Da Division durch 0 -> Exception");
    }
}
