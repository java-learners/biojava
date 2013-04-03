/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on DATE
 *
 */
package org.biojava3.core.sequence;

import java.util.logging.Logger;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.CompoundSet;

/**
 *
 * @author Scooter Willis
 */
public class CDSSequence extends DNASequence {

    private static final Logger log = Logger.getLogger(CDSSequence.class.getName());
    Integer phase = 0; // 0, 1, 2 http://www.sequenceontology.org/gff3.shtml
    TranscriptSequence parentTranscriptSequence;

    public CDSSequence(TranscriptSequence parentSequence, int begin, int end, int phase) {
        parentTranscriptSequence = parentSequence;
        this.setParentSequence(parentTranscriptSequence);
        setBioBegin(begin);
        setBioEnd(end);
        this.phase = phase;

    }

    public Integer getPhase(){
        return phase;
    }

    public Strand getStrand() {
        return parentTranscriptSequence.getStrand();
    }



    /**
     * http://www.sequenceontology.org/gff3.shtml
     * http://biowiki.org/~yam/bioe131/GFF.ppt
     * @return
     */
    public String getCodingSequence() {
        String sequence = this.getSequenceAsString(getBioBegin(),getBioEnd(),getStrand());
        if (getStrand() == Strand.NEGATIVE) {
            //need to take complement of sequence because it is negative and we are returning a coding sequence
            StringBuilder b = new StringBuilder(getLength());
            CompoundSet<NucleotideCompound> compoundSet = this.getCompoundSet();
            for (int i = 0; i < sequence.length(); i++) {
                String nucleotide = sequence.charAt(i) + "";
                NucleotideCompound nucleotideCompound = compoundSet.getCompoundForString(nucleotide);
                b.append(nucleotideCompound.getComplement().getShortName());
            }
            sequence = b.toString();
        }
        //  sequence = sequence.substring(phase);
        return sequence;
    }

}