import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The VirtualPiano class represents a virtual piano keyboard with a graphical user interface (GUI).
 * It allows users to play notes on the virtual keyboard using their computer keyboard or by clicking on the keys.
 * The class also provides functionality to change the instrument sound.
 */
public class VirtualPiano {
    private static MidiChannel[] channels; // The MIDI channels used to control the synthesizer
    private static final Set<Integer> activeNotes = new HashSet<>(); // The set of active notes being played
    private static final Map<Integer, JButton> noteToButton = new HashMap<>(); // A map that associates notes with their corresponding GUI buttons

    public static void main(String[] args) {
        // Initialize the MIDI Synthesizer and open it
        try {
            // The synthesizer used to generate sounds
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a new JFrame object, specify its name
        JFrame frame = new JFrame("Virtual Piano Keyboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLayeredPane and set its preferred size
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(60 * 25, 200));

        frame.setFocusTraversalKeysEnabled(false); // Enable TAB key
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            /**
             * This method is called when a key is pressed on the keyboard.
             * It plays the corresponding note if the key is mapped to a note.
             *
             * @param e The KeyEvent representing the key press
             */
            @Override
            public void keyPressed(KeyEvent e) {
                KeyNote keyToNote = KeyNote.fromKey(e.getKeyChar());
                if (keyToNote != null && !activeNotes.contains(keyToNote.getMidiNote())) {
                    playSound(keyToNote.getMidiNote());
                    activeNotes.add(keyToNote.getMidiNote());
                    noteToButton.get(keyToNote.getMidiNote()).setBackground(Color.GREEN); // Change color on key press
                }
            }

            /**
             * This method is called when a key is released on the keyboard.
             * It stops the corresponding note if the key is mapped to a note.
             *
             * @param e The KeyEvent representing the key release
             */
            @Override
            public void keyReleased(KeyEvent e) {
                KeyNote keyToNote = KeyNote.fromKey(e.getKeyChar());
                if (keyToNote != null) {
                    stopSound(keyToNote.getMidiNote());
                    activeNotes.remove(keyToNote.getMidiNote());
                    noteToButton.get(keyToNote.getMidiNote()).setBackground(keyToNote.isWhiteKey() ? Color.WHITE : Color.BLACK); // Revert color on key release
                }
            }
        });

        // Add white keys
        for (int i = 0; i < 25; i++) {
            JButton whiteKey = new JButton();
            whiteKey.setBounds(i * 60, 0, 60, 180);
            whiteKey.setBackground(Color.WHITE);
            KeyNote keyNote = KeyNote.values()[i];
            String buttonText = "<html>" + keyNote.getKeyboardKey() + "<br/>" + keyNote.getPianoNote() + "</html>";
            whiteKey.setText(buttonText);
            whiteKey.setVerticalAlignment(SwingConstants.BOTTOM);
            layeredPane.add(whiteKey, JLayeredPane.DEFAULT_LAYER);
            noteToButton.put(keyNote.getMidiNote(), whiteKey);
            whiteKey.setFocusable(false);

            // Add mouse listener to the white key
            whiteKey.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!activeNotes.contains(keyNote.getMidiNote())) {
                        playSound(keyNote.getMidiNote());
                        activeNotes.add(keyNote.getMidiNote());
                        whiteKey.setBackground(Color.GREEN);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    stopSound(keyNote.getMidiNote());
                    activeNotes.remove(keyNote.getMidiNote());
                    whiteKey.setBackground(Color.WHITE);
                }
            });
        }

        // Add black keys
        int[] skipKeys = {2, 6, 9, 13, 16, 20};
        int blackKeyIndex = 0;

        for (int i = 0; i < 23; i++) {
            boolean isSkipKey = false;
            for (int keyIndex : skipKeys) {
                if (i == keyIndex) {
                    isSkipKey = true;
                    break;
                }
            }

            if (isSkipKey) {
                continue;
            }

            JButton blackKey = new JButton();
            blackKey.setBounds(i * 60 + 38, 0, 45, 120);
            blackKey.setForeground(Color.WHITE);
            blackKey.setBackground(Color.BLACK);
            KeyNote keyNote = KeyNote.values()[25 + blackKeyIndex];
            String buttonText = "<html>" + keyNote.getKeyboardKey() + "<br/>" + keyNote.getPianoNote() + "</html>";
            blackKey.setText(buttonText);
            blackKey.setVerticalAlignment(SwingConstants.BOTTOM);
            layeredPane.add(blackKey, JLayeredPane.PALETTE_LAYER);
            noteToButton.put(keyNote.getMidiNote(), blackKey);
            blackKeyIndex++;
            blackKey.setFocusable(false);

            // Add mouse listener to the black key
            blackKey.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!activeNotes.contains(keyNote.getMidiNote())) {
                        playSound(keyNote.getMidiNote());
                        activeNotes.add(keyNote.getMidiNote());
                        blackKey.setBackground(Color.GREEN);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    stopSound(keyNote.getMidiNote());
                    activeNotes.remove(keyNote.getMidiNote());
                    blackKey.setBackground(Color.BLACK);
                }
            });
        }

        int[] instruments = {0, 32, 40, 56, 80, 88, 104}; // Array of instrument numbers
        String[] instrumentNames = {"Acoustic Grand Piano", "Bass", "Violin", "Trumpet", "Ocarina", "Lead 1 (square)", "Bird Tweet"}; // Corresponding instrument names

        // Create a JPanel for the instrument buttons
        JPanel instrumentPanel = new JPanel();
        instrumentPanel.setLayout(new GridLayout(1, instruments.length));

        // Create a button for each instrument
        for (int i = 0; i < instruments.length; i++) {
            JButton instrumentButton = new JButton(instrumentNames[i]);
            int instrument = instruments[i];
            instrumentButton.addActionListener(e -> {
                channels[0].programChange(instrument); // Change the instrument
            });
            instrumentPanel.add(instrumentButton);
            instrumentButton.setFocusable(false); // Stops button from taking focus when clicked
        }

        frame.add(instrumentPanel, BorderLayout.NORTH); // Add the instrument panel to the frame
        frame.add(layeredPane);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    /**
     * Plays the specified note on the MIDI synthesizer.
     *
     * @param note The MIDI note value to play
     */
    private static void playSound(int note) {
        try {
            channels[0].noteOn(note, 80); // Start the note
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the specified note on the MIDI synthesizer.
     *
     * @param note The MIDI note value to stop
     */
    private static void stopSound(int note) {
        try {
            channels[0].noteOff(note); // Stop the note
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}