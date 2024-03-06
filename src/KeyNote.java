import java.awt.event.KeyEvent;

/**
 * The KeyNote enum represents the mapping between keyboard keys and MIDI notes.
 * Each enum constant corresponds to a specific key on the keyboard and its associated MIDI note.
 */
enum KeyNote {
    C1(KeyEvent.VK_TAB, 24, "TAB"),
    D1('q', 26, "Q"),
    E1('w', 28, "W"),
    F1('e', 29, "E"),
    G1('r', 31, "R"),
    A1('t', 33, "T"),
    B1('y', 35, "Y"),
    C2('u', 36, "U"),
    D2('i', 38, "I"),
    E2('o', 40, "O"),
    F2('p', 41, "P"),
    G2('[', 43, "["),
    A2(']', 45, "]"),
    B2(KeyEvent.VK_ENTER, 47, "↵"),
    C3(KeyEvent.VK_BACK_SLASH, 48, "\\"),
    D3('z', 50, "Z"),
    E3('x', 52, "X"),
    F3('c', 53, "C"),
    G3('v', 55, "V"),
    A3('b', 57, "B"),
    B3('n', 59, "N"),
    C4('m', 60, "M"),
    D4(',', 62, ","),
    E4('.', 64, "."),
    F4('/', 65, "/"),
    C1_SHARP('1', 25, "1"),
    D1_SHARP('2', 27, "2"),
    F1_SHARP('4', 30, "4"),
    G1_SHARP('5', 32, "5"),
    A1_SHARP('6', 34, "6"),
    C2_SHARP('8', 37, "8"),
    D2_SHARP('9', 39, "9"),
    F2_SHARP('-', 42, "-"),
    G2_SHARP('=', 44, "="),
    A2_SHARP(KeyEvent.VK_BACK_SPACE, 46, "←"),
    C3_SHARP('a', 49, "A"),
    D3_SHARP('s', 51, "S"),
    F3_SHARP('f', 54, "F"),
    G3_SHARP('g', 56, "G"),
    A3_SHARP('h', 58, "H"),
    C4_SHARP('k', 61, "K"),
    D4_SHARP('l', 63, "L");

    private final int key; // The key code of the keyboard key
    private final int note; // The MIDI note value
    private final String keyboardKey; // The label for the keyboard key

    /**
     * Constructs a new KeyNote with the specified key code, MIDI note value, and keyboard key label.
     *
     * @param key         The key code of the keyboard key
     * @param note        The MIDI note value
     * @param keyboardKey The label for the keyboard key
     */
    KeyNote(int key, int note, String keyboardKey) {
        this.key = key;
        this.note = note;
        this.keyboardKey = keyboardKey;
    }

    /**
     * Returns the KeyNote corresponding to the specified key code.
     *
     * @param key The key code of the keyboard key
     * @return The KeyNote corresponding to the key code, or null if not found
     */
    public static KeyNote fromKey(int key) {
        for (KeyNote keyToNote : KeyNote.values()) {
            if (keyToNote.key == key) {
                return keyToNote;
            }
        }
        return null;
    }

    /**
     * Returns the key code of the keyboard key.
     *
     * @return The key code of the keyboard key
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns the MIDI note value.
     *
     * @return The MIDI note value
     */
    public int getNote() {
        return note;
    }

    /**
     * Returns the label for the keyboard key.
     *
     * @return The label for the keyboard key
     */
    public String getKeyboardKey() {
        return keyboardKey;
    }

    /**
     * Returns whether this KeyNote represents a white key on the piano keyboard.
     *
     * @return true if this KeyNote represents a white key, false otherwise
     */
    public boolean isWhiteKey() {
        return !this.name().contains("_SHARP");
    }
}