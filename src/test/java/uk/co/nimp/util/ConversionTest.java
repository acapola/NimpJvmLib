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
import junit.framework.TestCase;

import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Throwable;
import java.lang.reflect.Method;
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


public class ConversionTest extends TestCase{

    public ConversionTest(String name) {
        super(name);
    }

   /**
     * convert an hexadecimal digit into an int using the default (Lsb0) bit ordering.<p>
     * '1' is converted to 1
     * @param hexDigit the hexadecimal digit to convert
     * @return  an int equals to <code>hexDigit</code>
     */
    public void testHexDigitToInt() throws Exception {
            assertEquals(0,Conversion.hexDigitToInt('0'));
            assertEquals(1,Conversion.hexDigitToInt('1'));
            assertEquals(2,Conversion.hexDigitToInt('2'));
            assertEquals(3,Conversion.hexDigitToInt('3'));
            assertEquals(4,Conversion.hexDigitToInt('4'));
            assertEquals(5,Conversion.hexDigitToInt('5'));
            assertEquals(6,Conversion.hexDigitToInt('6'));
            assertEquals(7,Conversion.hexDigitToInt('7'));
            assertEquals(8,Conversion.hexDigitToInt('8'));
            assertEquals(9,Conversion.hexDigitToInt('9'));
            assertEquals(10,Conversion.hexDigitToInt('A'));
                assertEquals(10,Conversion.hexDigitToInt('a'));
            assertEquals(11,Conversion.hexDigitToInt('B'));
                assertEquals(11,Conversion.hexDigitToInt('b'));
            assertEquals(12,Conversion.hexDigitToInt('C'));
                assertEquals(12,Conversion.hexDigitToInt('c'));
            assertEquals(13,Conversion.hexDigitToInt('D'));
                assertEquals(13,Conversion.hexDigitToInt('d'));
            assertEquals(14,Conversion.hexDigitToInt('E'));
                assertEquals(14,Conversion.hexDigitToInt('e'));
            assertEquals(15,Conversion.hexDigitToInt('F'));
                assertEquals(15,Conversion.hexDigitToInt('f'));
    }

   /**
     * convert an hexadecimal digit into an int using the Msb0 bit ordering.<p>
     * '1' is converted to 8
     * @param hexDigit the hexadecimal digit to convert
     * @return  an int equals to <code>hexDigit</code>
     */
    public void testHexDigitM0ToInt() throws Exception {
            assertEquals(0x0,Conversion.hexDigitM0ToInt('0'));
            assertEquals(0x8,Conversion.hexDigitM0ToInt('1'));
            assertEquals(0x4,Conversion.hexDigitM0ToInt('2'));
            assertEquals(0xC,Conversion.hexDigitM0ToInt('3'));
            assertEquals(0x2,Conversion.hexDigitM0ToInt('4'));
            assertEquals(0xA,Conversion.hexDigitM0ToInt('5'));
            assertEquals(0x6,Conversion.hexDigitM0ToInt('6'));
            assertEquals(0xE,Conversion.hexDigitM0ToInt('7'));
            assertEquals(0x1,Conversion.hexDigitM0ToInt('8'));
            assertEquals(0x9,Conversion.hexDigitM0ToInt('9'));
            assertEquals(0x5,Conversion.hexDigitM0ToInt('A'));
                assertEquals(0x5,Conversion.hexDigitM0ToInt('a'));
            assertEquals(0xD,Conversion.hexDigitM0ToInt('B'));
                assertEquals(0xD,Conversion.hexDigitM0ToInt('b'));
            assertEquals(0x3,Conversion.hexDigitM0ToInt('C'));
                assertEquals(0x3,Conversion.hexDigitM0ToInt('c'));
            assertEquals(0xB,Conversion.hexDigitM0ToInt('D'));
                assertEquals(0xB,Conversion.hexDigitM0ToInt('d'));
            assertEquals(0x7,Conversion.hexDigitM0ToInt('E'));
                assertEquals(0x7,Conversion.hexDigitM0ToInt('e'));
            assertEquals(0xF,Conversion.hexDigitM0ToInt('F'));
                assertEquals(0xF,Conversion.hexDigitM0ToInt('f'));
    }

    /**
     * convert an hexadecimal digit into binary using the default (Lsb0) bit ordering.<p>
     * '1' is converted as follow: (1, 0, 0, 0)
     * @param hexDigit the hexadecimal digit to convert
     * @return  a boolean array with the binary representation of <code>hexDigit</code>
     */
    public void testHexDigitToBools() throws Exception {
            assertArrayEquals(new boolean[]{false, false, false, false}, Conversion.hexDigitToBools('0'));
            assertArrayEquals(new boolean[]{true, false, false, false}, Conversion.hexDigitToBools('1'));
            assertArrayEquals(new boolean[]{false, true, false, false}, Conversion.hexDigitToBools('2'));
            assertArrayEquals(new boolean[]{true, true, false, false}, Conversion.hexDigitToBools('3'));
            assertArrayEquals(new boolean[]{false, false, true, false}, Conversion.hexDigitToBools('4'));
            assertArrayEquals(new boolean[]{true, false, true, false}, Conversion.hexDigitToBools('5'));
            assertArrayEquals(new boolean[]{false, true, true, false}, Conversion.hexDigitToBools('6'));
            assertArrayEquals(new boolean[]{true, true, true, false}, Conversion.hexDigitToBools('7'));
            assertArrayEquals(new boolean[]{false, false, false, true}, Conversion.hexDigitToBools('8'));
            assertArrayEquals(new boolean[]{true, false, false, true}, Conversion.hexDigitToBools('9'));
            assertArrayEquals(new boolean[]{false, true, false, true}, Conversion.hexDigitToBools('A'));
                assertArrayEquals(new boolean[]{false, true, false, true}, Conversion.hexDigitToBools('a'));
            assertArrayEquals(new boolean[]{true, true, false, true}, Conversion.hexDigitToBools('B'));
                assertArrayEquals(new boolean[]{true, true, false, true}, Conversion.hexDigitToBools('b'));
            assertArrayEquals(new boolean[]{false, false, true, true}, Conversion.hexDigitToBools('C'));
                assertArrayEquals(new boolean[]{false, false, true, true}, Conversion.hexDigitToBools('c'));
            assertArrayEquals(new boolean[]{true, false, true, true}, Conversion.hexDigitToBools('D'));
                assertArrayEquals(new boolean[]{true, false, true, true}, Conversion.hexDigitToBools('d'));
            assertArrayEquals(new boolean[]{false, true, true, true}, Conversion.hexDigitToBools('E'));
                assertArrayEquals(new boolean[]{false, true, true, true}, Conversion.hexDigitToBools('e'));
            assertArrayEquals(new boolean[]{true, true, true, true}, Conversion.hexDigitToBools('F'));
                assertArrayEquals(new boolean[]{true, true, true, true}, Conversion.hexDigitToBools('f'));
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
    public void testHexDigitM0ToBools() throws Exception {
            assertArrayEquals(new boolean[]{false, false, false, false}, Conversion.hexDigitM0ToBools('0'));
            assertArrayEquals(new boolean[]{false, false, false, true}, Conversion.hexDigitM0ToBools('1'));
            assertArrayEquals(new boolean[]{false, false, true, false}, Conversion.hexDigitM0ToBools('2'));
            assertArrayEquals(new boolean[]{false, false, true, true}, Conversion.hexDigitM0ToBools('3'));
            assertArrayEquals(new boolean[]{false, true, false, false}, Conversion.hexDigitM0ToBools('4'));
            assertArrayEquals(new boolean[]{false, true, false, true}, Conversion.hexDigitM0ToBools('5'));
            assertArrayEquals(new boolean[]{false, true, true, false}, Conversion.hexDigitM0ToBools('6'));
            assertArrayEquals(new boolean[]{false, true, true, true}, Conversion.hexDigitM0ToBools('7'));
            assertArrayEquals(new boolean[]{true, false, false, false}, Conversion.hexDigitM0ToBools('8'));
            assertArrayEquals(new boolean[]{true, false, false, true}, Conversion.hexDigitM0ToBools('9'));
            assertArrayEquals(new boolean[]{true, false, true, false}, Conversion.hexDigitM0ToBools('A'));
                assertArrayEquals(new boolean[]{true, false, true, false}, Conversion.hexDigitM0ToBools('a'));
            assertArrayEquals(new boolean[]{true, false, true, true}, Conversion.hexDigitM0ToBools('B'));
                assertArrayEquals(new boolean[]{true, false, true, true}, Conversion.hexDigitM0ToBools('b'));
            assertArrayEquals(new boolean[]{true, true, false, false}, Conversion.hexDigitM0ToBools('C'));
                assertArrayEquals(new boolean[]{true, true, false, false}, Conversion.hexDigitM0ToBools('c'));
            assertArrayEquals(new boolean[]{true, true, false, true}, Conversion.hexDigitM0ToBools('D'));
                assertArrayEquals(new boolean[]{true, true, false, true}, Conversion.hexDigitM0ToBools('d'));
            assertArrayEquals(new boolean[]{true, true, true, false}, Conversion.hexDigitM0ToBools('E'));
                assertArrayEquals(new boolean[]{true, true, true, false}, Conversion.hexDigitM0ToBools('e'));
            assertArrayEquals(new boolean[]{true, true, true, true}, Conversion.hexDigitM0ToBools('F'));
                assertArrayEquals(new boolean[]{true, true, true, true}, Conversion.hexDigitM0ToBools('f'));
    }

    /**
     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '1'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     */
    public void testBoolsToHexDigit() throws Exception {
            assertEquals('0', Conversion.boolsToHexDigit(new boolean[]{false, false, false, false}));
            assertEquals('1', Conversion.boolsToHexDigit(new boolean[]{true, false, false, false}));
            assertEquals('2', Conversion.boolsToHexDigit(new boolean[]{false, true, false, false}));
            assertEquals('3', Conversion.boolsToHexDigit(new boolean[]{true, true, false, false}));
            assertEquals('4', Conversion.boolsToHexDigit(new boolean[]{false, false, true, false}));
            assertEquals('5', Conversion.boolsToHexDigit(new boolean[]{true, false, true, false}));
            assertEquals('6', Conversion.boolsToHexDigit(new boolean[]{false, true, true, false}));
            assertEquals('7', Conversion.boolsToHexDigit(new boolean[]{true, true, true, false}));
            assertEquals('8', Conversion.boolsToHexDigit(new boolean[]{false, false, false, true}));
            assertEquals('9', Conversion.boolsToHexDigit(new boolean[]{true, false, false, true}));
            assertEquals('A', Conversion.boolsToHexDigit(new boolean[]{false, true, false, true}));
            assertEquals('B', Conversion.boolsToHexDigit(new boolean[]{true, true, false, true}));
            assertEquals('C', Conversion.boolsToHexDigit(new boolean[]{false, false, true, true}));
            assertEquals('D', Conversion.boolsToHexDigit(new boolean[]{true, false, true, true}));
            assertEquals('E', Conversion.boolsToHexDigit(new boolean[]{false, true, true, true}));
            assertEquals('F', Conversion.boolsToHexDigit(new boolean[]{true, true, true, true}));
    }
   
    /**
     * Convert a boolean array to an hexadecimal digit using the default (Lsb0) bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '1'
     * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
     */
    public void testBoolsToHexDigit_2args() throws Exception {
        boolean[] shortArray = new boolean[]{false,true,true};
        assertEquals('6', Conversion.boolsToHexDigit(shortArray,0));
        assertEquals('3', Conversion.boolsToHexDigit(shortArray,1));
        assertEquals('1', Conversion.boolsToHexDigit(shortArray,2));
        boolean[] longArray = new boolean[]{true,false,true,false,false,true,true};
        assertEquals('5', Conversion.boolsToHexDigit(longArray,0));
        assertEquals('2', Conversion.boolsToHexDigit(longArray,1));
        assertEquals('9', Conversion.boolsToHexDigit(longArray,2));
        assertEquals('C', Conversion.boolsToHexDigit(longArray,3));
        assertEquals('6', Conversion.boolsToHexDigit(longArray,4));
        assertEquals('3', Conversion.boolsToHexDigit(longArray,5));
        assertEquals('1', Conversion.boolsToHexDigit(longArray,6));
    }

    /**
     * Convert a boolean array to an hexadecimal digit using the Msb0 bit ordering.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     * @warning src.length must be >= 4.
     */
    public void testBoolsToHexDigitM0_bits() throws Exception {
            assertEquals('0', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, false, false, false}));
            assertEquals('1', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, false, false, true}));
            assertEquals('2', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, false, true, false}));
            assertEquals('3', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, false, true, true}));
            assertEquals('4', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, true, false, false}));
            assertEquals('5', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, true, false, true}));
            assertEquals('6', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, true, true, false}));
            assertEquals('7', Conversion.boolsToHexDigitM0_4bits(new boolean[]{false, true, true, true}));
            assertEquals('8', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, false, false, false}));
            assertEquals('9', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, false, false, true}));
            assertEquals('A', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, false, true, false}));
            assertEquals('B', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, false, true, true}));
            assertEquals('C', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, true, false, false}));
            assertEquals('D', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, true, false, true}));
            assertEquals('E', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, true, true, false}));
            assertEquals('F', Conversion.boolsToHexDigitM0_4bits(new boolean[]{true, true, true, true}));
    }
                
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
   public void testBoolsToHexDigitM0_4bits_2args() throws Exception {
        //boolean[] shortArray = new boolean[]{true,true,false};
        //assertEquals('6', Conversion.boolsToHexDigitM0(shortArray,0));
        //assertEquals('3', Conversion.boolsToHexDigitM0(shortArray,1));
        //assertEquals('1', Conversion.boolsToHexDigitM0(shortArray,2));
        boolean[] shortArray = new boolean[]{true,true,false,true};
        assertEquals('D', Conversion.boolsToHexDigitM0_4bits(shortArray,0));
        boolean[] longArray = new boolean[]{true,false,true,false,false,true,true};
        assertEquals('A', Conversion.boolsToHexDigitM0_4bits(longArray,0));
        assertEquals('4', Conversion.boolsToHexDigitM0_4bits(longArray,1));
        assertEquals('9', Conversion.boolsToHexDigitM0_4bits(longArray,2));
        assertEquals('3', Conversion.boolsToHexDigitM0_4bits(longArray,3));
        //assertEquals('6', Conversion.boolsToHexDigitM0(longArray,4));
        //assertEquals('3', Conversion.boolsToHexDigitM0(longArray,5));
        //assertEquals('1', Conversion.boolsToHexDigitM0(longArray,6));
        boolean[] maxLengthArray = new boolean[]{true,false,true,false,false,true,true,true};
        assertEquals('A', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,0));
        assertEquals('4', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,1));
        assertEquals('9', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,2));
        assertEquals('3', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,3));
        assertEquals('7', Conversion.boolsToHexDigitM0_4bits(maxLengthArray,4));
        //assertEquals('7', Conversion.boolsToHexDigitM0(longArray,5));
        //assertEquals('3', Conversion.boolsToHexDigitM0(longArray,6));
        //assertEquals('1', Conversion.boolsToHexDigitM0(longArray,7));
        boolean[] javaDocCheck = new boolean[]{true,false,false,true,true,false,true,false};
        assertEquals('D', Conversion.boolsToHexDigitM0_4bits(javaDocCheck,3));

        }

   /**
     * Convert the first 4 bits of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
     * (1, 0, 0, 0) is converted as follow: '8'
    *  (1,0,0,0,0,0,0,0,   0,0,0,0,0,1,0,0) is converted to '4'
     * @param src the boolean array to convert
     * @return  an hexadecimal digit representing the selected bits
     */
    public void testBoolsBeM0ToHexDigit() throws Exception {
            assertEquals('0', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, false, false, false}));
            assertEquals('1', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, false, false, true}));
            assertEquals('2', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, false, true, false}));
            assertEquals('3', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, false, true, true}));
            assertEquals('4', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, true, false, false}));
            assertEquals('5', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, true, false, true}));
            assertEquals('6', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, true, true, false}));
            assertEquals('7', Conversion.boolsBeM0ToHexDigit(new boolean[]{false, true, true, true}));
            assertEquals('8', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, false, false, false}));
            assertEquals('9', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, false, false, true}));
            assertEquals('A', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, false, true, false}));
            assertEquals('B', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, false, true, true}));
            assertEquals('C', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, true, false, false}));
            assertEquals('D', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, true, false, true}));
            assertEquals('E', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, true, true, false}));
            assertEquals('F', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, true, true, true}));
        assertEquals('4', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false}));
    }

    /**
     * Convert a part of a boolean array in big endian Msb0 bit ordering to an hexadecimal digit.<p>
     * (1, 0, 0, 0) with srcPos = 0 is converted as follow: '8'
     * (1,0,0,0,0,0,0,0,   0,0,0,1,0,1,0,0) with srcPos = 2 is converted to '5'
     * @param src the boolean array to convert
     * @param srcPos the position of the lsb to start the conversion
     * @return  an hexadecimal digit representing the selected bits
     */
    public void testBoolsBeM0ToHexDigit_2args() throws Exception {
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(new boolean[]{true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false},2));

        boolean[] shortArray = new boolean[]{true,true,false};
        assertEquals('6', Conversion.boolsBeM0ToHexDigit(shortArray,0));
        assertEquals('3', Conversion.boolsBeM0ToHexDigit(shortArray,1));
        assertEquals('1', Conversion.boolsBeM0ToHexDigit(shortArray,2));
        boolean[] shortArray2 = new boolean[]{true, true, true, false, false, true, false, true};
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(shortArray2,0));
        assertEquals('2', Conversion.boolsBeM0ToHexDigit(shortArray2,1));
        assertEquals('9', Conversion.boolsBeM0ToHexDigit(shortArray2,2));
        assertEquals('C', Conversion.boolsBeM0ToHexDigit(shortArray2,3));
        assertEquals('E', Conversion.boolsBeM0ToHexDigit(shortArray2,4));
        assertEquals('7', Conversion.boolsBeM0ToHexDigit(shortArray2,5));
        assertEquals('3', Conversion.boolsBeM0ToHexDigit(shortArray2,6));
        assertEquals('1', Conversion.boolsBeM0ToHexDigit(shortArray2,7));
        boolean[] multiBytesArray = new boolean[]{
             true, true,false,false,    true,false, true,false,
             true, true, true,false,   false, true,false, true
        };
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,0));
        assertEquals('2', Conversion.boolsBeM0ToHexDigit(multiBytesArray,1));
        assertEquals('9', Conversion.boolsBeM0ToHexDigit(multiBytesArray,2));
        assertEquals('C', Conversion.boolsBeM0ToHexDigit(multiBytesArray,3));
        assertEquals('E', Conversion.boolsBeM0ToHexDigit(multiBytesArray,4));
        assertEquals('7', Conversion.boolsBeM0ToHexDigit(multiBytesArray,5));
        assertEquals('B', Conversion.boolsBeM0ToHexDigit(multiBytesArray,6));
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,7));

        assertEquals('A', Conversion.boolsBeM0ToHexDigit(multiBytesArray,8));
        assertEquals('5', Conversion.boolsBeM0ToHexDigit(multiBytesArray,9));
        assertEquals('2', Conversion.boolsBeM0ToHexDigit(multiBytesArray,10));
        assertEquals('9', Conversion.boolsBeM0ToHexDigit(multiBytesArray,11));
        assertEquals('C', Conversion.boolsBeM0ToHexDigit(multiBytesArray,12));
        assertEquals('6', Conversion.boolsBeM0ToHexDigit(multiBytesArray,13));
        assertEquals('3', Conversion.boolsBeM0ToHexDigit(multiBytesArray,14));
        assertEquals('1', Conversion.boolsBeM0ToHexDigit(multiBytesArray,15));

    }

     /**
        * Convert the 4 lsb of an int to an hexadecimal digit.<p>
        * 0 returns '0'<p>
        * 1 returns '1'<p>
        * 10 returns 'A' and so on...
        * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
        * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
        */
    public void testIntToHexDigit() throws Exception {
            assertEquals('0',Conversion.intToHexDigit(0));
            assertEquals('1',Conversion.intToHexDigit(1));
            assertEquals('2',Conversion.intToHexDigit(2));
            assertEquals('3',Conversion.intToHexDigit(3));
            assertEquals('4',Conversion.intToHexDigit(4));
            assertEquals('5',Conversion.intToHexDigit(5));
            assertEquals('6',Conversion.intToHexDigit(6));
            assertEquals('7',Conversion.intToHexDigit(7));
            assertEquals('8',Conversion.intToHexDigit(8));
            assertEquals('9',Conversion.intToHexDigit(9));
            assertEquals('A',Conversion.intToHexDigit(10));
            assertEquals('B',Conversion.intToHexDigit(11));
            assertEquals('C',Conversion.intToHexDigit(12));
            assertEquals('D',Conversion.intToHexDigit(13));
            assertEquals('E',Conversion.intToHexDigit(14));
            assertEquals('F',Conversion.intToHexDigit(15));
    }

    /**
     * Convert the 4 lsb of an int to an hexadecimal digit encoded using the Msb0 bit ordering.<p>
     * 0 returns '0'<p>
     * 1 returns '8'<p>
     * 10 returns '5' and so on...
     * @param nibble the int to convert, value outside of the range [0:15] are not allowed.
     * @return an hexadecimal digit representing the 4 lsb of <code>nibble</code>
     */
    public void testIntToHexDigitM0() throws Exception {
            assertEquals('0',Conversion.intToHexDigitM0(0));
            assertEquals('8',Conversion.intToHexDigitM0(1));
            assertEquals('4',Conversion.intToHexDigitM0(2));
            assertEquals('C',Conversion.intToHexDigitM0(3));
            assertEquals('2',Conversion.intToHexDigitM0(4));
            assertEquals('A',Conversion.intToHexDigitM0(5));
            assertEquals('6',Conversion.intToHexDigitM0(6));
            assertEquals('E',Conversion.intToHexDigitM0(7));
            assertEquals('1',Conversion.intToHexDigitM0(8));
            assertEquals('9',Conversion.intToHexDigitM0(9));
            assertEquals('5',Conversion.intToHexDigitM0(10));
            assertEquals('D',Conversion.intToHexDigitM0(11));
            assertEquals('3',Conversion.intToHexDigitM0(12));
            assertEquals('B',Conversion.intToHexDigitM0(13));
            assertEquals('7',Conversion.intToHexDigitM0(14));
            assertEquals('F',Conversion.intToHexDigitM0(15));
    }
    
    static String newHexs(int size){
        char []out = new char[size];
        java.util.Arrays.fill(out,'0');
        return new String(out);
    }
            
        public void testIntsToLongConvenienceMethods() throws Exception {
            int[] src = {0x1df8f8fc, 0x3120e716};
            assertEquals(Conversion.intsToLong(src,1,0L,0,1),Conversion.intsToLong(src,1,1));
            assertEquals(Conversion.intsToLong(src,0,0L,0,1),Conversion.intsToLong(src,1));
            assertEquals(Conversion.intsToLong(src,0,0L,0,2),Conversion.intsToLong(src));
        }
                        
        public void testIntsToLongsConvenienceMethods() throws Exception {
            int[] src = {0x05ba3138, 0xcd0710d5, 0x082daeec, 0xd5bc2626};
            long[] dstInitPart = new long[1];
            long[] dstInitFull = new long[2];
            assertArrayEquals(Conversion.intsToLongs(src,1,dstInitPart,0,1),Conversion.intsToLongs(src,1,1));
            assertArrayEquals(Conversion.intsToLongs(src,0,dstInitPart,0,1),Conversion.intsToLongs(src,1));
            assertArrayEquals(Conversion.intsToLongs(src,0,dstInitFull,0,4),Conversion.intsToLongs(src));
        }
                    
        public void testShortsToLongConvenienceMethods() throws Exception {
            short[] src = {(short)0x0461, (short)0xbe85, (short)0x550b, (short)0xfa01};
            assertEquals(Conversion.shortsToLong(src,1,0L,0,1),Conversion.shortsToLong(src,1,1));
            assertEquals(Conversion.shortsToLong(src,0,0L,0,1),Conversion.shortsToLong(src,1));
            assertEquals(Conversion.shortsToLong(src,0,0L,0,4),Conversion.shortsToLong(src));
        }
                        
        public void testShortsToLongsConvenienceMethods() throws Exception {
            short[] src = {(short)0x4a9d, (short)0x5259, (short)0xe3bf, (short)0xd933, (short)0x956d, (short)0x29ab, (short)0x881d, (short)0x2613};
            long[] dstInitPart = new long[1];
            long[] dstInitFull = new long[2];
            assertArrayEquals(Conversion.shortsToLongs(src,1,dstInitPart,0,1),Conversion.shortsToLongs(src,1,1));
            assertArrayEquals(Conversion.shortsToLongs(src,0,dstInitPart,0,1),Conversion.shortsToLongs(src,1));
            assertArrayEquals(Conversion.shortsToLongs(src,0,dstInitFull,0,8),Conversion.shortsToLongs(src));
        }
                    
        public void testShortsToIntConvenienceMethods() throws Exception {
            short[] src = {(short)0x2c4b, (short)0x94d8};
            assertEquals(Conversion.shortsToInt(src,1,0,0,1),Conversion.shortsToInt(src,1,1));
            assertEquals(Conversion.shortsToInt(src,0,0,0,1),Conversion.shortsToInt(src,1));
            assertEquals(Conversion.shortsToInt(src,0,0,0,2),Conversion.shortsToInt(src));
        }
                        
        public void testShortsToIntsConvenienceMethods() throws Exception {
            short[] src = {(short)0x2602, (short)0xdafb, (short)0x9903, (short)0x494c};
            int[] dstInitPart = new int[1];
            int[] dstInitFull = new int[2];
            assertArrayEquals(Conversion.shortsToInts(src,1,dstInitPart,0,1),Conversion.shortsToInts(src,1,1));
            assertArrayEquals(Conversion.shortsToInts(src,0,dstInitPart,0,1),Conversion.shortsToInts(src,1));
            assertArrayEquals(Conversion.shortsToInts(src,0,dstInitFull,0,4),Conversion.shortsToInts(src));
        }
                    
        public void testBytesToLongConvenienceMethods() throws Exception {
            byte[] src = {(byte)0x1b, (byte)0x48, (byte)0xa6, (byte)0xc7, (byte)0x29, (byte)0x96, (byte)0x33, (byte)0xb0};
            assertEquals(Conversion.bytesToLong(src,1,0L,0,1),Conversion.bytesToLong(src,1,1));
            assertEquals(Conversion.bytesToLong(src,0,0L,0,1),Conversion.bytesToLong(src,1));
            assertEquals(Conversion.bytesToLong(src,0,0L,0,8),Conversion.bytesToLong(src));
        }
                        
        public void testBytesToLongsConvenienceMethods() throws Exception {
            byte[] src = {(byte)0xe1, (byte)0xbe, (byte)0xca, (byte)0x55, (byte)0xaf, (byte)0xa0, (byte)0x62, (byte)0x29, (byte)0xe2, (byte)0xf5, (byte)0x0c, (byte)0x59, (byte)0x68, (byte)0xfc, (byte)0x81, (byte)0x89};
            long[] dstInitPart = new long[1];
            long[] dstInitFull = new long[2];
            assertArrayEquals(Conversion.bytesToLongs(src,1,dstInitPart,0,1),Conversion.bytesToLongs(src,1,1));
            assertArrayEquals(Conversion.bytesToLongs(src,0,dstInitPart,0,1),Conversion.bytesToLongs(src,1));
            assertArrayEquals(Conversion.bytesToLongs(src,0,dstInitFull,0,16),Conversion.bytesToLongs(src));
        }
                    
        public void testBytesToIntConvenienceMethods() throws Exception {
            byte[] src = {(byte)0xcc, (byte)0x83, (byte)0x97, (byte)0xcb};
            assertEquals(Conversion.bytesToInt(src,1,0,0,1),Conversion.bytesToInt(src,1,1));
            assertEquals(Conversion.bytesToInt(src,0,0,0,1),Conversion.bytesToInt(src,1));
            assertEquals(Conversion.bytesToInt(src,0,0,0,4),Conversion.bytesToInt(src));
        }
                        
        public void testBytesToIntsConvenienceMethods() throws Exception {
            byte[] src = {(byte)0x37, (byte)0x83, (byte)0x4c, (byte)0x2c, (byte)0xd1, (byte)0x3a, (byte)0x8f, (byte)0x86};
            int[] dstInitPart = new int[1];
            int[] dstInitFull = new int[2];
            assertArrayEquals(Conversion.bytesToInts(src,1,dstInitPart,0,1),Conversion.bytesToInts(src,1,1));
            assertArrayEquals(Conversion.bytesToInts(src,0,dstInitPart,0,1),Conversion.bytesToInts(src,1));
            assertArrayEquals(Conversion.bytesToInts(src,0,dstInitFull,0,8),Conversion.bytesToInts(src));
        }
                    
        public void testBytesToShortConvenienceMethods() throws Exception {
            byte[] src = {(byte)0x46, (byte)0x75};
            assertEquals(Conversion.bytesToShort(src,1,(short)0,0,1),Conversion.bytesToShort(src,1,1));
            assertEquals(Conversion.bytesToShort(src,0,(short)0,0,1),Conversion.bytesToShort(src,1));
            assertEquals(Conversion.bytesToShort(src,0,(short)0,0,2),Conversion.bytesToShort(src));
        }
                        
        public void testBytesToShortsConvenienceMethods() throws Exception {
            byte[] src = {(byte)0xab, (byte)0x45, (byte)0x2a, (byte)0x27};
            short[] dstInitPart = new short[1];
            short[] dstInitFull = new short[2];
            assertArrayEquals(Conversion.bytesToShorts(src,1,dstInitPart,0,1),Conversion.bytesToShorts(src,1,1));
            assertArrayEquals(Conversion.bytesToShorts(src,0,dstInitPart,0,1),Conversion.bytesToShorts(src,1));
            assertArrayEquals(Conversion.bytesToShorts(src,0,dstInitFull,0,4),Conversion.bytesToShorts(src));
        }
                    
        public void testHexsToLongConvenienceMethods() throws Exception {
            String src = "c46beb9d0caef2c7";
            assertEquals(Conversion.hexsToLong(src,1,0L,0,1),Conversion.hexsToLong(src,1,1));
            assertEquals(Conversion.hexsToLong(src,0,0L,0,1),Conversion.hexsToLong(src,1));
            assertEquals(Conversion.hexsToLong(src,0,0L,0,16),Conversion.hexsToLong(src));
        }
                        
        public void testHexsToLongsConvenienceMethods() throws Exception {
            String src = "c8b1d98abe82c328a64167c1b1ba542c";
            long[] dstInitPart = new long[1];
            long[] dstInitFull = new long[2];
            assertArrayEquals(Conversion.hexsToLongs(src,1,dstInitPart,0,1),Conversion.hexsToLongs(src,1,1));
            assertArrayEquals(Conversion.hexsToLongs(src,0,dstInitPart,0,1),Conversion.hexsToLongs(src,1));
            assertArrayEquals(Conversion.hexsToLongs(src,0,dstInitFull,0,32),Conversion.hexsToLongs(src));
        }
                    
        public void testHexsToIntConvenienceMethods() throws Exception {
            String src = "18602267";
            assertEquals(Conversion.hexsToInt(src,1,0,0,1),Conversion.hexsToInt(src,1,1));
            assertEquals(Conversion.hexsToInt(src,0,0,0,1),Conversion.hexsToInt(src,1));
            assertEquals(Conversion.hexsToInt(src,0,0,0,8),Conversion.hexsToInt(src));
        }
                        
        public void testHexsToIntsConvenienceMethods() throws Exception {
            String src = "5aeb5c3901ee7fd9";
            int[] dstInitPart = new int[1];
            int[] dstInitFull = new int[2];
            assertArrayEquals(Conversion.hexsToInts(src,1,dstInitPart,0,1),Conversion.hexsToInts(src,1,1));
            assertArrayEquals(Conversion.hexsToInts(src,0,dstInitPart,0,1),Conversion.hexsToInts(src,1));
            assertArrayEquals(Conversion.hexsToInts(src,0,dstInitFull,0,16),Conversion.hexsToInts(src));
        }
                    
        public void testHexsToShortConvenienceMethods() throws Exception {
            String src = "4ead";
            assertEquals(Conversion.hexsToShort(src,1,(short)0,0,1),Conversion.hexsToShort(src,1,1));
            assertEquals(Conversion.hexsToShort(src,0,(short)0,0,1),Conversion.hexsToShort(src,1));
            assertEquals(Conversion.hexsToShort(src,0,(short)0,0,4),Conversion.hexsToShort(src));
        }
                        
        public void testHexsToShortsConvenienceMethods() throws Exception {
            String src = "888d0a0c";
            short[] dstInitPart = new short[1];
            short[] dstInitFull = new short[2];
            assertArrayEquals(Conversion.hexsToShorts(src,1,dstInitPart,0,1),Conversion.hexsToShorts(src,1,1));
            assertArrayEquals(Conversion.hexsToShorts(src,0,dstInitPart,0,1),Conversion.hexsToShorts(src,1));
            assertArrayEquals(Conversion.hexsToShorts(src,0,dstInitFull,0,8),Conversion.hexsToShorts(src));
        }
                    
        public void testHexsToByteConvenienceMethods() throws Exception {
            String src = "5e";
            assertEquals(Conversion.hexsToByte(src,1,(byte)0,0,1),Conversion.hexsToByte(src,1,1));
            assertEquals(Conversion.hexsToByte(src,0,(byte)0,0,1),Conversion.hexsToByte(src,1));
            assertEquals(Conversion.hexsToByte(src,0,(byte)0,0,2),Conversion.hexsToByte(src));
        }
                        
        public void testHexsToBytesConvenienceMethods() throws Exception {
            String src = "54e3";
            byte[] dstInitPart = new byte[1];
            byte[] dstInitFull = new byte[2];
            assertArrayEquals(Conversion.hexsToBytes(src,1,dstInitPart,0,1),Conversion.hexsToBytes(src,1,1));
            assertArrayEquals(Conversion.hexsToBytes(src,0,dstInitPart,0,1),Conversion.hexsToBytes(src,1));
            assertArrayEquals(Conversion.hexsToBytes(src,0,dstInitFull,0,4),Conversion.hexsToBytes(src));
        }
                    
        public void testBoolsToLongConvenienceMethods() throws Exception {
            boolean[] src = {false, true, false, true, false, false, true, false, false, true, false, true, true, true, false, true, true, false, true, true, false, true, true, true, false, true, false, false, true, true, false, false, false, true, false, true, true, true, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, true, true, true, true, false, true};
            assertEquals(Conversion.boolsToLong(src,1,0L,0,1),Conversion.boolsToLong(src,1,1));
            assertEquals(Conversion.boolsToLong(src,0,0L,0,1),Conversion.boolsToLong(src,1));
            assertEquals(Conversion.boolsToLong(src,0,0L,0,64),Conversion.boolsToLong(src));
        }
                        
        public void testBoolsToLongsConvenienceMethods() throws Exception {
            boolean[] src = {true, true, true, true, true, true, false, false, false, true, false, true, false, true, true, false, false, true, false, true, true, true, true, false, true, true, false, false, true, false, true, false, false, false, true, false, false, false, true, true, true, false, false, false, false, false, false, true, true, false, true, true, true, false, false, false, true, true, false, false, false, false, true, false, false, false, true, false, true, true, false, true, true, false, false, true, true, false, false, true, false, true, true, true, false, false, false, true, false, false, true, true, true, false, true, true, false, true, true, false, true, true, false, false, false, false, false, false, false, false, true, false, true, true, false, true, true, false, false, false, true, true, true, false, true, true, true, false};
            long[] dstInitPart = new long[1];
            long[] dstInitFull = new long[2];
            assertArrayEquals(Conversion.boolsToLongs(src,1,dstInitPart,0,1),Conversion.boolsToLongs(src,1,1));
            assertArrayEquals(Conversion.boolsToLongs(src,0,dstInitPart,0,1),Conversion.boolsToLongs(src,1));
            assertArrayEquals(Conversion.boolsToLongs(src,0,dstInitFull,0,128),Conversion.boolsToLongs(src));
        }
                    
        public void testBoolsToIntConvenienceMethods() throws Exception {
            boolean[] src = {false, false, true, true, false, false, true, true, false, false, true, true, false, true, true, false, false, true, false, true, true, true, false, false, true, true, false, false, false, true, true, false};
            assertEquals(Conversion.boolsToInt(src,1,0,0,1),Conversion.boolsToInt(src,1,1));
            assertEquals(Conversion.boolsToInt(src,0,0,0,1),Conversion.boolsToInt(src,1));
            assertEquals(Conversion.boolsToInt(src,0,0,0,32),Conversion.boolsToInt(src));
        }
                        
        public void testBoolsToIntsConvenienceMethods() throws Exception {
            boolean[] src = {false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, true, false, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, false, true, false, false, false, false, true, false, false, false, true, true, false, false, true, true, false, false, true, true, true, true, false, false, true, true, true, true, true, true, true, false};
            int[] dstInitPart = new int[1];
            int[] dstInitFull = new int[2];
            assertArrayEquals(Conversion.boolsToInts(src,1,dstInitPart,0,1),Conversion.boolsToInts(src,1,1));
            assertArrayEquals(Conversion.boolsToInts(src,0,dstInitPart,0,1),Conversion.boolsToInts(src,1));
            assertArrayEquals(Conversion.boolsToInts(src,0,dstInitFull,0,64),Conversion.boolsToInts(src));
        }
                    
        public void testBoolsToShortConvenienceMethods() throws Exception {
            boolean[] src = {true, true, true, true, true, false, true, false, true, false, false, true, true, false, true, true};
            assertEquals(Conversion.boolsToShort(src,1,(short)0,0,1),Conversion.boolsToShort(src,1,1));
            assertEquals(Conversion.boolsToShort(src,0,(short)0,0,1),Conversion.boolsToShort(src,1));
            assertEquals(Conversion.boolsToShort(src,0,(short)0,0,16),Conversion.boolsToShort(src));
        }
                        
        public void testBoolsToShortsConvenienceMethods() throws Exception {
            boolean[] src = {true, false, false, false, false, true, false, false, true, false, true, true, false, false, false, true, true, false, false, true, false, true, false, false, true, true, false, false, true, true, true, true};
            short[] dstInitPart = new short[1];
            short[] dstInitFull = new short[2];
            assertArrayEquals(Conversion.boolsToShorts(src,1,dstInitPart,0,1),Conversion.boolsToShorts(src,1,1));
            assertArrayEquals(Conversion.boolsToShorts(src,0,dstInitPart,0,1),Conversion.boolsToShorts(src,1));
            assertArrayEquals(Conversion.boolsToShorts(src,0,dstInitFull,0,32),Conversion.boolsToShorts(src));
        }
                    
        public void testBoolsToByteConvenienceMethods() throws Exception {
            boolean[] src = {false, true, true, true, false, true, false, false};
            assertEquals(Conversion.boolsToByte(src,1,(byte)0,0,1),Conversion.boolsToByte(src,1,1));
            assertEquals(Conversion.boolsToByte(src,0,(byte)0,0,1),Conversion.boolsToByte(src,1));
            assertEquals(Conversion.boolsToByte(src,0,(byte)0,0,8),Conversion.boolsToByte(src));
        }
                        
        public void testBoolsToBytesConvenienceMethods() throws Exception {
            boolean[] src = {true, true, false, false, false, true, false, false, true, false, false, true, false, false, true, true};
            byte[] dstInitPart = new byte[1];
            byte[] dstInitFull = new byte[2];
            assertArrayEquals(Conversion.boolsToBytes(src,1,dstInitPart,0,1),Conversion.boolsToBytes(src,1,1));
            assertArrayEquals(Conversion.boolsToBytes(src,0,dstInitPart,0,1),Conversion.boolsToBytes(src,1));
            assertArrayEquals(Conversion.boolsToBytes(src,0,dstInitFull,0,16),Conversion.boolsToBytes(src));
        }
                
    void checkHexsToBools(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBools){
        String src=Conversion.boolsToHexs(srcAsBools,0,newHexs(srcAsBools.length/4),0,srcAsBools.length);
            boolean[] dst = new boolean[dstAsBools.length];
            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
            boolean[] expected;
            expected = dstAsBools;
        System.arraycopy(srcAsBools,srcPos*1,dstAsBools,dstPos*1, nBools*1);
        dst=Conversion.hexsToBools(src,srcPos,dst,dstPos,nBools);
        assertArrayEquals(expected,dst);
    }
    public void testHexsToBools() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/1;
        final int dstSize = 4+3;
        boolean[] dstAsBools = new boolean[dstSize*1];
        checkHexsToBools(srcAsBools, 0,dstAsBools, 0, 0);
        checkHexsToBools(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkHexsToBools(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkHexsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkHexsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkHexsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkHexsToBools(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testBoolsToHexConvenienceMethods() throws Exception {
            boolean[] src = {false, true, false, false};
            assertEquals(Conversion.boolsToHex(src,1,'0',0,1),Conversion.boolsToHex(src,1,1));
            assertEquals(Conversion.boolsToHex(src,0,'0',0,1),Conversion.boolsToHex(src,1));
            assertEquals(Conversion.boolsToHex(src,0,'0',0,4),Conversion.boolsToHex(src));
        }
                
        public void testBoolsToHexsConvenienceMethods() throws Exception {
            boolean[] src = {false, true, false, true, false, true, true, true};
            String dstInitPart = "0";
            String dstInitFull = "00";
            assertArrayEquals(Conversion.boolsToHexs(src,1,dstInitPart,0,1),Conversion.boolsToHexs(src,1,1));
            assertArrayEquals(Conversion.boolsToHexs(src,0,dstInitPart,0,1),Conversion.boolsToHexs(src,1));
            assertArrayEquals(Conversion.boolsToHexs(src,0,dstInitFull,0,8),Conversion.boolsToHexs(src));
        }
                
        public void testHexToBoolsConvenienceMethods() throws Exception {
                char src = 'a';
        boolean[] dstInitPart = new boolean[1];
        boolean[] dstInitFull = new boolean[4];
        assertArrayEquals(Conversion.hexToBools(src,1,dstInitPart,0,1),Conversion.hexToBools(src,1,1));
        assertArrayEquals(Conversion.hexToBools(src,0,dstInitPart,0,1),Conversion.hexToBools(src,1));
        assertArrayEquals(Conversion.hexToBools(src,0,dstInitFull,0,4),Conversion.hexToBools(src));
        }
                
        public void testHexsToBoolsConvenienceMethods() throws Exception {
            String src = "3";
            boolean[] dstInitPart = new boolean[1];
            boolean[] dstInitFull = new boolean[4];
            assertArrayEquals(Conversion.hexsToBools(src,1,dstInitPart,0,1),Conversion.hexsToBools(src,1,1));
            assertArrayEquals(Conversion.hexsToBools(src,0,dstInitPart,0,1),Conversion.hexsToBools(src,1));
            assertArrayEquals(Conversion.hexsToBools(src,0,dstInitFull,0,4),Conversion.hexsToBools(src));
        }
                        
        public void testLongToIntsConvenienceMethods() throws Exception {
                long src = 0x045da435519a274cL;
        int[] dstInitPart = new int[1];
        int[] dstInitFull = new int[2];
        assertArrayEquals(Conversion.longToInts(src,1,dstInitPart,0,1),Conversion.longToInts(src,1,1));
        assertArrayEquals(Conversion.longToInts(src,0,dstInitPart,0,1),Conversion.longToInts(src,1));
        assertArrayEquals(Conversion.longToInts(src,0,dstInitFull,0,2),Conversion.longToInts(src));
        }
                
    void checkLongsToInts(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nInts){
        long[] src=Conversion.boolsToLongs(srcAsBools,0, new long[srcAsBools.length/64],0,srcAsBools.length);
            int[] dst=Conversion.boolsToInts(dstAsBools,0, new int[dstAsBools.length/32],0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*32,dstAsBools,dstPos*32, nInts*32);
            int[] expected = Conversion.boolsToInts(dstAsBools,0, new int[dstAsBools.length/32],0,dstAsBools.length);
        dst=Conversion.longsToInts(src,srcPos,dst,dstPos,nInts);
        assertArrayEquals(expected,dst);
    }
    public void testLongsToInts() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/32;
        final int dstSize = 2+3;
        boolean[] dstAsBools = new boolean[dstSize*32];
        checkLongsToInts(srcAsBools, 0,dstAsBools, 0, 0);
        checkLongsToInts(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkLongsToInts(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkLongsToInts(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkLongsToInts(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkLongsToInts(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkLongsToInts(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testLongsToIntsConvenienceMethods() throws Exception {
            long[] src = {0x024375d506b7877dL};
            int[] dstInitPart = new int[1];
            int[] dstInitFull = new int[2];
            assertArrayEquals(Conversion.longsToInts(src,1,dstInitPart,0,1),Conversion.longsToInts(src,1,1));
            assertArrayEquals(Conversion.longsToInts(src,0,dstInitPart,0,1),Conversion.longsToInts(src,1));
            assertArrayEquals(Conversion.longsToInts(src,0,dstInitFull,0,2),Conversion.longsToInts(src));
        }
                        
        public void testLongToShortsConvenienceMethods() throws Exception {
                long src = 0x013df629c9986ff0L;
        short[] dstInitPart = new short[1];
        short[] dstInitFull = new short[4];
        assertArrayEquals(Conversion.longToShorts(src,1,dstInitPart,0,1),Conversion.longToShorts(src,1,1));
        assertArrayEquals(Conversion.longToShorts(src,0,dstInitPart,0,1),Conversion.longToShorts(src,1));
        assertArrayEquals(Conversion.longToShorts(src,0,dstInitFull,0,4),Conversion.longToShorts(src));
        }
                
    void checkLongsToShorts(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nShorts){
        long[] src=Conversion.boolsToLongs(srcAsBools,0, new long[srcAsBools.length/64],0,srcAsBools.length);
            short[] dst=Conversion.boolsToShorts(dstAsBools,0, new short[dstAsBools.length/16],0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*16,dstAsBools,dstPos*16, nShorts*16);
            short[] expected = Conversion.boolsToShorts(dstAsBools,0, new short[dstAsBools.length/16],0,dstAsBools.length);
        dst=Conversion.longsToShorts(src,srcPos,dst,dstPos,nShorts);
        assertArrayEquals(expected,dst);
    }
    public void testLongsToShorts() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/16;
        final int dstSize = 4+3;
        boolean[] dstAsBools = new boolean[dstSize*16];
        checkLongsToShorts(srcAsBools, 0,dstAsBools, 0, 0);
        checkLongsToShorts(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkLongsToShorts(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkLongsToShorts(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkLongsToShorts(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkLongsToShorts(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkLongsToShorts(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testLongsToShortsConvenienceMethods() throws Exception {
            long[] src = {0x01a494a67033eea5L};
            short[] dstInitPart = new short[1];
            short[] dstInitFull = new short[4];
            assertArrayEquals(Conversion.longsToShorts(src,1,dstInitPart,0,1),Conversion.longsToShorts(src,1,1));
            assertArrayEquals(Conversion.longsToShorts(src,0,dstInitPart,0,1),Conversion.longsToShorts(src,1));
            assertArrayEquals(Conversion.longsToShorts(src,0,dstInitFull,0,4),Conversion.longsToShorts(src));
        }
                        
        public void testIntToShortsConvenienceMethods() throws Exception {
                int src = 0x1bb91487;
        short[] dstInitPart = new short[1];
        short[] dstInitFull = new short[2];
        assertArrayEquals(Conversion.intToShorts(src,1,dstInitPart,0,1),Conversion.intToShorts(src,1,1));
        assertArrayEquals(Conversion.intToShorts(src,0,dstInitPart,0,1),Conversion.intToShorts(src,1));
        assertArrayEquals(Conversion.intToShorts(src,0,dstInitFull,0,2),Conversion.intToShorts(src));
        }
                
    void checkIntsToShorts(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nShorts){
        int[] src=Conversion.boolsToInts(srcAsBools,0, new int[srcAsBools.length/32],0,srcAsBools.length);
            short[] dst=Conversion.boolsToShorts(dstAsBools,0, new short[dstAsBools.length/16],0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*16,dstAsBools,dstPos*16, nShorts*16);
            short[] expected = Conversion.boolsToShorts(dstAsBools,0, new short[dstAsBools.length/16],0,dstAsBools.length);
        dst=Conversion.intsToShorts(src,srcPos,dst,dstPos,nShorts);
        assertArrayEquals(expected,dst);
    }
    public void testIntsToShorts() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/16;
        final int dstSize = 2+3;
        boolean[] dstAsBools = new boolean[dstSize*16];
        checkIntsToShorts(srcAsBools, 0,dstAsBools, 0, 0);
        checkIntsToShorts(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkIntsToShorts(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkIntsToShorts(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkIntsToShorts(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkIntsToShorts(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkIntsToShorts(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testIntsToShortsConvenienceMethods() throws Exception {
            int[] src = {0xf07ba7c3};
            short[] dstInitPart = new short[1];
            short[] dstInitFull = new short[2];
            assertArrayEquals(Conversion.intsToShorts(src,1,dstInitPart,0,1),Conversion.intsToShorts(src,1,1));
            assertArrayEquals(Conversion.intsToShorts(src,0,dstInitPart,0,1),Conversion.intsToShorts(src,1));
            assertArrayEquals(Conversion.intsToShorts(src,0,dstInitFull,0,2),Conversion.intsToShorts(src));
        }
                        
        public void testLongToBytesConvenienceMethods() throws Exception {
                long src = 0x0206e19f9a6cdda6L;
        byte[] dstInitPart = new byte[1];
        byte[] dstInitFull = new byte[8];
        assertArrayEquals(Conversion.longToBytes(src,1,dstInitPart,0,1),Conversion.longToBytes(src,1,1));
        assertArrayEquals(Conversion.longToBytes(src,0,dstInitPart,0,1),Conversion.longToBytes(src,1));
        assertArrayEquals(Conversion.longToBytes(src,0,dstInitFull,0,8),Conversion.longToBytes(src));
        }
                
    void checkLongsToBytes(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBytes){
        long[] src=Conversion.boolsToLongs(srcAsBools,0, new long[srcAsBools.length/64],0,srcAsBools.length);
            byte[] dst=Conversion.boolsToBytes(dstAsBools,0, new byte[dstAsBools.length/8],0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*8,dstAsBools,dstPos*8, nBytes*8);
            byte[] expected = Conversion.boolsToBytes(dstAsBools,0, new byte[dstAsBools.length/8],0,dstAsBools.length);
        dst=Conversion.longsToBytes(src,srcPos,dst,dstPos,nBytes);
        assertArrayEquals(expected,dst);
    }
    public void testLongsToBytes() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/8;
        final int dstSize = 8+3;
        boolean[] dstAsBools = new boolean[dstSize*8];
        checkLongsToBytes(srcAsBools, 0,dstAsBools, 0, 0);
        checkLongsToBytes(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkLongsToBytes(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkLongsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkLongsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkLongsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkLongsToBytes(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testLongsToBytesConvenienceMethods() throws Exception {
            long[] src = {0x008c837f9a866879L};
            byte[] dstInitPart = new byte[1];
            byte[] dstInitFull = new byte[8];
            assertArrayEquals(Conversion.longsToBytes(src,1,dstInitPart,0,1),Conversion.longsToBytes(src,1,1));
            assertArrayEquals(Conversion.longsToBytes(src,0,dstInitPart,0,1),Conversion.longsToBytes(src,1));
            assertArrayEquals(Conversion.longsToBytes(src,0,dstInitFull,0,8),Conversion.longsToBytes(src));
        }
                        
        public void testIntToBytesConvenienceMethods() throws Exception {
                int src = 0x599fee52;
        byte[] dstInitPart = new byte[1];
        byte[] dstInitFull = new byte[4];
        assertArrayEquals(Conversion.intToBytes(src,1,dstInitPart,0,1),Conversion.intToBytes(src,1,1));
        assertArrayEquals(Conversion.intToBytes(src,0,dstInitPart,0,1),Conversion.intToBytes(src,1));
        assertArrayEquals(Conversion.intToBytes(src,0,dstInitFull,0,4),Conversion.intToBytes(src));
        }
                
    void checkIntsToBytes(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBytes){
        int[] src=Conversion.boolsToInts(srcAsBools,0, new int[srcAsBools.length/32],0,srcAsBools.length);
            byte[] dst=Conversion.boolsToBytes(dstAsBools,0, new byte[dstAsBools.length/8],0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*8,dstAsBools,dstPos*8, nBytes*8);
            byte[] expected = Conversion.boolsToBytes(dstAsBools,0, new byte[dstAsBools.length/8],0,dstAsBools.length);
        dst=Conversion.intsToBytes(src,srcPos,dst,dstPos,nBytes);
        assertArrayEquals(expected,dst);
    }
    public void testIntsToBytes() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/8;
        final int dstSize = 4+3;
        boolean[] dstAsBools = new boolean[dstSize*8];
        checkIntsToBytes(srcAsBools, 0,dstAsBools, 0, 0);
        checkIntsToBytes(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkIntsToBytes(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkIntsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkIntsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkIntsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkIntsToBytes(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testIntsToBytesConvenienceMethods() throws Exception {
            int[] src = {0xf0682954};
            byte[] dstInitPart = new byte[1];
            byte[] dstInitFull = new byte[4];
            assertArrayEquals(Conversion.intsToBytes(src,1,dstInitPart,0,1),Conversion.intsToBytes(src,1,1));
            assertArrayEquals(Conversion.intsToBytes(src,0,dstInitPart,0,1),Conversion.intsToBytes(src,1));
            assertArrayEquals(Conversion.intsToBytes(src,0,dstInitFull,0,4),Conversion.intsToBytes(src));
        }
                        
        public void testShortToBytesConvenienceMethods() throws Exception {
                short src = (short)0xf7f1;
        byte[] dstInitPart = new byte[1];
        byte[] dstInitFull = new byte[2];
        assertArrayEquals(Conversion.shortToBytes(src,1,dstInitPart,0,1),Conversion.shortToBytes(src,1,1));
        assertArrayEquals(Conversion.shortToBytes(src,0,dstInitPart,0,1),Conversion.shortToBytes(src,1));
        assertArrayEquals(Conversion.shortToBytes(src,0,dstInitFull,0,2),Conversion.shortToBytes(src));
        }
                
    void checkShortsToBytes(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBytes){
        short[] src=Conversion.boolsToShorts(srcAsBools,0, new short[srcAsBools.length/16],0,srcAsBools.length);
            byte[] dst=Conversion.boolsToBytes(dstAsBools,0, new byte[dstAsBools.length/8],0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*8,dstAsBools,dstPos*8, nBytes*8);
            byte[] expected = Conversion.boolsToBytes(dstAsBools,0, new byte[dstAsBools.length/8],0,dstAsBools.length);
        dst=Conversion.shortsToBytes(src,srcPos,dst,dstPos,nBytes);
        assertArrayEquals(expected,dst);
    }
    public void testShortsToBytes() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/8;
        final int dstSize = 2+3;
        boolean[] dstAsBools = new boolean[dstSize*8];
        checkShortsToBytes(srcAsBools, 0,dstAsBools, 0, 0);
        checkShortsToBytes(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkShortsToBytes(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkShortsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkShortsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkShortsToBytes(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkShortsToBytes(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testShortsToBytesConvenienceMethods() throws Exception {
            short[] src = {(short)0x3a0e};
            byte[] dstInitPart = new byte[1];
            byte[] dstInitFull = new byte[2];
            assertArrayEquals(Conversion.shortsToBytes(src,1,dstInitPart,0,1),Conversion.shortsToBytes(src,1,1));
            assertArrayEquals(Conversion.shortsToBytes(src,0,dstInitPart,0,1),Conversion.shortsToBytes(src,1));
            assertArrayEquals(Conversion.shortsToBytes(src,0,dstInitFull,0,2),Conversion.shortsToBytes(src));
        }
                        
        public void testLongToHexsConvenienceMethods() throws Exception {
                long src = 0x073b9d14fa4353f3L;
        String dstInitPart = "0";
        String dstInitFull = "0000000000000000";
        assertArrayEquals(Conversion.longToHexs(src,1,dstInitPart,0,1),Conversion.longToHexs(src,1,1));
        assertArrayEquals(Conversion.longToHexs(src,0,dstInitPart,0,1),Conversion.longToHexs(src,1));
        assertArrayEquals(Conversion.longToHexs(src,0,dstInitFull,0,16),Conversion.longToHexs(src));
        }
                
    void checkLongsToHexs(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nHexs){
        long[] src=Conversion.boolsToLongs(srcAsBools,0, new long[srcAsBools.length/64],0,srcAsBools.length);
            String dst=Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*4,dstAsBools,dstPos*4, nHexs*4);
            String expected = Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        dst=Conversion.longsToHexs(src,srcPos,dst,dstPos,nHexs);
        assertArrayEquals(expected,dst);
    }
    public void testLongsToHexs() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/4;
        final int dstSize = 16+3;
        boolean[] dstAsBools = new boolean[dstSize*4];
        checkLongsToHexs(srcAsBools, 0,dstAsBools, 0, 0);
        checkLongsToHexs(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkLongsToHexs(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkLongsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkLongsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkLongsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkLongsToHexs(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testLongsToHexsConvenienceMethods() throws Exception {
            long[] src = {0x03bd9e1cef0b2e45L};
            String dstInitPart = "0";
            String dstInitFull = "0000000000000000";
            assertArrayEquals(Conversion.longsToHexs(src,1,dstInitPart,0,1),Conversion.longsToHexs(src,1,1));
            assertArrayEquals(Conversion.longsToHexs(src,0,dstInitPart,0,1),Conversion.longsToHexs(src,1));
            assertArrayEquals(Conversion.longsToHexs(src,0,dstInitFull,0,16),Conversion.longsToHexs(src));
        }
                        
        public void testIntToHexsConvenienceMethods() throws Exception {
                int src = 0x0302abbf;
        String dstInitPart = "0";
        String dstInitFull = "00000000";
        assertArrayEquals(Conversion.intToHexs(src,1,dstInitPart,0,1),Conversion.intToHexs(src,1,1));
        assertArrayEquals(Conversion.intToHexs(src,0,dstInitPart,0,1),Conversion.intToHexs(src,1));
        assertArrayEquals(Conversion.intToHexs(src,0,dstInitFull,0,8),Conversion.intToHexs(src));
        }
                
    void checkIntsToHexs(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nHexs){
        int[] src=Conversion.boolsToInts(srcAsBools,0, new int[srcAsBools.length/32],0,srcAsBools.length);
            String dst=Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*4,dstAsBools,dstPos*4, nHexs*4);
            String expected = Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        dst=Conversion.intsToHexs(src,srcPos,dst,dstPos,nHexs);
        assertArrayEquals(expected,dst);
    }
    public void testIntsToHexs() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/4;
        final int dstSize = 8+3;
        boolean[] dstAsBools = new boolean[dstSize*4];
        checkIntsToHexs(srcAsBools, 0,dstAsBools, 0, 0);
        checkIntsToHexs(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkIntsToHexs(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkIntsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkIntsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkIntsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkIntsToHexs(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testIntsToHexsConvenienceMethods() throws Exception {
            int[] src = {0x10b1eaa6};
            String dstInitPart = "0";
            String dstInitFull = "00000000";
            assertArrayEquals(Conversion.intsToHexs(src,1,dstInitPart,0,1),Conversion.intsToHexs(src,1,1));
            assertArrayEquals(Conversion.intsToHexs(src,0,dstInitPart,0,1),Conversion.intsToHexs(src,1));
            assertArrayEquals(Conversion.intsToHexs(src,0,dstInitFull,0,8),Conversion.intsToHexs(src));
        }
                        
        public void testShortToHexsConvenienceMethods() throws Exception {
                short src = (short)0x026e;
        String dstInitPart = "0";
        String dstInitFull = "0000";
        assertArrayEquals(Conversion.shortToHexs(src,1,dstInitPart,0,1),Conversion.shortToHexs(src,1,1));
        assertArrayEquals(Conversion.shortToHexs(src,0,dstInitPart,0,1),Conversion.shortToHexs(src,1));
        assertArrayEquals(Conversion.shortToHexs(src,0,dstInitFull,0,4),Conversion.shortToHexs(src));
        }
                
    void checkShortsToHexs(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nHexs){
        short[] src=Conversion.boolsToShorts(srcAsBools,0, new short[srcAsBools.length/16],0,srcAsBools.length);
            String dst=Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*4,dstAsBools,dstPos*4, nHexs*4);
            String expected = Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        dst=Conversion.shortsToHexs(src,srcPos,dst,dstPos,nHexs);
        assertArrayEquals(expected,dst);
    }
    public void testShortsToHexs() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/4;
        final int dstSize = 4+3;
        boolean[] dstAsBools = new boolean[dstSize*4];
        checkShortsToHexs(srcAsBools, 0,dstAsBools, 0, 0);
        checkShortsToHexs(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkShortsToHexs(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkShortsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkShortsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkShortsToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkShortsToHexs(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testShortsToHexsConvenienceMethods() throws Exception {
            short[] src = {(short)0x1fdf};
            String dstInitPart = "0";
            String dstInitFull = "0000";
            assertArrayEquals(Conversion.shortsToHexs(src,1,dstInitPart,0,1),Conversion.shortsToHexs(src,1,1));
            assertArrayEquals(Conversion.shortsToHexs(src,0,dstInitPart,0,1),Conversion.shortsToHexs(src,1));
            assertArrayEquals(Conversion.shortsToHexs(src,0,dstInitFull,0,4),Conversion.shortsToHexs(src));
        }
                        
        public void testByteToHexsConvenienceMethods() throws Exception {
                byte src = (byte)0xa6;
        String dstInitPart = "0";
        String dstInitFull = "00";
        assertArrayEquals(Conversion.byteToHexs(src,1,dstInitPart,0,1),Conversion.byteToHexs(src,1,1));
        assertArrayEquals(Conversion.byteToHexs(src,0,dstInitPart,0,1),Conversion.byteToHexs(src,1));
        assertArrayEquals(Conversion.byteToHexs(src,0,dstInitFull,0,2),Conversion.byteToHexs(src));
        }
                
    void checkBytesToHexs(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nHexs){
        byte[] src=Conversion.boolsToBytes(srcAsBools,0, new byte[srcAsBools.length/8],0,srcAsBools.length);
            String dst=Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        System.arraycopy(srcAsBools,srcPos*4,dstAsBools,dstPos*4, nHexs*4);
            String expected = Conversion.boolsToHexs(dstAsBools,0,newHexs(dstAsBools.length/4),0,dstAsBools.length);
        dst=Conversion.bytesToHexs(src,srcPos,dst,dstPos,nHexs);
        assertArrayEquals(expected,dst);
    }
    public void testBytesToHexs() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/4;
        final int dstSize = 2+3;
        boolean[] dstAsBools = new boolean[dstSize*4];
        checkBytesToHexs(srcAsBools, 0,dstAsBools, 0, 0);
        checkBytesToHexs(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkBytesToHexs(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkBytesToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkBytesToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkBytesToHexs(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkBytesToHexs(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testBytesToHexsConvenienceMethods() throws Exception {
            byte[] src = {(byte)0x47};
            String dstInitPart = "0";
            String dstInitFull = "00";
            assertArrayEquals(Conversion.bytesToHexs(src,1,dstInitPart,0,1),Conversion.bytesToHexs(src,1,1));
            assertArrayEquals(Conversion.bytesToHexs(src,0,dstInitPart,0,1),Conversion.bytesToHexs(src,1));
            assertArrayEquals(Conversion.bytesToHexs(src,0,dstInitFull,0,2),Conversion.bytesToHexs(src));
        }
                        
        public void testLongToBoolsConvenienceMethods() throws Exception {
                long src = 0x0290ae3fd3f44260L;
        boolean[] dstInitPart = new boolean[1];
        boolean[] dstInitFull = new boolean[64];
        assertArrayEquals(Conversion.longToBools(src,1,dstInitPart,0,1),Conversion.longToBools(src,1,1));
        assertArrayEquals(Conversion.longToBools(src,0,dstInitPart,0,1),Conversion.longToBools(src,1));
        assertArrayEquals(Conversion.longToBools(src,0,dstInitFull,0,64),Conversion.longToBools(src));
        }
                
    void checkLongsToBools(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBools){
        long[] src=Conversion.boolsToLongs(srcAsBools,0, new long[srcAsBools.length/64],0,srcAsBools.length);
            boolean[] dst = new boolean[dstAsBools.length];
            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
            boolean[] expected;
            expected = dstAsBools;
        System.arraycopy(srcAsBools,srcPos*1,dstAsBools,dstPos*1, nBools*1);
        dst=Conversion.longsToBools(src,srcPos,dst,dstPos,nBools);
        assertArrayEquals(expected,dst);
    }
    public void testLongsToBools() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/1;
        final int dstSize = 64+3;
        boolean[] dstAsBools = new boolean[dstSize*1];
        checkLongsToBools(srcAsBools, 0,dstAsBools, 0, 0);
        checkLongsToBools(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkLongsToBools(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkLongsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkLongsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkLongsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkLongsToBools(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testLongsToBoolsConvenienceMethods() throws Exception {
            long[] src = {0x0306f0adc69fb39aL};
            boolean[] dstInitPart = new boolean[1];
            boolean[] dstInitFull = new boolean[64];
            assertArrayEquals(Conversion.longsToBools(src,1,dstInitPart,0,1),Conversion.longsToBools(src,1,1));
            assertArrayEquals(Conversion.longsToBools(src,0,dstInitPart,0,1),Conversion.longsToBools(src,1));
            assertArrayEquals(Conversion.longsToBools(src,0,dstInitFull,0,64),Conversion.longsToBools(src));
        }
                        
        public void testIntToBoolsConvenienceMethods() throws Exception {
                int src = 0x9a1c1650;
        boolean[] dstInitPart = new boolean[1];
        boolean[] dstInitFull = new boolean[32];
        assertArrayEquals(Conversion.intToBools(src,1,dstInitPart,0,1),Conversion.intToBools(src,1,1));
        assertArrayEquals(Conversion.intToBools(src,0,dstInitPart,0,1),Conversion.intToBools(src,1));
        assertArrayEquals(Conversion.intToBools(src,0,dstInitFull,0,32),Conversion.intToBools(src));
        }
                
    void checkIntsToBools(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBools){
        int[] src=Conversion.boolsToInts(srcAsBools,0, new int[srcAsBools.length/32],0,srcAsBools.length);
            boolean[] dst = new boolean[dstAsBools.length];
            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
            boolean[] expected;
            expected = dstAsBools;
        System.arraycopy(srcAsBools,srcPos*1,dstAsBools,dstPos*1, nBools*1);
        dst=Conversion.intsToBools(src,srcPos,dst,dstPos,nBools);
        assertArrayEquals(expected,dst);
    }
    public void testIntsToBools() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/1;
        final int dstSize = 32+3;
        boolean[] dstAsBools = new boolean[dstSize*1];
        checkIntsToBools(srcAsBools, 0,dstAsBools, 0, 0);
        checkIntsToBools(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkIntsToBools(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkIntsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkIntsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkIntsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkIntsToBools(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testIntsToBoolsConvenienceMethods() throws Exception {
            int[] src = {0x29c31544};
            boolean[] dstInitPart = new boolean[1];
            boolean[] dstInitFull = new boolean[32];
            assertArrayEquals(Conversion.intsToBools(src,1,dstInitPart,0,1),Conversion.intsToBools(src,1,1));
            assertArrayEquals(Conversion.intsToBools(src,0,dstInitPart,0,1),Conversion.intsToBools(src,1));
            assertArrayEquals(Conversion.intsToBools(src,0,dstInitFull,0,32),Conversion.intsToBools(src));
        }
                        
        public void testShortToBoolsConvenienceMethods() throws Exception {
                short src = (short)0xe806;
        boolean[] dstInitPart = new boolean[1];
        boolean[] dstInitFull = new boolean[16];
        assertArrayEquals(Conversion.shortToBools(src,1,dstInitPart,0,1),Conversion.shortToBools(src,1,1));
        assertArrayEquals(Conversion.shortToBools(src,0,dstInitPart,0,1),Conversion.shortToBools(src,1));
        assertArrayEquals(Conversion.shortToBools(src,0,dstInitFull,0,16),Conversion.shortToBools(src));
        }
                
    void checkShortsToBools(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBools){
        short[] src=Conversion.boolsToShorts(srcAsBools,0, new short[srcAsBools.length/16],0,srcAsBools.length);
            boolean[] dst = new boolean[dstAsBools.length];
            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
            boolean[] expected;
            expected = dstAsBools;
        System.arraycopy(srcAsBools,srcPos*1,dstAsBools,dstPos*1, nBools*1);
        dst=Conversion.shortsToBools(src,srcPos,dst,dstPos,nBools);
        assertArrayEquals(expected,dst);
    }
    public void testShortsToBools() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/1;
        final int dstSize = 16+3;
        boolean[] dstAsBools = new boolean[dstSize*1];
        checkShortsToBools(srcAsBools, 0,dstAsBools, 0, 0);
        checkShortsToBools(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkShortsToBools(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkShortsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkShortsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkShortsToBools(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkShortsToBools(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testShortsToBoolsConvenienceMethods() throws Exception {
            short[] src = {(short)0x1bcc};
            boolean[] dstInitPart = new boolean[1];
            boolean[] dstInitFull = new boolean[16];
            assertArrayEquals(Conversion.shortsToBools(src,1,dstInitPart,0,1),Conversion.shortsToBools(src,1,1));
            assertArrayEquals(Conversion.shortsToBools(src,0,dstInitPart,0,1),Conversion.shortsToBools(src,1));
            assertArrayEquals(Conversion.shortsToBools(src,0,dstInitFull,0,16),Conversion.shortsToBools(src));
        }
                        
        public void testByteToBoolsConvenienceMethods() throws Exception {
                byte src = (byte)0xd6;
        boolean[] dstInitPart = new boolean[1];
        boolean[] dstInitFull = new boolean[8];
        assertArrayEquals(Conversion.byteToBools(src,1,dstInitPart,0,1),Conversion.byteToBools(src,1,1));
        assertArrayEquals(Conversion.byteToBools(src,0,dstInitPart,0,1),Conversion.byteToBools(src,1));
        assertArrayEquals(Conversion.byteToBools(src,0,dstInitFull,0,8),Conversion.byteToBools(src));
        }
                
    void checkBytesToBools(boolean []srcAsBools,int srcPos, boolean []dstAsBools, int dstPos, int nBools){
        byte[] src=Conversion.boolsToBytes(srcAsBools,0, new byte[srcAsBools.length/8],0,srcAsBools.length);
            boolean[] dst = new boolean[dstAsBools.length];
            System.arraycopy(dstAsBools,0,dst,0, dstAsBools.length);
            boolean[] expected;
            expected = dstAsBools;
        System.arraycopy(srcAsBools,srcPos*1,dstAsBools,dstPos*1, nBools*1);
        dst=Conversion.bytesToBools(src,srcPos,dst,dstPos,nBools);
        assertArrayEquals(expected,dst);
    }
    public void testBytesToBools() throws Exception{
        //src is the conversion of "0123456789ABCDEFC539275BE64A8C38D83725FB908A163D"
        boolean[] srcAsBools = new boolean[]{true, false, true, true, true, true, false, false, false, true, true, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, false, false, true, false, false, true, true, true, false, true, true, false, false, false, false, false, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false};
        //src and dst sizes in dst units
        final int srcSize = srcAsBools.length/1;
        final int dstSize = 8+3;
        boolean[] dstAsBools = new boolean[dstSize*1];
        checkBytesToBools(srcAsBools, 0,dstAsBools, 0, 0);
        checkBytesToBools(srcAsBools, 0,dstAsBools, 0, dstSize);
        checkBytesToBools(srcAsBools, 0,dstAsBools, 1, dstSize-1);
        checkBytesToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize);
        checkBytesToBools(srcAsBools,srcSize-dstSize,dstAsBools, 0, dstSize-1);
        checkBytesToBools(srcAsBools,srcSize-dstSize,dstAsBools, 1, dstSize-1);
        checkBytesToBools(srcAsBools,srcSize-1,dstAsBools, dstSize-1, 1);
    }
            
        public void testBytesToBoolsConvenienceMethods() throws Exception {
            byte[] src = {(byte)0xcb};
            boolean[] dstInitPart = new boolean[1];
            boolean[] dstInitFull = new boolean[8];
            assertArrayEquals(Conversion.bytesToBools(src,1,dstInitPart,0,1),Conversion.bytesToBools(src,1,1));
            assertArrayEquals(Conversion.bytesToBools(src,0,dstInitPart,0,1),Conversion.bytesToBools(src,1));
            assertArrayEquals(Conversion.bytesToBools(src,0,dstInitFull,0,8),Conversion.bytesToBools(src));
        }
        
        static String dbgPrint(long[] src) {
            StringBuilder sb = new StringBuilder();
                    for(long e:src){
                        sb.append("0x").append(Long.toHexString(e)).append(',');
                    }
            String out = sb.toString();
            return out.substring(0,out.length()-1);
        }
        static void assertArrayEquals(long[] expected, long[] actual){
            assertEquals(expected.length,actual.length);
            for(int i=0;i<expected.length;i++){
                try{
                    assertEquals(expected[i], actual[i]);
                }catch(Throwable e){
                    String msg = "Mismatch at index "+i+" between:\n"+
                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
                    TestCase.fail(msg+"\n"+e.getMessage());
                }
            }
        }
        static String dbgPrint(int[] src) {
            StringBuilder sb = new StringBuilder();
                    for(int e:src){
                        sb.append("0x").append(Long.toHexString(e)).append(',');
                    }
            String out = sb.toString();
            return out.substring(0,out.length()-1);
        }
        static void assertArrayEquals(int[] expected, int[] actual){
            assertEquals(expected.length,actual.length);
            for(int i=0;i<expected.length;i++){
                try{
                    assertEquals(expected[i], actual[i]);
                }catch(Throwable e){
                    String msg = "Mismatch at index "+i+" between:\n"+
                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
                    TestCase.fail(msg+"\n"+e.getMessage());
                }
            }
        }
        static String dbgPrint(short[] src) {
            StringBuilder sb = new StringBuilder();
                    for(short e:src){
                        sb.append("0x").append(Long.toHexString(e)).append(',');
                    }
            String out = sb.toString();
            return out.substring(0,out.length()-1);
        }
        static void assertArrayEquals(short[] expected, short[] actual){
            assertEquals(expected.length,actual.length);
            for(int i=0;i<expected.length;i++){
                try{
                    assertEquals(expected[i], actual[i]);
                }catch(Throwable e){
                    String msg = "Mismatch at index "+i+" between:\n"+
                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
                    TestCase.fail(msg+"\n"+e.getMessage());
                }
            }
        }
        static String dbgPrint(byte[] src) {
            StringBuilder sb = new StringBuilder();
                    for(byte e:src){
                        sb.append("0x").append(Long.toHexString(e)).append(',');
                    }
            String out = sb.toString();
            return out.substring(0,out.length()-1);
        }
        static void assertArrayEquals(byte[] expected, byte[] actual){
            assertEquals(expected.length,actual.length);
            for(int i=0;i<expected.length;i++){
                try{
                    assertEquals(expected[i], actual[i]);
                }catch(Throwable e){
                    String msg = "Mismatch at index "+i+" between:\n"+
                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
                    TestCase.fail(msg+"\n"+e.getMessage());
                }
            }
        }
        static String dbgPrint(boolean[] src) {
            StringBuilder sb = new StringBuilder();
                    for(boolean e:src){
                        if(e) sb.append("1,");
                        else sb.append("0,");
                    }
            String out = sb.toString();
            return out.substring(0,out.length()-1);
        }
        static void assertArrayEquals(boolean[] expected, boolean[] actual){
            assertEquals(expected.length,actual.length);
            for(int i=0;i<expected.length;i++){
                try{
                    assertEquals(expected[i], actual[i]);
                }catch(Throwable e){
                    String msg = "Mismatch at index "+i+" between:\n"+
                            dbgPrint(expected)+" and\n"+dbgPrint(actual);
                    TestCase.fail(msg+"\n"+e.getMessage());
                }
            }
        }
    static void assertArrayEquals(String expected, String actual){
        assertEquals(expected,actual);
    }
    public void testIntsToLong() throws Exception {
        int[] src = new int[]{
                0xCDF1F0C1,
                0x0F123456,
                0x78000000};
        assertEquals(0x0000000000000000L,Conversion.intsToLong(src,0,0L,0,0));
        assertEquals(0x0000000000000000L,Conversion.intsToLong(src,1,0L,0,0));
        assertEquals(0x00000000CDF1F0C1L,Conversion.intsToLong(src,0,0L,0,1));
        assertEquals(0x0F123456CDF1F0C1L,Conversion.intsToLong(src,0,0L,0,2));
        assertEquals(0x000000000F123456L,Conversion.intsToLong(src,1,0L,0,1));
        assertEquals(0x123456789ABCDEF0L,Conversion.intsToLong(src,0,0x123456789ABCDEF0L,0,0));
        assertEquals(0x1234567878000000L,Conversion.intsToLong(src,2,0x123456789ABCDEF0L,0,1));
        //assertEquals(0x0F12345678000000L,Conversion.intsToLong(src,1,0x123456789ABCDEF0L,32,2));
    }
    public void testShortsToLong() throws Exception {
        //TODO: implement this test. This is somehow low priority since intsToLong and bytesToLong are tested
    }
    public void testBytesToLong() throws Exception {
        byte[] src = new byte[]{
                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
                (byte)0x78};
        assertEquals(0x0000000000000000L,Conversion.bytesToLong(src,0,0L,0,0));
        assertEquals(0x00000000000000CDL,Conversion.bytesToLong(src,0,0L,0,1));
        assertEquals(0x00000000C1F0F1CDL,Conversion.bytesToLong(src,0,0L,0,4));
        assertEquals(0x000000000FC1F0F1L,Conversion.bytesToLong(src,1,0L,0,4));
        assertEquals(0x123456789ABCDEF0L,Conversion.bytesToLong(src,0,0x123456789ABCDEF0L,0,0));
        assertEquals(0x12345678CDBCDEF0L,Conversion.bytesToLong(src,0,0x123456789ABCDEF0L,24,1));
        //assertEquals(0x123456789A7856F0L,Conversion.bytesToLong(src,7,0x123456789ABCDEF0L,8,2));
    }
    public void testShortsToInt() throws Exception {
        short[] src = new short[]{
                (short)0xCDF1,(short)0xF0C1,
                (short)0x0F12,(short)0x3456,
                (short)0x7800};
        assertEquals(0x00000000,Conversion.shortsToInt(src, 0, 0, 0, 0));
        assertEquals(0x0000CDF1,Conversion.shortsToInt(src, 0, 0, 0, 1));
        assertEquals(0xF0C1CDF1,Conversion.shortsToInt(src, 0, 0, 0, 2));
        assertEquals(0x0F12F0C1,Conversion.shortsToInt(src, 1, 0, 0, 2));
        assertEquals(0x12345678,Conversion.shortsToInt(src, 0, 0x12345678, 0, 0));
        assertEquals(0xCDF15678,Conversion.shortsToInt(src, 0, 0x12345678, 16, 1));
        //assertEquals(0x34567800,Conversion.shortsToInt(src, 3, 0x12345678, 16, 2));

    }
    public void testBytesToInt() throws Exception {
        byte[] src = new byte[]{
                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
                (byte)0x78};
        assertEquals(0x00000000,Conversion.bytesToInt(src, 0, 0, 0, 0));
        assertEquals(0x000000CD,Conversion.bytesToInt(src, 0, 0, 0, 1));
        assertEquals(0xC1F0F1CD,Conversion.bytesToInt(src, 0, 0, 0, 4));
        assertEquals(0x0FC1F0F1,Conversion.bytesToInt(src, 1, 0, 0, 4));
        assertEquals(0x12345678,Conversion.bytesToInt(src, 0, 0x12345678, 0, 0));
        assertEquals(0xCD345678,Conversion.bytesToInt(src, 0, 0x12345678, 24, 1));
        //assertEquals(0x56341278,Conversion.bytesToInt(src, 5, 0x01234567, 8, 4));
    }
    public void testBytesToShort() throws Exception {
        byte[] src = new byte[]{
                (byte)0xCD,(byte)0xF1,(byte)0xF0,(byte)0xC1,
                (byte)0x0F,(byte)0x12,(byte)0x34,(byte)0x56,
                (byte)0x78};
        assertEquals((short)0x0000,Conversion.bytesToShort(src, 0, (short) 0, 0, 0));
        assertEquals((short)0x00CD,Conversion.bytesToShort(src, 0, (short) 0, 0, 1));
        assertEquals((short)0xF1CD,Conversion.bytesToShort(src, 0, (short) 0, 0, 2));
        assertEquals((short)0xF0F1,Conversion.bytesToShort(src, 1, (short) 0, 0, 2));
        assertEquals((short)0x1234,Conversion.bytesToShort(src, 0, (short) 0x1234, 0, 0));
        assertEquals((short)0xCD34,Conversion.bytesToShort(src, 0, (short) 0x1234, 8, 1));
        //assertEquals((short)0x5678,Conversion.bytesToShort(src, 7, (short) 0x0123, 8, 2));
    }
    public void testHexsToLong() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals(0x0000000000000000L,Conversion.hexsToLong(src,0,0L,0,0));
        assertEquals(0x000000000000000CL,Conversion.hexsToLong(src,0,0L,0,1));
        assertEquals(0x000000001C0F1FDCL,Conversion.hexsToLong(src,0,0L,0,8));
        assertEquals(0x0000000001C0F1FDL,Conversion.hexsToLong(src,1,0L,0,8));
        assertEquals(0x123456798ABCDEF0L,Conversion.hexsToLong(src, 0,0x123456798ABCDEF0L,0,0));
        assertEquals(0x1234567876BCDEF0L,Conversion.hexsToLong(src,15,0x123456798ABCDEF0L,24,3));
    }
    public void testHexsToInt() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals(0x00000000,Conversion.hexsToInt(src,0,0,0,0));
        assertEquals(0x0000000C,Conversion.hexsToInt(src,0,0,0,1));
        assertEquals(0x1C0F1FDC,Conversion.hexsToInt(src,0,0,0,8));
        assertEquals(0x01C0F1FD,Conversion.hexsToInt(src,1,0,0,8));
        assertEquals(0x12345679,Conversion.hexsToInt(src, 0,0x12345679,0,0));
        assertEquals(0x87645679,Conversion.hexsToInt(src,15,0x12345679,20,3));
    }
    public void testHexsToShort() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals((short)0x0000,Conversion.hexsToShort(src, 0,(short)0,0,0));
        assertEquals((short)0x000C,Conversion.hexsToShort(src, 0,(short)0,0,1));
        assertEquals((short)0x1FDC,Conversion.hexsToShort(src, 0,(short)0,0,4));
        assertEquals((short)0xF1FD,Conversion.hexsToShort(src, 1,(short)0,0,4));
        assertEquals((short)0x1234,Conversion.hexsToShort(src, 0,(short)0x1234,0,0));
        assertEquals((short)0x8764,Conversion.hexsToShort(src,15,(short)0x1234,4,3));
    }
    public void testHexsToByte() throws Exception {
        String src = "CDF1F0C10F12345678";
        assertEquals((byte)0x00,Conversion.hexsToByte(src, 0,(byte)0,0,0));
        assertEquals((byte)0x0C,Conversion.hexsToByte(src, 0,(byte)0,0,1));
        assertEquals((byte)0xDC,Conversion.hexsToByte(src, 0,(byte)0,0,2));
        assertEquals((byte)0xFD,Conversion.hexsToByte(src, 1,(byte)0,0,2));
        assertEquals((byte)0x34,Conversion.hexsToByte(src, 0,(byte)0x34,0,0));
        assertEquals((byte)0x84,Conversion.hexsToByte(src,17,(byte)0x34,4,1));
    }
    public void testBoolsToLong() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools

        assertEquals(0x0000000000000000L,Conversion.boolsToLong(src,0,0L,0,0));
        assertEquals(0x000000000000000CL,Conversion.boolsToLong(src,0,0L,0,1*4));
        assertEquals(0x000000001C0F1FDCL,Conversion.boolsToLong(src,0,0L,0,8*4));
        assertEquals(0x0000000001C0F1FDL,Conversion.boolsToLong(src,1*4,0L,0,8*4));
        assertEquals(0x123456798ABCDEF0L,Conversion.boolsToLong(src, 0,0x123456798ABCDEF0L,0,0));
        assertEquals(0x1234567876BCDEF0L,Conversion.boolsToLong(src,15*4,0x123456798ABCDEF0L,24,3*4));
    }
    public void testBoolsToInt() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals(0x00000000,Conversion.boolsToInt(src, 0*4,0,0,0*4));
        assertEquals(0x0000000C,Conversion.boolsToInt(src, 0*4,0,0,1*4));
        assertEquals(0x1C0F1FDC,Conversion.boolsToInt(src, 0*4,0,0,8*4));
        assertEquals(0x01C0F1FD,Conversion.boolsToInt(src, 1*4,0,0,8*4));
        assertEquals(0x12345679,Conversion.boolsToInt(src, 0*4,0x12345679, 0,0*4));
        assertEquals(0x87645679,Conversion.boolsToInt(src,15*4,0x12345679,20,3*4));
    }
    public void testBoolsToShort() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals((short)0x0000,Conversion.boolsToShort(src, 0*4,(short)0,0,0*4));
        assertEquals((short)0x000C,Conversion.boolsToShort(src, 0*4,(short)0,0,1*4));
        assertEquals((short)0x1FDC,Conversion.boolsToShort(src, 0*4,(short)0,0,4*4));
        assertEquals((short)0xF1FD,Conversion.boolsToShort(src, 1*4,(short)0,0,4*4));
        assertEquals((short)0x1234,Conversion.boolsToShort(src, 0*4,(short)0x1234,0,0*4));
        assertEquals((short)0x8764,Conversion.boolsToShort(src,15*4,(short)0x1234,4,3*4));
    }
    public void testBoolsToByte() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals((byte)0x00,Conversion.boolsToByte(src, 0*4,(byte)0,0,0*4));
        assertEquals((byte)0x0C,Conversion.boolsToByte(src, 0*4,(byte)0,0,1*4));
        assertEquals((byte)0xDC,Conversion.boolsToByte(src, 0*4,(byte)0,0,2*4));
        assertEquals((byte)0xFD,Conversion.boolsToByte(src, 1*4,(byte)0,0,2*4));
        assertEquals((byte)0x34,Conversion.boolsToByte(src, 0*4,(byte)0x34,0,0*4));
        assertEquals((byte)0x84,Conversion.boolsToByte(src,17*4,(byte)0x34,4,1*4));
    }
    public void testBoolsToHex() throws Exception {
        boolean[] src = new boolean[]{
                false, false, true, true, true, false, true, true,
                true,  true,  true,  true, true, false, false, false,
                true,  true,  true,  true,false, false, false, false,
                false, false,  true,  true, true, false, false, false,
                false, false, false, false, true,  true,  true,  true,
                true, false, false, false,false,  true, false, false,
                true,  true, false, false,false, false,  true, false,
                true, false,  true, false,false,  true,  true, false,
                true,  true,  true, false,false, false, false,  true} ;//conversion of "CDF1F0C10F12345678" by hexsToBools
        assertEquals('F',Conversion.boolsToHex(src, 0*4,'F',0,0*4));
        assertEquals('C',Conversion.boolsToHex(src, 0*4,'F',0,1*4));
        assertEquals('D',Conversion.boolsToHex(src, 1*4,'F',0,1*4));
        assertEquals('8',Conversion.boolsToHex(src,17*4,'F',0,1*4));
        assertEquals('9',Conversion.boolsToHex(src, 3*4,'F',0,3));
        assertEquals('1',Conversion.boolsToHex(src, 3*4,'0',0,3));
        assertEquals('E',Conversion.boolsToHex(src,   1,'F',0,4));
        assertEquals('1',Conversion.boolsToHex(src,17*4+3,'0',0,1));
        assertEquals('2',Conversion.boolsToHex(src,17*4+3,'0',1,1));
        assertEquals('4',Conversion.boolsToHex(src,17*4+3,'0',2,1));
        assertEquals('8',Conversion.boolsToHex(src,17*4+3,'0',3,1));
    }
    public void testLongToInts() throws Exception {
        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 0,new int[]{},0,0));
        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 100,new int[]{},0,0));
        assertArrayEquals(new int[]{},Conversion.longToInts(0x0000000000000000L, 0,new int[]{},100,0));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,0));
        assertArrayEquals(new int[]{0x90ABCDEF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,1));
        assertArrayEquals(new int[]{0x90ABCDEF,0x12345678,0xFFFFFFFF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,2));
        //assertArrayEquals(new int[]{0x90ABCDEF,0x12345678,0x90ABCDEF,0x12345678},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},0,4));//rejected by assertion
        //assertArrayEquals(new int[]{0xFFFFFFFF,0x90ABCDEF,0x12345678,0x90ABCDEF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},1,3));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF,0x12345678},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},2,2));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0x90ABCDEF},Conversion.longToInts(0x1234567890ABCDEFL, 0,new int[]{-1,-1,-1,-1},3,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x4855E6F7,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 1,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x242AF37B,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 2,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x121579BD,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 3,new int[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new int[]{0xFFFFFFFF,0xFFFFFFFF,0x890ABCDE,0xFFFFFFFF},Conversion.longToInts(0x1234567890ABCDEFL, 4,new int[]{-1,-1,-1,-1},2,1));
        //assertArrayEquals(new int[]{0x4855E6F7,0x091A2B3C,0x4855E6F7,0x091A2B3C},Conversion.longToInts(0x1234567890ABCDEFL, 1,new int[]{-1,-1,-1,-1},0,4));//rejected by assertion
        assertArrayEquals(new int[]{0x091A2B3C},Conversion.longToInts(0x1234567890ABCDEFL,33,new int[]{0},0,1));
    }
    public void testLongToShorts() throws Exception {
        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 0,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 100,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.longToShorts(0x0000000000000000L, 0,new short[]{},100,0));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,0));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,1));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0xFFFF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,2));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0x5678,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,3));
        assertArrayEquals(new short[]{(short)0xCDEF,(short)0x90AB,(short)0x5678,(short)0x1234},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},0,4));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xCDEF,(short)0x90AB,(short)0x5678},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},1,3));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xCDEF,(short)0x90AB},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},2,2));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xCDEF,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xCDEF},Conversion.longToShorts(0x1234567890ABCDEFL, 0,new short[]{-1,-1,-1,-1},3,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xE6F7,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 1,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xF37B,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 2,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x79BD,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 3,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xBCDE,(short)0xFFFF},Conversion.longToShorts(0x1234567890ABCDEFL, 4,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xE6F7,(short)0x4855,(short)0x2B3C,(short)0x091A},Conversion.longToShorts(0x1234567890ABCDEFL, 1,new short[]{-1,-1,-1,-1},0,4));
        assertArrayEquals(new short[]{(short)0x2B3C},Conversion.longToShorts(0x1234567890ABCDEFL,33,new short[]{0},0,1));
    }
    public void testIntToShorts() throws Exception {
        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 0,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 100,new short[]{},0,0));
        assertArrayEquals(new short[]{},Conversion.intToShorts(0x00000000, 0,new short[]{},100,0));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,0));
        assertArrayEquals(new short[]{(short)0x5678,(short)0xFFFF,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,1));
        assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0xFFFF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,2));
        //assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0x5678,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,3));//rejected by assertion
        //assertArrayEquals(new short[]{(short)0x5678,(short)0x1234,(short)0x5678,(short)0x1234},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},0,4));
        //assertArrayEquals(new short[]{(short)0xFFFF,(short)0x5678,(short)0x1234,(short)0x5678},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},1,3));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x5678,(short)0x1234},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},2,2));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x5678,(short)0xFFFF},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0xFFFF,(short)0x5678},Conversion.intToShorts(0x12345678, 0,new short[]{-1,-1,-1,-1},3,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x2B3C,(short)0xFFFF},Conversion.intToShorts(0x12345678, 1,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x159E,(short)0xFFFF},Conversion.intToShorts(0x12345678, 2,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x8ACF,(short)0xFFFF},Conversion.intToShorts(0x12345678, 3,new short[]{-1,-1,-1,-1},2,1));
        assertArrayEquals(new short[]{(short)0xFFFF,(short)0xFFFF,(short)0x4567,(short)0xFFFF},Conversion.intToShorts(0x12345678, 4,new short[]{-1,-1,-1,-1},2,1));
        //assertArrayEquals(new short[]{(short)0xE6F7,(short)0x4855,(short)0x2B3C,(short)0x091A},Conversion.intToShorts(0x12345678, 1,new short[]{-1,-1,-1,-1},0,4));//rejected by assertion
        //assertArrayEquals(new short[]{(short)0x2B3C},Conversion.intToShorts(0x12345678,33,new short[]{0},0,1));//rejected by assertion
        assertArrayEquals(new short[]{(short)0x091A},Conversion.intToShorts(0x12345678,17,new short[]{0},0,1));
    }
    public void testLongToBytes() throws Exception {
        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 0,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 100,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.longToBytes(0x0000000000000000L, 0,new byte[]{},100,0));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,0));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,2));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,4));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,7));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,8));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,2));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,7));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12},Conversion.longToBytes(0x1234567890ABCDEFL, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));
        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00},Conversion.longToBytes(0x1234567890ABCDEFL, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));
        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00,(byte)0x00},Conversion.longToBytes(0x1234567890ABCDEFL,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,8));//rejected by assertion
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xC4,(byte)0xB3,(byte)0xA2,(byte)0x91,(byte)0x00,(byte)0xFF},Conversion.longToBytes(0x1234567890ABCDEFL,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,7));
    }
    public void testIntToBytes() throws Exception {
        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 0,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 100,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.intToBytes(0x00000000, 0,new byte[]{},100,0));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,0));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,2));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,4));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,2));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x90,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));
        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0x00,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,4));//rejected by assertion
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0x85,(byte)0xFC,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.intToBytes(0x90ABCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1},3,3));
    }
    public void testShortToBytes() throws Exception {
        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 0,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 100,new byte[]{},0,0));
        assertArrayEquals(new byte[]{},Conversion.shortToBytes((short)0x0000, 0,new byte[]{},100,0));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,0));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,2));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},3,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xEF,(byte)0xCD,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 0,new byte[]{-1,-1,-1,-1,-1,-1,-1},3,2));
        assertArrayEquals(new byte[]{(byte)0xF7,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 1,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0x7B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 2,new byte[]{-1,-1,-1,-1,-1,-1,-1},0,1));
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x6F,(byte)0xFE,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF, 5,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,2));
        //assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x5E,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,2));//rejected by assertion
        assertArrayEquals(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0xFE,(byte)0xFF,(byte)0xFF,(byte)0xFF},Conversion.shortToBytes((short)0xCDEF,13,new byte[]{-1, 0,-1,-1,-1,-1,-1},3,1));
    }
    public void testLongToHexs() throws Exception {
        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 0,"",0,0));
        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 100,"",0,0));
        assertEquals("",Conversion.longToHexs(0x0000000000000000L, 0,"",100,0));
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDE3L, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
        assertEquals("FEDCBA098765432FFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,15));
        assertEquals("FEDCBA0987654321FFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,16));
        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDE3L, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
        assertEquals("FFFFEDCFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
        assertEquals("FFFFEDCBA098765432FFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,15));
        assertEquals("FFFFEDCBA0987654321FFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));
        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FFFDB975121FCA86420FFFFF",Conversion.longToHexs(0x1234567890ABCDEFL, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));
        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL,4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,16));//rejected by assertion
        assertEquals("FFFEDCBA0987654321FFFFFF",Conversion.longToHexs(0x1234567890ABCDEFL,4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,15));
    }
    public void testIntToHexs() throws Exception {
        assertEquals("",Conversion.intToHexs(0x00000000, 0,"",0,0));
        assertEquals("",Conversion.intToHexs(0x00000000, 100,"",0,0));
        assertEquals("",Conversion.intToHexs(0x00000000, 0,"",100,0));
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
        assertEquals("FEDCBA0FFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,7));
        assertEquals("FEDCBA09FFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,8));
        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
        assertEquals("FFFFEDCFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
        assertEquals("FFFFEDCBA0FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,7));
        assertEquals("FFFFEDCBA09FFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));
        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FFFDB97512FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));
        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,8));//rejected by assertion
        assertEquals("FFFEDCBA09FFFFFFFFFFFFFF",Conversion.intToHexs(0x90ABCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,7));
    }
    public void testShortToHexs() throws Exception {
        assertEquals("",Conversion.shortToHexs((short)0x0000, 0,"",0,0));
        assertEquals("",Conversion.shortToHexs((short)0x0000, 100,"",0,0));
        assertEquals("",Conversion.shortToHexs((short)0x0000, 0,"",100,0));
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,0));
        assertEquals("3FFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FEFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,2));
        assertEquals("FEDFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,3));
        assertEquals("FEDCFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",0,4));
        assertEquals("FFF3FFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDE3, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,1));
        assertEquals("FFFFEFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 0,"FFFFFFFFFFFFFFFFFFFFFFFF",3,2));
        assertEquals("7FFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 1,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("BFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 2,"FFFFFFFFFFFFFFFFFFFFFFFF",0,1));
        assertEquals("FFFDB9FFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 3,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));
        //assertEquals("FFFFFFFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,4));//rejected by assertion
        assertEquals("FFFEDCFFFFFFFFFFFFFFFFFF",Conversion.shortToHexs((short)0xCDEF, 4,"FFFFFFFFFFFFFFFFFFFFFFFF",3,3));
    }
    public void testByteToHexs() throws Exception {
        assertEquals("",Conversion.byteToHexs((byte)0x00, 0,"",0,0));
        assertEquals("",Conversion.byteToHexs((byte)0x00, 100,"",0,0));
        assertEquals("",Conversion.byteToHexs((byte)0x00, 0,"",100,0));
        assertEquals("00000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,0));
        assertEquals("F0000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,1));
        assertEquals("FE000",Conversion.byteToHexs((byte)0xEF, 0,"00000",0,2));
        assertEquals("000F0",Conversion.byteToHexs((byte)0xEF, 0,"00000",3,1));
        assertEquals("000FE",Conversion.byteToHexs((byte)0xEF, 0,"00000",3,2));
        assertEquals("70000",Conversion.byteToHexs((byte)0xEF, 1,"00000",0,1));
        assertEquals("B0000",Conversion.byteToHexs((byte)0xEF, 2,"00000",0,1));
        assertEquals("000DF",Conversion.byteToHexs((byte)0xEF, 3,"00000",3,2));
        //assertEquals("00000",Conversion.byteToHexs((byte)0xEF, 4,"00000",3,2));//rejected by assertion
        assertEquals("000E0",Conversion.byteToHexs((byte)0xEF, 4,"00000",3,1));
    }
    public void testLongToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.longToBools(0x0000000000000000L, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 0));

        assertArrayEquals(new boolean[]{true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 1));
        assertArrayEquals(new boolean[]{true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 2));
        assertArrayEquals(new boolean[]{true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0, 3));
        assertArrayEquals(new boolean[]{true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,63));
        assertArrayEquals(new boolean[]{true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 0,64));
        assertArrayEquals(new boolean[]{false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2, 1));
        assertArrayEquals(new boolean[]{false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 0,new boolean[69], 2,64));
        assertArrayEquals(new boolean[]{true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 1,new boolean[69], 0, 63));
        assertArrayEquals(new boolean[]{true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 0, 62));

        //assertArrayEquals(new boolean[]{false,false,false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false    ,false,false,false,false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 3, 63));//rejected by assertion
        assertArrayEquals(new boolean[]{false,false,false, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false    ,false,false,false,false},Conversion.longToBools(0x1234567890ABCDEFL, 2,new boolean[69], 3, 62));
    }
    public void testIntToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.intToBools(0x00000000, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.intToBools(0x90ABCDEF, 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 1));
        assertArrayEquals(new boolean[]{ true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 2));
        assertArrayEquals(new boolean[]{ true,  true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0, 3));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0,31));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 0,32));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false,    false,  true, false, false, false},Conversion.intToBools(0x90ABCDEF, 0,new boolean[37], 2,32));
        assertArrayEquals(new boolean[]{ true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 1,new boolean[37], 0,31));
        assertArrayEquals(new boolean[]{ true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,  true, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 0,30));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,    false, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 3,31));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,  true,  true, false,  true, false,  true, false,  true, false, false, false, false,  true, false, false,     true, false, false, false, false},Conversion.intToBools(0x90ABCDEF, 2,new boolean[37], 3,30));
    }
    public void testShortToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.shortToBools((short)0x0000, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.shortToBools((short)0xCDEF, 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 1));
        assertArrayEquals(new boolean[]{ true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 2));
        assertArrayEquals(new boolean[]{ true,  true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0, 3));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0,15));
        assertArrayEquals(new boolean[]{ true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 0,16));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true,  true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,       true,  true, false, false, false},Conversion.shortToBools((short)0xCDEF, 0,new boolean[21], 2,16));
        assertArrayEquals(new boolean[]{ true,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 1,new boolean[21], 0,15));
        assertArrayEquals(new boolean[]{ true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,  true, false, false,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 0,14));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,      false, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 3,15));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,  true,  true, false,  true,  true, false, false,  true,       true, false, false, false, false},Conversion.shortToBools((short)0xCDEF, 2,new boolean[21], 3,14));
    }
    public void testByteToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.byteToBools((byte)0x00, 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.byteToBools((byte)0xEF, 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 1));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 2));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 3));
        assertArrayEquals(new boolean[]{ true, false,  true, false,  true, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 7));
        assertArrayEquals(new boolean[]{ true, false,  true, false,  true, false, false,  true,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 0, 8));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true, false,  true, false,  true, false,      false,  true, false, false, false},Conversion.byteToBools((byte)0x95, 0,new boolean[13], 2, 8));
        assertArrayEquals(new boolean[]{false,  true, false,  true, false, false,  true, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 1,new boolean[13], 0, 7));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false,  true, false, false,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 0, 6));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true,      false, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 3, 7));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true, false,  true, false, false,       true, false, false, false, false},Conversion.byteToBools((byte)0x95, 2,new boolean[13], 3, 6));
    }
    public void testHexToBools() throws Exception {
        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 0,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 100,new boolean[]{},0,0));
        assertArrayEquals(new boolean[]{},Conversion.hexToBools('0', 0,new boolean[]{},100,0));
        assertArrayEquals(new boolean[69],Conversion.hexToBools('F', 0,new boolean[69], 0, 0));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 1));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 2));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 3));
        assertArrayEquals(new boolean[]{ true, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 0, 4));
        assertArrayEquals(new boolean[]{ true, false,  true, false,  true,  true,  true,  true},Conversion.hexToBools('5', 0,new boolean[]{true,true,true,true,true,true,true,true}, 0, 4));
        assertArrayEquals(new boolean[]{false, false,  true, false, false, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 2, 1));
        assertArrayEquals(new boolean[]{false, false,  true, false,  true, false, false, false},Conversion.hexToBools('5', 0,new boolean[8], 2, 4));
        assertArrayEquals(new boolean[]{false,  true, false, false, false, false, false, false},Conversion.hexToBools('5', 1,new boolean[8], 0, 3));
        assertArrayEquals(new boolean[]{ true, false, false, false, false, false, false, false},Conversion.hexToBools('5', 2,new boolean[8], 0, 2));
        //assertArrayEquals(new boolean[]{false, false, false,  true,  true, false,  true,  true},Conversion.hexToBools('5', 2,new boolean[8], 3, 3));//rejected by assertion
        assertArrayEquals(new boolean[]{false, false, false,  true, false, false, false, false},Conversion.hexToBools('5', 2,new boolean[8], 3, 1));
    }
    public void testIntsToLongs() throws Exception{
        int[] src = new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.intsToLongs(src,0,dst,0,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.intsToLongs(src,0,dst,1,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.intsToLongs(src,0,dst,5,1));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.intsToLongs(src,0,dst,3,2));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.intsToLongs(src,1,dst,3,2));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.intsToLongs(src,2,dst,3,2));
    }
    public void testShortsToLongs() throws Exception{
        short[] src = new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.shortsToLongs(src,0,dst, 0,8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.shortsToLongs(src,0,dst, 2,8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.shortsToLongs(src,0,dst,10,2));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.shortsToLongs(src,0,dst, 6,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.shortsToLongs(src,2,dst, 6,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.shortsToLongs(src,4,dst, 6,4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64AE64AL},Conversion.shortsToLongs(src,7,dst, 8,1));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE64A4567E64AE64AL},Conversion.shortsToLongs(src,7,dst,11,1));
        assertArrayEquals(new long[] {0x012345670123E64AL, 0xC539275B89ABCDEFL, 0xE64A4567E64AE64AL},Conversion.shortsToLongs(src,7,dst, 0,1));
    }
    public void testShortsToInts() throws Exception{
        short[] src = new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A};
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38,0x00000000, 0x00000000},Conversion.shortsToInts(src,0,dst, 0,8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x00000000},Conversion.shortsToInts(src,0,dst, 2,8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.shortsToInts(src,0,dst,10,2));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x01234567,0x89ABCDEF, 0x01234567},Conversion.shortsToInts(src,0,dst, 6,4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x89ABCDEF,0xC539275B, 0x01234567},Conversion.shortsToInts(src,2,dst, 6,4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.shortsToInts(src,4,dst, 6,4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0x01234567},Conversion.shortsToInts(src,7,dst, 8,1));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0xE64A4567},Conversion.shortsToInts(src,7,dst,11,1));
        assertArrayEquals(new int[] {0x0123E64A,0x01234567, 0x89ABCDEF,0xC539275B,0xE64AE64A, 0xE64A4567},Conversion.shortsToInts(src,7,dst, 0,1));
    }
    public void testBytesToLongs() throws Exception{
        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.bytesToLongs(src, 0,dst, 0,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.bytesToLongs(src, 0,dst, 4,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.bytesToLongs(src, 0,dst,20, 4));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.bytesToLongs(src, 0,dst,12, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.bytesToLongs(src, 4,dst,12, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.bytesToLongs(src, 8,dst,12, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst,16, 1));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE6234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst,23, 1));
        assertArrayEquals(new long[] {0x01234567012345E6L, 0xC539275B89ABCDEFL, 0xE6234567E64A8CE6L},Conversion.bytesToLongs(src,15,dst, 0, 1));
    }
    public void testBytesToInts() throws Exception{
        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38,0x00000000, 0x00000000},Conversion.bytesToInts(src, 0,dst, 0,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x00000000},Conversion.bytesToInts(src, 0,dst, 4,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.bytesToInts(src, 0,dst,20, 4));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x01234567,0x89ABCDEF, 0x01234567},Conversion.bytesToInts(src, 0,dst,12, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0x89ABCDEF,0xC539275B, 0x01234567},Conversion.bytesToInts(src, 4,dst,12, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8C38, 0x01234567},Conversion.bytesToInts(src, 8,dst,12, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0x01234567},Conversion.bytesToInts(src,15,dst,16, 1));
        assertArrayEquals(new int[] {0x01234567,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0xE6234567},Conversion.bytesToInts(src,15,dst,23, 1));
        assertArrayEquals(new int[] {0x012345E6,0x01234567, 0x89ABCDEF,0xC539275B,0xE64A8CE6, 0xE6234567},Conversion.bytesToInts(src,15,dst, 0, 1));
    }
    public void testBytesToShorts() throws Exception{
        byte[] src = new byte[] {(byte)0x67,(byte)0x45,(byte)0x23, (byte)0x01, (byte)0xEF,(byte)0xCD, (byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27, (byte) 0x39,(byte)0xC5,(byte)0x38,(byte)0x8C, (byte)0x4A, (byte)0xE6};
        short[] dst = new short[12];
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.bytesToShorts(src, 0,dst, 0,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.bytesToShorts(src, 0,dst, 4,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 0,dst,20, 4));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 0,dst,12, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 4,dst,12, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src, 8,dst,12, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.bytesToShorts(src,15,dst,16, 1));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0xE623},Conversion.bytesToShorts(src,15,dst,23, 1));
        assertArrayEquals(new short[] {(short)0x45E6,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8CE6,(short)0xE64A,(short)0x4567,(short)0xE623},Conversion.bytesToShorts(src,15,dst, 0, 1));
    }
    public void testHexsToLongs() throws Exception{
        String src = "76543210FEDCBA98B572935C83C8A46E";
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.hexsToLongs(src, 0,dst, 0,32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.hexsToLongs(src, 0,dst, 8,32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.hexsToLongs(src, 0,dst,40, 8));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.hexsToLongs(src, 0,dst,24,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.hexsToLongs(src, 8,dst,24,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.hexsToLongs(src,16,dst,24,16));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0xE1234567E64A8C38L},Conversion.hexsToLongs(src,31,dst,47, 1));
    }
    public void testHexsToInts() throws Exception{
        String src = "76543210FEDCBA98B572935C83C8A46E";
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF,0xC539275B, 0xE64A8C38,0x00000000, 0x00000000},Conversion.hexsToInts(src, 0,dst, 0,32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x00000000},Conversion.hexsToInts(src, 0,dst, 8,32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.hexsToInts(src, 0,dst,40, 8));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x01234567,0x89ABCDEF, 0x01234567},Conversion.hexsToInts(src, 0,dst,24,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x89ABCDEF,0xC539275B, 0x01234567},Conversion.hexsToInts(src, 8,dst,24,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.hexsToInts(src,16,dst,24,16));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0xE1234567},Conversion.hexsToInts(src,31,dst,47, 1));
    }
    public void testHexsToShorts() throws Exception{
        String src = "76543210FEDCBA98B572935C83C8A46E";
        short[] dst = new short[12];
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.hexsToShorts(src, 0,dst, 0,32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.hexsToShorts(src, 0,dst, 8,32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 0,dst,40, 8));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 0,dst,24,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src, 8,dst,24,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.hexsToShorts(src,16,dst,24,16));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0xE123},Conversion.hexsToShorts(src,31,dst,47, 1));
    }
    public void testHexsToBytes() throws Exception{
        String src = "76543210FEDCBA98B572935C83C8A46E";
        byte[] dst = new byte[24];
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.hexsToBytes(src, 0,dst, 0,32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.hexsToBytes(src, 0,dst, 8,32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 0,dst,40, 8));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 0,dst,24,16));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src, 8,dst,24,16));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.hexsToBytes(src,16,dst,24,16));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0xE1},Conversion.hexsToBytes(src,31,dst,47, 1));
    }
    public void testBoolsToLongs() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};
        long[] dst = new long[3];
        assertArrayEquals(new long[] {0x89ABCDEF01234567L, 0xE64A8C38C539275BL, 0x0000000000000000L},Conversion.boolsToLongs(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x00000000E64A8C38L},Conversion.boolsToLongs(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.boolsToLongs(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x0123456789ABCDEFL, 0x0123456789ABCDEFL},Conversion.boolsToLongs(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0x89ABCDEF89ABCDEFL, 0x01234567C539275BL},Conversion.boolsToLongs(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x01234567E64A8C38L},Conversion.boolsToLongs(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new long[] {0x0123456701234567L, 0xC539275B89ABCDEFL, 0x81234567E64A8C38L},Conversion.boolsToLongs(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new long[] {0x0123456701234566L, 0xC539275B89ABCDEFL, 0x81234567E64A8C38L},Conversion.boolsToLongs(src,1*32-1,dst,0,1));
    }
    public void testBoolsToInts() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};
        int[] dst = new int[6];
        assertArrayEquals(new int[] {0x01234567,0x89ABCDEF,0xC539275B, 0xE64A8C38,0x00000000, 0x00000000},Conversion.boolsToInts(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x00000000},Conversion.boolsToInts(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.boolsToInts(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x01234567,0x89ABCDEF, 0x01234567},Conversion.boolsToInts(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0x89ABCDEF,0xC539275B, 0x01234567},Conversion.boolsToInts(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x01234567},Conversion.boolsToInts(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new int[] {0x01234567,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x81234567},Conversion.boolsToInts(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new int[] {0x01234566,0x01234567,0x89ABCDEF, 0xC539275B,0xE64A8C38, 0x81234567},Conversion.boolsToInts(src,1*32-1,dst,0,1));
    }
    public void testBoolsToShorts() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};
        short[] dst = new short[12];
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB, (short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000,(short)0x0000,(short)0x0000},Conversion.boolsToShorts(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x0000,(short)0x0000},Conversion.boolsToShorts(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123,(short)0xCDEF,(short)0x89AB,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x0123},Conversion.boolsToShorts(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new short[] {(short)0x4567,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x8123},Conversion.boolsToShorts(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new short[] {(short)0x4566,(short)0x0123,(short)0x4567,(short)0x0123, (short)0xCDEF,(short)0x89AB,(short)0x275B,(short)0xC539,(short)0x8C38,(short)0xE64A,(short)0x4567,(short)0x8123},Conversion.boolsToShorts(src,1*32-1,dst,0,1));
    }
    public void testBoolsToBytes() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};
        byte[] dst = new byte[24];
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89, (byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.boolsToBytes(src,0*32,dst,0*32,4*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00},Conversion.boolsToBytes(src,0*32,dst,1*32,4*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,0*32,dst,5*32,1*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,0*32,dst,3*32,2*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,1*32,dst,3*32,2*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01},Conversion.boolsToBytes(src,2*32,dst,3*32,2*32));
        assertArrayEquals(new byte[] {(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x81},Conversion.boolsToBytes(src,4*32-1,dst,3*64-1,1));
        assertArrayEquals(new byte[] {(byte)0x66,(byte)0x45,(byte)0x23,(byte)0x01,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x01, (byte)0xEF,(byte)0xCD,(byte)0xAB,(byte)0x89,(byte)0x5B,(byte)0x27,(byte)0x39,(byte)0xC5,(byte)0x38,(byte)0x8C,(byte)0x4A,(byte)0xE6,(byte)0x67,(byte)0x45,(byte)0x23,(byte)0x81},Conversion.boolsToBytes(src,1*32-1,dst,0,1));
    }
    public void testBoolsToHexs() throws Exception{
        //src is the conversion of new int[] {0x01234567,0x89ABCDEF,0xC539275B,0xE64A8C38};
        boolean[] src = new boolean[]{true, true, true, false, false, true, true, false, true, false, true, false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true, false, true, false, true, false, true, true, false, false, true, false, false, false, true, true, true, false, true, true, false, true, false, true, true, true, false, false, true, false, false, true, false, false, true, true, true, false, false, true, false, true, false, false, false, true, true, false, false, false, true, true, true, false, false, false, false, true, true, false, false, false, true, false, true, false, true, false, false, true, false, false, true, true, false, false, true, true, true};

        assertEquals("76543210FEDCBA98B572935C83C8A46E",Conversion.boolsToHexs(src,0*32,"00000000000000000000000000000000",0*32,4*32));
        assertEquals("000000007",Conversion.boolsToHexs(src, 0,"000000000",32, 4));
        assertEquals("000000006",Conversion.boolsToHexs(src, 4,"000000000",32, 4));
        assertEquals("000000003",Conversion.boolsToHexs(src, 1,"000000000",32, 4));
        assertEquals("000000006",Conversion.boolsToHexs(src, 1,"000000000",33, 3));
        assertEquals("00000000C",Conversion.boolsToHexs(src, 1,"000000000",34, 2));
        assertEquals("000000008",Conversion.boolsToHexs(src, 1,"000000000",35, 1));
        assertEquals("00000000B",Conversion.boolsToHexs(src, 1,"000000008",32, 3));
    }



    boolean hasMethod(String methodName){
        Method[] methods = this.getClass().getMethods();
        for(Method m:methods){
            if(m.getName().equals(methodName)) return true;
        }
        return false;
    }
    public void testTestsToCheckImplemented() throws Exception {
        if(!hasMethod("testIntsToLong")) fail("test testIntsToLong not implemented");
        if(!hasMethod("testIntsToLongs")) fail("test testIntsToLongs not implemented");
        if(!hasMethod("testShortsToLong")) fail("test testShortsToLong not implemented");
        if(!hasMethod("testShortsToLongs")) fail("test testShortsToLongs not implemented");
        if(!hasMethod("testShortsToInt")) fail("test testShortsToInt not implemented");
        if(!hasMethod("testShortsToInts")) fail("test testShortsToInts not implemented");
        if(!hasMethod("testBytesToLong")) fail("test testBytesToLong not implemented");
        if(!hasMethod("testBytesToLongs")) fail("test testBytesToLongs not implemented");
        if(!hasMethod("testBytesToInt")) fail("test testBytesToInt not implemented");
        if(!hasMethod("testBytesToInts")) fail("test testBytesToInts not implemented");
        if(!hasMethod("testBytesToShort")) fail("test testBytesToShort not implemented");
        if(!hasMethod("testBytesToShorts")) fail("test testBytesToShorts not implemented");
        if(!hasMethod("testHexsToLong")) fail("test testHexsToLong not implemented");
        if(!hasMethod("testHexsToLongs")) fail("test testHexsToLongs not implemented");
        if(!hasMethod("testHexsToInt")) fail("test testHexsToInt not implemented");
        if(!hasMethod("testHexsToInts")) fail("test testHexsToInts not implemented");
        if(!hasMethod("testHexsToShort")) fail("test testHexsToShort not implemented");
        if(!hasMethod("testHexsToShorts")) fail("test testHexsToShorts not implemented");
        if(!hasMethod("testHexsToByte")) fail("test testHexsToByte not implemented");
        if(!hasMethod("testHexsToBytes")) fail("test testHexsToBytes not implemented");
        if(!hasMethod("testBoolsToLong")) fail("test testBoolsToLong not implemented");
        if(!hasMethod("testBoolsToLongs")) fail("test testBoolsToLongs not implemented");
        if(!hasMethod("testBoolsToInt")) fail("test testBoolsToInt not implemented");
        if(!hasMethod("testBoolsToInts")) fail("test testBoolsToInts not implemented");
        if(!hasMethod("testBoolsToShort")) fail("test testBoolsToShort not implemented");
        if(!hasMethod("testBoolsToShorts")) fail("test testBoolsToShorts not implemented");
        if(!hasMethod("testBoolsToByte")) fail("test testBoolsToByte not implemented");
        if(!hasMethod("testBoolsToBytes")) fail("test testBoolsToBytes not implemented");
        if(!hasMethod("testLongToInts")) fail("test testLongToInts not implemented");
        if(!hasMethod("testLongToShorts")) fail("test testLongToShorts not implemented");
        if(!hasMethod("testIntToShorts")) fail("test testIntToShorts not implemented");
        if(!hasMethod("testLongToBytes")) fail("test testLongToBytes not implemented");
        if(!hasMethod("testIntToBytes")) fail("test testIntToBytes not implemented");
        if(!hasMethod("testShortToBytes")) fail("test testShortToBytes not implemented");
        if(!hasMethod("testLongToHexs")) fail("test testLongToHexs not implemented");
        if(!hasMethod("testIntToHexs")) fail("test testIntToHexs not implemented");
        if(!hasMethod("testShortToHexs")) fail("test testShortToHexs not implemented");
        if(!hasMethod("testByteToHexs")) fail("test testByteToHexs not implemented");
        if(!hasMethod("testLongToBools")) fail("test testLongToBools not implemented");
        if(!hasMethod("testIntToBools")) fail("test testIntToBools not implemented");
        if(!hasMethod("testShortToBools")) fail("test testShortToBools not implemented");
        if(!hasMethod("testByteToBools")) fail("test testByteToBools not implemented");
}

}
