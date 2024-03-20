import java.awt.event.KeyEvent;

/**
 * The KeyNote enum represents the mapping between keyboard keys and MIDI notes.
 * Each enum constant corresponds to a specific key on the keyboard and its associated MIDI note.
 */
enum KeyNote {
    C1(KeyEvent.VK_TAB, 24, "TAB", "C"),
    D1('q', 26, "Q", "D"),
    E1('w', 28, "W", "E"),
    F1('e', 29, "E", "F"),
    G1('r', 31, "R", "G"),
    A1('t', 33, "T", "A"),
    B1('y', 35, "Y", "B"),
    C2('u', 36, "U", "C"),
    D2('i', 38, "I", "D"),
    E2('o', 40, "O", "E"),
    F2('p', 41, "P", "F"),
    G2('[', 43, "[", "G"),
    A2(']', 45, "]", "A"),
    B2(KeyEvent.VK_ENTER, 47, "↵", "B"),
    C3(KeyEvent.VK_BACK_SLASH, 48, "\\", "C"),
    D3('z', 50, "Z", "D"),
    E3('x', 52, "X", "E"),
    F3('c', 53, "C", "F"),
    G3('v', 55, "V", "G"),
    A3('b', 57, "B", "A"),
    B3('n', 59, "N", "B"),
    C4('m', 60, "M", "C"),
    D4(',', 62, ",", "D"),
    E4('.', 64, ".", "E"),
    F4('/', 65, "/", "F"),
    C1_SHARP('1', 25, "1", "C#"),
    D1_SHARP('2', 27, "2", "D#"),
    F1_SHARP('4', 30, "4", "F#"),
    G1_SHARP('5', 32, "5", "G#"),
    A1_SHARP('6', 34, "6", "A#"),
    C2_SHARP('8', 37, "8", "C#"),
    D2_SHARP('9', 39, "9", "D#"),
    F2_SHARP('-', 42, "-", "F#"),
    G2_SHARP('=', 44, "=", "G#"),
    A2_SHARP(KeyEvent.VK_BACK_SPACE, 46, "←", "A#"),
    C3_SHARP('a', 49, "A", "C#"),
    D3_SHARP('s', 51, "S", "D#"),
    F3_SHARP('f', 54, "F", "F#"),
    G3_SHARP('g', 56, "G", "G#"),
    A3_SHARP('h', 58, "H", "A#"),
    C4_SHARP('k', 61, "K", "C#"),
    D4_SHARP('l', 63, "L", "D#");

    private final int key; // The key code of the keyboard key
    private final int note; // The MIDI note value
    private final String keyboardKey; // The label for the keyboard key
    private final String pianoNote; // The label for the piano note

    /**
     * Constructs a new KeyNote with the specified key code, MIDI note value, and keyboard key label.
     *
     * @param key         The key code of the keyboard key
     * @param note        The MIDI note value
     * @param keyboardKey The label for the keyboard key
     */
    KeyNote(int key, int note, String keyboardKey, String pianoNote) {
        this.key = key;
        this.note = note;
        this.keyboardKey = keyboardKey;
        this.pianoNote = pianoNote;
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
    public int getMidiNote() {
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
     * Returns the label for the piano note.
     *
     * @return The label for the piano note
     */
    public String getPianoNote(){
        return pianoNote;
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