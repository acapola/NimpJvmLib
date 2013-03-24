/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package uk.co.nimp.util;
import java.lang.String;

/**
 * Static methods to convert a type into another, with endianness and bit ordering awareness.<p>
 * The methods names follow a naming rule:
 * <pre>
 * &ltsource type&gt[source endianness][source bit ordering]To&ltdestination type&gt[destination endianness][destination bit ordering]
 * Source/Destination type fields: either of the following. An 's' added at the end indicate an array
 *  - "bool"
 *  - "byte"
 *  - "int"
 *  - "long"
 *  - "Hex": a String containing hexadecimal digits
 *  - "HexDigit": a Char containing an hexadecimal digit
 * Endianness field: little endian is the default, in this case the field is absent. In case of big endian, the field is "Be".
 * Bit ordering: Lsb0 is the default, in this case the field is absent. In case of Msb0, the field is "M0".
 *
 * Example: intBeM0ToHex convert an int with big endian byte order and Msb0 bit order into its hexadecimal string representation
 * </pre>
 * Most of the methods provide only default encoding for destination, this limits the number of ways to do one thing.
 * Unless you are dealing with data from/to outside of the JVM platform, you should not need to use "Be" and "M0" methods.
 * <p>Development status: work on going, only a part of the little endian, Lsb0 methods implemented so far</p>
 * @author Sebastien Riou
 * @version $Id:$
 * @since
 * gpp timestamp: 20130324223306
 */


public class Conversion{

    private static String getInitializedString(int nChars){
        StringBuffer out=new java.lang.StringBuffer(nChars);
        for(int i=0;i<nChars;i++) out.append('0');
        return out.toString();
    }
   /**
     * convert an hexadecimal digit into an int using the default (Lsb0) bit ordering.<p>
     * '1' is converted to 1
     * @param hexDigit the hexadecimal digit to convert
     * @return  an int equals to <code>hexDigit</code>
     */
    public static int hexDigitToInt(char hexDigit) {
        switch(hexDigit){
                case '0': return 0;
                case '1': return 1;
                case '2': return 2;
                case '3': return 3;
                case '4': return 4;
                case '5': return 5;
                case '6': return 6;
                case '7': return 7;
                case '8': return 8;
                case '9': return 9;
                case 'a'://fall through
                case 'A': return 10;
                case 'b'://fall through
                case 'B': return 11;
                case 'c'://fall through
                case 'C': return 12;
                case 'd'://fall through
                case 'D': return 13;
                case 'e'://fall through
                case 'E': return 14;
                case 'f'://fall through
                case 'F': return 15;
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    

   /**
     * convert an hexadecimal digit into an int using the Msb0 bit ordering.<p>
     * '1' is converted to 8
     * @param hexDigit the hexadecimal digit to convert
     * @return  an int equals to <code>hexDigit</code>
     */
    public static int hexDigitM0ToInt(char hexDigit) {
        switch(hexDigit){
                case '0': return 0x0;
                case '1': return 0x8;
                case '2': return 0x4;
                case '3': return 0xC;
                case '4': return 0x2;
                case '5': return 0xA;
                case '6': return 0x6;
                case '7': return 0xE;
                case '8': return 0x1;
                case '9': return 0x9;
                case 'a'://fall through
                case 'A': return 0x5;
                case 'b'://fall through
                case 'B': return 0xD;
                case 'c'://fall through
                case 'C': return 0x3;
                case 'd'://fall through
                case 'D': return 0xB;
                case 'e'://fall through
                case 'E': return 0x7;
                case 'f'://fall through
                case 'F': return 0xF;
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    

    /**
     * convert an hexadecimal digit into binary using the default (Lsb0) bit ordering.<p>
     * '1' is converted as follow: (1, 0, 0, 0)
     * @param hexDigit the hexadecimal digit to convert
     * @return  a boolean array with the binary representation of <code>hexDigit</code>
     */
    public static boolean[] hexDigitToBools(char hexDigit) {
        switch(hexDigit){
                case '0': return new boolean[]{false,false,false,false};
                case '1': return new boolean[]{true,false,false,false};
                case '2': return new boolean[]{false,true,false,false};
                case '3': return new boolean[]{true,true,false,false};
                case '4': return new boolean[]{false,false,true,false};
                case '5': return new boolean[]{true,false,true,false};
                case '6': return new boolean[]{false,true,true,false};
                case '7': return new boolean[]{true,true,true,false};
                case '8': return new boolean[]{false,false,false,true};
                case '9': return new boolean[]{true,false,false,true};
                case 'a'://fall through
                case 'A': return new boolean[]{false,true,false,true};
                case 'b'://fall through
                case 'B': return new boolean[]{true,true,false,true};
                case 'c'://fall through
                case 'C': return new boolean[]{false,false,true,true};
                case 'd'://fall through
                case 'D': return new boolean[]{true,false,true,true};
                case 'e'://fall through
                case 'E': return new boolean[]{false,true,true,true};
                case 'f'://fall through
                case 'F': return new boolean[]{true,true,true,true};
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    

    /**
    * Convert an hexadecimal digit into binary using the Msb0 bit ordering.<p>
    * '1' is converted as follow: (0, 0, 0, 1)
    * @param hexDigit the hexadecimal digit to convert
    * @return  a boolean array with the binary representation of <code>hexDigit</code>
    */
    public static boolean[] hexDigitM0ToBools(char hexDigit) {
        switch(hexDigit){
                case '0': return new boolean[]{false,false,false,false};
                case '1': return new boolean[]{false,false,false,true};
                case '2': return new boolean[]{false,false,true,false};
                case '3': return new boolean[]{false,false,true,true};
                case '4': return new boolean[]{false,true,false,false};
                case '5': return new boolean[]{false,true,false,true};
                case '6': return new boolean[]{false,true,true,false};
                case '7': return new boolean[]{false,true,true,true};
                case '8': return new boolean[]{true,false,false,false};
                case '9': return new boolean[]{true,false,false,true};
                case 'a'://fall through
                case 'A': return new boolean[]{true,false,true,false};
                case 'b'://fall through
                case 'B': return new boolean[]{true,false,true,true};
                case 'c'://fall through
                case 'C': return new boolean[]{true,true,false,false};
                case 'd'://fall through
                case 'D': return new boolean[]{true,true,false,true};
                case 'e'://fall through
                case 'E': return new boolean[]{true,true,true,false};
                case 'f'://fall through
                case 'F': return new boolean[]{true,true,true,true};
            default: throw new IllegalArgumentException("Cannot interpret '"+hexDigit+"' as an hexadecimal digit");
        }
    }
    

    /**
     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '1'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     */
    public static char boolsToHexDigit(boolean[] src){return boolsToHexDigit(src,0);}
    
   /**
     * Convert an array of boolean into a char using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit initial value of the destination char
     * @param dstPos the position of the lsb, in bits, in the result char
     * @param nBools the number of boolean to convert
     * @return a char containing the selected bits
     */
     public static char boolsToHex(
            boolean[] src,
            int srcPos,
            char dstInit,
            int dstPos,
            int nBools){
        if(0==nBools) return dstInit;
        int out = 0;
        if(nBools<4) out = hexDigitToInt(dstInit);
        int shift=0;
        for(int i = 0; i< nBools;i++){
            shift = i*1+dstPos;
            int bits = ((src[i+srcPos]) ? 1 : 0) << shift;
            int mask = 0x1 << shift;
            out = (out & ~mask) | bits;
        }
        if(shift>=4) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 4");
        return intToHexDigit(out);
    }
    /**
         * Convert a char into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
         *
         * @param src the char to convert
         * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position in <code>dst</code> where to copy the result
         * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
         * @return <code>dst</code>
         */
        public static boolean[] hexToBools(
                char src,
                int srcPos,
                boolean[] dst,
                int dstPos,
                int nBools){
            if(0==nBools) return dst;
            int shift=0;
            assert((nBools-1)*1<4-srcPos);
            int srcInt = hexDigitToInt(src);
            for(int i = 0; i< nBools;i++){
                shift = i*1+srcPos;
                int bit = 0x1 & (srcInt >> shift);
                dst[dstPos+i]= (bit!=0);
            }
            return dst;
        } 
    /**
     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '1'
     * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
     */
    public static char boolsToHexDigit(boolean[] src, int srcPos){
        if(src.length>srcPos+3 && src[srcPos+3]){
            if(src.length>srcPos+2 && src[srcPos+2]){
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return 'F';
                    else return 'E';
                }else{
                    if(src[srcPos]) return 'D';
                    else return 'C';
                }
            }else{
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return 'B';
                    else return 'A';
                }else{
                    if(src[srcPos]) return '9';
                    else return '8';
                }
            }
        }else{
            if(src.length>srcPos+2 && src[srcPos+2]){
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return '7';
                    else return '6';
                }else{
                    if(src[srcPos]) return '5';
                    else return '4';
                }
            }else{
                if(src.length>srcPos+1 && src[srcPos+1]){
                    if(src[srcPos]) return '3';
                    else return '2';
                }else{
                    if(src[srcPos]) return '1';
                    else return '0';
                }
            }
        }
    }
    

    /**
     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     * @warning src.length must be >= 4.
     */
    public static char boolsToHexDigitM0_4bits(boolean[] src){return boolsToHexDigitM0_4bits(src,0);}
    
                
   /**
     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
    *  (1,0,0,1,1,0,1,0) with srcPos = 3 is converted to 'D'
    * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
    * @warning src.length must be 8 at most.
    * @warning srcPos+4 must be <= src.length.
    */
   public static char boolsToHexDigitM0_4bits(boolean[] src, int srcPos){
        if(src.length>8) throw new IllegalArgumentException("src.length>8: src.length="+src.length);
        if(src.length-srcPos<4) throw new IllegalArgumentException("src.length-srcPos<4: src.length="+src.length+", srcPos="+srcPos);
        if(src[srcPos+3]){
            if(src[srcPos+2]){
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'F';
                    else return '7';
                }else{
                    if(src[srcPos]) return 'B';
                    else return '3';
                }
            }else{
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'D';
                    else return '5';
                }else{
                    if(src[srcPos]) return '9';
                    else return '1';
                }
            }
        }else{
            if(src[srcPos+2]){
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'E';
                    else return '6';
                }else{
                    if(src[srcPos]) return 'A';
                    else return '2';
                }
            }else{
                if(src[srcPos+1]){
                    if(src[srcPos]) return 'C';
                    else return '4';
                }else{
                    if(src[srcPos]) return '8';
                    else return '0';
                }
            }
        }
    }
    

   /**
     * Convert the first 4 bits of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
    *  (1,0,0,0,0,0,0,0,   0,0,0,0,0,1,0,0) is converted to '4'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     */
    public static char boolsBeM0ToHexDigit(boolean[] src){return boolsBeM0ToHexDigit(src,0);}
    

    /**
     * Convert a part of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
     * (1, 0, 0, 0) with srcPos = 0 is converted as follow: '8'
     * (1,0,0,0,0,0,0,0,   0,0,0,1,0,1,0,0) with srcPos = 2 is converted to '5'
     * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
     */
    public static char boolsBeM0ToHexDigit(boolean[] src, int srcPos){
        int beSrcPos = src.length-1-srcPos;
        int srcLen = Math.min(4,beSrcPos+1);
        boolean []paddedSrc = new boolean[4];
        System.arraycopy(src,beSrcPos+1-srcLen,paddedSrc,4-srcLen,srcLen);
        src = paddedSrc;
        srcPos=0;
        if(src[srcPos]){
            if(src.length>srcPos+1 && src[srcPos+1]){
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return 'F';
                    else return 'E';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return 'D';
                    else return 'C';
                }
            }else{
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return 'B';
                    else return 'A';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return '9';
                    else return '8';
                }
            }
        }else{
            if(src.length>srcPos+1 && src[srcPos+1]){
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return '7';
                    else return '6';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return '5';
                    else return '4';
                }
            }else{
                if(src.length>srcPos+2 && src[srcPos+2]){
                    if(src.length>srcPos+3 && src[srcPos+3]) return '3';
                    else return '2';
                }else{
                    if(src.length>srcPos+3 && src[srcPos+3]) return '1';
                    else return '0';
                }
            }
        }
    }
    

     /**
        * Convert the 4 lsb of an int to an hexadecimal digit.<p>
        * 0 returns '0'<p>
        * 1 returns '1'<p>
        * 10 returns 'A' and so on...
        * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
        * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
        */
    public static char intToHexDigit(int nibble){
        switch(nibble){
            case 0x0: return '0';
            case 0x1: return '1';
            case 0x2: return '2';
            case 0x3: return '3';
            case 0x4: return '4';
            case 0x5: return '5';
            case 0x6: return '6';
            case 0x7: return '7';
            case 0x8: return '8';
            case 0x9: return '9';
            case 0xA: return 'A';
            case 0xB: return 'B';
            case 0xC: return 'C';
            case 0xD: return 'D';
            case 0xE: return 'E';
            case 0xF: return 'F';
            default: throw new java.lang.IllegalArgumentException("nibble value not between 0 and 15: "+nibble);
        }
    }
    

    /**
     * Convert the 4 lsb of an int to an hexadecimal digit encoded using the Msb0 bit ordering.<p>
     * 0 returns '0'<p>
     * 1 returns '8'<p>
     * 10 returns '5' and so on...
     * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
     * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
     */
    public static char intToHexDigitM0(int nibble){
        switch(nibble){
            case 0x0: return '0';
            case 0x1: return '8';
            case 0x2: return '4';
            case 0x3: return 'C';
            case 0x4: return '2';
            case 0x5: return 'A';
            case 0x6: return '6';
            case 0x7: return 'E';
            case 0x8: return '1';
            case 0x9: return '9';
            case 0xA: return '5';
            case 0xB: return 'D';
            case 0xC: return '3';
            case 0xD: return 'B';
            case 0xE: return '7';
            case 0xF: return 'F';
            default: throw new java.lang.IllegalArgumentException("nibble value not between 0 and 15: "+nibble);
        }
    }
    
    
    /**
     * Convert a boolean array into a String using the default (little endian, Lsb0) byte and bit order
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit the initial value of the destination array
     * @param dstPos the position of the lsb, in boolean unit, in the result array
     * @param nBools the number of boolean to convert
     * @return <code>dst</code>, updated with the selected bits
     */
   public static String boolsToHexs(
            boolean[] src,
            int srcPos,
            String dstInit,
            int dstPos,
            int nBools){
        if(0==nBools) return dstInit;
        final int inPerOut = 4;
        int inCnt = 0;
        int inIndex = dstPos % inPerOut;
        int outIndex;
        StringBuffer sb = new StringBuffer(dstInit);
        if(0 != inIndex){
            int l=java.lang.Math.min(inPerOut-inIndex, nBools);
            int bi = dstPos/inPerOut;
            sb.setCharAt(bi,boolsToHex(src,srcPos,dstInit.charAt(bi),inIndex*1,l)); //unaligned start
            inCnt += l;
            outIndex=bi+1;
        } else {
            outIndex=dstPos/inPerOut;
        }
        int nFullOut = (nBools - inCnt)/inPerOut;
        for(int i = 0 ;i< nFullOut;i++){
            sb.setCharAt(outIndex+i,boolsToHex(src,srcPos+inCnt,'0',0,inPerOut));
            inCnt += inPerOut;
        }
        int remaining = nBools-inCnt;
        if(0!=remaining) sb.setCharAt(outIndex+nFullOut,boolsToHex(src,srcPos+inCnt,dstInit.charAt(outIndex+nFullOut),0,remaining));//unaligned end
        return sb.toString();
    }
    /**
     * Convert an array of int into a long using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the int array to convert
     * @param srcPos the position in <code>src</code>, in int unit, from where to start the conversion
     * @param dstInit initial value of the destination long
     * @param dstPos the position of the lsb, in bits, in the result long
     * @param nInts the number of int to convert
     * @return a long containing the selected bits
    */
    public static long intsToLong(
        int[] src,
        int srcPos,
        long dstInit,
        int dstPos,
        int nInts){
        if(0==nInts) return dstInit;
        long out = dstInit;
        int shift=0;
        for(int i = 0; i< nInts;i++){
            shift = i*32+dstPos;
            long bits = (long)(((long)(0xffffffffL & src[i+srcPos])) << shift);
            long mask = (long)(((long) 0xffffffffL) << shift);
            out = (long)((out & ~mask) | bits);
        }
        if(shift>=64) throw new IllegalArgumentException("(nInts-1)*32+dstPos is greather or equal to than 64");
        return out;
    }
            
        public static long intsToLong(
                int[] src,
                int srcPos,
                int dstPos,
                int nInts){
            return intsToLong(src,srcPos,0L,dstPos,nInts);
        }
        public static long intsToLong(
                int[] src,
                int srcPos,
                int nInts){
            return intsToLong(src,srcPos,0L,0,nInts);
        }
        public static long intsToLong(
            int[] src,
            int nInts){
            return intsToLong(src,0,0L,0,nInts);
        }
        public static long intsToLong(
                int[] src){
            return intsToLong(src,0,0L,0,src.length);
        }
                /**
         * Convert a byte array into a long array using the default (little endian, Lsb0) byte and bit order
         * @param src the int array to convert
         * @param srcPos the position in <code>src</code>, in int unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in int unit, in the result array
         * @param nInts the number of int to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static long[] intsToLongs(
        int[] src,
        int srcPos,
        long[] dst,
        int dstPos,
        int nInts){
            if(0==nInts) return dst;
            final int inPerOut = 2;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nInts);
                int bi = dstPos/inPerOut;
                dst[bi]=intsToLong(src,srcPos,dst[bi],inIndex*32,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nInts - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=intsToLong(src,srcPos+inCnt,0L,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nInts-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=intsToLong(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static long[] intsToLongs(
            int[] src,
            int srcPos,
            int dstPos,
            int nInts){
            long[] dstInit = new long[(nInts+1)/2];
            return intsToLongs(src,srcPos,dstInit,dstPos,nInts);
        }
        public static long[] intsToLongs(
            int[] src,
            int srcPos,
            int nInts){
            long[] dstInit = new long[(nInts+1)/2];
            return intsToLongs(src,srcPos,dstInit,0,nInts);
        }
        public static long[] intsToLongs(
            int[] src,
            int nInts){
            long[] dstInit = new long[(nInts+1)/2];
            return intsToLongs(src,0,dstInit,0,nInts);
        }
        public static long[] intsToLongs(
            int[] src){
            long[] dstInit = new long[(src.length+1)/2];
            return intsToLongs(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of short into a long using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the short array to convert
     * @param srcPos the position in <code>src</code>, in short unit, from where to start the conversion
     * @param dstInit initial value of the destination long
     * @param dstPos the position of the lsb, in bits, in the result long
     * @param nShorts the number of short to convert
     * @return a long containing the selected bits
    */
    public static long shortsToLong(
        short[] src,
        int srcPos,
        long dstInit,
        int dstPos,
        int nShorts){
        if(0==nShorts) return dstInit;
        long out = dstInit;
        int shift=0;
        for(int i = 0; i< nShorts;i++){
            shift = i*16+dstPos;
            long bits = (long)(((long)(0xffffL & src[i+srcPos])) << shift);
            long mask = (long)(((long) 0xffffL) << shift);
            out = (long)((out & ~mask) | bits);
        }
        if(shift>=64) throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 64");
        return out;
    }
            
        public static long shortsToLong(
                short[] src,
                int srcPos,
                int dstPos,
                int nShorts){
            return shortsToLong(src,srcPos,0L,dstPos,nShorts);
        }
        public static long shortsToLong(
                short[] src,
                int srcPos,
                int nShorts){
            return shortsToLong(src,srcPos,0L,0,nShorts);
        }
        public static long shortsToLong(
            short[] src,
            int nShorts){
            return shortsToLong(src,0,0L,0,nShorts);
        }
        public static long shortsToLong(
                short[] src){
            return shortsToLong(src,0,0L,0,src.length);
        }
                /**
         * Convert a byte array into a long array using the default (little endian, Lsb0) byte and bit order
         * @param src the short array to convert
         * @param srcPos the position in <code>src</code>, in short unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in short unit, in the result array
         * @param nShorts the number of short to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static long[] shortsToLongs(
        short[] src,
        int srcPos,
        long[] dst,
        int dstPos,
        int nShorts){
            if(0==nShorts) return dst;
            final int inPerOut = 4;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nShorts);
                int bi = dstPos/inPerOut;
                dst[bi]=shortsToLong(src,srcPos,dst[bi],inIndex*16,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nShorts - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=shortsToLong(src,srcPos+inCnt,0L,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nShorts-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=shortsToLong(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static long[] shortsToLongs(
            short[] src,
            int srcPos,
            int dstPos,
            int nShorts){
            long[] dstInit = new long[(nShorts+3)/4];
            return shortsToLongs(src,srcPos,dstInit,dstPos,nShorts);
        }
        public static long[] shortsToLongs(
            short[] src,
            int srcPos,
            int nShorts){
            long[] dstInit = new long[(nShorts+3)/4];
            return shortsToLongs(src,srcPos,dstInit,0,nShorts);
        }
        public static long[] shortsToLongs(
            short[] src,
            int nShorts){
            long[] dstInit = new long[(nShorts+3)/4];
            return shortsToLongs(src,0,dstInit,0,nShorts);
        }
        public static long[] shortsToLongs(
            short[] src){
            long[] dstInit = new long[(src.length+3)/4];
            return shortsToLongs(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of short into a int using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the short array to convert
     * @param srcPos the position in <code>src</code>, in short unit, from where to start the conversion
     * @param dstInit initial value of the destination int
     * @param dstPos the position of the lsb, in bits, in the result int
     * @param nShorts the number of short to convert
     * @return a int containing the selected bits
    */
    public static int shortsToInt(
        short[] src,
        int srcPos,
        int dstInit,
        int dstPos,
        int nShorts){
        if(0==nShorts) return dstInit;
        int out = dstInit;
        int shift=0;
        for(int i = 0; i< nShorts;i++){
            shift = i*16+dstPos;
            int bits = (int)(((int)(0xffff & src[i+srcPos])) << shift);
            int mask = (int)(((int) 0xffff) << shift);
            out = (int)((out & ~mask) | bits);
        }
        if(shift>=32) throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 32");
        return out;
    }
            
        public static int shortsToInt(
                short[] src,
                int srcPos,
                int dstPos,
                int nShorts){
            return shortsToInt(src,srcPos,0,dstPos,nShorts);
        }
        public static int shortsToInt(
                short[] src,
                int srcPos,
                int nShorts){
            return shortsToInt(src,srcPos,0,0,nShorts);
        }
        public static int shortsToInt(
            short[] src,
            int nShorts){
            return shortsToInt(src,0,0,0,nShorts);
        }
        public static int shortsToInt(
                short[] src){
            return shortsToInt(src,0,0,0,src.length);
        }
                /**
         * Convert a byte array into a int array using the default (little endian, Lsb0) byte and bit order
         * @param src the short array to convert
         * @param srcPos the position in <code>src</code>, in short unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in short unit, in the result array
         * @param nShorts the number of short to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static int[] shortsToInts(
        short[] src,
        int srcPos,
        int[] dst,
        int dstPos,
        int nShorts){
            if(0==nShorts) return dst;
            final int inPerOut = 2;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nShorts);
                int bi = dstPos/inPerOut;
                dst[bi]=shortsToInt(src,srcPos,dst[bi],inIndex*16,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nShorts - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=shortsToInt(src,srcPos+inCnt,0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nShorts-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=shortsToInt(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static int[] shortsToInts(
            short[] src,
            int srcPos,
            int dstPos,
            int nShorts){
            int[] dstInit = new int[(nShorts+1)/2];
            return shortsToInts(src,srcPos,dstInit,dstPos,nShorts);
        }
        public static int[] shortsToInts(
            short[] src,
            int srcPos,
            int nShorts){
            int[] dstInit = new int[(nShorts+1)/2];
            return shortsToInts(src,srcPos,dstInit,0,nShorts);
        }
        public static int[] shortsToInts(
            short[] src,
            int nShorts){
            int[] dstInit = new int[(nShorts+1)/2];
            return shortsToInts(src,0,dstInit,0,nShorts);
        }
        public static int[] shortsToInts(
            short[] src){
            int[] dstInit = new int[(src.length+1)/2];
            return shortsToInts(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of byte into a long using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the byte array to convert
     * @param srcPos the position in <code>src</code>, in byte unit, from where to start the conversion
     * @param dstInit initial value of the destination long
     * @param dstPos the position of the lsb, in bits, in the result long
     * @param nBytes the number of byte to convert
     * @return a long containing the selected bits
    */
    public static long bytesToLong(
        byte[] src,
        int srcPos,
        long dstInit,
        int dstPos,
        int nBytes){
        if(0==nBytes) return dstInit;
        long out = dstInit;
        int shift=0;
        for(int i = 0; i< nBytes;i++){
            shift = i*8+dstPos;
            long bits = (long)(((long)(0xffL & src[i+srcPos])) << shift);
            long mask = (long)(((long) 0xffL) << shift);
            out = (long)((out & ~mask) | bits);
        }
        if(shift>=64) throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 64");
        return out;
    }
            
        public static long bytesToLong(
                byte[] src,
                int srcPos,
                int dstPos,
                int nBytes){
            return bytesToLong(src,srcPos,0L,dstPos,nBytes);
        }
        public static long bytesToLong(
                byte[] src,
                int srcPos,
                int nBytes){
            return bytesToLong(src,srcPos,0L,0,nBytes);
        }
        public static long bytesToLong(
            byte[] src,
            int nBytes){
            return bytesToLong(src,0,0L,0,nBytes);
        }
        public static long bytesToLong(
                byte[] src){
            return bytesToLong(src,0,0L,0,src.length);
        }
                /**
         * Convert a byte array into a long array using the default (little endian, Lsb0) byte and bit order
         * @param src the byte array to convert
         * @param srcPos the position in <code>src</code>, in byte unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in byte unit, in the result array
         * @param nBytes the number of byte to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static long[] bytesToLongs(
        byte[] src,
        int srcPos,
        long[] dst,
        int dstPos,
        int nBytes){
            if(0==nBytes) return dst;
            final int inPerOut = 8;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBytes);
                int bi = dstPos/inPerOut;
                dst[bi]=bytesToLong(src,srcPos,dst[bi],inIndex*8,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBytes - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=bytesToLong(src,srcPos+inCnt,0L,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBytes-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=bytesToLong(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static long[] bytesToLongs(
            byte[] src,
            int srcPos,
            int dstPos,
            int nBytes){
            long[] dstInit = new long[(nBytes+7)/8];
            return bytesToLongs(src,srcPos,dstInit,dstPos,nBytes);
        }
        public static long[] bytesToLongs(
            byte[] src,
            int srcPos,
            int nBytes){
            long[] dstInit = new long[(nBytes+7)/8];
            return bytesToLongs(src,srcPos,dstInit,0,nBytes);
        }
        public static long[] bytesToLongs(
            byte[] src,
            int nBytes){
            long[] dstInit = new long[(nBytes+7)/8];
            return bytesToLongs(src,0,dstInit,0,nBytes);
        }
        public static long[] bytesToLongs(
            byte[] src){
            long[] dstInit = new long[(src.length+7)/8];
            return bytesToLongs(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of byte into a int using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the byte array to convert
     * @param srcPos the position in <code>src</code>, in byte unit, from where to start the conversion
     * @param dstInit initial value of the destination int
     * @param dstPos the position of the lsb, in bits, in the result int
     * @param nBytes the number of byte to convert
     * @return a int containing the selected bits
    */
    public static int bytesToInt(
        byte[] src,
        int srcPos,
        int dstInit,
        int dstPos,
        int nBytes){
        if(0==nBytes) return dstInit;
        int out = dstInit;
        int shift=0;
        for(int i = 0; i< nBytes;i++){
            shift = i*8+dstPos;
            int bits = (int)(((int)(0xff & src[i+srcPos])) << shift);
            int mask = (int)(((int) 0xff) << shift);
            out = (int)((out & ~mask) | bits);
        }
        if(shift>=32) throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 32");
        return out;
    }
            
        public static int bytesToInt(
                byte[] src,
                int srcPos,
                int dstPos,
                int nBytes){
            return bytesToInt(src,srcPos,0,dstPos,nBytes);
        }
        public static int bytesToInt(
                byte[] src,
                int srcPos,
                int nBytes){
            return bytesToInt(src,srcPos,0,0,nBytes);
        }
        public static int bytesToInt(
            byte[] src,
            int nBytes){
            return bytesToInt(src,0,0,0,nBytes);
        }
        public static int bytesToInt(
                byte[] src){
            return bytesToInt(src,0,0,0,src.length);
        }
                /**
         * Convert a byte array into a int array using the default (little endian, Lsb0) byte and bit order
         * @param src the byte array to convert
         * @param srcPos the position in <code>src</code>, in byte unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in byte unit, in the result array
         * @param nBytes the number of byte to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static int[] bytesToInts(
        byte[] src,
        int srcPos,
        int[] dst,
        int dstPos,
        int nBytes){
            if(0==nBytes) return dst;
            final int inPerOut = 4;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBytes);
                int bi = dstPos/inPerOut;
                dst[bi]=bytesToInt(src,srcPos,dst[bi],inIndex*8,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBytes - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=bytesToInt(src,srcPos+inCnt,0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBytes-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=bytesToInt(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static int[] bytesToInts(
            byte[] src,
            int srcPos,
            int dstPos,
            int nBytes){
            int[] dstInit = new int[(nBytes+3)/4];
            return bytesToInts(src,srcPos,dstInit,dstPos,nBytes);
        }
        public static int[] bytesToInts(
            byte[] src,
            int srcPos,
            int nBytes){
            int[] dstInit = new int[(nBytes+3)/4];
            return bytesToInts(src,srcPos,dstInit,0,nBytes);
        }
        public static int[] bytesToInts(
            byte[] src,
            int nBytes){
            int[] dstInit = new int[(nBytes+3)/4];
            return bytesToInts(src,0,dstInit,0,nBytes);
        }
        public static int[] bytesToInts(
            byte[] src){
            int[] dstInit = new int[(src.length+3)/4];
            return bytesToInts(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of byte into a short using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the byte array to convert
     * @param srcPos the position in <code>src</code>, in byte unit, from where to start the conversion
     * @param dstInit initial value of the destination short
     * @param dstPos the position of the lsb, in bits, in the result short
     * @param nBytes the number of byte to convert
     * @return a short containing the selected bits
    */
    public static short bytesToShort(
        byte[] src,
        int srcPos,
        short dstInit,
        int dstPos,
        int nBytes){
        if(0==nBytes) return dstInit;
        short out = dstInit;
        int shift=0;
        for(int i = 0; i< nBytes;i++){
            shift = i*8+dstPos;
            short bits = (short)(((short)(0xff & src[i+srcPos])) << shift);
            short mask = (short)(((short) 0xff) << shift);
            out = (short)((out & ~mask) | bits);
        }
        if(shift>=16) throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 16");
        return out;
    }
            
        public static short bytesToShort(
                byte[] src,
                int srcPos,
                int dstPos,
                int nBytes){
            return bytesToShort(src,srcPos,(short)0,dstPos,nBytes);
        }
        public static short bytesToShort(
                byte[] src,
                int srcPos,
                int nBytes){
            return bytesToShort(src,srcPos,(short)0,0,nBytes);
        }
        public static short bytesToShort(
            byte[] src,
            int nBytes){
            return bytesToShort(src,0,(short)0,0,nBytes);
        }
        public static short bytesToShort(
                byte[] src){
            return bytesToShort(src,0,(short)0,0,src.length);
        }
                /**
         * Convert a byte array into a short array using the default (little endian, Lsb0) byte and bit order
         * @param src the byte array to convert
         * @param srcPos the position in <code>src</code>, in byte unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in byte unit, in the result array
         * @param nBytes the number of byte to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static short[] bytesToShorts(
        byte[] src,
        int srcPos,
        short[] dst,
        int dstPos,
        int nBytes){
            if(0==nBytes) return dst;
            final int inPerOut = 2;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBytes);
                int bi = dstPos/inPerOut;
                dst[bi]=bytesToShort(src,srcPos,dst[bi],inIndex*8,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBytes - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=bytesToShort(src,srcPos+inCnt,(short)0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBytes-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=bytesToShort(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static short[] bytesToShorts(
            byte[] src,
            int srcPos,
            int dstPos,
            int nBytes){
            short[] dstInit = new short[(nBytes+1)/2];
            return bytesToShorts(src,srcPos,dstInit,dstPos,nBytes);
        }
        public static short[] bytesToShorts(
            byte[] src,
            int srcPos,
            int nBytes){
            short[] dstInit = new short[(nBytes+1)/2];
            return bytesToShorts(src,srcPos,dstInit,0,nBytes);
        }
        public static short[] bytesToShorts(
            byte[] src,
            int nBytes){
            short[] dstInit = new short[(nBytes+1)/2];
            return bytesToShorts(src,0,dstInit,0,nBytes);
        }
        public static short[] bytesToShorts(
            byte[] src){
            short[] dstInit = new short[(src.length+1)/2];
            return bytesToShorts(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of char into a long using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the char array to convert
     * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
     * @param dstInit initial value of the destination long
     * @param dstPos the position of the lsb, in bits, in the result long
     * @param nHexs the number of char to convert
     * @return a long containing the selected bits
    */
    public static long hexsToLong(
        String src,
        int srcPos,
        long dstInit,
        int dstPos,
        int nHexs){
        if(0==nHexs) return dstInit;
        long out = dstInit;
        int shift=0;
        for(int i = 0; i< nHexs;i++){
            shift = i*4+dstPos;
            long bits = (long)(((long)(0xfL & hexDigitToInt(src.charAt(i+srcPos)))) << shift);
            long mask = (long)(((long) 0xfL) << shift);
            out = (long)((out & ~mask) | bits);
        }
        if(shift>=64) throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 64");
        return out;
    }
            
        public static long hexsToLong(
                String src,
                int srcPos,
                int dstPos,
                int nHexs){
            return hexsToLong(src,srcPos,0L,dstPos,nHexs);
        }
        public static long hexsToLong(
                String src,
                int srcPos,
                int nHexs){
            return hexsToLong(src,srcPos,0L,0,nHexs);
        }
        public static long hexsToLong(
            String src,
            int nHexs){
            return hexsToLong(src,0,0L,0,nHexs);
        }
        public static long hexsToLong(
                String src){
            return hexsToLong(src,0,0L,0,src.length());
        }
                /**
         * Convert a byte array into a long array using the default (little endian, Lsb0) byte and bit order
         * @param src the char array to convert
         * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in char unit, in the result array
         * @param nHexs the number of char to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static long[] hexsToLongs(
        String src,
        int srcPos,
        long[] dst,
        int dstPos,
        int nHexs){
            if(0==nHexs) return dst;
            final int inPerOut = 16;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nHexs);
                int bi = dstPos/inPerOut;
                dst[bi]=hexsToLong(src,srcPos,dst[bi],inIndex*4,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nHexs - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=hexsToLong(src,srcPos+inCnt,0L,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nHexs-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=hexsToLong(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static long[] hexsToLongs(
            String src,
            int srcPos,
            int dstPos,
            int nHexs){
            long[] dstInit = new long[(nHexs+15)/16];
            return hexsToLongs(src,srcPos,dstInit,dstPos,nHexs);
        }
        public static long[] hexsToLongs(
            String src,
            int srcPos,
            int nHexs){
            long[] dstInit = new long[(nHexs+15)/16];
            return hexsToLongs(src,srcPos,dstInit,0,nHexs);
        }
        public static long[] hexsToLongs(
            String src,
            int nHexs){
            long[] dstInit = new long[(nHexs+15)/16];
            return hexsToLongs(src,0,dstInit,0,nHexs);
        }
        public static long[] hexsToLongs(
            String src){
            long[] dstInit = new long[(src.length()+15)/16];
            return hexsToLongs(src,0,dstInit,0,src.length());
        }
        /**
     * Convert an array of char into a int using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the char array to convert
     * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
     * @param dstInit initial value of the destination int
     * @param dstPos the position of the lsb, in bits, in the result int
     * @param nHexs the number of char to convert
     * @return a int containing the selected bits
    */
    public static int hexsToInt(
        String src,
        int srcPos,
        int dstInit,
        int dstPos,
        int nHexs){
        if(0==nHexs) return dstInit;
        int out = dstInit;
        int shift=0;
        for(int i = 0; i< nHexs;i++){
            shift = i*4+dstPos;
            int bits = (int)(((int)(0xf & hexDigitToInt(src.charAt(i+srcPos)))) << shift);
            int mask = (int)(((int) 0xf) << shift);
            out = (int)((out & ~mask) | bits);
        }
        if(shift>=32) throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 32");
        return out;
    }
            
        public static int hexsToInt(
                String src,
                int srcPos,
                int dstPos,
                int nHexs){
            return hexsToInt(src,srcPos,0,dstPos,nHexs);
        }
        public static int hexsToInt(
                String src,
                int srcPos,
                int nHexs){
            return hexsToInt(src,srcPos,0,0,nHexs);
        }
        public static int hexsToInt(
            String src,
            int nHexs){
            return hexsToInt(src,0,0,0,nHexs);
        }
        public static int hexsToInt(
                String src){
            return hexsToInt(src,0,0,0,src.length());
        }
                /**
         * Convert a byte array into a int array using the default (little endian, Lsb0) byte and bit order
         * @param src the char array to convert
         * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in char unit, in the result array
         * @param nHexs the number of char to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static int[] hexsToInts(
        String src,
        int srcPos,
        int[] dst,
        int dstPos,
        int nHexs){
            if(0==nHexs) return dst;
            final int inPerOut = 8;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nHexs);
                int bi = dstPos/inPerOut;
                dst[bi]=hexsToInt(src,srcPos,dst[bi],inIndex*4,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nHexs - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=hexsToInt(src,srcPos+inCnt,0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nHexs-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=hexsToInt(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static int[] hexsToInts(
            String src,
            int srcPos,
            int dstPos,
            int nHexs){
            int[] dstInit = new int[(nHexs+7)/8];
            return hexsToInts(src,srcPos,dstInit,dstPos,nHexs);
        }
        public static int[] hexsToInts(
            String src,
            int srcPos,
            int nHexs){
            int[] dstInit = new int[(nHexs+7)/8];
            return hexsToInts(src,srcPos,dstInit,0,nHexs);
        }
        public static int[] hexsToInts(
            String src,
            int nHexs){
            int[] dstInit = new int[(nHexs+7)/8];
            return hexsToInts(src,0,dstInit,0,nHexs);
        }
        public static int[] hexsToInts(
            String src){
            int[] dstInit = new int[(src.length()+7)/8];
            return hexsToInts(src,0,dstInit,0,src.length());
        }
        /**
     * Convert an array of char into a short using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the char array to convert
     * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
     * @param dstInit initial value of the destination short
     * @param dstPos the position of the lsb, in bits, in the result short
     * @param nHexs the number of char to convert
     * @return a short containing the selected bits
    */
    public static short hexsToShort(
        String src,
        int srcPos,
        short dstInit,
        int dstPos,
        int nHexs){
        if(0==nHexs) return dstInit;
        short out = dstInit;
        int shift=0;
        for(int i = 0; i< nHexs;i++){
            shift = i*4+dstPos;
            short bits = (short)(((short)(0xf & hexDigitToInt(src.charAt(i+srcPos)))) << shift);
            short mask = (short)(((short) 0xf) << shift);
            out = (short)((out & ~mask) | bits);
        }
        if(shift>=16) throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 16");
        return out;
    }
            
        public static short hexsToShort(
                String src,
                int srcPos,
                int dstPos,
                int nHexs){
            return hexsToShort(src,srcPos,(short)0,dstPos,nHexs);
        }
        public static short hexsToShort(
                String src,
                int srcPos,
                int nHexs){
            return hexsToShort(src,srcPos,(short)0,0,nHexs);
        }
        public static short hexsToShort(
            String src,
            int nHexs){
            return hexsToShort(src,0,(short)0,0,nHexs);
        }
        public static short hexsToShort(
                String src){
            return hexsToShort(src,0,(short)0,0,src.length());
        }
                /**
         * Convert a byte array into a short array using the default (little endian, Lsb0) byte and bit order
         * @param src the char array to convert
         * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in char unit, in the result array
         * @param nHexs the number of char to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static short[] hexsToShorts(
        String src,
        int srcPos,
        short[] dst,
        int dstPos,
        int nHexs){
            if(0==nHexs) return dst;
            final int inPerOut = 4;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nHexs);
                int bi = dstPos/inPerOut;
                dst[bi]=hexsToShort(src,srcPos,dst[bi],inIndex*4,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nHexs - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=hexsToShort(src,srcPos+inCnt,(short)0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nHexs-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=hexsToShort(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static short[] hexsToShorts(
            String src,
            int srcPos,
            int dstPos,
            int nHexs){
            short[] dstInit = new short[(nHexs+3)/4];
            return hexsToShorts(src,srcPos,dstInit,dstPos,nHexs);
        }
        public static short[] hexsToShorts(
            String src,
            int srcPos,
            int nHexs){
            short[] dstInit = new short[(nHexs+3)/4];
            return hexsToShorts(src,srcPos,dstInit,0,nHexs);
        }
        public static short[] hexsToShorts(
            String src,
            int nHexs){
            short[] dstInit = new short[(nHexs+3)/4];
            return hexsToShorts(src,0,dstInit,0,nHexs);
        }
        public static short[] hexsToShorts(
            String src){
            short[] dstInit = new short[(src.length()+3)/4];
            return hexsToShorts(src,0,dstInit,0,src.length());
        }
        /**
     * Convert an array of char into a byte using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the char array to convert
     * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
     * @param dstInit initial value of the destination byte
     * @param dstPos the position of the lsb, in bits, in the result byte
     * @param nHexs the number of char to convert
     * @return a byte containing the selected bits
    */
    public static byte hexsToByte(
        String src,
        int srcPos,
        byte dstInit,
        int dstPos,
        int nHexs){
        if(0==nHexs) return dstInit;
        byte out = dstInit;
        int shift=0;
        for(int i = 0; i< nHexs;i++){
            shift = i*4+dstPos;
            byte bits = (byte)(((byte)(0xf & hexDigitToInt(src.charAt(i+srcPos)))) << shift);
            byte mask = (byte)(((byte) 0xf) << shift);
            out = (byte)((out & ~mask) | bits);
        }
        if(shift>=8) throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 8");
        return out;
    }
            
        public static byte hexsToByte(
                String src,
                int srcPos,
                int dstPos,
                int nHexs){
            return hexsToByte(src,srcPos,(byte)0,dstPos,nHexs);
        }
        public static byte hexsToByte(
                String src,
                int srcPos,
                int nHexs){
            return hexsToByte(src,srcPos,(byte)0,0,nHexs);
        }
        public static byte hexsToByte(
            String src,
            int nHexs){
            return hexsToByte(src,0,(byte)0,0,nHexs);
        }
        public static byte hexsToByte(
                String src){
            return hexsToByte(src,0,(byte)0,0,src.length());
        }
                /**
         * Convert a byte array into a byte array using the default (little endian, Lsb0) byte and bit order
         * @param src the char array to convert
         * @param srcPos the position in <code>src</code>, in char unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in char unit, in the result array
         * @param nHexs the number of char to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static byte[] hexsToBytes(
        String src,
        int srcPos,
        byte[] dst,
        int dstPos,
        int nHexs){
            if(0==nHexs) return dst;
            final int inPerOut = 2;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nHexs);
                int bi = dstPos/inPerOut;
                dst[bi]=hexsToByte(src,srcPos,dst[bi],inIndex*4,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nHexs - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=hexsToByte(src,srcPos+inCnt,(byte)0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nHexs-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=hexsToByte(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static byte[] hexsToBytes(
            String src,
            int srcPos,
            int dstPos,
            int nHexs){
            byte[] dstInit = new byte[(nHexs+1)/2];
            return hexsToBytes(src,srcPos,dstInit,dstPos,nHexs);
        }
        public static byte[] hexsToBytes(
            String src,
            int srcPos,
            int nHexs){
            byte[] dstInit = new byte[(nHexs+1)/2];
            return hexsToBytes(src,srcPos,dstInit,0,nHexs);
        }
        public static byte[] hexsToBytes(
            String src,
            int nHexs){
            byte[] dstInit = new byte[(nHexs+1)/2];
            return hexsToBytes(src,0,dstInit,0,nHexs);
        }
        public static byte[] hexsToBytes(
            String src){
            byte[] dstInit = new byte[(src.length()+1)/2];
            return hexsToBytes(src,0,dstInit,0,src.length());
        }
        /**
     * Convert an array of boolean into a long using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit initial value of the destination long
     * @param dstPos the position of the lsb, in bits, in the result long
     * @param nBools the number of boolean to convert
     * @return a long containing the selected bits
    */
    public static long boolsToLong(
        boolean[] src,
        int srcPos,
        long dstInit,
        int dstPos,
        int nBools){
        if(0==nBools) return dstInit;
        long out = dstInit;
        int shift=0;
        for(int i = 0; i< nBools;i++){
            shift = i*1+dstPos;
            long bits = (long)(((long)(0x1L & ((src[i+srcPos]) ? 1 : 0))) << shift);
            long mask = (long)(((long) 0x1L) << shift);
            out = (long)((out & ~mask) | bits);
        }
        if(shift>=64) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 64");
        return out;
    }
            
        public static long boolsToLong(
                boolean[] src,
                int srcPos,
                int dstPos,
                int nBools){
            return boolsToLong(src,srcPos,0L,dstPos,nBools);
        }
        public static long boolsToLong(
                boolean[] src,
                int srcPos,
                int nBools){
            return boolsToLong(src,srcPos,0L,0,nBools);
        }
        public static long boolsToLong(
            boolean[] src,
            int nBools){
            return boolsToLong(src,0,0L,0,nBools);
        }
        public static long boolsToLong(
                boolean[] src){
            return boolsToLong(src,0,0L,0,src.length);
        }
                /**
         * Convert a byte array into a long array using the default (little endian, Lsb0) byte and bit order
         * @param src the boolean array to convert
         * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in boolean unit, in the result array
         * @param nBools the number of boolean to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static long[] boolsToLongs(
        boolean[] src,
        int srcPos,
        long[] dst,
        int dstPos,
        int nBools){
            if(0==nBools) return dst;
            final int inPerOut = 64;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBools);
                int bi = dstPos/inPerOut;
                dst[bi]=boolsToLong(src,srcPos,dst[bi],inIndex*1,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBools - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=boolsToLong(src,srcPos+inCnt,0L,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBools-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=boolsToLong(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static long[] boolsToLongs(
            boolean[] src,
            int srcPos,
            int dstPos,
            int nBools){
            long[] dstInit = new long[(nBools+63)/64];
            return boolsToLongs(src,srcPos,dstInit,dstPos,nBools);
        }
        public static long[] boolsToLongs(
            boolean[] src,
            int srcPos,
            int nBools){
            long[] dstInit = new long[(nBools+63)/64];
            return boolsToLongs(src,srcPos,dstInit,0,nBools);
        }
        public static long[] boolsToLongs(
            boolean[] src,
            int nBools){
            long[] dstInit = new long[(nBools+63)/64];
            return boolsToLongs(src,0,dstInit,0,nBools);
        }
        public static long[] boolsToLongs(
            boolean[] src){
            long[] dstInit = new long[(src.length+63)/64];
            return boolsToLongs(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of boolean into a int using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit initial value of the destination int
     * @param dstPos the position of the lsb, in bits, in the result int
     * @param nBools the number of boolean to convert
     * @return a int containing the selected bits
    */
    public static int boolsToInt(
        boolean[] src,
        int srcPos,
        int dstInit,
        int dstPos,
        int nBools){
        if(0==nBools) return dstInit;
        int out = dstInit;
        int shift=0;
        for(int i = 0; i< nBools;i++){
            shift = i*1+dstPos;
            int bits = (int)(((int)(0x1 & ((src[i+srcPos]) ? 1 : 0))) << shift);
            int mask = (int)(((int) 0x1) << shift);
            out = (int)((out & ~mask) | bits);
        }
        if(shift>=32) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 32");
        return out;
    }
            
        public static int boolsToInt(
                boolean[] src,
                int srcPos,
                int dstPos,
                int nBools){
            return boolsToInt(src,srcPos,0,dstPos,nBools);
        }
        public static int boolsToInt(
                boolean[] src,
                int srcPos,
                int nBools){
            return boolsToInt(src,srcPos,0,0,nBools);
        }
        public static int boolsToInt(
            boolean[] src,
            int nBools){
            return boolsToInt(src,0,0,0,nBools);
        }
        public static int boolsToInt(
                boolean[] src){
            return boolsToInt(src,0,0,0,src.length);
        }
                /**
         * Convert a byte array into a int array using the default (little endian, Lsb0) byte and bit order
         * @param src the boolean array to convert
         * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in boolean unit, in the result array
         * @param nBools the number of boolean to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static int[] boolsToInts(
        boolean[] src,
        int srcPos,
        int[] dst,
        int dstPos,
        int nBools){
            if(0==nBools) return dst;
            final int inPerOut = 32;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBools);
                int bi = dstPos/inPerOut;
                dst[bi]=boolsToInt(src,srcPos,dst[bi],inIndex*1,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBools - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=boolsToInt(src,srcPos+inCnt,0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBools-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=boolsToInt(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static int[] boolsToInts(
            boolean[] src,
            int srcPos,
            int dstPos,
            int nBools){
            int[] dstInit = new int[(nBools+31)/32];
            return boolsToInts(src,srcPos,dstInit,dstPos,nBools);
        }
        public static int[] boolsToInts(
            boolean[] src,
            int srcPos,
            int nBools){
            int[] dstInit = new int[(nBools+31)/32];
            return boolsToInts(src,srcPos,dstInit,0,nBools);
        }
        public static int[] boolsToInts(
            boolean[] src,
            int nBools){
            int[] dstInit = new int[(nBools+31)/32];
            return boolsToInts(src,0,dstInit,0,nBools);
        }
        public static int[] boolsToInts(
            boolean[] src){
            int[] dstInit = new int[(src.length+31)/32];
            return boolsToInts(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of boolean into a short using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit initial value of the destination short
     * @param dstPos the position of the lsb, in bits, in the result short
     * @param nBools the number of boolean to convert
     * @return a short containing the selected bits
    */
    public static short boolsToShort(
        boolean[] src,
        int srcPos,
        short dstInit,
        int dstPos,
        int nBools){
        if(0==nBools) return dstInit;
        short out = dstInit;
        int shift=0;
        for(int i = 0; i< nBools;i++){
            shift = i*1+dstPos;
            short bits = (short)(((short)(0x1 & ((src[i+srcPos]) ? 1 : 0))) << shift);
            short mask = (short)(((short) 0x1) << shift);
            out = (short)((out & ~mask) | bits);
        }
        if(shift>=16) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 16");
        return out;
    }
            
        public static short boolsToShort(
                boolean[] src,
                int srcPos,
                int dstPos,
                int nBools){
            return boolsToShort(src,srcPos,(short)0,dstPos,nBools);
        }
        public static short boolsToShort(
                boolean[] src,
                int srcPos,
                int nBools){
            return boolsToShort(src,srcPos,(short)0,0,nBools);
        }
        public static short boolsToShort(
            boolean[] src,
            int nBools){
            return boolsToShort(src,0,(short)0,0,nBools);
        }
        public static short boolsToShort(
                boolean[] src){
            return boolsToShort(src,0,(short)0,0,src.length);
        }
                /**
         * Convert a byte array into a short array using the default (little endian, Lsb0) byte and bit order
         * @param src the boolean array to convert
         * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in boolean unit, in the result array
         * @param nBools the number of boolean to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static short[] boolsToShorts(
        boolean[] src,
        int srcPos,
        short[] dst,
        int dstPos,
        int nBools){
            if(0==nBools) return dst;
            final int inPerOut = 16;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBools);
                int bi = dstPos/inPerOut;
                dst[bi]=boolsToShort(src,srcPos,dst[bi],inIndex*1,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBools - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=boolsToShort(src,srcPos+inCnt,(short)0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBools-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=boolsToShort(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static short[] boolsToShorts(
            boolean[] src,
            int srcPos,
            int dstPos,
            int nBools){
            short[] dstInit = new short[(nBools+15)/16];
            return boolsToShorts(src,srcPos,dstInit,dstPos,nBools);
        }
        public static short[] boolsToShorts(
            boolean[] src,
            int srcPos,
            int nBools){
            short[] dstInit = new short[(nBools+15)/16];
            return boolsToShorts(src,srcPos,dstInit,0,nBools);
        }
        public static short[] boolsToShorts(
            boolean[] src,
            int nBools){
            short[] dstInit = new short[(nBools+15)/16];
            return boolsToShorts(src,0,dstInit,0,nBools);
        }
        public static short[] boolsToShorts(
            boolean[] src){
            short[] dstInit = new short[(src.length+15)/16];
            return boolsToShorts(src,0,dstInit,0,src.length);
        }
        /**
     * Convert an array of boolean into a byte using the default (little endian, Lsb0) byte and bit ordering.<p>
     * @param src the boolean array to convert
     * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
     * @param dstInit initial value of the destination byte
     * @param dstPos the position of the lsb, in bits, in the result byte
     * @param nBools the number of boolean to convert
     * @return a byte containing the selected bits
    */
    public static byte boolsToByte(
        boolean[] src,
        int srcPos,
        byte dstInit,
        int dstPos,
        int nBools){
        if(0==nBools) return dstInit;
        byte out = dstInit;
        int shift=0;
        for(int i = 0; i< nBools;i++){
            shift = i*1+dstPos;
            byte bits = (byte)(((byte)(0x1 & ((src[i+srcPos]) ? 1 : 0))) << shift);
            byte mask = (byte)(((byte) 0x1) << shift);
            out = (byte)((out & ~mask) | bits);
        }
        if(shift>=8) throw new IllegalArgumentException("(nBools-1)*1+dstPos is greather or equal to than 8");
        return out;
    }
            
        public static byte boolsToByte(
                boolean[] src,
                int srcPos,
                int dstPos,
                int nBools){
            return boolsToByte(src,srcPos,(byte)0,dstPos,nBools);
        }
        public static byte boolsToByte(
                boolean[] src,
                int srcPos,
                int nBools){
            return boolsToByte(src,srcPos,(byte)0,0,nBools);
        }
        public static byte boolsToByte(
            boolean[] src,
            int nBools){
            return boolsToByte(src,0,(byte)0,0,nBools);
        }
        public static byte boolsToByte(
                boolean[] src){
            return boolsToByte(src,0,(byte)0,0,src.length);
        }
                /**
         * Convert a byte array into a byte array using the default (little endian, Lsb0) byte and bit order
         * @param src the boolean array to convert
         * @param srcPos the position in <code>src</code>, in boolean unit, from where to start the conversion
         * @param dst the destination array
         * @param dstPos the position of the lsb, in boolean unit, in the result array
         * @param nBools the number of boolean to convert
         * @return <code>dst</code>, updated with the selected bits
         */
    public static byte[] boolsToBytes(
        boolean[] src,
        int srcPos,
        byte[] dst,
        int dstPos,
        int nBools){
            if(0==nBools) return dst;
            final int inPerOut = 8;
            int inCnt = 0;
            int inIndex = dstPos % inPerOut;
            int outIndex;
            if(0 != inIndex){
                int l=java.lang.Math.min(inPerOut-inIndex, nBools);
                int bi = dstPos/inPerOut;
                dst[bi]=boolsToByte(src,srcPos,dst[bi],inIndex*1,l); //unaligned start
                inCnt += l;
                outIndex=bi+1;
            } else {
                outIndex=dstPos/inPerOut;
            }
            int nFullOut = (nBools - inCnt)/inPerOut;
            for(int i = 0 ;i< nFullOut;i++){
                dst[outIndex+i]=boolsToByte(src,srcPos+inCnt,(byte)0,0,inPerOut);
                inCnt += inPerOut;
            }
            int remaining = nBools-inCnt;
            if(0!=remaining) dst[outIndex+nFullOut]=boolsToByte(src,srcPos+inCnt,dst[outIndex+nFullOut],0,remaining);//unaligned end
            return dst;
        }
                
        public static byte[] boolsToBytes(
            boolean[] src,
            int srcPos,
            int dstPos,
            int nBools){
            byte[] dstInit = new byte[(nBools+7)/8];
            return boolsToBytes(src,srcPos,dstInit,dstPos,nBools);
        }
        public static byte[] boolsToBytes(
            boolean[] src,
            int srcPos,
            int nBools){
            byte[] dstInit = new byte[(nBools+7)/8];
            return boolsToBytes(src,srcPos,dstInit,0,nBools);
        }
        public static byte[] boolsToBytes(
            boolean[] src,
            int nBools){
            byte[] dstInit = new byte[(nBools+7)/8];
            return boolsToBytes(src,0,dstInit,0,nBools);
        }
        public static byte[] boolsToBytes(
            boolean[] src){
            byte[] dstInit = new byte[(src.length+7)/8];
            return boolsToBytes(src,0,dstInit,0,src.length);
        }
            
    /**
     * Convert part of a char array into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the char array to convert
     * @param srcPos the position in <code>src</code>:  the number of boolean from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static boolean[] hexsToBools(
            String src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
        if(0==nBools) return dst;
        final int booleanPerChar = 4;
        int outCnt = 0;
        final int outIndex = srcPos % booleanPerChar;
        int inIndex=srcPos/booleanPerChar;
        if(0 != outIndex){
            final int nOutFirst=Math.min(booleanPerChar-outIndex, nBools);
            dst=hexToBools(src.charAt(inIndex),outIndex*1,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBools - outCnt)/booleanPerChar;
        for(int i = 0;i<nFullIns;i++){
            dst=hexToBools(src.charAt(inIndex+i),0,dst,dstPos+outCnt,booleanPerChar);
            outCnt += booleanPerChar;
        }
        final int remaining = nBools-outCnt;
        if(0!=remaining) dst=hexToBools(src.charAt(inIndex+nFullIns),0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static char boolsToHex(
                boolean[] src,
                int srcPos,
                int dstPos,
                int nBools){
            return boolsToHex(src,srcPos,'0',dstPos,nBools);
        }
        public static char boolsToHex(
                boolean[] src,
                int srcPos,
                int nBools){
            return boolsToHex(src,srcPos,'0',0,nBools);
        }
        public static char boolsToHex(
            boolean[] src,
            int nBools){
            return boolsToHex(src,0,'0',0,nBools);
        }
        public static char boolsToHex(
                boolean[] src){
            return boolsToHex(src,0,'0',0,src.length);
        }
                
        public static String boolsToHexs(
            boolean[] src,
            int srcPos,
            int dstPos,
            int nBools){
            String dstInit = getInitializedString((nBools+3)/4);
            return boolsToHexs(src,srcPos,dstInit,dstPos,nBools);
        }
        public static String boolsToHexs(
            boolean[] src,
            int srcPos,
            int nBools){
            String dstInit = getInitializedString((nBools+3)/4);
            return boolsToHexs(src,srcPos,dstInit,0,nBools);
        }
        public static String boolsToHexs(
            boolean[] src,
            int nBools){
            String dstInit = getInitializedString((nBools+3)/4);
            return boolsToHexs(src,0,dstInit,0,nBools);
        }
        public static String boolsToHexs(
            boolean[] src){
            String dstInit = getInitializedString((src.length+3)/4);
            return boolsToHexs(src,0,dstInit,0,src.length);
        }
            
        public static boolean[] hexToBools(
        char src,
        int srcPos,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return hexToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] hexToBools(
        char src,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return hexToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] hexToBools(
        char src){
            boolean[] dstInit = new boolean[4];
            return hexToBools(src,0,dstInit,0,4);
        }
                
        public static boolean[] hexsToBools(
            String src,
            int srcPos,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return hexsToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] hexsToBools(
            String src,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return hexsToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] hexsToBools(
            String src){
            boolean[] dstInit = new boolean[4];
            return hexsToBools(src,0,dstInit,0,4);
        }
                
        /**
             * Convert a long into an array of  int using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the long to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nInts the number of int to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static int[] longToInts(
            long src,
            int srcPos,
            int[] dst,
            int dstPos,
            int nInts){
                if(0==nInts) return dst;
                int shift=0;
                assert((nInts-1)*32<64-srcPos);
                for(int i = 0; i< nInts;i++){
                    shift = i*32+srcPos;
                    Long bits = (long)(0xffffffff & (src >> shift));
                    dst[dstPos+i]=                bits.intValue();
                }
                return dst;
            }
                
        public static int[] longToInts(
        long src,
        int srcPos,
        int nInts){
            int[] dstInit = new int[nInts];
            return longToInts(src,srcPos,dstInit,0,nInts);
        }
        public static int[] longToInts(
        long src,
        int nInts){
            int[] dstInit = new int[nInts];
            return longToInts(src,0,dstInit,0,nInts);
        }
        public static int[] longToInts(
        long src){
            int[] dstInit = new int[2];
            return longToInts(src,0,dstInit,0,2);
        }
                
    /**
     * Convert part of a long array into an array of  int using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the long array to convert
     * @param srcPos the position in <code>src</code>:  the number of int from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nInts the number of int to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static int[] longsToInts(
            long[] src,
            int srcPos,
            int[] dst,
            int dstPos,
            int nInts){
        if(0==nInts) return dst;
        final int intPerLong = 2;
        int outCnt = 0;
        final int outIndex = srcPos % intPerLong;
        int inIndex=srcPos/intPerLong;
        if(0 != outIndex){
            final int nOutFirst=Math.min(intPerLong-outIndex, nInts);
            dst=longToInts(src[inIndex],outIndex*32,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nInts - outCnt)/intPerLong;
        for(int i = 0;i<nFullIns;i++){
            dst=longToInts(src[inIndex+i],0,dst,dstPos+outCnt,intPerLong);
            outCnt += intPerLong;
        }
        final int remaining = nInts-outCnt;
        if(0!=remaining) dst=longToInts(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static int[] longsToInts(
            long[] src,
            int srcPos,
            int nInts){
            int[] dstInit = new int[nInts];
            return longsToInts(src,srcPos,dstInit,0,nInts);
        }
        public static int[] longsToInts(
            long[] src,
            int nInts){
            int[] dstInit = new int[nInts];
            return longsToInts(src,0,dstInit,0,nInts);
        }
        public static int[] longsToInts(
            long[] src){
            int[] dstInit = new int[2];
            return longsToInts(src,0,dstInit,0,2);
        }
                
        /**
             * Convert a long into an array of  short using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the long to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nShorts the number of short to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static short[] longToShorts(
            long src,
            int srcPos,
            short[] dst,
            int dstPos,
            int nShorts){
                if(0==nShorts) return dst;
                int shift=0;
                assert((nShorts-1)*16<64-srcPos);
                for(int i = 0; i< nShorts;i++){
                    shift = i*16+srcPos;
                    Long bits = (long)(0xffff & (src >> shift));
                    dst[dstPos+i]=                bits.shortValue();
                }
                return dst;
            }
                
        public static short[] longToShorts(
        long src,
        int srcPos,
        int nShorts){
            short[] dstInit = new short[nShorts];
            return longToShorts(src,srcPos,dstInit,0,nShorts);
        }
        public static short[] longToShorts(
        long src,
        int nShorts){
            short[] dstInit = new short[nShorts];
            return longToShorts(src,0,dstInit,0,nShorts);
        }
        public static short[] longToShorts(
        long src){
            short[] dstInit = new short[4];
            return longToShorts(src,0,dstInit,0,4);
        }
                
    /**
     * Convert part of a long array into an array of  short using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the long array to convert
     * @param srcPos the position in <code>src</code>:  the number of short from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nShorts the number of short to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static short[] longsToShorts(
            long[] src,
            int srcPos,
            short[] dst,
            int dstPos,
            int nShorts){
        if(0==nShorts) return dst;
        final int shortPerLong = 4;
        int outCnt = 0;
        final int outIndex = srcPos % shortPerLong;
        int inIndex=srcPos/shortPerLong;
        if(0 != outIndex){
            final int nOutFirst=Math.min(shortPerLong-outIndex, nShorts);
            dst=longToShorts(src[inIndex],outIndex*16,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nShorts - outCnt)/shortPerLong;
        for(int i = 0;i<nFullIns;i++){
            dst=longToShorts(src[inIndex+i],0,dst,dstPos+outCnt,shortPerLong);
            outCnt += shortPerLong;
        }
        final int remaining = nShorts-outCnt;
        if(0!=remaining) dst=longToShorts(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static short[] longsToShorts(
            long[] src,
            int srcPos,
            int nShorts){
            short[] dstInit = new short[nShorts];
            return longsToShorts(src,srcPos,dstInit,0,nShorts);
        }
        public static short[] longsToShorts(
            long[] src,
            int nShorts){
            short[] dstInit = new short[nShorts];
            return longsToShorts(src,0,dstInit,0,nShorts);
        }
        public static short[] longsToShorts(
            long[] src){
            short[] dstInit = new short[4];
            return longsToShorts(src,0,dstInit,0,4);
        }
                
        /**
             * Convert a int into an array of  short using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the int to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nShorts the number of short to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static short[] intToShorts(
            int src,
            int srcPos,
            short[] dst,
            int dstPos,
            int nShorts){
                if(0==nShorts) return dst;
                int shift=0;
                assert((nShorts-1)*16<32-srcPos);
                for(int i = 0; i< nShorts;i++){
                    shift = i*16+srcPos;
                    Integer bits = (int)(0xffff & (src >> shift));
                    dst[dstPos+i]=                bits.shortValue();
                }
                return dst;
            }
                
        public static short[] intToShorts(
        int src,
        int srcPos,
        int nShorts){
            short[] dstInit = new short[nShorts];
            return intToShorts(src,srcPos,dstInit,0,nShorts);
        }
        public static short[] intToShorts(
        int src,
        int nShorts){
            short[] dstInit = new short[nShorts];
            return intToShorts(src,0,dstInit,0,nShorts);
        }
        public static short[] intToShorts(
        int src){
            short[] dstInit = new short[2];
            return intToShorts(src,0,dstInit,0,2);
        }
                
    /**
     * Convert part of a int array into an array of  short using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the int array to convert
     * @param srcPos the position in <code>src</code>:  the number of short from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nShorts the number of short to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static short[] intsToShorts(
            int[] src,
            int srcPos,
            short[] dst,
            int dstPos,
            int nShorts){
        if(0==nShorts) return dst;
        final int shortPerInt = 2;
        int outCnt = 0;
        final int outIndex = srcPos % shortPerInt;
        int inIndex=srcPos/shortPerInt;
        if(0 != outIndex){
            final int nOutFirst=Math.min(shortPerInt-outIndex, nShorts);
            dst=intToShorts(src[inIndex],outIndex*16,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nShorts - outCnt)/shortPerInt;
        for(int i = 0;i<nFullIns;i++){
            dst=intToShorts(src[inIndex+i],0,dst,dstPos+outCnt,shortPerInt);
            outCnt += shortPerInt;
        }
        final int remaining = nShorts-outCnt;
        if(0!=remaining) dst=intToShorts(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static short[] intsToShorts(
            int[] src,
            int srcPos,
            int nShorts){
            short[] dstInit = new short[nShorts];
            return intsToShorts(src,srcPos,dstInit,0,nShorts);
        }
        public static short[] intsToShorts(
            int[] src,
            int nShorts){
            short[] dstInit = new short[nShorts];
            return intsToShorts(src,0,dstInit,0,nShorts);
        }
        public static short[] intsToShorts(
            int[] src){
            short[] dstInit = new short[2];
            return intsToShorts(src,0,dstInit,0,2);
        }
                
        /**
             * Convert a long into an array of  byte using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the long to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBytes the number of byte to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static byte[] longToBytes(
            long src,
            int srcPos,
            byte[] dst,
            int dstPos,
            int nBytes){
                if(0==nBytes) return dst;
                int shift=0;
                assert((nBytes-1)*8<64-srcPos);
                for(int i = 0; i< nBytes;i++){
                    shift = i*8+srcPos;
                    Long bits = (long)(0xff & (src >> shift));
                    dst[dstPos+i]=                bits.byteValue();
                }
                return dst;
            }
                
        public static byte[] longToBytes(
        long src,
        int srcPos,
        int nBytes){
            byte[] dstInit = new byte[nBytes];
            return longToBytes(src,srcPos,dstInit,0,nBytes);
        }
        public static byte[] longToBytes(
        long src,
        int nBytes){
            byte[] dstInit = new byte[nBytes];
            return longToBytes(src,0,dstInit,0,nBytes);
        }
        public static byte[] longToBytes(
        long src){
            byte[] dstInit = new byte[8];
            return longToBytes(src,0,dstInit,0,8);
        }
                
    /**
     * Convert part of a long array into an array of  byte using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the long array to convert
     * @param srcPos the position in <code>src</code>:  the number of byte from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBytes the number of byte to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static byte[] longsToBytes(
            long[] src,
            int srcPos,
            byte[] dst,
            int dstPos,
            int nBytes){
        if(0==nBytes) return dst;
        final int bytePerLong = 8;
        int outCnt = 0;
        final int outIndex = srcPos % bytePerLong;
        int inIndex=srcPos/bytePerLong;
        if(0 != outIndex){
            final int nOutFirst=Math.min(bytePerLong-outIndex, nBytes);
            dst=longToBytes(src[inIndex],outIndex*8,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBytes - outCnt)/bytePerLong;
        for(int i = 0;i<nFullIns;i++){
            dst=longToBytes(src[inIndex+i],0,dst,dstPos+outCnt,bytePerLong);
            outCnt += bytePerLong;
        }
        final int remaining = nBytes-outCnt;
        if(0!=remaining) dst=longToBytes(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static byte[] longsToBytes(
            long[] src,
            int srcPos,
            int nBytes){
            byte[] dstInit = new byte[nBytes];
            return longsToBytes(src,srcPos,dstInit,0,nBytes);
        }
        public static byte[] longsToBytes(
            long[] src,
            int nBytes){
            byte[] dstInit = new byte[nBytes];
            return longsToBytes(src,0,dstInit,0,nBytes);
        }
        public static byte[] longsToBytes(
            long[] src){
            byte[] dstInit = new byte[8];
            return longsToBytes(src,0,dstInit,0,8);
        }
                
        /**
             * Convert a int into an array of  byte using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the int to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBytes the number of byte to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static byte[] intToBytes(
            int src,
            int srcPos,
            byte[] dst,
            int dstPos,
            int nBytes){
                if(0==nBytes) return dst;
                int shift=0;
                assert((nBytes-1)*8<32-srcPos);
                for(int i = 0; i< nBytes;i++){
                    shift = i*8+srcPos;
                    Integer bits = (int)(0xff & (src >> shift));
                    dst[dstPos+i]=                bits.byteValue();
                }
                return dst;
            }
                
        public static byte[] intToBytes(
        int src,
        int srcPos,
        int nBytes){
            byte[] dstInit = new byte[nBytes];
            return intToBytes(src,srcPos,dstInit,0,nBytes);
        }
        public static byte[] intToBytes(
        int src,
        int nBytes){
            byte[] dstInit = new byte[nBytes];
            return intToBytes(src,0,dstInit,0,nBytes);
        }
        public static byte[] intToBytes(
        int src){
            byte[] dstInit = new byte[4];
            return intToBytes(src,0,dstInit,0,4);
        }
                
    /**
     * Convert part of a int array into an array of  byte using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the int array to convert
     * @param srcPos the position in <code>src</code>:  the number of byte from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBytes the number of byte to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static byte[] intsToBytes(
            int[] src,
            int srcPos,
            byte[] dst,
            int dstPos,
            int nBytes){
        if(0==nBytes) return dst;
        final int bytePerInt = 4;
        int outCnt = 0;
        final int outIndex = srcPos % bytePerInt;
        int inIndex=srcPos/bytePerInt;
        if(0 != outIndex){
            final int nOutFirst=Math.min(bytePerInt-outIndex, nBytes);
            dst=intToBytes(src[inIndex],outIndex*8,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBytes - outCnt)/bytePerInt;
        for(int i = 0;i<nFullIns;i++){
            dst=intToBytes(src[inIndex+i],0,dst,dstPos+outCnt,bytePerInt);
            outCnt += bytePerInt;
        }
        final int remaining = nBytes-outCnt;
        if(0!=remaining) dst=intToBytes(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static byte[] intsToBytes(
            int[] src,
            int srcPos,
            int nBytes){
            byte[] dstInit = new byte[nBytes];
            return intsToBytes(src,srcPos,dstInit,0,nBytes);
        }
        public static byte[] intsToBytes(
            int[] src,
            int nBytes){
            byte[] dstInit = new byte[nBytes];
            return intsToBytes(src,0,dstInit,0,nBytes);
        }
        public static byte[] intsToBytes(
            int[] src){
            byte[] dstInit = new byte[4];
            return intsToBytes(src,0,dstInit,0,4);
        }
                
        /**
             * Convert a short into an array of  byte using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the short to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBytes the number of byte to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static byte[] shortToBytes(
            short src,
            int srcPos,
            byte[] dst,
            int dstPos,
            int nBytes){
                if(0==nBytes) return dst;
                int shift=0;
                assert((nBytes-1)*8<16-srcPos);
                for(int i = 0; i< nBytes;i++){
                    shift = i*8+srcPos;
                    Short bits = (short)(0xff & (src >> shift));
                    dst[dstPos+i]=                bits.byteValue();
                }
                return dst;
            }
                
        public static byte[] shortToBytes(
        short src,
        int srcPos,
        int nBytes){
            byte[] dstInit = new byte[nBytes];
            return shortToBytes(src,srcPos,dstInit,0,nBytes);
        }
        public static byte[] shortToBytes(
        short src,
        int nBytes){
            byte[] dstInit = new byte[nBytes];
            return shortToBytes(src,0,dstInit,0,nBytes);
        }
        public static byte[] shortToBytes(
        short src){
            byte[] dstInit = new byte[2];
            return shortToBytes(src,0,dstInit,0,2);
        }
                
    /**
     * Convert part of a short array into an array of  byte using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the short array to convert
     * @param srcPos the position in <code>src</code>:  the number of byte from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBytes the number of byte to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static byte[] shortsToBytes(
            short[] src,
            int srcPos,
            byte[] dst,
            int dstPos,
            int nBytes){
        if(0==nBytes) return dst;
        final int bytePerShort = 2;
        int outCnt = 0;
        final int outIndex = srcPos % bytePerShort;
        int inIndex=srcPos/bytePerShort;
        if(0 != outIndex){
            final int nOutFirst=Math.min(bytePerShort-outIndex, nBytes);
            dst=shortToBytes(src[inIndex],outIndex*8,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBytes - outCnt)/bytePerShort;
        for(int i = 0;i<nFullIns;i++){
            dst=shortToBytes(src[inIndex+i],0,dst,dstPos+outCnt,bytePerShort);
            outCnt += bytePerShort;
        }
        final int remaining = nBytes-outCnt;
        if(0!=remaining) dst=shortToBytes(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static byte[] shortsToBytes(
            short[] src,
            int srcPos,
            int nBytes){
            byte[] dstInit = new byte[nBytes];
            return shortsToBytes(src,srcPos,dstInit,0,nBytes);
        }
        public static byte[] shortsToBytes(
            short[] src,
            int nBytes){
            byte[] dstInit = new byte[nBytes];
            return shortsToBytes(src,0,dstInit,0,nBytes);
        }
        public static byte[] shortsToBytes(
            short[] src){
            byte[] dstInit = new byte[2];
            return shortsToBytes(src,0,dstInit,0,2);
        }
                
        /**
                 * Convert a long into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
                 *
                 * @param src the long to convert
                 * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
                 * @param dstInit the initial value for the result String
                 * @param dstPos the position in <code>dst</code> where to copy the result
                 * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
                 * @return <code>dst</code>
                 */
        public static String longToHexs(
            long src,
            int srcPos,
            String dstInit,
            int dstPos,
            int nHexs){
                if(0==nHexs) return dstInit;
                StringBuffer sb = new StringBuffer(dstInit);
                int shift=0;
                assert((nHexs-1)*4<64-srcPos);
                for(int i = 0; i< nHexs;i++){
                    shift = i*4+srcPos;
                    int bits = (int)(0xF & (src >> shift));
                    sb.setCharAt(dstPos+i,intToHexDigit(bits));
                }
                return sb.toString();
            }
                
        public static String longToHexs(
        long src,
        int srcPos,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return longToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String longToHexs(
        long src,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return longToHexs(src,0,dstInit,0,nHexs);
        }
        public static String longToHexs(
        long src){
            String dstInit = "0000000000000000";
            return longToHexs(src,0,dstInit,0,16);
        }
                
    /**
     * Convert part of a long array into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the long array to convert
     * @param srcPos the position in <code>src</code>:  the number of char from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static String longsToHexs(
            long[] src,
            int srcPos,
            String dst,
            int dstPos,
            int nHexs){
        if(0==nHexs) return dst;
        final int charPerLong = 16;
        int outCnt = 0;
        final int outIndex = srcPos % charPerLong;
        int inIndex=srcPos/charPerLong;
        if(0 != outIndex){
            final int nOutFirst=Math.min(charPerLong-outIndex, nHexs);
            dst=longToHexs(src[inIndex],outIndex*4,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nHexs - outCnt)/charPerLong;
        for(int i = 0;i<nFullIns;i++){
            dst=longToHexs(src[inIndex+i],0,dst,dstPos+outCnt,charPerLong);
            outCnt += charPerLong;
        }
        final int remaining = nHexs-outCnt;
        if(0!=remaining) dst=longToHexs(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static String longsToHexs(
            long[] src,
            int srcPos,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return longsToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String longsToHexs(
            long[] src,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return longsToHexs(src,0,dstInit,0,nHexs);
        }
        public static String longsToHexs(
            long[] src){
            String dstInit = "0000000000000000";
            return longsToHexs(src,0,dstInit,0,16);
        }
                
        /**
                 * Convert a int into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
                 *
                 * @param src the int to convert
                 * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
                 * @param dstInit the initial value for the result String
                 * @param dstPos the position in <code>dst</code> where to copy the result
                 * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
                 * @return <code>dst</code>
                 */
        public static String intToHexs(
            int src,
            int srcPos,
            String dstInit,
            int dstPos,
            int nHexs){
                if(0==nHexs) return dstInit;
                StringBuffer sb = new StringBuffer(dstInit);
                int shift=0;
                assert((nHexs-1)*4<32-srcPos);
                for(int i = 0; i< nHexs;i++){
                    shift = i*4+srcPos;
                    int bits = (int)(0xF & (src >> shift));
                    sb.setCharAt(dstPos+i,intToHexDigit(bits));
                }
                return sb.toString();
            }
                
        public static String intToHexs(
        int src,
        int srcPos,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return intToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String intToHexs(
        int src,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return intToHexs(src,0,dstInit,0,nHexs);
        }
        public static String intToHexs(
        int src){
            String dstInit = "00000000";
            return intToHexs(src,0,dstInit,0,8);
        }
                
    /**
     * Convert part of a int array into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the int array to convert
     * @param srcPos the position in <code>src</code>:  the number of char from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static String intsToHexs(
            int[] src,
            int srcPos,
            String dst,
            int dstPos,
            int nHexs){
        if(0==nHexs) return dst;
        final int charPerInt = 8;
        int outCnt = 0;
        final int outIndex = srcPos % charPerInt;
        int inIndex=srcPos/charPerInt;
        if(0 != outIndex){
            final int nOutFirst=Math.min(charPerInt-outIndex, nHexs);
            dst=intToHexs(src[inIndex],outIndex*4,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nHexs - outCnt)/charPerInt;
        for(int i = 0;i<nFullIns;i++){
            dst=intToHexs(src[inIndex+i],0,dst,dstPos+outCnt,charPerInt);
            outCnt += charPerInt;
        }
        final int remaining = nHexs-outCnt;
        if(0!=remaining) dst=intToHexs(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static String intsToHexs(
            int[] src,
            int srcPos,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return intsToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String intsToHexs(
            int[] src,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return intsToHexs(src,0,dstInit,0,nHexs);
        }
        public static String intsToHexs(
            int[] src){
            String dstInit = "00000000";
            return intsToHexs(src,0,dstInit,0,8);
        }
                
        /**
                 * Convert a short into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
                 *
                 * @param src the short to convert
                 * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
                 * @param dstInit the initial value for the result String
                 * @param dstPos the position in <code>dst</code> where to copy the result
                 * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
                 * @return <code>dst</code>
                 */
        public static String shortToHexs(
            short src,
            int srcPos,
            String dstInit,
            int dstPos,
            int nHexs){
                if(0==nHexs) return dstInit;
                StringBuffer sb = new StringBuffer(dstInit);
                int shift=0;
                assert((nHexs-1)*4<16-srcPos);
                for(int i = 0; i< nHexs;i++){
                    shift = i*4+srcPos;
                    int bits = (int)(0xF & (src >> shift));
                    sb.setCharAt(dstPos+i,intToHexDigit(bits));
                }
                return sb.toString();
            }
                
        public static String shortToHexs(
        short src,
        int srcPos,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return shortToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String shortToHexs(
        short src,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return shortToHexs(src,0,dstInit,0,nHexs);
        }
        public static String shortToHexs(
        short src){
            String dstInit = "0000";
            return shortToHexs(src,0,dstInit,0,4);
        }
                
    /**
     * Convert part of a short array into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the short array to convert
     * @param srcPos the position in <code>src</code>:  the number of char from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static String shortsToHexs(
            short[] src,
            int srcPos,
            String dst,
            int dstPos,
            int nHexs){
        if(0==nHexs) return dst;
        final int charPerShort = 4;
        int outCnt = 0;
        final int outIndex = srcPos % charPerShort;
        int inIndex=srcPos/charPerShort;
        if(0 != outIndex){
            final int nOutFirst=Math.min(charPerShort-outIndex, nHexs);
            dst=shortToHexs(src[inIndex],outIndex*4,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nHexs - outCnt)/charPerShort;
        for(int i = 0;i<nFullIns;i++){
            dst=shortToHexs(src[inIndex+i],0,dst,dstPos+outCnt,charPerShort);
            outCnt += charPerShort;
        }
        final int remaining = nHexs-outCnt;
        if(0!=remaining) dst=shortToHexs(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static String shortsToHexs(
            short[] src,
            int srcPos,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return shortsToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String shortsToHexs(
            short[] src,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return shortsToHexs(src,0,dstInit,0,nHexs);
        }
        public static String shortsToHexs(
            short[] src){
            String dstInit = "0000";
            return shortsToHexs(src,0,dstInit,0,4);
        }
                
        /**
                 * Convert a byte into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
                 *
                 * @param src the byte to convert
                 * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
                 * @param dstInit the initial value for the result String
                 * @param dstPos the position in <code>dst</code> where to copy the result
                 * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
                 * @return <code>dst</code>
                 */
        public static String byteToHexs(
            byte src,
            int srcPos,
            String dstInit,
            int dstPos,
            int nHexs){
                if(0==nHexs) return dstInit;
                StringBuffer sb = new StringBuffer(dstInit);
                int shift=0;
                assert((nHexs-1)*4<8-srcPos);
                for(int i = 0; i< nHexs;i++){
                    shift = i*4+srcPos;
                    int bits = (int)(0xF & (src >> shift));
                    sb.setCharAt(dstPos+i,intToHexDigit(bits));
                }
                return sb.toString();
            }
                
        public static String byteToHexs(
        byte src,
        int srcPos,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return byteToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String byteToHexs(
        byte src,
        int nHexs){
            String dstInit = getInitializedString(nHexs);
            return byteToHexs(src,0,dstInit,0,nHexs);
        }
        public static String byteToHexs(
        byte src){
            String dstInit = "00";
            return byteToHexs(src,0,dstInit,0,2);
        }
                
    /**
     * Convert part of a byte array into an array of  char using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the byte array to convert
     * @param srcPos the position in <code>src</code>:  the number of char from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nHexs the number of char to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static String bytesToHexs(
            byte[] src,
            int srcPos,
            String dst,
            int dstPos,
            int nHexs){
        if(0==nHexs) return dst;
        final int charPerByte = 2;
        int outCnt = 0;
        final int outIndex = srcPos % charPerByte;
        int inIndex=srcPos/charPerByte;
        if(0 != outIndex){
            final int nOutFirst=Math.min(charPerByte-outIndex, nHexs);
            dst=byteToHexs(src[inIndex],outIndex*4,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nHexs - outCnt)/charPerByte;
        for(int i = 0;i<nFullIns;i++){
            dst=byteToHexs(src[inIndex+i],0,dst,dstPos+outCnt,charPerByte);
            outCnt += charPerByte;
        }
        final int remaining = nHexs-outCnt;
        if(0!=remaining) dst=byteToHexs(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static String bytesToHexs(
            byte[] src,
            int srcPos,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return bytesToHexs(src,srcPos,dstInit,0,nHexs);
        }
        public static String bytesToHexs(
            byte[] src,
            int nHexs){
            String dstInit = getInitializedString(nHexs);
            return bytesToHexs(src,0,dstInit,0,nHexs);
        }
        public static String bytesToHexs(
            byte[] src){
            String dstInit = "00";
            return bytesToHexs(src,0,dstInit,0,2);
        }
                
        /**
             * Convert a long into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the long to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static boolean[] longToBools(
            long src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
                if(0==nBools) return dst;
                int shift=0;
                assert((nBools-1)*1<64-srcPos);
                for(int i = 0; i< nBools;i++){
                    shift = i*1+srcPos;
                    Long bits = (long)(0x1 & (src >> shift));
                    dst[dstPos+i]=                (bits!=0);
                }
                return dst;
            }
                
        public static boolean[] longToBools(
        long src,
        int srcPos,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return longToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] longToBools(
        long src,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return longToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] longToBools(
        long src){
            boolean[] dstInit = new boolean[64];
            return longToBools(src,0,dstInit,0,64);
        }
                
    /**
     * Convert part of a long array into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the long array to convert
     * @param srcPos the position in <code>src</code>:  the number of boolean from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static boolean[] longsToBools(
            long[] src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
        if(0==nBools) return dst;
        final int booleanPerLong = 64;
        int outCnt = 0;
        final int outIndex = srcPos % booleanPerLong;
        int inIndex=srcPos/booleanPerLong;
        if(0 != outIndex){
            final int nOutFirst=Math.min(booleanPerLong-outIndex, nBools);
            dst=longToBools(src[inIndex],outIndex*1,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBools - outCnt)/booleanPerLong;
        for(int i = 0;i<nFullIns;i++){
            dst=longToBools(src[inIndex+i],0,dst,dstPos+outCnt,booleanPerLong);
            outCnt += booleanPerLong;
        }
        final int remaining = nBools-outCnt;
        if(0!=remaining) dst=longToBools(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static boolean[] longsToBools(
            long[] src,
            int srcPos,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return longsToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] longsToBools(
            long[] src,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return longsToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] longsToBools(
            long[] src){
            boolean[] dstInit = new boolean[64];
            return longsToBools(src,0,dstInit,0,64);
        }
                
        /**
             * Convert a int into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the int to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static boolean[] intToBools(
            int src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
                if(0==nBools) return dst;
                int shift=0;
                assert((nBools-1)*1<32-srcPos);
                for(int i = 0; i< nBools;i++){
                    shift = i*1+srcPos;
                    Integer bits = (int)(0x1 & (src >> shift));
                    dst[dstPos+i]=                (bits!=0);
                }
                return dst;
            }
                
        public static boolean[] intToBools(
        int src,
        int srcPos,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return intToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] intToBools(
        int src,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return intToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] intToBools(
        int src){
            boolean[] dstInit = new boolean[32];
            return intToBools(src,0,dstInit,0,32);
        }
                
    /**
     * Convert part of a int array into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the int array to convert
     * @param srcPos the position in <code>src</code>:  the number of boolean from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static boolean[] intsToBools(
            int[] src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
        if(0==nBools) return dst;
        final int booleanPerInt = 32;
        int outCnt = 0;
        final int outIndex = srcPos % booleanPerInt;
        int inIndex=srcPos/booleanPerInt;
        if(0 != outIndex){
            final int nOutFirst=Math.min(booleanPerInt-outIndex, nBools);
            dst=intToBools(src[inIndex],outIndex*1,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBools - outCnt)/booleanPerInt;
        for(int i = 0;i<nFullIns;i++){
            dst=intToBools(src[inIndex+i],0,dst,dstPos+outCnt,booleanPerInt);
            outCnt += booleanPerInt;
        }
        final int remaining = nBools-outCnt;
        if(0!=remaining) dst=intToBools(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static boolean[] intsToBools(
            int[] src,
            int srcPos,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return intsToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] intsToBools(
            int[] src,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return intsToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] intsToBools(
            int[] src){
            boolean[] dstInit = new boolean[32];
            return intsToBools(src,0,dstInit,0,32);
        }
                
        /**
             * Convert a short into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the short to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static boolean[] shortToBools(
            short src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
                if(0==nBools) return dst;
                int shift=0;
                assert((nBools-1)*1<16-srcPos);
                for(int i = 0; i< nBools;i++){
                    shift = i*1+srcPos;
                    Short bits = (short)(0x1 & (src >> shift));
                    dst[dstPos+i]=                (bits!=0);
                }
                return dst;
            }
                
        public static boolean[] shortToBools(
        short src,
        int srcPos,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return shortToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] shortToBools(
        short src,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return shortToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] shortToBools(
        short src){
            boolean[] dstInit = new boolean[16];
            return shortToBools(src,0,dstInit,0,16);
        }
                
    /**
     * Convert part of a short array into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the short array to convert
     * @param srcPos the position in <code>src</code>:  the number of boolean from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static boolean[] shortsToBools(
            short[] src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
        if(0==nBools) return dst;
        final int booleanPerShort = 16;
        int outCnt = 0;
        final int outIndex = srcPos % booleanPerShort;
        int inIndex=srcPos/booleanPerShort;
        if(0 != outIndex){
            final int nOutFirst=Math.min(booleanPerShort-outIndex, nBools);
            dst=shortToBools(src[inIndex],outIndex*1,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBools - outCnt)/booleanPerShort;
        for(int i = 0;i<nFullIns;i++){
            dst=shortToBools(src[inIndex+i],0,dst,dstPos+outCnt,booleanPerShort);
            outCnt += booleanPerShort;
        }
        final int remaining = nBools-outCnt;
        if(0!=remaining) dst=shortToBools(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static boolean[] shortsToBools(
            short[] src,
            int srcPos,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return shortsToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] shortsToBools(
            short[] src,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return shortsToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] shortsToBools(
            short[] src){
            boolean[] dstInit = new boolean[16];
            return shortsToBools(src,0,dstInit,0,16);
        }
                
        /**
             * Convert a byte into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
             *
             * @param src the byte to convert
             * @param srcPos the position in <code>src</code>, in bits, from where to start the conversion
             * @param dst the destination array
             * @param dstPos the position in <code>dst</code> where to copy the result
             * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
             * @return <code>dst</code>
             */
        public static boolean[] byteToBools(
            byte src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
                if(0==nBools) return dst;
                int shift=0;
                assert((nBools-1)*1<8-srcPos);
                for(int i = 0; i< nBools;i++){
                    shift = i*1+srcPos;
                    Byte bits = (byte)(0x1 & (src >> shift));
                    dst[dstPos+i]=                (bits!=0);
                }
                return dst;
            }
                
        public static boolean[] byteToBools(
        byte src,
        int srcPos,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return byteToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] byteToBools(
        byte src,
        int nBools){
            boolean[] dstInit = new boolean[nBools];
            return byteToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] byteToBools(
        byte src){
            boolean[] dstInit = new boolean[8];
            return byteToBools(src,0,dstInit,0,8);
        }
                
    /**
     * Convert part of a byte array into an array of  boolean using the default (little endian, Lsb0) byte and bit ordering.<p>
     *
     * @param src the byte array to convert
     * @param srcPos the position in <code>src</code>:  the number of boolean from where to start the conversion
     * @param dst the destination array
     * @param dstPos the position in <code>dst</code> where to copy the result
     * @param nBools the number of boolean to copy to <code>dst</code>. must be smaller or equal to the width of the input (from srcPos to msb)
     * @return <code>dst</code>
     */
    public static boolean[] bytesToBools(
            byte[] src,
            int srcPos,
            boolean[] dst,
            int dstPos,
            int nBools){
        if(0==nBools) return dst;
        final int booleanPerByte = 8;
        int outCnt = 0;
        final int outIndex = srcPos % booleanPerByte;
        int inIndex=srcPos/booleanPerByte;
        if(0 != outIndex){
            final int nOutFirst=Math.min(booleanPerByte-outIndex, nBools);
            dst=byteToBools(src[inIndex],outIndex*1,dst,dstPos,nOutFirst); //unaligned start
            outCnt += nOutFirst;
            inIndex++;
        }
        final int nFullIns = (nBools - outCnt)/booleanPerByte;
        for(int i = 0;i<nFullIns;i++){
            dst=byteToBools(src[inIndex+i],0,dst,dstPos+outCnt,booleanPerByte);
            outCnt += booleanPerByte;
        }
        final int remaining = nBools-outCnt;
        if(0!=remaining) dst=byteToBools(src[inIndex+nFullIns],0,dst,dstPos+outCnt,remaining);//unaligned end
        return dst;
    }        
        public static boolean[] bytesToBools(
            byte[] src,
            int srcPos,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return bytesToBools(src,srcPos,dstInit,0,nBools);
        }
        public static boolean[] bytesToBools(
            byte[] src,
            int nBools){
            boolean[] dstInit = new boolean[nBools];
            return bytesToBools(src,0,dstInit,0,nBools);
        }
        public static boolean[] bytesToBools(
            byte[] src){
            boolean[] dstInit = new boolean[8];
            return bytesToBools(src,0,dstInit,0,8);
        }
        


}
