package eu.interopehrate.pseudoidgenerator.PseudoIdGenerator;


import javax.persistence.*;

@Entity(name = "pseudo_identity")
public class PseudoIdentity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String prefix;

    private int incremental_number;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getIncrementalNumber() {
        return incremental_number;
    }

    public void setIncrementalNumber(int incremental_number) {
        this.incremental_number = incremental_number;
    }

}
