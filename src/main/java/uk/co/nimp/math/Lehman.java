package uk.co.nimp.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: seb
 * Date: 8/10/12
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Lehman {

    public static List<Integer> primeFactors(final int toFactor){
        int n=toFactor;
        List<Integer> factors = new ArrayList<Integer>();

        n=Primes.smallTrialDivision(n,factors);
        if(1==n) return factors;

        int B=(int)Math.cbrt(n);
        assert(B<Primes.PRIMES_LAST);
        /*List<Integer> res=new ArrayList<Integer>();
        n=Primes.boundedTrialDivision(n,B,res);
        if(res.size()>1){//trial div found some factors
            res.remove(res.size()-1);
            factors.addAll(res);
            if(1==n) return factors;
            B=(int)Math.cbrt(n);
        }  */

        //res= bigPrimeFactors(n,B);
        //if(res.size()>1) factors.addAll(res);
        //else factors.add(n);//n is a prime
        int f=factor(n,B);
        if(1!=f){
            factors.add(f);
            factors.add(n/f);
        }else factors.add(n);

        assert(toFactor==product(factors));
        return factors;
    }
    public static int product(List<Integer> numbers){
        int out = 1;
        for(int i:numbers) out*=i;
        return out;
    }
    public static List<Integer> bigPrimeFactors(int n,final int B){
        List<Integer> factors = new ArrayList<Integer>();
        int f=factor(n,B);
        if(1!=f){
            n=n/f;
            List<Integer> ff=bigPrimeFactors(f, B);
            if(ff.isEmpty()) factors.add(f); else factors.addAll(ff);
            List<Integer> nf=bigPrimeFactors(n, B);
            if(nf.isEmpty()) factors.add(n); else factors.addAll(nf);
        }
        return factors;
    }

    static int factor(int n, final int B){
        final long B2=((long)B)*B;
        assert(B2>0);
        for(int k=1;k<=B;k++){
            int r=1;
            int m=2;
            if(1==(k&1)){//k is odd
                r=k+n;
                m=4;
            }

            final long fourKn=(4L*k)*n;
            int a=(int)Math.sqrt(fourKn);
            long a2=((long)a)*a;
            if(a2<fourKn){
                a++;
            }
            final int rModm=r%m;
            while((a%m)!=rModm) a++;
            a2=((long)a)*a;
            assert(a2>0);

            while(a2<(fourKn+B2)){
                //assert((a%m)==rModm);
                long c = a2-fourKn;
                //assert(c>=0);
                int b=(int)Math.sqrt(c);
                //assert(b>=0);
                long b2=((long)b)*b;
                if(b2==c){
                    int sum=a+b;
                    assert(sum>0);
                    int f=Gcd.gcdPositive(sum,n);
                    assert(f>=B);
                    return f;
                }
                a+=m;
                //assert(a>0);
                a2=((long)a)*a;
                //assert(a2>0);
            }
        }
        return 1;
    }



    final static boolean[] qr=new boolean[729];
    static{
        for(int i=0;i<364;i++){
            int j=(i*i)%729;
            qr[j]=true;
        }
    }
    static int factorOrg(int n, int r){
        int[] c=new int[9];//we use index 1 to 8 only

        c[1]= 30;
        int f=large(1,0,n,r,c); if(1!=f) return f;
        c[1]= 48;c[2] = c[3] = c[4] = 24;
        f=large(4,-24,n,r,c); if(1!=f) return f;
        c[1]= c[2] = c[4] = 24;c[3] = 48;
        f=large(4,-12,n,r,c); if(1!=f) return f;
        c[1]= c[2] = c[4] = 36;c[3] = 72;
        f=large(4,-18,n,r,c);

        return f;
    }
    static int large(int m, int m0,int n,long r, int[]c){
        int s=1;
        int k=m0;
        do{
            k=k+c[s];
            assert(k>=0);
            if(s==m) s=1; else s++;
            assert(s>0);
            long prod=(4L*k)*n;
            assert(prod>0);
            int[] xu=isqrt(prod);int x=xu[0],u=xu[1];
            int[] jt=isqrt(n/k);int j=jt[0],t=jt[1];
            j=(int)((j-1)/(4*(r+1)));
            assert(j>=0);

            int i1;
            if(0==((x+k)%2)){
                i1=1;
                u=u+2*x+1;
                x++;
                assert(u>0);
                assert(x>0);
            }else i1=0;

            boolean odd= ((k%2) == 1);
            int jump;
            if(odd){
                jump=4;
                if(((k+n)%4) == (x%4)){
                    i1+=2;
                    u+=4*(x+1);
                    x+=2;
                    assert(i1>0);
                    assert(u>0);
                    assert(x>0);
                }
            } else jump=2;

            for(int i=i1;i<j+1;i+=jump){
                int index=u%729;
                if(qr[index]){
                    int[] yt=isqrt(u);int y=yt[0];t=yt[1];
                    if(0==t){
                        int p=(int)Gcd.gcd(n,x-y);
                        if(p>(n/p)) p=n/p;
                        return p;
                    }
                }
                if(odd){
                    u+=8*(x+2);
                    x+=4;
                } else {
                    u+=4*(x+1);
                    x+=x+2;
                }
                assert(u>0);
                assert(x>0);
            }
        }while(k<=r);
        return 1;
    }
    static int[] isqrt(int n){
        assert(n>=0);
        int j=(int) Math.sqrt(n);
        int prod=j*j;
        if(prod<n){
            j++;
            prod=j*j;
        }
        int u=prod-n;
        return new int[]{j,u};
    }
    static int[] isqrt(long n){
        assert(n>=0);
        int j=(int) Math.sqrt(n);
        int prod=j*j;
        if(prod<n){
            j++;
            prod=j*j;
        }
        int u=(int)(prod-n);
        return new int[]{j,u};
    }
}
