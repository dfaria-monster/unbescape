/*
 * =============================================================================
 * 
 *   Copyright (c) 2014, The UNBESCAPE team (http://www.unbescape.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.unbescape.csv;

import java.io.IOException;
import java.io.Writer;


/**
 * <p>
 *   Utility class for performing CSV escape/unescape operations.
 * </p>
 *
 * <h4><u>Features</u></h4>
 *
 * <p>
 *   Specific features of the CSV escape/unescape operations performed by means of this class:
 * </p>
 * <ul>
 *   <li>Works according to the rules specified in RFC4180 (there is no <em>CSV standard</em> as such).</li>
 *   <li>Encloses escaped values in double-quotes (<tt>"value"</tt>) if they contain any non-alphanumeric
 *       characters.</li>
 *   <li>Escapes double-quote characters (<tt>"</tt>) by writing them twice: <tt>""</tt>.</li>
 * </ul>
 *
 * <h4><u>Input/Output</u></h4>
 *
 * <p>
 *   There are two different input/output modes that can be used in escape/unescape operations:
 * </p>
 * <ul>
 *   <li><em><tt>String</tt> input, <tt>String</tt> output</em>: Input is specified as a <tt>String</tt> object
 *       and output is returned as another. In order to improve memory performance, all escape and unescape
 *       operations <u>will return the exact same input object as output if no escape/unescape modifications
 *       are required</u>.</li>
 *   <li><em><tt>char[]</tt> input, <tt>java.io.Writer</tt> output</em>: Input will be read from a char array
 *       (<tt>char[]</tt>) and output will be written into the specified <tt>java.io.Writer</tt>.
 *       Two <tt>int</tt> arguments called <tt>offset</tt> and <tt>len</tt> will be
 *       used for specifying the part of the <tt>char[]</tt> that should be escaped/unescaped. These methods
 *       should be called with <tt>offset = 0</tt> and <tt>len = text.length</tt> in order to process
 *       the whole <tt>char[]</tt>.</li>
 * </ul>
 *
 * <h4><u>Specific instructions for Microsoft Excel-compatible files</u></h4>
 *
 * <p>
 *   In order for Microsoft Excel to correcly open a CSV file &mdash;including field values with line
 *   breaks&mdash; these rules should be followed:
 * </p>
 * <ul>
 *   <li>Separate fields with comma (<tt>,</tt>) in English-language setups, and semi-colon (<tt>;</tt>) in
 *       non-English-language setups (this depends on the language of the installation of MS Excel you intend
 *       your files to be open in).</li>
 *   <li>Separate records with Windows-style line breaks
 *       (<tt>&#92;r&#92;n</tt>, <tt>U+000D</tt> + <tt>U+000A</tt>).</li>
 *   <li>Enclose field values in double-quotes (<tt>"</tt>) if they contain any non-alphanumeric characters.</li>
 *   <li>Don't leave any whitespace between the field separator (<tt>;</tt>) and the enclosing quotes
 *       (<tt>"</tt>).</li>
 *   <li>Escape double-quote characters (<tt>"</tt>) inside field values with two double-quotes (<tt>""</tt>).</li>
 *   <li>Use <tt>&#92;n</tt> (<tt>U+000A</tt>, unix-style line breaks) for line breaks inside field values,
 *       even if records are separated with Windows-style line breaks (<tt>&#92;r&#92;n</tt>)
 *       [ EXCEL 2003 compatibility ].</li>
 *   <li>Open CSV files in Excel with <tt>File -> Open...</tt>, not with <tt>Data -> Import...</tt>
 *       The latter option will not correctly understand line breaks inside field values (up to Excel 2010).</li>
 * </ul>
 * <p>
 *   <em>(Note unbescape will perform escaping of field values only, so it will take care of enclosing in
 *   double-quotes, using unix-style line breaks inside values, etc. But separating fields (e.g. with <tt>;</tt>),
 *   delimiting records (e.g. with <tt>\r\n</tt>) and using the correct character encoding when writing CSV files
 *   will be the responsibility of the application calling unbescape.)</em>
 * </p>
 * <p>
 *   The described format for Excel is also supported by OpenOffice.org Calc (<tt>File -> Open...</tt>) and also
 *   Google Spreadsheets (<tt>File -> Import...</tt>)
 * </p>
 *
 * <h4><u>References</u></h4>
 *
 * <p>
 *   The following references apply:
 * </p>
 * <ul>
 *   <li><a href="http://tools.ietf.org/html/rfc4180" target="_blank">RFC4180: Common Format and MIME Type
 *       for Comma-Separated Values (CSV) files</a> [ietf.org]</li>
 *   <li><a href="http://en.wikipedia.org/wiki/Comma-separated_values" target="_blank">Comma-Separated Values</a>
 *       [wikipedia.org]</li>
 *   <li><a href="http://creativyst.com/Doc/Articles/CSV/CSV01.htm" target="_blank">How to: The Comma Separated Value
 *       (CSV) File Format - Create or parse data in this popular pseudo-standard format</a> [creativyst.com]</li>
 * </ul>
 *
 *
 * @author Daniel Fern&aacute;ndez
 *
 * @since 1.0.0
 *
 */
public final class CsvEscape {


    /**
     * <p>
     *   Perform a CSV <strong>escape</strong> operation on a <tt>String</tt> input.
     * </p>
     * <p>
     *   This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text the <tt>String</tt> to be escaped.
     * @return The escaped result <tt>String</tt>. As a memory-performance improvement, will return the exact
     *         same object as the <tt>text</tt> input argument if no escaping modifications were required (and
     *         no additional <tt>String</tt> objects will be created during processing). Will
     *         return <tt>null</tt> if <tt>text</tt> is <tt>null</tt>.
     */
    public static String escapeCsv(final String text) {
        return CsvEscapeUtil.escape(text);
    }






    /**
     * <p>
     *   Perform a CSV <strong>escape</strong> operation on a <tt>char[]</tt> input.
     * </p>
     * <p>
     *   This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text the <tt>char[]</tt> to be escaped.
     * @param offset the position in <tt>text</tt> at which the escape operation should start.
     * @param len the number of characters in <tt>text</tt> that should be escaped.
     * @param writer the <tt>java.io.Writer</tt> to which the escaped result will be written. Nothing will
     *               be written at all to this writer if <tt>text</tt> is <tt>null</tt>.
     */
    public static void escapeCsv(final char[] text, final int offset, final int len, final Writer writer)
                                  throws IOException {

        if (writer == null) {
            throw new IllegalArgumentException("Argument 'writer' cannot be null");
        }

        final int textLen = (text == null? 0 : text.length);

        if (offset < 0 || offset > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        if (len < 0 || (offset + len) > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        CsvEscapeUtil.escape(text, offset, len, writer);

    }








    /**
     * <p>
     *   Perform a CSV <strong>unescape</strong> operation on a <tt>String</tt> input.
     * </p>
     * <p>
     *   This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text the <tt>String</tt> to be unescaped.
     * @return The unescaped result <tt>String</tt>. As a memory-performance improvement, will return the exact
     *         same object as the <tt>text</tt> input argument if no unescaping modifications were required (and
     *         no additional <tt>String</tt> objects will be created during processing). Will
     *         return <tt>null</tt> if <tt>text</tt> is <tt>null</tt>.
     */
    public static String unescapeCsv(final String text) {
        return CsvEscapeUtil.unescape(text);
    }


    /**
     * <p>
     *   Perform a CSV <strong>unescape</strong> operation on a <tt>char[]</tt> input.
     * </p>
     * <p>
     *   This method is <strong>thread-safe</strong>.
     * </p>
     *
     * @param text the <tt>char[]</tt> to be unescaped.
     * @param offset the position in <tt>text</tt> at which the unescape operation should start.
     * @param len the number of characters in <tt>text</tt> that should be unescaped.
     * @param writer the <tt>java.io.Writer</tt> to which the unescaped result will be written. Nothing will
     *               be written at all to this writer if <tt>text</tt> is <tt>null</tt>.
     */
    public static void unescapeCsv(final char[] text, final int offset, final int len, final Writer writer)
                                    throws IOException{
        if (writer == null) {
            throw new IllegalArgumentException("Argument 'writer' cannot be null");
        }

        final int textLen = (text == null? 0 : text.length);

        if (offset < 0 || offset > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        if (len < 0 || (offset + len) > textLen) {
            throw new IllegalArgumentException(
                    "Invalid (offset, len). offset=" + offset + ", len=" + len + ", text.length=" + textLen);
        }

        CsvEscapeUtil.unescape(text, offset, len, writer);

    }




    private CsvEscape() {
        super();
    }


}

