package com.luaeditor2.aluasyntax;

public class lctype {
    public lctype() {
    }

    //    #define ALPHABIT	0
//            #define DIGITBIT	1
//            #define PRINTBIT	2
//            #define SPACEBIT	3
//            #define XDIGITBIT	4
    public static final int ALPHABIT = 0;
    public static final int DIGITBIT = 1;
    public static final int PRINTBIT = 2;
    public static final int SPACEBIT = 3;
    public static final int XDIGITBIT = 4;

    //#define MASK(B)		(1 << (B))
    public static int MASK(int B) {
        return (1 << (B));
    }

    //            #define testprop(c,p)	(luai_ctype_[(c)+1] & (p))
    public static boolean testprop(byte c, int p) {
        return (luai_ctype_[(c&0xff) + 1] & (p)) != 0;
    }

    //            #define lislalpha(c)	testprop(c, MASK(ALPHABIT))
//            #define lislalnum(c)	testprop(c, (MASK(ALPHABIT) | MASK(DIGITBIT)))
//            #define lisdigit(c)	testprop(c, MASK(DIGITBIT))
//            #define lisspace(c)	testprop(c, MASK(SPACEBIT))
//            #define lisprint(c)	testprop(c, MASK(PRINTBIT))
//            #define lisxdigit(c)	testprop(c, MASK(XDIGITBIT))
    public static boolean lislalpha(byte c) {
        return testprop(c, MASK(ALPHABIT));
    }
    public static boolean lislalpha(int  c) {
        return lislalpha((byte) c);
    }

    public static boolean lislalnum(byte c) {
        return testprop(c, (MASK(ALPHABIT) | MASK(DIGITBIT)));
    }

    public static boolean lislalnum(int c) {
        return lislalnum((byte) c);
    }

    public static boolean lisdigit(byte c) {
        return testprop(c, MASK(DIGITBIT));
    }

    public static boolean lisdigit(int c) {
        return lisdigit((byte) c);
    }

    public static boolean lisspace(byte c) {
        return testprop(c, MASK(SPACEBIT));
    }

    public static boolean lisspace(int c) {
        return lisspace((byte) c);
    }

    public static boolean lisprint(byte c) {
        return testprop(c, MASK(PRINTBIT));
    }

    public static boolean lisprint(int c) {
        return lisprint((byte) c);
    }

    public static boolean lisxdigit(byte c) {
        return testprop(c, MASK(XDIGITBIT));
    }

    public static boolean lisxdigit(int c) {
        return lisxdigit((byte) c);
    }

    //            #define ltolower(c)	((c) | ('A' ^ 'a'))
    public static byte ltolower(byte c) {
        return (byte) ((c) | ('A' ^ 'a'));
    }

    public final static byte[] luai_ctype_ = new byte[]{
            0x00,  /* EOZ */
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    /* 0. */
            0x00, 0x08, 0x08, 0x08, 0x08, 0x08, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    /* 1. */
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x0c, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04,    /* 2. */
            0x04, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04,
            0x16, 0x16, 0x16, 0x16, 0x16, 0x16, 0x16, 0x16,    /* 3. */
            0x16, 0x16, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04,
            0x04, 0x15, 0x15, 0x15, 0x15, 0x15, 0x15, 0x05,    /* 4. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* 5. */
            0x05, 0x05, 0x05, 0x04, 0x04, 0x04, 0x04, 0x05,
            0x04, 0x15, 0x15, 0x15, 0x15, 0x15, 0x15, 0x05,    /* 6. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* 7. */
            0x05, 0x05, 0x05, 0x04, 0x04, 0x04, 0x04, 0x00,

            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* e. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* e. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* e. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* e. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,


            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    /* c. */
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    /* d. */
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,


            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,    /* e. */
            0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05,


            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,    /* f. */
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
    };
}
