package synth;

import javax.sound.midi.*;
import javax.sound.midi.spi.MidiFileReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Sintetizzatore {

    private Synthesizer SYNT;
    private Sequencer sequencer;
    private Sequence seq;
    private Soundbank BANK;
    public Instrument[] Strumenti;
    public MidiChannel[] CANALI;
    private int SA=0;

    public Sintetizzatore() {
        setupSintetizzatore();
    }

    void setupSintetizzatore() {
        try {
            SYNT= MidiSystem.getSynthesizer();
            sequencer=MidiSystem.getSequencer();
            seq= new Sequence(Sequence.PPQ, 10);
            SYNT.open();
            BANK = SYNT.getDefaultSoundbank();
            if (BANK != null)
                Strumenti = SYNT.getDefaultSoundbank().getInstruments();
            else
                Strumenti = SYNT.getAvailableInstruments();
            CANALI=SYNT.getChannels();
        }
        catch(MidiUnavailableException ecc){allnull();}
        catch(InvalidMidiDataException ecc2){allnull();}
    }

    void allnull() {
        SYNT=null;
        sequencer=null;
        seq=null;
        BANK=null;
        Strumenti=null;
    }

    public void setStrumento(int str,int can) {
        SA=str;
        int prog=Strumenti[str].getPatch().getProgram();
        CANALI[can].programChange(prog);
    }

    public void cambiaCanale(int can) {
        int prog=Strumenti[SA].getPatch().getProgram();
        CANALI[can].programChange(prog);
    }

    public void suona(int nota,int tempo, int canale) {
        CANALI[canale].allNotesOff();
        CANALI[canale].noteOn(nota,tempo);
    }

    public void suona(int nota,int tempo) {
        suona(nota,tempo,0);
    }

    public void Mute() {
        CANALI[0].allNotesOff();
    }
}