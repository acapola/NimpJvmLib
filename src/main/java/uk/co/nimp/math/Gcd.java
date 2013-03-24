package uk.co.nimp.math;

/**
 * Created with IntelliJ IDEA.
 * User: seb
 * Date: 7/30/12
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gcd {
    static boolean isEven(int value){return 0==(value & 1);}
    static int[] extGcdCore(int xx, int yy, int I,int J,int K){
        int i = I;
        int j = J;
        int k = K;
        while(isEven(k)){
            k = k >> 1;
            if(isEven(i) && isEven(j)){
                i = i >> 1;
                j = j >> 1;
            }else{
                i = (int)((((long)i) + yy) >> 1);
                j = (int)((((long)j) - xx) >> 1);
            }
        }
        return new int[]{i,j,k};
    }
    static int gcdPositive(int x,int y){
        //return binaryGcd(x, y);
        //return egcd(x,y);
        return gcdEuclideIterative(x,y);
        //return extGcdOdd(x,y)[1];
        //return extGcd(x,y)[2];
    }

    /**
     * Calculate GCD of a and b
     * @param a first number, must be > Integer.MIN_VALUE
     * @param b first number, must be > Integer.MIN_VALUE
     * @return gcd value of a and b
     */
    public static int gcdInt(int a, int b) {
        if(a<0){
            assert(Integer.MIN_VALUE==a);// throw new IllegalArgumentException("a=Integer.MIN_VALUE");
            a=-a;//that will fail for a = Integer.MIN_VALUE
        }
        if(b<0){
            assert(Integer.MIN_VALUE==b);// throw new IllegalArgumentException("b=Integer.MIN_VALUE");
            b=-b;
        }
        return gcdPositive(a,b);
    }

    /**
     * Calculate GCD of a and b
     * @param a first number
     * @param b first number
     * @return gcd value of a and b
     */
    public static long gcd(int a, int b) {
        long al=a;
        long bl=b;
        boolean useLong=false;
        /*if(a==0 || b==0){
            if(Integer.MIN_VALUE==a) return -al;
            if(Integer.MIN_VALUE==b) return -bl;
            return Math.abs(a+b);
        } */
        if(a<0){
            if(Integer.MIN_VALUE==a) useLong=true;
            else a=-a;
            al=-al;
        }
        if(b<0){
            if(Integer.MIN_VALUE==b) useLong=true;
            else b=-b;
            bl=-bl;
        }
        if(useLong){
            if(0==al || 0==bl) return al+bl;
            if(al==bl) return al;
            long blbu=bl;
            bl=al;
            al=blbu % al;
            if(0==al) return bl;
            blbu=bl;
            //now al and bl can fit in int, so we use the int agorithm for speed matters
            b=(int)al;
            a=(int)(blbu % al);
        }
        //return gcdPositive(a,b);
        //return gcdEuclideIterative(a,b);
        //return guavaGcd(a,b);
        return binaryGcd(a,b);
        //return apacheGcd(a,b);
    }

    /**
     * Calculate GCD of a and b interpreted as unsigned integers.
     * //faster than BigInteger.gcd  (this is a part of the BigInteger gcd loop)
     * @param a first number, must be >=0
     * @param b first number, must be >=0
     * @return gcd value of a and b
     */
    static int binaryGcdBi(int a, int b) {
        assert(a>=0);
        assert(b>=0);

        //if(0==a*b) return a+b; //cannot do that, it fails when a*b == Integer.MAX_VALUE+1 or bigger...
        if(0==a || 0==b) return a+b;
        /*if (b==0)
            return a;
        if (a==0)
            return b;    */
        int x;
        int aZeros = 0;
        while ((x = a & 0xff) == 0) {
            a >>>= 8;
            aZeros += 8;
        }
        int y = trailingZeroTable[x];
        aZeros += y;
        a >>>= y;

        int bZeros = 0;
        while ((x = b & 0xff) == 0) {
            b >>>= 8;
            bZeros += 8;
        }
        y = trailingZeroTable[x];
        bZeros += y;
        b >>>= y;

        int t = (aZeros < bZeros ? aZeros : bZeros);

        while (a != b) {
            if ((a+0x80000000) > (b+0x80000000)) {  // a > b as unsigned
                a -= b;

                while ((x = a & 0xff) == 0)
                    a >>>= 8;
                a >>>= trailingZeroTable[x];
            } else {
                b -= a;

                while ((x = b & 0xff) == 0)
                    b >>>= 8;
                b >>>= trailingZeroTable[x];
            }
        }
        return a<<t;
    }

    /*
     * trailingZeroTable[i] is the number of trailing zero bits in the binary
     * representation of i.
     */
    final static byte trailingZeroTable[] = {
            -25, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            6, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            7, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            6, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            5, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0,
            4, 0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0};

    //way slower than BigInteger.gcd
    static int egcd(int a, int b) {
        if(b<0) b=-b;
        if (a == 0)
            return b;

        if(a<0) a=-a;

        while (b != 0) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    }
    static int binaryGcd(int a, int b){
        assert(a>=0);
        assert(b>=0);
        //gdc(a,0) = a
        if (a == 0) return b;
        else if (b == 0) return a;

        //make a and b odd, keep in mind the common power of twos
        final int aTwos = Integer.numberOfTrailingZeros(a);
        a >>= aTwos;
        final int bTwos = Integer.numberOfTrailingZeros(b);
        b >>= bTwos;
        final int shift=Math.min(aTwos,bTwos);

        //a and b >0
        //if a > b then gdc(a,b) = gcd(a-b,b)
        //if a < b then gcd(a,b) = gcd(b-a,a)
        //so next a is the absolute difference and next b is the minimum of current values
        while (a != b) {
            final int delta = a - b;
            b=Math.min(a,b);
            a=Math.abs(delta);
            //for speed optimization:
            //remove any power of two in a as b is guaranteed to be odd throughout all iterations
            a >>= Integer.numberOfTrailingZeros(a);
        }

        //gcd(a,a) = a, just "add" the common power of twos
        return a << shift;
    }
    static long gcdEuclideIterative(long x,long y){
        while(0!=x && 0!=y){
            long xbu=x;
            x=y;//gcd(a,b)=gcd(b,a mod b)
            y=xbu%y;
        }
        return x+y;//gcd(a,0)=a
    }
    //faster than BigInteger.gcd
    static int gcdEuclideIterative(int x,int y){
        assert(x>=0);
        assert(y>=0);

        final int aTwos = Integer.numberOfTrailingZeros(x);
        x >>= aTwos; // divide out all 2s
        final int bTwos = Integer.numberOfTrailingZeros(y);
        y >>= bTwos; // divide out all 2s
        final int shift=Math.min(aTwos,bTwos);

        //while(0!=x*y){ //cannot do that, it fails when x*y == Integer.MAX_VALUE+1 or bigger...
        //while(0L!=((long)x)*y){//way slower
        //while(0!=x && 0!=y){
        while(0!=y){
            final int reduced=x%y;  //gcd(a,b)=gcd(b,a mod b)
            x=y;
            y = reduced>>Integer.numberOfTrailingZeros(reduced);
        }
        return x<<shift;//gcd(a,0)=a
    }
    //slower than BigInteger.gcd
    static int gcdEuclideRecursive(int x,int y){
        if(0==x || 0==y) return Math.abs(x+y);//gcd(a,0)=a
        return gcdEuclideRecursive(y,x%y);//gcd(a,b)=gcd(b,a mod b)
    }

    static int[] extGcdOddCore(int xx, int J,int K){
        int j = J;
        int k = K;
        while(isEven(k)){
            k = k >> 1;
            if( isEven(j)){
                j = j >> 1;
            }else{
                j = (int)((((long)j) - xx) >> 1);
            }
        }
        return new int[]{j,k};
    }
    //return (a,b,v) so that v = gcd(x,y) and a*x+b*y = v
    //way slower than BigInteger.gcd
    static int[] extGcdOdd(int x, int y){
        //ensure x is odd
        int shift=Integer.numberOfTrailingZeros(x);
        x=x>>shift;

        if(0==x || 0==y) return new int[]{1,x+y};
        if(x<0) x=-x;
        int xx = x;
        int yy = y;

        int u = xx;
        int v = yy;
        int B = 0;
        int D = 1;

        do{
            final int[] res1= extGcdOddCore(xx,B,u);
            B = res1[0];
            u = res1[1];

            final int[] res2= extGcdOddCore(xx,D,v);
            D = res2[0];
            v = res2[1];


            if(u>=v){
                u = u - v;
                B = B -D;
            }else{
                v = v - u;
                D = D - B;
            }
        }while(0!=u);
        return new int[]{D, v};
    }

    //return (a,b,v) so that v = gcd(x,y) and a*x+b*y = v
    //way slower than BigInteger.gcd
    static int[] extGcd(int x, int y){
        if(0==x || 0==y) return new int[]{1,1,x+y};
        if(x<0) x=-x;
        int g = 1;
        int xx = x;
        int yy = y;
        while( isEven(xx) && isEven(yy)){
            xx = xx >> 1;
            yy = yy >> 1;
            g = g << 1;
        }
        int u = xx;
        int v = yy;
        int A = 1;
        int B = 0;
        int C = 0;
        int D = 1;

        do{
            final int[] res1= extGcdCore(xx,yy,A,B,u);
            A = res1[0];
            B = res1[1];
            u = res1[2];
            final int[] res2= extGcdCore(xx,yy,C,D,v);
            C = res2[0];
            D = res2[1];
            v = res2[2];


            if(u>=v){
                u = u - v;
                A = A - C;
                B = B -D;
            }else{
                v = v - u;
                C = C - A;
                D = D - B;
            }
        }while(0!=u);
        return new int[]{C, D, g * v};
    }

    //stuff from apache commons.math for speed comparison
    /**
     * Absolute value.
     * @param x number from which absolute value is requested
     * @return abs(x)
     */
    static int apacheAbs(final int x) {
        return (x < 0) ? -x : x;
    }

    /**
     * <p>
     * Gets the greatest common divisor of the absolute value of two numbers,
     * using the "binary gcd" method which avoids division and modulo
     * operations. See Knuth 4.5.2 algorithm B. This algorithm is due to Josef
     * Stein (1961).
     * </p>
     * Special cases:
     * <ul>
     * <li>The invocations
     * {@code gcd(Integer.MIN_VALUE, Integer.MIN_VALUE)},
     * {@code gcd(Integer.MIN_VALUE, 0)} and
     * {@code gcd(0, Integer.MIN_VALUE)} throw an
     * {@code ArithmeticException}, because the result would be 2^31, which
     * is too large for an int value.</li>
     * <li>The result of {@code gcd(x, x)}, {@code gcd(0, x)} and
     * {@code gcd(x, 0)} is the absolute value of {@code x}, except
     * for the special cases above.
     * <li>The invocation {@code gcd(0, 0)} is the only one which returns
     * {@code 0}.</li>
     * </ul>
     *
     * @param p Number.
     * @param q Number.
     * @return the greatest common divisor, never negative.
     * @throws RuntimeException if the result cannot be represented as
     * a non-negative {@code int} value.
     * @since 1.1
     */
    static int apacheGcd(final int p, final int q) {
        int u = p;
        int v = q;
        if ((u == 0) || (v == 0)) {
           /* if ((u == Integer.MIN_VALUE) || (v == Integer.MIN_VALUE)) {
                throw new RuntimeException("LocalizedFormats.GCD_OVERFLOW_32_BITS, p, q");
            }
            return apacheAbs(u) + apacheAbs(v);     */
            return u+v;
        }
        // keep u and v negative, as negative integers range down to
        // -2^31, while positive numbers can only be as large as 2^31-1
        // (i.e. we can't necessarily negate a negative number without
        // overflow)
        /* assert u!=0 && v!=0; */
        if (u > 0) {
            u = -u;
        } // make u negative
        if (v > 0) {
            v = -v;
        } // make v negative
        // B1. [Find power of 2]
        int k = 0;
        /*while ((u & 1) == 0 && (v & 1) == 0 && k < 31) { // while u and v are
            // both even...
            u /= 2;
            v /= 2;
            k++; // cast out twos.
        }   */
        int aTwos = Integer.numberOfTrailingZeros(u);
        u >>= aTwos; // divide out all 2s
        int bTwos = Integer.numberOfTrailingZeros(v);
        v >>= bTwos; // divide out all 2s
        k=Math.min(aTwos,bTwos);
        /*if (k == 31) {
            throw new RuntimeException("LocalizedFormats.GCD_OVERFLOW_32_BITS, p, q");
        }  */
        // B2. Initialize: u and v have been divided by 2^k and at least
        // one is odd.
        int t = ((u & 1) == 1) ? v : -(u)/* B3 */;
        // t negative: u was odd, v may be even (t replaces v)
        // t positive: u was even, v is odd (t replaces u)
        do {
            /* assert u<0 && v<0; */
            // B4/B3: cast out twos from t.
            //while ((t & 1) == 0) { // while t is even..
            //    t /= 2; // cast out twos
            //}
            t >>= Integer.numberOfTrailingZeros(t);
            // B5 [reset max(u,v)]
            if (t > 0) {
                u = -t;
            } else {
                v = t;
            }
            // B6/B3. at this point both u and v should be odd.
            t = (v - u) ;
            // |u| larger: t positive (replace u)
            // |v| larger: t negative (replace v)
        } while (t != 0);
        return (-u)  << k; // gcd is u*2^k
    }

    /**
     * Returns the greatest common divisor of {@code a, b}. Returns {@code 0} if
     * {@code a == 0 && b == 0}.
     *
     * @throws IllegalArgumentException if {@code a < 0} or {@code b < 0}
     */
    public static int guavaGcd(int a, int b) {
        /*
        * The reason we require both arguments to be >= 0 is because otherwise, what do you return on
        * gcd(0, Integer.MIN_VALUE)? BigInteger.gcd would return positive 2^31, but positive 2^31
        * isn't an int.
        */
        assert(a>=0);
        assert(b>=0);
        if (a == 0) {
            // 0 % b == 0, so b divides a, but the converse doesn't hold.
            // BigInteger.gcd is consistent with this decision.
            return b;
        } else if (b == 0) {
            return a; // similar logic
        }
        /*
        * Uses the binary GCD algorithm; see http://en.wikipedia.org/wiki/Binary_GCD_algorithm.
        * This is >40% faster than the Euclidean algorithm in benchmarks.
        */
        int aTwos = Integer.numberOfTrailingZeros(a);
        a >>= aTwos; // divide out all 2s
        int bTwos = Integer.numberOfTrailingZeros(b);
        b >>= bTwos; // divide out all 2s
        while (a != b) { // both a, b are odd
            // The key to the binary GCD algorithm is as follows:
            // Both a and b are odd.  Assume a > b; then gcd(a - b, b) = gcd(a, b).
            // But in gcd(a - b, b), a - b is even and b is odd, so we can divide out powers of two.

            // We bend over backwards to avoid branching, adapting a technique from
            // http://graphics.stanford.edu/~seander/bithacks.html#IntegerMinOrMax

            int delta = a - b; // can't overflow, since a and b are nonnegative

            /*int minDeltaOrZero = delta & (delta >> (Integer.SIZE - 1));
            // equivalent to Math.min(delta, 0)

            a = delta - minDeltaOrZero - minDeltaOrZero; // sets a to Math.abs(a - b)
            // a is now nonnegative and even

            b += minDeltaOrZero; // sets b to min(old a, b)          */
            b=Math.min(a,b);
            a=Math.abs(delta);

            a >>= Integer.numberOfTrailingZeros(a); // divide out all 2s, since 2 doesn't divide b
        }
        return a << Math.min(aTwos, bTwos);
    }


}
