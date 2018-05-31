package oo.assignment14;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {
    private static final int BUFFER_SIZE = 2;
    private static final Buffer<Integer> BUFFER = new Buffer<>(BUFFER_SIZE);

    public static void main(String[] args) {
        testBuffer(10);
        testPrimes(100);
    }

    private static void testBuffer(int n) {
        // Start a new thread that will put the integers [1, n] in the buffer.
        new Thread(() -> IntStream.rangeClosed(1, n).forEach(Main::putInBuffer)).start();

        // Consume n integers on this thread.
        IntStream.rangeClosed(1, n).forEach(Main::getFromBuffer);
    }

    private static void putInBuffer(int i) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(10, 75));
            //System.out.printf("Putting %d\n", i);
            BUFFER.put(i);
            //System.out.printf("Put %d\n", i);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while putting");
        }
    }

    private static void getFromBuffer(int expected ) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(50, 100));
            //System.out.printf("Getting %d\n", expected);
            Integer value = BUFFER.get();

            assert value == expected;

            System.out.printf("Got %d\n", value);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while getting");
        }
    }

    private static void testPrimes(int n) {
        List<Integer> primes = new PrimeGenerator(n).generate();

        IntStream.rangeClosed(1, n).forEach(i -> System.out.printf("%d: %d\n", i, primes.get(i - 1)));
    }
}

/*
First 100 generated primes:
1: 2
2: 3
3: 5
4: 7
5: 11
6: 13
7: 17
8: 19
9: 23
10: 29
11: 31
12: 37
13: 41
14: 43
15: 47
16: 53
17: 59
18: 61
19: 67
20: 71
21: 73
22: 79
23: 83
24: 89
25: 97
26: 101
27: 103
28: 107
29: 109
30: 113
31: 127
32: 131
33: 137
34: 139
35: 149
36: 151
37: 157
38: 163
39: 167
40: 173
41: 179
42: 181
43: 191
44: 193
45: 197
46: 199
47: 211
48: 223
49: 227
50: 229
51: 233
52: 239
53: 241
54: 251
55: 257
56: 263
57: 269
58: 271
59: 277
60: 281
61: 283
62: 293
63: 307
64: 311
65: 313
66: 317
67: 331
68: 337
69: 347
70: 349
71: 353
72: 359
73: 367
74: 373
75: 379
76: 383
77: 389
78: 397
79: 401
80: 409
81: 419
82: 421
83: 431
84: 433
85: 439
86: 443
87: 449
88: 457
89: 461
90: 463
91: 467
92: 479
93: 487
94: 491
95: 499
96: 503
97: 509
98: 521
99: 523
100: 541
*/
