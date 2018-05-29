package oo.assignment14;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * @author Ciske Harsema - s1010048
 * @author Michiel Verloop - s1009995
 */
public class Main {

    public static void main(String[] args) {
        //testBuffer();
        testPrimes(100);
    }

    private static void testBuffer() {
        Buffer<Integer> buffer = new Buffer<>(2);
        IntConsumer put = i -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 750));
                //System.out.printf("Putting %d\n", i);
                buffer.put(i);
                //System.out.printf("Put %d\n", i);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while putting");
            }
        };

        new Thread(() -> IntStream.rangeClosed(1, 10).forEach(put)).start();

        for(int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                //System.out.printf("Getting %d\n", i);
                Integer value = buffer.get();

                assert value == i;

                System.out.printf("Got number %d\n", i);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while getting");
            }
        }
    }

    private static void testPrimes(int n) {
        PrimeGenerator generator = new PrimeGenerator(n);

        generator.generate().forEach(System.out::println);
    }

}

/*
First 100 generated primes:
2
3
5
7
11
13
17
19
23
29
31
37
41
43
47
53
59
61
67
71
73
79
83
89
97
101
103
107
109
113
127
131
137
139
149
151
157
163
167
173
179
181
191
193
197
199
211
223
227
229
233
239
241
251
257
263
269
271
277
281
283
293
307
311
313
317
331
337
347
349
353
359
367
373
379
383
389
397
401
409
419
421
431
433
439
443
449
457
461
463
467
479
487
491
499
503
509
521
523
541
*/
