package frc.team3465.omegafactor2017.hardware.revrobotics;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by FIRST on 2/5/2016.
 */
public class MXPDigitalBoard {
    private static class CharacterRegistry {
        public static final int A = 10;
        public static final int B = 11;
        public static final int C = 12;
        public static final int D = 13;
        public static final int E = 14;
        public static final int F = 15;
        public static final int G = 16;
        public static final int H = 17;
        public static final int I = 18;
        public static final int J = 19;
        public static final int K = 20;
        public static final int L = 21;
        public static final int M = 22;
        public static final int N = 23;
        public static final int O = 24;
        public static final int P = 25;
        public static final int Q = 26;
        public static final int R = 27;
        public static final int S = 28;
        public static final int T = 29;
        public static final int U = 30;
        public static final int V = 31;
        public static final int W = 32;
        public static final int X = 33;
        public static final int Y = 34;
        public static final int Z = 35;
        private int[][] register = new int[36][2]; //register is short for character registry

        private CharacterRegistry() {
            register[0][0] = 0x3f;
            register[0][1] = 0x00; //0
            register[1][0] = 0x06;
            register[1][1] = 0x00; //1
            register[2][0] = 0xdb;
            register[2][1] = 0x00; //2
            register[3][0] = 0xcf;
            register[3][1] = 0x00; //3
            register[4][0] = 0xe6;
            register[4][1] = 0x00; //4
            register[5][0] = 0xed;
            register[5][1] = 0x00; //5
            register[6][0] = 0xfd;
            register[6][1] = 0x00; //6
            register[7][0] = 0x07;
            register[7][1] = 0x00; //7
            register[8][0] = 0xff;
            register[8][1] = 0x00; //8
            register[9][0] = 0xef;
            register[9][1] = 0x00; //9
            register[A][0] = 0xf7;
            register[A][1] = 0x00; //A
            register[B][0] = 0x8f;
            register[B][1] = 0x12; //B
            register[C][0] = 0x39;
            register[C][1] = 0x00; //C
            register[D][0] = 0x0f;
            register[D][1] = 0x12; //D
            register[E][0] = 0xf9;
            register[E][1] = 0x00; //E
            register[F][0] = 0xf1;
            register[F][1] = 0x00; //F
            register[G][0] = 0xbd;
            register[G][1] = 0x00; //G
            register[H][0] = 0xf6;
            register[H][1] = 0x00; //H
            register[I][0] = 0x09;
            register[I][1] = 0x12; //I
            register[J][0] = 0x1e;
            register[J][1] = 0x00; //J
            register[K][0] = 0x70;
            register[K][1] = 0x0c; //K
            register[L][0] = 0x38;
            register[L][1] = 0x00; //L
            register[M][0] = 0x36;
            register[M][1] = 0x05; //M
            register[N][0] = 0x36;
            register[N][1] = 0x09; //N
            register[O][0] = 0x3f;
            register[O][1] = 0x00; //O
            register[P][0] = 0xf3;
            register[P][1] = 0x00; //P
            register[Q][0] = 0x3f;
            register[Q][1] = 0x08; //Q
            register[R][0] = 0xf3;
            register[R][1] = 0x08; //R
            register[S][0] = 0x8d;
            register[S][1] = 0x01; //S
            register[T][0] = 0x01;
            register[T][1] = 0x12; //T
            register[U][0] = 0x3e;
            register[U][1] = 0x00; //U
            register[V][0] = 0x30;
            register[V][1] = 0x24; //V
            register[W][0] = 0x36;
            register[W][1] = 0x28; //W
            register[X][0] = 0x00;
            register[X][1] = 0x2d; //X
            register[Y][0] = 0x00;
            register[Y][1] = 0x15; //Y
            register[Z][0] = 0x09;
            register[Z][1] = 0x24; //Z
        }

        public byte[] getByteFrom(int character) {
            final int[] ints = register[character];
            return convertFromIntegerArray(ints);
        }

        public byte[] getByteFrom(char character) {
            return getByteFrom(characterToValue(character));
        }

        public static int characterToValue(char character) {
            character = Character.toUpperCase(character);
            int value = Character.getNumericValue(character);
            if (value > 0) {
                return value;
            }

            value = character - 65 - 10;
            if (value >= 10 || value <= 35) {
                return value;
            } else {
                return -1;
            }
        }

        @Contract(pure = true)
        private static byte[] convertFromIntegerArray(@NotNull int[] array) {
            byte[] bytes = new byte[array.length];
            for (int i = 0; i < array.length; i++) {
                bytes[i] = (byte) array[i];
            }

            return bytes;
        }

    }

}
